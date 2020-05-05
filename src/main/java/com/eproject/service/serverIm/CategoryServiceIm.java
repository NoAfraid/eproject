package com.eproject.service.serverIm;

import com.eproject.common.PageQuery;
import com.eproject.common.PageResult;
import com.eproject.dao.CategoryDao;
import com.eproject.entity.Category;
import com.eproject.service.CategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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
}
