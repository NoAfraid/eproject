package com.eproject.controller;

import com.eproject.common.PageQuery;
import com.eproject.common.R;
import com.eproject.domain.OrderDeliveryParam;
import com.eproject.domain.ReceiverInfoParam;
import com.eproject.entity.Order;
import com.eproject.entity.Product;
import com.eproject.service.AdminOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.util.*;

@Controller
@ResponseBody
@RequestMapping(method= RequestMethod.POST, value = "/adminOrder",produces = "application/json;charset=UTF-8")
public class AdminOrderController {

    @Resource
    private AdminOrderService adminOrderService;

    /**
     * 获取订单详情
     *
     * */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/getList",produces = "application/json;charset=UTF-8")
    public R getList(@RequestBody Map<String, Object> result){
        try {
            if (StringUtils.isEmpty(result.get("page")) || StringUtils.isEmpty(result.get("limit"))) {
                return R.error(-1, "请求错误");
            }
            PageQuery pageQuery = new PageQuery(result);
            return R.ok().put("data", adminOrderService.orderItmList(pageQuery));
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(-405, "查询订单列表失败，msg:" + e.getMessage());
        }
    }

    /**
     * 根据id获取商品信息
     */
    @RequestMapping(value = "/get", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public R getProductInfo(HttpServletRequest request, @RequestParam("id") Integer id){
        Order info = adminOrderService.getOrderById(id);
        if (info != null){
            return R.ok().put("data",info);
        }
        return R.error("查询错误");
    }

    /**
     * 修改订单信息
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/updateAddress",produces = "application/json;charset=UTF-8")
    public R updateReceiverInfo(@RequestBody Order order){
        //, @RequestParam("orderId") Integer orderId
        int count = adminOrderService.updateReceiverDetailAddress(order);
        if (count > 0){
            return R.ok("修改成功");
        }
        return R.error(-1,"修改错误");
    }

    /**
     * 批量发货
     *
     * */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/checkDone",produces = "application/json;charset=UTF-8")
    public R checkDone(@RequestBody String [] ids){
        List<String> orderId = new ArrayList<String>(ids.length);
        Collections.addAll(orderId, ids);
        System.out.println(orderId);
        int count = adminOrderService.checkDone(ids);
        if (count > 0){
            return R.ok("配货成功");
        }
        return R.error(-1,"配货失败:商品未支付或已关闭");
    }

    /**
     * 批量发货
     *
     * */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/delivery",produces = "application/json;charset=UTF-8")
    public R delivery(@RequestBody List<OrderDeliveryParam> deliveryParamList){
        //@RequestBody Integer[] ids
        int count = adminOrderService.delivery(deliveryParamList);
        if (count > 0){
            return R.ok("发货成功");
        }
        return R.error(-1,"发货失败");
    }

    /**
     * 批量关闭订单
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/closeOrder",produces = "application/json;charset=UTF-8")
    public R closeOrder(@RequestBody Integer[] id){
        int count = adminOrderService.closeOrder(id);
        if (count > 0){
            return R.ok("关闭成功");
        }
        return R.error(-1,"关闭失败");
    }

    /**
     * 批量删除订单
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/deleteOrder",produces = "application/json;charset=UTF-8")
    public R deleteOrder(@RequestBody Integer[] id){
        int count = adminOrderService.delete(id);
        if (count > 0){
            return R.ok("删除成功");
        }
        return R.error(-1,"删除失败");
    }

    /**
     * 模糊查询订单信息
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/searchOrder",produces = "application/json;charset=UTF-8")
    public R searchOrder(@RequestBody Map<String, Object> params){
        if (StringUtils.isEmpty(params.get("page"))){
            params.put("page",1);
        }
        String orderNo = "";
        String productId = "";
        String userId = "";
        String id = "";
        //对keyword做去空格处理
        if (params.containsKey("orderNo") && !StringUtils.isEmpty((params.get("orderNo") + "").trim())) {
            orderNo = params.get("orderNo") + "";
        }
        params.put("orderNo",orderNo);

        if (params.containsKey("productId") && !StringUtils.isEmpty((params.get("productId") + "").trim())) {
            productId = params.get("productId") + "";
        }
        params.put("productId",productId);

        if (params.containsKey("userId") && !StringUtils.isEmpty((params.get("userId") + "").trim())) {
            userId = params.get("userId") + "";
        }
        params.put("userId",userId);

        if (params.containsKey("id") && !StringUtils.isEmpty((params.get("id") + "").trim())) {
            userId = params.get("id") + "";
        }
        params.put("id",id);
        //封装商品数据
        PageQuery pageQuery = new PageQuery(params);
        return R.ok().put("data",adminOrderService.selectItemInfo(pageQuery));
    }
}
