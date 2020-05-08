package com.eproject.dao;

import com.eproject.entity.Alipay;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AlipayDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Alipay record);

    int insertSelective(Alipay record);

    Alipay selectByPrimaryKey(Integer id);

    Alipay selectByOrderNo(String orderNo);

    int updateByPrimaryKeySelective(Alipay record);

    int updateByPrimaryKey(Alipay record);
}