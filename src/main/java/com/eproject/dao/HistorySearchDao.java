package com.eproject.dao;

import com.eproject.entity.HistorySearch;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HistorySearchDao {
    int deleteByPrimaryKey(Integer id);

    int insert(HistorySearch record);

    int insertSelective(HistorySearch record);

    HistorySearch selectByPrimaryKey(Integer id);

//    List<List> selectSameProduct()

    int updateByPrimaryKeySelective(HistorySearch record);

    int updateByPrimaryKey(HistorySearch record);

    List<HistorySearch> getProductForHistorySearch(@Param("number") int number, int userId);

    List<HistorySearch> selectByProductIdAndUserId( HistorySearch list);

    int updateSearchCount(HistorySearch historySearch);
}