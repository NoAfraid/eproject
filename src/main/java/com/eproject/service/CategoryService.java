package com.eproject.service;

import com.eproject.common.PageQuery;
import com.eproject.common.PageResult;
import com.eproject.entity.Category;

import java.util.List;

public interface CategoryService {

    /**
     * 添加分类
     */
    int saveCategory(Category category);

    /**
     * 删除分类
     */
    int deleteBatch(Integer[] ids);

    /**
     * 更新分类
     */
    int updateCategory(Category category);

    /**
     * 根据parentId和level获取分类列表
     *
     * @param parentIds
     * @param categoryLevel
     * @return
     */
    List<Category> selectByLevelAndParentIdsAndNumber(List<Integer> parentIds, int categoryLevel);
    Category getGoodsCategoryById(Integer id);

    /**
     * 分页查询
     */
    PageResult getCategoriesPage(PageQuery pageUtil);
}
