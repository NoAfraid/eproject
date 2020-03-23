package com.eproject.dao;

import com.eproject.entity.UserReceiveAddress;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserReceiveAddressDao {
    int deleteByPrimaryKey(Integer id);

    int insert(UserReceiveAddress record);

    int insertSelective(UserReceiveAddress record);

    List<UserReceiveAddress> selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(UserReceiveAddress record);

    int updateByPrimaryKey(UserReceiveAddress record);
}