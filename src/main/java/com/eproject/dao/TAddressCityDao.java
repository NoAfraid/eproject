package com.eproject.dao;

import com.eproject.entity.TAddressCity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TAddressCityDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TAddressCity record);

    int insertSelective(TAddressCity record);

    TAddressCity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TAddressCity record);

    int updateByPrimaryKey(TAddressCity record);

    List<TAddressCity> findByProvinceCode(int provinceCode);
}