package com.eproject.dao;

import com.eproject.common.PageQuery;
import com.eproject.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductDao {
    int deleteByPrimaryKey(Integer[] ids);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    Product selectById(@Param("ids") Integer[] ids);

    int updateByExampleSelective(Integer[] ids,int verifyStatus);

    int updateBySelective(Integer[] ids,int publicStatus);

    int updateByRecomandStatus(Integer[] ids,int recomandStatus);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    int saveProduct(Product product);

    List<Product> selectProductList(PageQuery pageQuery);

    List<Product> selectIdList(Product product, @Param("ids") Integer[] ids);

    int getProductPage(PageQuery pageQuery);

    int deleteBatch(Integer[] ids);

    int updateStock(@Param("ids") Integer[] ids);

    int updateSale(@Param("id") Integer[] id);
}