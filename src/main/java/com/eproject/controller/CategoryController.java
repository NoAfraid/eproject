package com.eproject.controller;

import com.eproject.common.*;
import com.eproject.domain.CategoryParam;
import com.eproject.domain.SearchPageCategoryVO;
import com.eproject.entity.Category;
import com.eproject.service.CategoryService;
import com.eproject.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping(method= RequestMethod.POST, value = "/category",produces = "application/json;charset=UTF-8")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    @Resource
    private ProductService productService;

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
            return R.ok();
        }
       return R.error();
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
            return R.ok();
        } else {
            return R.error("更新失败");
        }
    }
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST, value = "/selectCategoryById",produces = "application/json;charset=UTF-8")
    public R selectCategoryById(@RequestBody Category category){
        return  R.ok().put("data",categoryService.getGoodsCategoryById(category.getId()));
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

    /**
     * 首页调用分类
     */
    @RequestMapping(value = "/getCategoryForIndex", method = RequestMethod.POST)
    @ResponseBody
    public R getCategoryForIndex(){
        List<CategoryParam> categories = categoryService.getCategoriesForIndex();
        if (CollectionUtils.isEmpty(categories)) {
            return R.error("获取错误");
        }
        return R.ok().put("data",categories);
    }

    @ResponseBody
    @RequestMapping(value = "/searchCategory", method = RequestMethod.POST)
    public R searchPage(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        if (StringUtils.isEmpty(params.get("page"))) {
            params.put("page", 1);
        }
        params.put("limit", Contants.GOODS_SEARCH_PAGE_LIMIT);
        //封装分类数据
        if (params.containsKey("categoryId") && !StringUtils.isEmpty(params.get("categoryId") + "")) {
            Integer id = Integer.valueOf(params.get("categoryId") + "");
            SearchPageCategoryVO searchPageCategoryVO = categoryService.getCategoriesForSearch(id);
            if (searchPageCategoryVO != null) {
                request.setAttribute("goodsCategoryId", id);
                request.setAttribute("searchPageCategoryVO", searchPageCategoryVO);
                String productName = searchPageCategoryVO.getCurrentCategoryName();
                params.put("keyword",productName);
                params.put("productName",productName);
                PageQuery pageQuery = new PageQuery(params);
                return R.ok().put("data", productService.searchProduct(pageQuery));
//                return R.ok().put("data", searchPageCategoryVO);
            }
        }
        //封装参数供前端回显
//        if (params.containsKey("orderBy") && !StringUtils.isEmpty(params.get("orderBy") + "")) {
//            request.setAttribute("orderBy", params.get("orderBy") + "");
//        }
//        String keyword = "";
//        //对keyword做过滤 去掉空格
//        if (params.containsKey("keyword") && !StringUtils.isEmpty((params.get("keyword") + "").trim())) {
//            keyword = params.get("keyword") + "";
//        }
//        request.setAttribute("keyword", keyword);
//        params.put("keyword", keyword);
//        //封装商品数据
//        PageQuery pageQuery = new PageQuery(params);
        return R.error(-1,"查询错误");
    }
}
