package com.eproject.dao;

import com.eproject.entity.TAddressCity;

public interface TAddressCityDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TAddressCity record);

    int insertSelective(TAddressCity record);

    TAddressCity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TAddressCity record);

    int updateByPrimaryKey(TAddressCity record);
}