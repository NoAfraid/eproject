package com.eproject.controller;

import com.eproject.common.CategoryLevelEnum;
import com.eproject.common.PageQuery;
import com.eproject.common.R;
import com.eproject.entity.Category;
import com.eproject.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

@Controller
@RequestMapping(method= RequestMethod.POST, value = "/category",produces = "application/json;charset=UTF-8")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    /**
     *添加分类
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/insert",produces = "application/json;charset=UTF-8")
    public R insertCategory(@RequestBody Category category){
        if (Objects.isNull(category.getCategoryLevel())
                || StringUtils.isEmpty(category.getCategoryName())
                || Objects.isNull(category.getParentId())
                || Objects.isNull(category.getCategoryRank())) {
            return R.error("参数异常！");
        }
        int result = categoryService.saveCategory(category);
        if (result == 0){
            return R.ok("添加成功");
        }
       return R.error("添加出错");
    }

    /**
     *添加分类
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/delete",produces = "application/json;charset=UTF-8")
    public R deleteCategory(@RequestBody Integer[] ids){
        if (ids.length < 1) {
            return R.error("参数异常！");
        }
        if (categoryService.deleteBatch(ids) ==0) {
            return R.ok("删除成功");
        } else {
            return R.error("删除失败");
        }
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/update",produces = "application/json;charset=UTF-8")
    public R updateCategory(@RequestBody Category category){
        if (Objects.isNull(category.getCategoryLevel())
                || StringUtils.isEmpty(category.getCategoryName())
                || Objects.isNull(category.getParentId())
                || Objects.isNull(category.getCategoryRank())) {
            return R.error("参数异常！");
        }
        int result = categoryService.updateCategory(category);
        if (result ==0 ) {
            return R.ok("更新成功");
        } else {
            return R.error("更新失败");
        }
    }

    /**
     * 查找分类
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/select",produces = "application/json;charset=UTF-8")
    public R selectCategory(@RequestParam("id") Integer id){
        if (id == null || id < 1) {
            return R.error("缺少参数！");
        }
        Category category = categoryService.getGoodsCategoryById(id);
        //既不是一级分类也不是二级分类则为不返回数据
        if (category == null || category.getCategoryLevel() .equals(CategoryLevelEnum.LEVEL_THREE.getLevel()) ) {
            return R.error("参数异常！");
        }
        Map categoryResult = new HashMap(2);
        if (category.getCategoryLevel() .equals(CategoryLevelEnum.LEVEL_ONE.getLevel())) {
            //如果是一级分类则返回当前一级分类下的所有二级分类，以及二级分类列表中第一条数据下的所有三级分类列表
            //查询一级分类列表中第一个实体的所有二级分类
            List<Category> secondLevelCategories = categoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(id), CategoryLevelEnum.LEVEL_TWO.getLevel());
            if (!CollectionUtils.isEmpty(secondLevelCategories)) {
                //查询二级分类列表中第一个实体的所有三级分类
                List<Category> thirdLevelCategories = categoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(secondLevelCategories.get(0).getId()), CategoryLevelEnum.LEVEL_THREE.getLevel());
                categoryResult.put("secondLevelCategories", secondLevelCategories);
                categoryResult.put("thirdLevelCategories", thirdLevelCategories);
            }
        }
        if (category.getCategoryLevel() .equals(CategoryLevelEnum.LEVEL_TWO.getLevel()) ) {
            //如果是二级分类则返回当前分类下的所有三级分类列表
            List<Category> thirdLevelCategories = categoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(id), CategoryLevelEnum.LEVEL_THREE.getLevel());
            categoryResult.put("thirdLevelCategories", thirdLevelCategories);
        }
        return R.ok().put("data",categoryResult);
    }

    /**
     * 分页查询列表
     */
    @RequestMapping(value = "/categories/list", method = RequestMethod.POST)
    @ResponseBody
    public R list(@RequestBody Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return R.error("参数异常！");
        }
        PageQuery pageUtil = new PageQuery(params);
        return R.ok().put("data",categoryService.getCategoriesPage(pageUtil));
    }
}
