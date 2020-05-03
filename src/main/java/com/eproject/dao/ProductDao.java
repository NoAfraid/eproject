package com.eproject.dao;

import com.eproject.common.PageQuery;
import com.eproject.entity.OrderItem;
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

    List<Product> selectIdList(@Param("goods") List<Product> goods);

    int getProductPage(PageQuery pageQuery);

    int deleteBatch(Integer[] ids);

    int updateStock(@Param("list") List<Product> goods);

    int updateSale(@Param("list") List<Product> goods);

    List<Product> selectProductBySearchPage(PageQuery pageQuery);

    int selectTotalProductBySearch(PageQuery pageQuery);

    List<Product> selectProductListByNumber(@Param("number") int number);

    List<Product> getProductSaleForIndex(@Param("number") int number);

    /**
     * 修改 pms_sku_stock表的锁定库存及真实库存
     */
    int updateSkuStock(@Param("itemList") List<OrderItem> orderItemList);

    int updateStockAndSale(@Param("itemList") List<OrderItem> orderItemList);

    /**
     * 选择最新的一条记录
     */
    Product selectNewProduct(Product product);
}