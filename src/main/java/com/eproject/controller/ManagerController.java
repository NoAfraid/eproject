package com.eproject.controller;

import com.alibaba.fastjson.JSONObject;
import com.eproject.entity.Manager;
import com.eproject.service.ManagerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@ResponseBody
@RequestMapping(method= RequestMethod.POST, value = "/manager",produces = "application/json;charset=UTF-8")
public class ManagerController {

    @Resource
    private ManagerService managerService;

    @RequestMapping("/login")
    public String login(@RequestParam("userName") String userName,
                        @RequestParam("password") String password){
        Manager login = managerService.login(userName, password);
        JSONObject result = new JSONObject();
        if (login==null){
            result.put("error","-1");
        }else {
            result.put("登录成功",1);
        }
        return result.toJSONString();
    }
}
