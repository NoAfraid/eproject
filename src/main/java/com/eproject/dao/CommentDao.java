package com.eproject.dao;

import com.eproject.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentDao {
    int deleteByPrimaryKey(Long id);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);
}