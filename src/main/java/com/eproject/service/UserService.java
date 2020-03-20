package com.eproject.service;

import com.eproject.common.R;
import com.eproject.entity.User;

import java.util.List;

public interface UserService {

    /**
     * 用户注册
     */
    String register(User user);

    /**
     * 登录
     */
    User loginUser(User user);

    /**
     * 获取用户信息
     */
    List<User> selectUserInfo(User user);
    /**
     * 修改用户信息
     *  并且返回最新的用户信息
     */
    List<User> updateUserInfo(User user);

    /**
     * 修改密码
     */
    void updatePassword(Integer id, String originalPassword, String newPassword);

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
