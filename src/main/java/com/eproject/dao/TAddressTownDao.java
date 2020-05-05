package com.eproject.dao;

import com.eproject.entity.TAddressTown;

public interface TAddressTownDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TAddressTown record);

    int insertSelective(TAddressTown record);

    TAddressTown selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TAddressTown record);

    int updateByPrimaryKey(TAddressTown record);
}