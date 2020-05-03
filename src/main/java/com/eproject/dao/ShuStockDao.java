package com.eproject.dao;

import com.eproject.entity.ShuStock;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShuStockDao {
    int deleteByPrimaryKey(Integer id);

    int insert(ShuStock record);

    int insertSelective(ShuStock record);

    ShuStock selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShuStock record);

    int updateByPrimaryKey(ShuStock record);
}