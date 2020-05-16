package com.eproject.service.serverIm;

import com.eproject.common.CategoryLevelEnum;
import com.eproject.common.Contants;
import com.eproject.common.PageQuery;
import com.eproject.common.PageResult;
import com.eproject.dao.CategoryDao;
import com.eproject.domain.CategoryParam;
import com.eproject.domain.SearchPageCategoryVO;
import com.eproject.domain.SecondLevelCategoryParam;
import com.eproject.domain.ThirdLevelCategoryParam;
import com.eproject.entity.Category;
import com.eproject.service.CategoryService;
import com.eproject.util.BeanUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class CategoryServiceIm implements CategoryService {

    @Resource
    private CategoryDao categoryDao;

    @Override
    public int saveCategory(Category category){
        Category temp = categoryDao.selectByCategory(category);
        category.setCreateTime(new Date());
        if (temp != null){
            return -1;
        }
        int num = categoryDao.insertSelective(category);
        if (num > 0 ){
            return 0;
        }
        return -2;
    }

    @Override
    public int deleteBatch(Integer[] ids){
        int num = categoryDao.deleteBatch(ids);
        if (ids.length < 1) {
            return -1;
        }
        if (num >0)
            return 0;
        //删除分类数据
        return  -1;
    }

    @Override
    public int updateCategory(Category category){
        Category temp = categoryDao.selectByPrimaryKey(category.getId());
        if (temp == null) {
            // 不存在
            return -1;
        }
        Category temp2 = categoryDao.selectByLevelAndName(category.getCategoryLevel(), category.getCategoryName());
        if (temp2 != null && !temp2.getId().equals(category.getId())) {
            //同名且不同id 不能继续修改
            return -2;
        }
        category.setUpdateTime(new Date());
        if (categoryDao.updateByPrimaryKeySelective(category) > 0) {
            return 0;
        }
        //更新出错
        return -3;
    }

    @Override
    public Category getGoodsCategoryById(Integer id){
        return categoryDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Category> selectByLevelAndParentIdsAndNumber(List<Integer> parentIds, int categoryLevel) {
        return categoryDao.selectByLevelAndParentIdsAndNumber(parentIds, categoryLevel, 0);//0代表查询所有
    }

    @Override
    public PageResult getCategoriesPage(PageQuery pageUtil) {
        List<Category> goodsCategories = categoryDao.findGoodsCategoryList(pageUtil);
        int total = goodsCategories.size();
        PageResult pageResult = new PageResult(goodsCategories, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public List<CategoryParam> getCategoriesForIndex() {
        List<CategoryParam> newBeeMallIndexCategoryVOS = new ArrayList<>();
        //获取一级分类的固定数量的数据
        List<Category> firstLevelCategories = categoryDao.selectByLevelAndParentIdsAndNumber(Collections.singletonList(0), CategoryLevelEnum.LEVEL_ONE.getLevel(), Contants.INDEX_CATEGORY_NUMBER);
        if (!CollectionUtils.isEmpty(firstLevelCategories)) {
            List<Integer> firstLevelCategoryIds = firstLevelCategories.stream().map(Category::getId).collect(Collectors.toList());
            //获取二级分类的数据
            List<Category> secondLevelCategories = categoryDao.selectByLevelAndParentIdsAndNumber(firstLevelCategoryIds, CategoryLevelEnum.LEVEL_TWO.getLevel(), 0);
            if (!CollectionUtils.isEmpty(secondLevelCategories)) {
                List<Integer> secondLevelCategoryIds = secondLevelCategories.stream().map(Category::getId).collect(Collectors.toList());
                //获取三级分类的数据
                List<Category> thirdLevelCategories = categoryDao.selectByLevelAndParentIdsAndNumber(secondLevelCategoryIds, CategoryLevelEnum.LEVEL_THREE.getLevel(), 0);
                if (!CollectionUtils.isEmpty(thirdLevelCategories)) {
                    //根据 parentId 将 thirdLevelCategories 分组
                    Map<Integer, List<Category>> thirdLevelCategoryMap = thirdLevelCategories.stream().collect(groupingBy(Category::getParentId));
                    List<SecondLevelCategoryParam> secondLevelCategoryVOS = new ArrayList<>();
                    //处理二级分类
                    for (Category secondLevelCategory : secondLevelCategories) {
                        SecondLevelCategoryParam secondLevelCategoryVO = new SecondLevelCategoryParam();
                        BeanUtil.copyProperties(secondLevelCategory, secondLevelCategoryVO);
                        //如果该二级分类下有数据则放入 secondLevelCategoryVOS 对象中
                        if (thirdLevelCategoryMap.containsKey(secondLevelCategory.getId())) {
                            //根据二级分类的id取出thirdLevelCategoryMap分组中的三级分类list
                            List<Category> tempGoodsCategories = thirdLevelCategoryMap.get(secondLevelCategory.getId());
                            secondLevelCategoryVO.setThirdLevelCategoryParam((BeanUtil.copyList(tempGoodsCategories, ThirdLevelCategoryParam.class)));
                            secondLevelCategoryVOS.add(secondLevelCategoryVO);
                        }
                    }
                    //处理一级分类
                    if (!CollectionUtils.isEmpty(secondLevelCategoryVOS)) {
                        //根据 parentId 将 thirdLevelCategories 分组
                        Map<Integer, List<SecondLevelCategoryParam>> secondLevelCategoryVOMap = secondLevelCategoryVOS.stream().collect(groupingBy(SecondLevelCategoryParam::getParentId));
                        for (Category firstCategory : firstLevelCategories) {
                            CategoryParam categoryParam = new CategoryParam();
                            BeanUtil.copyProperties(firstCategory, categoryParam);
                            //如果该一级分类下有数据则放入 newBeeMallIndexCategoryVOS 对象中
                            if (secondLevelCategoryVOMap.containsKey(firstCategory.getId())) {
                                //根据一级分类的id取出secondLevelCategoryVOMap分组中的二级级分类list
                                List<SecondLevelCategoryParam> tempGoodsCategories = secondLevelCategoryVOMap.get(firstCategory.getId());
                                categoryParam.setSecondLevelCategoryParams(tempGoodsCategories);
                                newBeeMallIndexCategoryVOS.add(categoryParam);
                            }
                        }
                    }
                }
            }
            return newBeeMallIndexCategoryVOS;
        } else {
            return null;
        }
    }

    @Override
    public SearchPageCategoryVO getCategoriesForSearch(Integer categoryId) {
        SearchPageCategoryVO searchPageCategoryVO = new SearchPageCategoryVO();
        Category thirdLevelGoodsCategory = categoryDao.selectByPrimaryKey(categoryId);
        if (thirdLevelGoodsCategory != null && thirdLevelGoodsCategory.getCategoryLevel() == CategoryLevelEnum.LEVEL_THREE.getLevel()) {
            //获取当前三级分类的二级分类
            Category secondLevelGoodsCategory = categoryDao.selectByPrimaryKey(thirdLevelGoodsCategory.getParentId());
            if (secondLevelGoodsCategory != null && secondLevelGoodsCategory.getCategoryLevel() == CategoryLevelEnum.LEVEL_TWO.getLevel()) {
                //获取当前二级分类下的三级分类List
                List<Category> thirdLevelCategories = categoryDao.selectByLevelAndParentIdsAndNumber(Collections.singletonList(secondLevelGoodsCategory.getId()), CategoryLevelEnum.LEVEL_THREE.getLevel(), Contants.SEARCH_CATEGORY_NUMBER);
                searchPageCategoryVO.setCurrentCategoryName(thirdLevelGoodsCategory.getCategoryName());
                searchPageCategoryVO.setSecondLevelCategoryName(secondLevelGoodsCategory.getCategoryName());
                searchPageCategoryVO.setThirdLevelCategoryList(thirdLevelCategories);
                return searchPageCategoryVO;
            }
        }
        return null;
    }
}
