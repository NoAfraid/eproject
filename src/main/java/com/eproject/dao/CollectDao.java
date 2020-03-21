package com.eproject.dao;

import com.eproject.entity.Collect;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CollectDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Collect record);

    int insertSelective(Collect record);

    Collect selectByPrimaryKey(Integer id);

    List<Collect> selectInfo(Collect collect);

    int selectByCollect(Integer id, Integer userId);

    int selectCollectInfo(Integer id, Integer userId);

    int updateByPrimaryKeySelective(Collect record);

    int updateByPrimaryKey(Collect record);

    int updateCollectStatus(Integer id, Integer status);
}