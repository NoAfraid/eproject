package com.eproject.service;

import com.eproject.common.PageQuery;
import com.eproject.common.PageResult;
import com.eproject.common.R;
import com.eproject.entity.Collect;
import com.eproject.entity.Follow;
import com.eproject.entity.User;

import java.util.List;

public interface UserService {

    /**
     * 后台处理用户列表
     */
    PageResult usersPage(PageQuery pageUtil);

    /**
     * 用户禁用与解除禁用(0-未锁定 1-已锁定)
     *
     * @param ids
     * @param disableStatus
     * @return
     */
    Boolean disableUsers(Integer[] ids, int disableStatus);

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
    User selectUserInfo(User user);
    /**
     * 修改用户信息
     *  并且返回最新的用户信息
     */
    User updateUserInfo(User user);

    /**
     * 修改密码
     */
    void updatePassword(Integer id, String originalPassword, String newPassword);

    /**
     * 修改用户头像
     */
    String updatePic(User user);

    /**
     * 用户收藏产品
     */
    int selectCollectInfo(Integer id, Integer userId);

    /**
     * 查看用户收藏的单个商品
     */
    Collect selectByProductId(Integer productId,Integer userId);
    /**
     * 用户的关注：人和产品
     */
    int insertFollowInfo(Integer id, Integer userId);

    /**
     * 取消关注
     */
    int updateFollowStatus(Integer status,Integer id);

    /**
     *
     * 取消收藏
     */
    int updateCollectStatus(Integer status,Integer id);

    /**
     * 查看收藏内容
     */
    PageResult selectInfo(PageQuery pageQuery);

    /**
     * 查看关注内容
     */
    PageResult selectFollowInfo(PageQuery pageQuery);

    /**
     * 筛选用户Nick
     */
    String selectNick(Integer userId);
    /**
     * 退出登录
     */

    /**
     * 刷新token（有时间就做）
     */
}
