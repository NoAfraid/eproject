package com.eproject.dao;

import com.eproject.entity.TAddressTown;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TAddressTownDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TAddressTown record);

    int insertSelective(TAddressTown record);

    TAddressTown selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TAddressTown record);

    int updateByPrimaryKey(TAddressTown record);

    List<TAddressTown> findByCityCode(int cityCode);

    String selectByCode(String code);
}