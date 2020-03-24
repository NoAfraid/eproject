package com.eproject.service;

import com.eproject.entity.UserReceiveAddress;

import java.util.List;

public interface UserReceiveAddressService {

    /**
     * 添加收货地址
     */
    int add(UserReceiveAddress address);

    /**
     * 删除收货地址
     * @param id 地址表的id
     */
    int delete(Integer id);

    /**
     * 修改收货地址
     * @param id 地址表的id
     * @param address 修改的收货地址信息
     */
    int update(Integer id, UserReceiveAddress address);

    /**
     * 返回当前用户的收货地址
     */
    List<UserReceiveAddress> list(UserReceiveAddress address);

    /**
     * 获取地址详情
     * @param id 地址id
     */
    UserReceiveAddress getItem(Integer userId);
}
