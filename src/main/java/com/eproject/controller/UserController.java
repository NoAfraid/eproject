package com.eproject.controller;

import com.eproject.annotation.LoginUser;
import com.eproject.common.Contants;
import com.eproject.common.PageQuery;
import com.eproject.common.R;
import com.eproject.common.Result;
import com.eproject.entity.Collect;
import com.eproject.entity.Follow;
import com.eproject.entity.Product;
import com.eproject.entity.User;
import com.eproject.service.ProductService;
import com.eproject.service.UserService;
import com.eproject.util.MD5Encode;
import com.eproject.util.PasswordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Controller
@ResponseBody
@RequestMapping(method= RequestMethod.POST, value = "/user",produces = "application/json;charset=UTF-8")
public class UserController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    private UserService userService;

    @Resource
    private ProductService productService;

    /**
     * 注册方法
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/registerUser",produces = "application/json;charset=UTF-8")
    public R registUser(@RequestBody  User user){
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
        String registerResult = userService.register(user);
        // 返回注册成功的信息
        if (Result.SUCCESS.getResult().equals(registerResult)){
            return R.ok().put("data","注册成功");
        } else {
            return R.error(-1,"用户名已存在");
        }
    }

    /**
     * 登录
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/login",produces = "application/json;charset=UTF-8")
    public R loginUser(@RequestBody  User user,@RequestParam("verifyCode") String verifyCode,
                       HttpSession session){
        if (StringUtils.isEmpty(verifyCode)) {
            return R.error(-1,"验证码不能为空");
        }
        String kaptchaCode = session.getAttribute("MallVerifyCode") + "";
        if (StringUtils.isEmpty(kaptchaCode) || !verifyCode.equals(kaptchaCode)) {
            return R.error(-3, "验证码错误!");
        }
        User loginUser = userService.loginUser(user);
        //loginUser.getUsername().equals(user.getUsername()) ||loginUser.getPhone().equals(user.getPhone()
        if (loginUser != null){
            if (loginUser.getPassword().equals(MD5Encode.MD5Encode(user.getPassword(),"utf-8"))){
//                return R.ok().put("data","登录成功");
                Integer userId = loginUser.getId();
                String token = "";
                try {
                    token = MD5Encode.EncoderByMd5(UUID.randomUUID().toString() + System.currentTimeMillis());
                    //session过期时间设置
                    session.setMaxInactiveInterval(60 * 60 * 2);
                    log.info("======登录token：{}",token);
                } catch (NoSuchAlgorithmException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    log.info("======生成token失败");
                    return R.error(-1, "登录失败,生成token失败!");
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    log.info("======生成token失败");
                    return R.error(-1, "登录失败,生成token失败");
                }
                return R.ok().put("code", 0).put("data",loginUser).put("accessToken", token).put("sessionId",userId);
            }
        }
        else if (loginUser == null){
            return R.error(-2,"该账号未注册，请注册");
        }
        return R.error(-4,"账号或者密码错误");
    }
    /**
     * 获取用户信息
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/selectInfo",produces = "application/json;charset=UTF-8")
    public R selectInfo(@RequestBody User userInfo, @LoginUser User u){
        if (u == null){
            return R.error(-1,"未登录");
        }
        User  info = userService.selectUserInfo(userInfo);
        if (info == null){
            return R.error();
        }
        return R.ok().put("data",info);
    }

    /**
     * 修改用户信息
     *  并且返回最新的用户信息
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/updateUserInfo",produces = "application/json;charset=UTF-8")
    public R updateInfo(@RequestBody User updateInfo, @LoginUser User u){
        if (u == null){
            return R.error(-1,"未登录");
        }
        User  info = userService.updateUserInfo(updateInfo);
        if (info == null){
            return R.error(-1,"修改异常");
        }
        return R.ok().put("data",info);
    }
    /**
     * 修改密码
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/updatePassword/{id}",produces = "application/json;charset=UTF-8")
    public R updatePassword(@RequestBody Map<String,Object> map, @PathVariable("id") Integer id,
                            HttpServletRequest request, @LoginUser User u){
        if (u == null){
            return R.error(-1,"未登录");
        }
        if (StringUtils.isEmpty((String)map.get("originalPassword"))){
            return R.error(-1,"原密码不能为空");
        }
        if (StringUtils.isEmpty((String)map.get("newPassword"))){
            return R.error(-1,"新密码不能为空");
        }
        if (map.get("newPassword").equals(map.get("originalPassword"))){
            return R.error(-1,"新旧密码相同");
        }
        if (!PasswordUtils.validatorPassord((String)map.get("newPassword"))){
            return R.error(-1,"修改密码失败,密码必须是字母和数字的组合，并且长度大于等于6位");
        }
        //confirmPassword.equals(newPassword) 两次密码是否相同
        //获取登录名的ID
//        Integer loginUserId = (int) request.getSession().getAttribute("loginUserId");
        userService.updatePassword(id,(String)map.get("originalPassword"),(String)map.get("newPassword"));
        return R.ok().put("修改成功",1);
    }

    /**
     * 修改用户头像
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/updatePic")//,produces = "application/json;charset=UTF-8"
    public R updatePic( User user,@RequestParam("file") MultipartFile file, @LoginUser User u){
        if (u == null){
            return R.error(-1,"未登录");
        }
        try {
            //获取文件名，带扩展名
            String originFileName = file.getOriginalFilename();
            //获取文件扩展名
            String extension = originFileName.substring(originFileName.lastIndexOf("."));
            //以日期的形式设置文件夹
            //LocalDate now = LocalDate.now();
            //String fileFolder = "/" + now.getYear() + "/" + now.getMonthValue() + "/" +now.getDayOfMonth();
            //以存储在U盘中的文件夹来命名文件夹
            String fileFolder = Contants.FILE_UPLOAD_DIC;
            //截取掉U盘名
            fileFolder = fileFolder.substring(2);
            if (Objects.isNull(user.getId())){
                return R.error(-1,"用户不存在");
            }
            //更新文件名
            String name = UUID.randomUUID().toString().replace("-", "");
            String newfileName =  name + extension;
            user.setPic(fileFolder  + newfileName);
            String result = userService.updatePic(user);
            return R.ok().put("filePath", fileFolder + "/" + newfileName).put("fileName", originFileName);
        }catch (Exception e){
            e.printStackTrace();
            return R.error(-405,"图片上传失败，msg:"+e.getMessage());
        }

    }

    /**
     * 用户收藏产品
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/collect",produces = "application/json;charset=UTF-8")
    public R collectInfo(@RequestBody User user, @RequestParam("id") Integer id, @LoginUser User u){
        if (u == null){
            return R.error(-1,"未登录");
        }
        int num =userService.selectCollectInfo(id,user.getId());
        //判断是否收藏过
        if (num == -1){
            return R.error(-1,"已收藏过该产品");
        }
        //判断用户是否为null
        if (num == -2){
            return R.error(-1,"该用户不存在");
        }
        return R.ok("收藏成功");
    }

    /**
     * 用户的关注：人和产品
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/follow",produces = "application/json;charset=UTF-8")
    public R followInfo(@RequestBody User user, @RequestParam("id") Integer id, @LoginUser User u){
        if (u == null){
            return R.error(-1,"未登录");
        }
        int num = userService.insertFollowInfo(id,user.getId());
        //判断是否收藏过
        if (num == -1){
            return R.error(-1,"已关注过该产品");
        }
        //判断用户是否为null
        if (num == -2){
            return R.error(-1,"该用户不存在");
        }
        return R.ok("关注成功");
    }
    /**
     * 取消关注
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/cancelFollow",produces = "application/json;charset=UTF-8")
    public R cancelFollow(@RequestBody Follow follow, @LoginUser User u){
        if (u == null){
            return R.error(-1,"未登录");
        }
        //if (id == null){
        //  return R.error(-1,"未关注该商品");
        //}
        //Follow follow = new Follow();
        follow.setStatus(0);
        int count = userService.updateFollowStatus(follow.getId(), follow.getStatus());
        if (count > 0) {
            return R.ok().put("已取消关注", count);
        } else {
            return R.error(-1, "取消异常");
        }
    }

    /**
     * 查看单个收藏商品
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/CollectById",produces = "application/json;charset=UTF-8")
    public R CollectById(@RequestBody Map<String, Object> result, @LoginUser User u){
        if (u == null){
            return R.error(-1,"未登录");
        }
        Integer productId = Integer.parseInt(result.get("id")+"");
        Integer userId = Integer.parseInt(result.get("userId")+"");
        Collect collect = userService.selectByProductId(productId,userId);
        return R.ok().put("data",collect);
    }
    /**
     *
     * 取消收藏
    */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/cancelCollect",produces = "application/json;charset=UTF-8")
    public R cancelCollect(@RequestBody Collect collect, @LoginUser User u){
        if (u == null){
            return R.error(-1,"未登录");
        }
//        if (id == null){
//            return R.error(-1,"未收藏该商品");
//        }
//        Collect collect = new Collect();
        collect.setStatus(0);
        int count = userService.updateCollectStatus(collect.getId(), collect.getStatus());
        if (count > 0) {
            return R.ok().put("已取消收藏", count);
        } else {
            return R.error(-1, "取消异常");
        }
    }

    /**
     * 查看收藏内容
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/searchCollect",produces = "application/json;charset=UTF-8")
    public R searchCollect(@RequestBody Map<String, Object> result, @LoginUser User u){
        if (u == null){
            return R.error(-1,"未登录");
        }
        try {
            if (StringUtils.isEmpty(result.get("page")) || StringUtils.isEmpty(result.get("limit"))) {
                return R.error(-1, "请求错误");
            }
            PageQuery pageQuery = new PageQuery(result);
            return R.ok().put("data", userService.selectInfo(pageQuery));
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(-405, "查询列表失败，msg:" + e.getMessage());
        }
    }
    /**
     * 查看关注内容
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/searchFollow",produces = "application/json;charset=UTF-8")
    public R searchFollow(@RequestBody Map<String, Object> result, @LoginUser User u){
        if (u == null){
            return R.error(-1,"未登录");
        }
        try {
            if (StringUtils.isEmpty(result.get("page")) || StringUtils.isEmpty(result.get("limit"))) {
                return R.error(-1, "请求错误");
            }
            PageQuery pageQuery = new PageQuery(result);
            return R.ok().put("data", userService.selectFollowInfo(pageQuery));
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(-405, "查询列表失败，msg:" + e.getMessage());
        }
    }

    /**
     * 退出登录
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/logout",produces = "application/json;charset=UTF-8")
    public R logout(@RequestBody Map<String, Object> params,HttpServletRequest request){
        try {
            String accessToken = (String) params.get("accessToken");
            if (StringUtils.isEmpty(accessToken)) {
                return R.error(-1, "accessToken为空");
            }
            request.getSession().removeAttribute("loginUserId");
            request.getSession().removeAttribute("loginUser");
            request.getSession().removeAttribute("errorMsg");
            return R.error(0, "已退出");
        }catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
            return R.error(-2,"退出失败，msg:"+e.getMessage());
        }
    }
    /**
     * 刷新token（有时间就做）
     */
}
