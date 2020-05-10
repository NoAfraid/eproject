package com.eproject.dao;

import com.eproject.entity.TAddressProvince;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface TAddressProvinceDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TAddressProvince record);

    int insertSelective(TAddressProvince record);

    TAddressProvince selectByPrimaryKey(Integer id);

    List<TAddressProvince> selectAll();

    int updateByPrimaryKeySelective(TAddressProvince record);

    int updateByPrimaryKey(TAddressProvince record);

    String selectByCode(String code);
}