package com.eproject.controller;

import com.eproject.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@ResponseBody
@RequestMapping(method= RequestMethod.POST, value = "/order",produces = "application/json;charset=UTF-8")
public class OrderController {

    @Resource
    private OrderService orderService;


}
