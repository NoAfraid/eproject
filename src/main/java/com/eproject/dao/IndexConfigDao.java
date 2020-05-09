package com.eproject.dao;

import com.eproject.common.PageQuery;
import com.eproject.entity.IndexConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IndexConfigDao {
    int deleteByPrimaryKey(Integer id);

    int insert(IndexConfig record);

    int insertSelective(IndexConfig record);

    IndexConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IndexConfig record);

    int updateByPrimaryKey(IndexConfig record);

    List<IndexConfig> findIndexConfigList(PageQuery pageUtil);

    int deleteBatch(Integer[] ids);

    List<IndexConfig> findIndexConfigsByTypeAndNum(@Param("configType") int configType, @Param("number") int number);
}