package com.eproject.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.eproject.common.Contants;
import com.eproject.common.PageQuery;
import com.eproject.common.R;
import com.eproject.common.Result;
import com.eproject.config.AlipayConfig;
import com.eproject.domain.ConfirmOrderResult;
import com.eproject.domain.ReceiverInfoParam;
import com.eproject.entity.*;
import com.eproject.service.AlipayService;
import com.eproject.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@ResponseBody
@RequestMapping(method = RequestMethod.POST, value = "/order", produces = "application/json;charset=UTF-8")
public class OrderController {

    @Resource
    private OrderService orderService;

    @Resource
    private AlipayService alipayService;

    /**
     * 根据用户购物车信息生成确认单信息
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/generateConfirmOrder", produces = "application/json;charset=UTF-8")
    public R<ConfirmOrderResult> generateConfirmOrder(@RequestBody Cart cart) {
        ConfirmOrderResult confirmOrderResult = orderService.generateConfirmOrder();
        return R.ok().put("data", confirmOrderResult);
    }

    /**
     * 根据订单单号获取购物信息
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/getOrderByOrderNo", produces = "application/json;charset=UTF-8")
    public R getOrderByOrderNo(@RequestBody Order order) {
        List<Order> result = orderService.getOrderByOrderNo(order.getOrderNo());
        List<OrderItem> orderItemList = orderService.getByOrderNo(order.getOrderNo());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", result);
        map.put("orderItemList", orderItemList);
        return R.ok().put("data", map);
    }

    /**
     * 根据提交信息生成订单
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/generateOrder", produces = "application/json;charset=UTF-8")
    public R generateOrder(@RequestBody Order order) {
        Map<String, Object> result = orderService.generateOrder(order);
        return R.ok().put("data", result);
    }

    /**
     * 获取用户个人订单列表
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/getOrderInfo", produces = "application/json;charset=UTF-8")
    public R getOrderInfo(@RequestBody Map<String, Object> result) {
        try {
            if (StringUtils.isEmpty(result.get("page")) || StringUtils.isEmpty(result.get("limit"))) {
                return R.error(-1, "请求错误");
            }
            PageQuery pageQuery = new PageQuery(result);
            return R.ok().put("data", orderService.getMyOrders(pageQuery));
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(-405, "查询订单列表失败，msg:" + e.getMessage());
        }
    }

    /**
     * 修改订单地址信息
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/update/receiverInfo", produces = "application/json;charset=UTF-8")
    public R updateReceiverInfo(@RequestBody ReceiverInfoParam infoParam) {
        //, @RequestParam("orderId") Integer orderId
        int count = orderService.updateReceiverInfo(infoParam);
        if (count > 0) {
            return R.ok();
        }
        return R.error(-1, "修改错误");
    }

    /**
     * 自动取消超时订单(未测试)
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/cancelTimeOutOrder", produces = "application/json;charset=UTF-8")
    public R cancelTimeOutOrder() {
        orderService.cancelTimeOutOrder();
        return R.ok();
    }

    /**
     * 手动取消订单
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/cancelOrder", produces = "application/json;charset=UTF-8")
    public R cancelOrder(@RequestBody Order order, HttpSession httpSession) {
        //获取session中的user
        User user = (User) httpSession.getAttribute(Contants.MALL_USER_SESSION_KEY);
        //测试用的Order中的user_id,实际用的是user.getId()
        String cancelOrderResult = orderService.cancelOrder(order.getOrderNo(), order.getUserId());
        if (Result.SUCCESS.getResult().equals(cancelOrderResult)) {
            return R.ok("已取消");
        }
        return R.error(-1, "取消错误");
    }

    /**
     * 去支付
     */
//    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/pay")
    public void pay(@RequestParam("orderNo") String orderNo,HttpServletRequest request, HttpServletResponse response) throws AlipayApiException, IOException {
        Order o = orderService.pay(orderNo);
        if (o != null) {
            //获得初始化的AlipayClient
            AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.APP_ID, AlipayConfig.APP_PRIVATE_KEY, "json", AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.sign_type);
            //商户订单号，商户网站订单系统中唯一订单号，必填
            String out_trade_no = o.getOrderNo();
            //付款金额，必填
            BigDecimal total_amount = o.getTotalPrice();
            //订单名称，必填
            String subject = o.getOrderNo();
            //商品描述，可空
            String body = o.getProductName();
            //订单超时时间
            String timeout_express = "90m";
            //设置请求参数
            Alipay alipay = new Alipay();
            alipay.setOrderId(o.getId());
            alipay.setOrderNo(orderNo);
            alipay.setUserId(o.getUserId());
            alipay.setProductId(o.getProductId());
            alipay.setTotalPrice(o.getTotalPrice());
            AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
            alipayRequest.setReturnUrl(AlipayConfig.return_url);
            alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"timeout_express\":\"90m\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
//            alipayRequest.setBizContent(JSON.toJSONString("{\"out_trade_no\":\"" + out_trade_no + "\","
//                    + "\"total_amount\":\"" + total_amount + "\","
//                    + "\"subject\":\"" + subject + "\","
//                    + "\"body\":\"" + body + "\","
//                    + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}"));
//            String form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
//        Integer count = orderService.paySuccess(o.getOrderNo(),o.getPayType());
            //请求
            String form = "";
            try {
                form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
            } catch (AlipayApiException e) {
                e.printStackTrace();
            }
            response.setContentType("text/html;charset=" + AlipayConfig.CHARSET);
            try {
                response.getWriter().write(form);//直接将完整的表单html输出到页面
            } catch (IOException e) {
                e.printStackTrace();
            }
            response.getWriter().flush();
            response.getWriter().close();
            try {
                alipayService.webPagePay(alipay);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Integer count = orderService.paySuccess(o.getOrderNo(), o.getPayType());
//            return form;
//            return R.ok().put("data", form);
        }
//        return R.error(-1, "参数异常");
//        return null;
    }

