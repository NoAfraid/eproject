package com.eproject.dao;

import com.eproject.common.PageQuery;
import com.eproject.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CategoryDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

    Category selectByCategory(Category category);

    int deleteBatch(Integer[] ids);

    Category selectByLevelAndName(@Param("categoryLevel") String categoryLevel, @Param("categoryName") String categoryName);

    List<Category> selectByLevelAndParentIdsAndNumber(@Param("parentIds") List<Integer> parentIds, @Param("categoryLevel") int categoryLevel, @Param("number") int number);

    List<Category> findGoodsCategoryList(PageQuery pageUtil);
}