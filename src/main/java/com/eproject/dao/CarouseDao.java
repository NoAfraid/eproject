package com.eproject.dao;

import com.eproject.common.PageQuery;
import com.eproject.entity.Carouse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CarouseDao {
    int deleteByPrimaryKey(Integer id);

    int deleteCarouse(@Param("ids") List<Integer> ids);

    int insert(Carouse record);

    int insertSelective(Carouse record);

    Carouse selectByPrimaryKey(Integer id);

    List<Carouse> selectCarouseList(PageQuery pageQuery);

    List<Carouse> selectCarouseListByNumber(@Param("number") Integer number);

    int updateByPrimaryKeySelective(Carouse record);

    int updateByPrimaryKey(Carouse record);
}