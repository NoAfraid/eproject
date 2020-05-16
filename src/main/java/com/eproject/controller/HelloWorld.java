package com.eproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HelloWorld {
    @RequestMapping("/index")
//    @ResponseBody
    public String hello(){
        return "alipay";
    }

    @RequestMapping("/alipay")
    public String toTest(){
        return "alipay";
    }

    @RequestMapping("/categories")
    public String categoriesPage(HttpServletRequest request, @RequestParam("categoryLevel") Integer categoryLevel,
                                 @RequestParam("parentId") Integer parentId,
                                 @RequestParam("backParentId") Integer backParentId) {
        if (categoryLevel == null || categoryLevel < 1 || categoryLevel > 3) {
            return "error/error_5xx";
        }
//        request.setAttribute("path", "newbee_mall_category");
        request.setAttribute("parentId", parentId);
        request.setAttribute("backParentId", backParentId);
        request.setAttribute("categoryLevel", categoryLevel);
        return "categorys";
    }
}

