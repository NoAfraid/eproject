package com.eproject.service.serverIm;

import com.eproject.common.PageQuery;
import com.eproject.common.PageResult;
import com.eproject.common.Result;
import com.eproject.dao.ProductDao;
import com.eproject.entity.Product;
import com.eproject.service.ProductService;
import com.fasterxml.jackson.databind.util.BeanUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ProductServiceIm implements ProductService {

    @Resource
    private ProductDao productDao;

    @Override
    public String saveProduct(Product product){
        if (productDao.saveProduct(product) > 0){
            return Result.SUCCESS.getResult();
        }
        return Result.ERROR.getResult();
    }

    @Override
    public PageResult getProductInfo(PageQuery pageQuery) {
        List<Product> productList = productDao.selectProductList(pageQuery);
        int total = productDao.getProductPage(pageQuery);
        PageResult pageResult = new PageResult(productList, total, pageQuery.getLimit(), pageQuery.getPage());
        return pageResult;
    }

    @Override
    public String updateProductInfo(Product product){
        Product temp = productDao.selectByPrimaryKey(product.getId());
        if (temp == null){
            return Result.DATA_NOT_EXIST.getResult();
        }
        if (productDao.updateByPrimaryKeySelective(product) > 0){
            return Result.SUCCESS.getResult();
        }
        return Result.DB_ERROR.getResult();
    }

    @Override
    public Boolean deleteProduct(Integer[] ids){
//        Product temp = productDao.selectByPrimaryKey(product.getId());
        if (ids.length < 1) {
            return false;
        }
        return productDao.deleteBatch(ids) > 0;
    }

    @Override
    public int updateVerifyStatus(Integer[] ids, int verifyStatus){
       return productDao.updateByExampleSelective(ids,verifyStatus);
    }

    @Override
    public int updatePublicStatus(Integer[] ids, int publicStatus){
        return productDao.updateBySelective(ids,publicStatus);
    }

    @Override
    public int updateRecomandStatus(Integer[] ids, int recomandStatus){
        return productDao.updateByRecomandStatus(ids,recomandStatus);
    }

    @Override
    public int updateStock(Integer[] ids, Product goods){
        List<Product> product =  productDao.selectIdList(goods,ids);
        //转化为o数组对象
        Object[] o = product.toArray();
        for (int i=0; i<o.length; i++){
            //转化为Product对象
            Product products = (Product) o[i];
            int count = products.getStock();
            products.setStock(count < 0 ? 0 : count);
        }
        return productDao.updateStock(ids);
    }

    @Override
    public int updateSaleNumber(Integer[] id){
        return productDao.updateSale(id);
    }

    @Override
    public PageResult searchProduct(PageQuery pageQuery){
        List<Product> productList = productDao.selectProductBySearchPage(pageQuery);
        Object[] o = productList.toArray();
        int total = productDao.selectTotalProductBySearch(pageQuery);
        if (!CollectionUtils.isEmpty(productList)){
           for (int i=0; i<o.length; i++){
               //转化为Product对象
               Product products = (Product) o[i];
               String productName = products.getProductName();
               String description = products.getDescription();
               //截取标题的前20个字符
               if (productName.length() > 20){
                   productName = productName.substring(0,20) + "...";
                   products.setProductName(productName);
               }
               //截取描述的前30字符
               if (description.length() > 30){
                   description = products.getDescription();
                   products.setDescription(description);
               }
           }
        }
        PageResult result = new PageResult(productList, total, pageQuery.getLimit(), pageQuery.getPage());
        return result;
    }

    @Override
    public Product selectProductById(Integer id){
        return productDao.selectByPrimaryKey(id);
    }
}
