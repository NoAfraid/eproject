package com.eproject.dao;

import com.eproject.entity.TAddressProvince;

public interface TAddressProvinceDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TAddressProvince record);

    int insertSelective(TAddressProvince record);

    TAddressProvince selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TAddressProvince record);

    int updateByPrimaryKey(TAddressProvince record);
}