package com.eproject.dao;

import com.eproject.entity.HistorySearch;
import com.eproject.entity.HotSearch;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HotSearchDao {
    int deleteByPrimaryKey(Integer id);

    int insert(HotSearch record);

    int insertSelective(HistorySearch record);

    HotSearch selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HotSearch record);

    int updateByPrimaryKey(HotSearch record);

    List<HotSearch> getProductForHotSearch(@Param("number") int number);
}