    /**
     * 支付成功后的回调
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/paySuccess", produces = "application/json;charset=UTF-8")
    public R paySuccess(@RequestBody Order o, HttpServletRequest request, HttpServletResponse response) throws IOException, AlipayApiException {
        Integer count = orderService.paySuccess(o.getOrderNo(), o.getPayType());
        return R.ok().put("data", count);
    }

    @ApiOperation("支付异步通知接口")
    @GetMapping("alipay/notify_url")
    public String notifyAlipay() {
        return " a li pay notify ";
    }

    @ApiOperation("支付完成以后的回调接口")
    @GetMapping("alipay/return_url")
    public String returnAlipay() {
        return " a li pay return ";
    }

    /**
     * 退款
     * @throws AlipayApiException
     */
    @GetMapping("alipayRefundOrder")
    public R alipayRefund(
            @RequestParam("orderNo")String orderNo,
            @RequestParam(value = "refundReason", required = false)String refundReason,
            @RequestParam(value = "refundAmount", required = false)BigDecimal refundAmount
    ) throws AlipayApiException{

        /** 调取数据*/
//        String outTradeNo = "15382028806591197";
        String outRequestNo = orderNo;
        //refundAmount = 1;
        //outRequestNo = "22";
//        Integer outRequestNum = Integer.valueOf(outRequestNo);
        String refund = alipayService.refund(orderNo, refundReason, refundAmount, outRequestNo);
        return R.ok().put("data",refund);
    }

    /**
     * 退款查询
     * @throws AlipayApiException
     */
    @PostMapping("refundQuery")
    public R refundQuery() throws AlipayApiException{

        /** 调取数据*/
        String outTradeNo = "13123";
        String outRequestNo = "2";

        String refund = alipayService.refundQuery(outTradeNo, outRequestNo);

        return R.ok().put("data",refund);

    }

    /**
     * 确认订单
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/finishOrder", produces = "application/json;charset=UTF-8")
    public R finishOrder(@RequestBody Order o, HttpSession httpSession) {
        //获取session中的user
        //User user =  (User) httpSession.getAttribute(Contants.MALL_USER_SESSION_KEY);
        //测试用的Order中的user_id,实际用的是user.getId()
        String cancelOrderResult = orderService.finishOrder(o.getOrderNo(), o.getUserId());
        if (Result.SUCCESS.getResult().equals(cancelOrderResult)) {
            return R.ok("确认收货");
        }
        return R.error(-1, "确认错误");
    }

    /**
     * 根据id获取商品信息
     */
    @RequestMapping(value = "/get", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public R getOrderInfo(HttpServletRequest request, @RequestParam("id") Integer id) {
        Order info = orderService.getOrderById(id);
        if (info != null) {
            return R.ok().put("data", info);
        }
        return R.error("查询错误");
    }
}
