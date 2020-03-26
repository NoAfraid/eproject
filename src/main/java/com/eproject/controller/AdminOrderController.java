package com.eproject.controller;

import com.eproject.common.PageQuery;
import com.eproject.common.R;
import com.eproject.domain.OrderDeliveryParam;
import com.eproject.service.AdminOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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
    public R getList(@RequestParam Map<String, Object> result){
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
     * 批量发货
     *
     * */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/delivery",produces = "application/json;charset=UTF-8")
    public R delivery(@RequestBody List<OrderDeliveryParam> deliveryParamList){
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
    public R closeOrder(@RequestParam("id") List<Integer> id){
        int count = adminOrderService.closeOrder(id);
        if (count > 0){
            return R.ok("关闭成功");
        }
        return R.error(-1,"关闭失败");
    }

    /**
     * 批量关闭订单
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/deleteOrder",produces = "application/json;charset=UTF-8")
    public R deleteOrder(@RequestParam("id") List<Integer> id){
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
