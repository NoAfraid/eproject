package com.eproject.service.serverIm;

import com.eproject.common.PageQuery;
import com.eproject.common.PageResult;
import com.eproject.common.Result;
import com.eproject.dao.HistorySearchDao;
import com.eproject.dao.HotSearchDao;
import com.eproject.dao.ProductDao;
import com.eproject.dao.ShuStockDao;
import com.eproject.entity.*;
import com.eproject.service.ProductService;
import com.fasterxml.jackson.databind.util.BeanUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.annotation.Resources;
import java.util.*;

@Service
public class ProductServiceIm implements ProductService {

    @Resource
    private ProductDao productDao;

    @Resource
    private ShuStockDao shuStockDao;

    @Resource
    private HistorySearchDao historySearchDao;

    @Resource
    private HotSearchDao hotSearchDao;

    @Override
    public String saveProduct(Product p) {

        if (productDao.insertSelective(p) > 0) {
            Product product = productDao.selectNewProduct(p);
            ShuStock shuStock = new ShuStock();
            shuStock.setProductId(product.getId());
            shuStock.setPromotionPrice(p.getPromotePrice());
            shuStock.setSkuCode(p.getProductSn());
            shuStock.setStock(p.getStock());
            shuStock.setPrice(p.getPrice());
            shuStock.setPic(p.getProductImg());
            shuStock.setLowStock(50);
            shuStockDao.insertSelective(shuStock);
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
    public Product getProductById(Integer id) {
        Product temp = productDao.selectByPrimaryKey(id);
        if (temp == null) {
            return null;
        }
        return temp;
    }

    @Override
    public String updateProductInfo(Product product) {
        Product temp = productDao.selectByPrimaryKey(product.getId());
        if (temp == null) {
            return Result.DATA_NOT_EXIST.getResult();
        }
        if (productDao.updateByPrimaryKeySelective(product) > 0) {
            return Result.SUCCESS.getResult();
        }
        return Result.DB_ERROR.getResult();
    }

    @Override
    public Boolean deleteProduct(Integer[] ids) {
//        Product temp = productDao.selectByPrimaryKey(product.getId());
        if (ids.length < 1) {
            return false;
        }
        return productDao.deleteBatch(ids) > 0;
    }

    @Override
    public int updateVerifyStatus(Integer[] ids, int verifyStatus) {
        return productDao.updateByExampleSelective(ids, verifyStatus);
    }

    @Override
    public int updatePublicStatus(Integer[] ids, int publicStatus) {
        return productDao.updateBySelective(ids, publicStatus);
    }

    @Override
    public int updateRecomandStatus(Integer[] ids, int recomandStatus) {
        return productDao.updateByRecomandStatus(ids, recomandStatus);
    }

    @Override
    public int updateStock(List<Product> goods) {
        List<Product> product = productDao.selectIdList(goods);
        //转化为o数组对象
        Object[] o = product.toArray();
        for (int i = 0; i < o.length; i++) {
            //转化为Product对象
            Product products = (Product) o[i];
            int count = products.getStock();
            products.setStock(count < 0 ? 0 : count);
        }
        return productDao.updateStock(goods);
    }

    @Override
    public int updateSaleNumber(List<Product> goods) {
        return productDao.updateSale(goods);
    }

    @Override
    public PageResult searchProduct(PageQuery pageQuery) {
        List<Product> productList = productDao.selectProductBySearchPage(pageQuery);
        Object[] o = productList.toArray();
        int total = productDao.selectTotalProductBySearch(pageQuery);
        if (!CollectionUtils.isEmpty(productList)) {
            for (int i = 0; i < o.length; i++) {
                //转化为Product对象
                Product products = (Product) o[i];
                String productName = products.getProductName();
                String description = products.getDescription();
                int count = products.getSearchCount();
                //每搜索一次，searchCount加一
//                count++;
//                products.setSearchCount(count);
//                productDao.updateSearchCount(products);
                //截取标题的前20个字符
                if (productName.length() > 20) {
                    productName = productName.substring(0, 20) + "...";
                    products.setProductName(productName);
                }
                //截取描述的前30字符
//               if (description.length() > 30){
//                   description = products.getDescription();
//                   products.setDescription(description);
//               }
            }
        }
        PageResult result = new PageResult(productList, total, pageQuery.getLimit(), pageQuery.getPage());
        return result;
    }

    @Override
    public Product selectProductById(Integer id) {
        return productDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Product> getProductForIndex(int number) {
        List<Product> carouseList = new ArrayList<>(number);
        List<Product> list = productDao.selectProductListByNumber(number);
        if (!CollectionUtils.isEmpty(list)) {
            return list;
        }
        return null;
    }

    @Override
    public List<Product> getProductSaleForIndex(int number) {
        List<Product> carouseList = new ArrayList<>(number);
        List<Product> list = productDao.getProductSaleForIndex(number);
        if (!CollectionUtils.isEmpty(list)) {
            return list;
        }
        return null;
    }

    @Override
    public List<HotSearch> getProductForHotSearch(int number) {
        List<Product> carouseList = new ArrayList<>(number);
        List<HotSearch> list = hotSearchDao.getProductForHotSearch(number);
        List<Product> l = productDao.getProductForHotSearch(number);
        Object[] o = l.toArray();
        if (!CollectionUtils.isEmpty(list)) {
            for (int i = 0; i < o.length; i++) {
                //转化为Product对象
                Product products = (Product) o[i];
                //String[] keyword = {"水准仪","无人机","全站仪","经纬仪","测量仪","测量系统"};
                String productName = products.getProductName();
//                for (int j=0; j<keyword.length;j++){
//                    if (productName.contains(keyword[j])) {
//                        productName = keyword[j];
//
//                    }
//                }
                products.setProductName(productName);
                //String p = products.getProductName();
                //System.out.println(p);
                int count = products.getSearchCount();
                //每搜索一次，searchCount加一
                count++;
                products.setSearchCount(count);
                productDao.updateSearchCount(products);
            }
            return list;
        }
        return null;
    }

    @Override
    public List<Product> searchProductForIndex(Map<String, Object> params) {
        List<Product> list = productDao.searchProductForIndex(params);
        Object[] o = list.toArray();
        HistorySearch historySearch = new HistorySearch();
        if (list.size() > 0) {
            for (int i = 0; i < o.length; i++) {
                //转化为Product对象
                Product products = (Product) o[i];
                int count = products.getSearchCount();
                //每搜索一次，searchCount加一
                count++;
                products.setSearchCount(count);
                historySearch.setProductId(products.getId());
                historySearch.setProductName(products.getProductName());
                historySearch.setSearchCount(count);
                historySearch.setSearchName(params.get("productName")+"");
                if (StringUtils.isEmpty(params.get("userId"))){
                    productDao.updateSearchCount(products);
                    return list;
                }
                else {
                    int userId = Integer.parseInt(params.get("userId") + "") ;
                    historySearch.setUserId(userId);
                    historySearch.setCreatTime(new Date());
                    productDao.updateSearchCount(products);
                    List<HistorySearch> hs = historySearchDao.selectByProductIdAndUserId(historySearch);
                    hotSearchDao.insertSelective(historySearch);
                    if (hs.size() <= 0 ){
                        historySearchDao.insertSelective(historySearch);
                    }
                }
            }
            return list;
        }
        return null;
    }

    @Override
    public PageResult selectAll(PageQuery pageQuery){
        List<Product> list = productDao.selectAll(pageQuery);
        int total = list.size();
        PageResult pageResult = new PageResult(list, total, pageQuery.getLimit(), pageQuery.getPage());
        return pageResult;
    }

    @Override
    public List<Product> orderBy(String orderField,String orderType){
        return productDao.orderByProduct(orderField,orderType);
    }
}
