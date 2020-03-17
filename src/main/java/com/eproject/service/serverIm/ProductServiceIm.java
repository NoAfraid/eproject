package com.eproject.service.serverIm;

import com.eproject.common.PageQuery;
import com.eproject.common.PageResult;
import com.eproject.common.Result;
import com.eproject.dao.ProductDao;
import com.eproject.entity.Product;
import com.eproject.service.ProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
        Object[] o = product.toArray();
        for (int i=0; i<o.length; i++){
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
}
