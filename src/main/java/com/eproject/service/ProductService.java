package com.eproject.service;


import com.eproject.common.PageQuery;
import com.eproject.common.PageResult;
import com.eproject.entity.Product;

import java.util.List;

public interface ProductService {
    /**
     * 添加商品信息
     * @return
     */
    String saveProduct(Product products);

    /**
     * 查询商品信息分页）
     */
    PageResult getProductInfo(PageQuery pageQuery);

    /**
     * 修改商品信息
     */
    String updateProductInfo(Product product);

    /**
     * 删除商品
     */
    Boolean deleteProduct(Integer[] ids);

    /**
     * 批量修改审核状态
     * @param ids 产品id
     * @param verifyStatus 审核状态
     */
    int updateVerifyStatus(Integer[] ids, int verifyStatus);

    /**
     * 批量修改审核状态
     * @param ids 产品id
     * @param publicStatus 上下架状态
     */
    int updatePublicStatus(Integer[] ids, int publicStatus);

    /**
     * 批量修改审核状态
     * @param ids 产品id
     * @param recomandStatus 推荐状态
     */
    int updateRecomandStatus(Integer[] ids, int recomandStatus);

    /**
     * 修改库存数
     * @param ids
     */
    int updateStock(Integer[] ids, Product goods);

    /**
     * 修改销售数
     * @param id
     */
    int updateSaleNumber(Integer[] id);

    /**
     * 模糊查询
     * 根据商品名或者货号
     * 分页
     */
    PageResult searchProduct(PageQuery pageQuery);

    /**
     * 获取商品详情
     */
    Product selectProductById(Integer id);
}
