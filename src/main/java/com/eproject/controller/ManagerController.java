package com.eproject.controller;

import com.eproject.common.R;
import com.eproject.entity.Manager;
import com.eproject.service.ManagerService;
import com.eproject.util.MD5Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Controller
@ResponseBody
@RequestMapping(method = RequestMethod.POST, value = "/manager", produces = "application/json;charset=UTF-8")
public class ManagerController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    private ManagerService managerService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/login", produces = "application/json;charset=UTF-8")
    public R login(@RequestBody Manager manager, @RequestParam("verifyCode") String verifyCode,
                   HttpSession session) {

        if (StringUtils.isEmpty(verifyCode)) {
            return R.error(-1,"验证码不能为空");
        }
        String kaptchaCode = session.getAttribute("verifyCode") + "";
        if (StringUtils.isEmpty(kaptchaCode) || !verifyCode.equals(kaptchaCode)) {
            return R.error(-1, "验证码错误");
        }
        Manager login = managerService.login(manager.getLoginName(), manager.getPassword());
//        JSONObject result = new JSONObject();
        if (login == null) {
            return R.error("登录失败");
        }
        //生成登录的token信息
        String token = null;
        try {
            token = MD5Encode.EncoderByMd5(UUID.randomUUID().toString() + System.currentTimeMillis());
            log.info("======登录token：{}",token);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            log.info("======生成token失败");
            return R.error(-1, "登录失败,生成token失败");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            log.info("======生成token失败");
            return R.error(-1, "登录失败,生成token失败");
        }
        return R.ok().put("code", 0).put("data",login).put("accessToken", token);
    }
}
