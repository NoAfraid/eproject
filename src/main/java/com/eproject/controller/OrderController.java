package com.eproject.controller;

import com.eproject.common.Contants;
import com.eproject.common.PageQuery;
import com.eproject.common.R;
import com.eproject.common.Result;
import com.eproject.domain.ConfirmOrderResult;
import com.eproject.domain.ReceiverInfoParam;
import com.eproject.entity.*;
import com.eproject.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@ResponseBody
@RequestMapping(method= RequestMethod.POST, value = "/order",produces = "application/json;charset=UTF-8")
public class OrderController {

    @Resource
    private OrderService orderService;

    /**
     * 根据用户购物车信息生成确认单信息
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/generateConfirmOrder",produces = "application/json;charset=UTF-8")
    public R<ConfirmOrderResult> generateConfirmOrder(@RequestBody Cart cart){
        ConfirmOrderResult confirmOrderResult = orderService.generateConfirmOrder();
        return R.ok().put("data", confirmOrderResult);
    }

    /**
     * 根据订单单号获取购物信息
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/getOrderByOrderNo",produces = "application/json;charset=UTF-8")
    public R getOrderByOrderNo(@RequestBody Order order){
        List<Order> result = orderService.getOrderByOrderNo(order.getOrderNo());
        List<OrderItem> orderItemList = orderService.getByOrderNo(order.getOrderNo());
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("result",result);
        map.put("orderItemList",orderItemList);
        return R.ok().put("data", map);
    }
    /**
     * 根据提交信息生成订单
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/generateOrder",produces = "application/json;charset=UTF-8")
    public R generateOrder(@RequestBody Order order){
        Map<String, Object> result = orderService.generateOrder(order);
        return R.ok().put("data", result);
    }

    /**
     * 获取用户个人订单列表
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/getOrderInfo",produces = "application/json;charset=UTF-8")
    public R getOrderInfo(@RequestBody Map<String, Object> result){
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
    @RequestMapping(method= RequestMethod.POST, value = "/update/receiverInfo",produces = "application/json;charset=UTF-8")
    public R updateReceiverInfo(@RequestBody ReceiverInfoParam infoParam){
        //, @RequestParam("orderId") Integer orderId
        int count = orderService.updateReceiverInfo(infoParam);
        if (count > 0){
            return R.ok();
        }
        return R.error(-1,"修改错误");
    }

    /**
     * 自动取消超时订单(未测试)
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/cancelTimeOutOrder",produces = "application/json;charset=UTF-8")
    public R cancelTimeOutOrder(){
        orderService.cancelTimeOutOrder();
        return R.ok();
    }

    /**
     * 手动取消订单
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/cancelOrder",produces = "application/json;charset=UTF-8")
    public R cancelOrder(@RequestBody Order order, HttpSession httpSession){
        //获取session中的user
        User user =  (User) httpSession.getAttribute(Contants.MALL_USER_SESSION_KEY);
        //测试用的Order中的user_id,实际用的是user.getId()
        String cancelOrderResult = orderService.cancelOrder(order.getOrderNo(),order.getUserId());
        if (Result.SUCCESS.getResult().equals(cancelOrderResult)){
            return R.ok("已取消");
        }
        return R.error(-1,"取消错误");
    }

    /**
     * 去支付
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/pay",produces = "application/json;charset=UTF-8")
    public R pay(@RequestBody Order order){
        Order o = orderService.pay(order.getOrderNo());
        if (o != null){
            return R.ok().put("date",o);
        }
        return R.error(-1,"参数异常");
    }
    /**
     * 支付成功后的回调
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/paySuccess",produces = "application/json;charset=UTF-8")
    public R paySuccess(@RequestBody Order o){
        Integer count = orderService.paySuccess(o.getOrderNo(),o.getPayType());
        if (count > 0){
            return R.ok("支付成功");
        }
        return R.error(-1,"支付失败");
    }

    /**
     * 确认订单
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/finishOrder",produces = "application/json;charset=UTF-8")
    public R finishOrder(@RequestBody Order o,HttpSession httpSession){
        //获取session中的user
        //User user =  (User) httpSession.getAttribute(Contants.MALL_USER_SESSION_KEY);
        //测试用的Order中的user_id,实际用的是user.getId()
        String cancelOrderResult = orderService.finishOrder(o.getOrderNo(),o.getUserId());
        if (Result.SUCCESS.getResult().equals(cancelOrderResult)){
            return R.ok("确认收货");
        }
        return R.error(-1,"确认错误");
    }
}
