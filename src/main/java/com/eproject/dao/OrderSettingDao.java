package com.eproject.dao;

import com.eproject.entity.OrderSetting;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderSettingDao {
    int deleteByPrimaryKey(Long id);

    int insert(OrderSetting record);

    int insertSelective(OrderSetting record);

    OrderSetting selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderSetting record);

    int updateByPrimaryKey(OrderSetting record);
}