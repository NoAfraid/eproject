package com.eproject.controller;

import com.eproject.common.Contants;
import com.eproject.common.R;
import com.eproject.common.Result;
import com.eproject.entity.User;
import com.eproject.service.UserService;
import com.eproject.util.MD5Encode;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@ResponseBody
@RequestMapping(method= RequestMethod.POST, value = "/user",produces = "application/json;charset=UTF-8")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 注册方法
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/registerUser",produces = "application/json;charset=UTF-8")
    public R registUser(@RequestBody  User user,

                        HttpSession httpSession){
        // @RequestParam("verifyCode") String verifyCode, 验证码
        if (StringUtils.isEmpty(user.getUsername())){
            return R.error(-1,"请输入用户名");
        }
        if (StringUtils.isEmpty(user.getPassword())){
            return R.error(-1,"请输入密码");
        }
        if (StringUtils.isEmpty(user.getPhone())){
            return R.error(-1,"请输入手机号");
        }
//        if (StringUtils.isEmpty(verifyCode)){
//            return R.error(-1,"请输入验证码");
//        }
//        String kaptchaCode = httpSession.getAttribute(Contants.MALL_VERIFY_CODE_KEY) + "";
        String registerResult = userService.register(user);
        // 返回注册成功的信息
        if (Result.SUCCESS.getResult().equals(registerResult)){
            return R.ok().put("注册成功","success");
        } else {
            return R.error(-1,"用户名已存在");
        }
    }

    /**
     * 登录
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/loginUser",produces = "application/json;charset=UTF-8")
    public R loginUser(@RequestBody  User user){
        User loginUser = userService.loginUser(user);
        //loginUser.getUsername().equals(user.getUsername()) ||loginUser.getPhone().equals(user.getPhone()
        if (loginUser != null){
            if (loginUser.getPassword().equals(MD5Encode.MD5Encode(user.getPassword(),"utf-8"))){
                return R.ok().put("登录成功",1);
            }
        }
        return R.error(-1,"账号或者密码错误");
    }
    /**
     * 获取用户信息
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/selectInfo",produces = "application/json;charset=UTF-8")
    public R selectInfo(@RequestBody User userInfo){
        List<User>  info = userService.selectUserInfo(userInfo);
        if (info.size() <= 0){
            return R.error(-1,"查询异常");
        }
        return R.ok().put("data",info);
    }

    /**
     * 修改用户信息
     *  并且返回最新的用户信息
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/updateUserInfo",produces = "application/json;charset=UTF-8")
    public R updateInfo(@RequestBody User updateInfo){
        List<User>  info = userService.updateUserInfo(updateInfo);
        if (info.size() <= 0){
            return R.error(-1,"修改异常");
        }
        return R.ok().put("data",info);
    }
    /**
     * 修改密码
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/updatePassword/{id}",produces = "application/json;charset=UTF-8")
    public R updatePassword(@RequestParam("originalPassword") String originalPassword,
                            @RequestParam("newPassword") String newPassword,
                            @PathVariable("id") Integer id,
                            HttpServletRequest request){
        if (StringUtils.isEmpty(originalPassword)){
            return R.error(-1,"原密码不能为空");
        }
        if (StringUtils.isEmpty(newPassword)){
            return R.error(-1,"新密码不能为空");
        }
        //confirmPassword.equals(newPassword) 两次密码是否相同
        //获取登录名的ID
//        Integer loginUserId = (int) request.getSession().getAttribute("loginUserId");
        userService.updatePassword(id,originalPassword,newPassword);
        return R.ok().put("修改成功",1);
    }

    /**
     * 修改用户头像
     */

    /**
     * 用户收藏产品
     */

    /**
     * 用户的关注：人和产品
     */

    /**
     * 刷新token（有时间就做）
     */
}
