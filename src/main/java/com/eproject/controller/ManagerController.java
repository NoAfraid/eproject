package com.eproject.controller;

import com.eproject.common.R;
import com.eproject.entity.Manager;
import com.eproject.service.ManagerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@ResponseBody
@RequestMapping(method= RequestMethod.POST, value = "/manager",produces = "application/json;charset=UTF-8")
public class ManagerController {

    @Resource
    private ManagerService managerService;

//    @ResponseBody
@RequestMapping(method= RequestMethod.POST, value = "/login",produces = "application/json;charset=UTF-8")
    public R login(@RequestBody Manager manager){
        Manager login = managerService.login(manager.getLoginName(),manager.getPassword());
//        JSONObject result = new JSONObject();
        if (login==null){
            return R.error("登录失败");
        }
    return R.ok("登录成功");
    }
}
