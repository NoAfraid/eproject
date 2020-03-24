package com.eproject.controller;

import com.eproject.common.R;
import com.eproject.domain.ConfirmOrderResult;
import com.eproject.entity.Cart;
import com.eproject.entity.Order;
import com.eproject.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
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
     * 根据提交信息生成订单
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/generateOrder",produces = "application/json;charset=UTF-8")
    public R generateOrder(@RequestBody Order order){
        Map<String, Object> result = orderService.generateOrder(order);
        return R.ok().put("data", result);
    }
}
