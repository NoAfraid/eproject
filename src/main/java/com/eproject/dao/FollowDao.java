package com.eproject.dao;

import com.eproject.common.PageQuery;
import com.eproject.entity.Follow;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FollowDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Follow record);

    int insertSelective(Follow record);

    Follow selectByPrimaryKey(Integer id);

    List<Follow> selectInfo(PageQuery pageQuery);

    int selectByFollow(Integer id, Integer userId);

    int updateByPrimaryKeySelective(Follow record);

    int updateByPrimaryKey(Follow record);

    int insertFollowInfo(Integer id, Integer userId);

    int updateFollowStatus(Integer id, Integer status);

    int updateFollowTime(Integer id);
}