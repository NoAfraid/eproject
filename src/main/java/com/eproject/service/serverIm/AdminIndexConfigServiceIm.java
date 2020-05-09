package com.eproject.service.serverIm;

import com.eproject.common.PageQuery;
import com.eproject.common.PageResult;
import com.eproject.common.ServiceResultEnum;
import com.eproject.dao.IndexConfigDao;
import com.eproject.dao.ProductDao;
import com.eproject.domain.IndexConfigParam;
import com.eproject.entity.IndexConfig;
import com.eproject.entity.Product;
import com.eproject.service.AdminIndexConfigService;
import com.eproject.util.BeanUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminIndexConfigServiceIm implements AdminIndexConfigService {

    @Resource
    private IndexConfigDao indexConfigDao;

    @Resource
    private ProductDao productDao;

    @Override
    public PageResult getConfigsPage(PageQuery pageUtil) {
        List<IndexConfig> indexConfigs = indexConfigDao.findIndexConfigList(pageUtil);
        int total = indexConfigs.size();
        PageResult pageResult = new PageResult(indexConfigs, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public String saveIndexConfig(IndexConfig indexConfig) {
        //todo 判断是否存在该商品
        if (indexConfigDao.insertSelective(indexConfig) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public String updateIndexConfig(IndexConfig indexConfig) {
        //todo 判断是否存在该商品
        IndexConfig temp = indexConfigDao.selectByPrimaryKey(indexConfig.getId());
        if (temp == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        if (indexConfigDao.updateByPrimaryKeySelective(indexConfig) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public IndexConfig getIndexConfigById(Integer id) {
        IndexConfig indexConfig = indexConfigDao.selectByPrimaryKey(id);
        return indexConfig;
    }

    @Override
    public Boolean deleteBatch(Integer[] ids) {
        if (ids.length < 1) {
            return false;
        }
        //删除数据
        return indexConfigDao.deleteBatch(ids) > 0;
    }

    @Override
    public List<IndexConfigParam> getConfigGoodsesForIndex(int configType, int number) {
        List<IndexConfigParam> IndexConfigParam = new ArrayList<>(number);
        List<IndexConfig> indexConfigs = indexConfigDao.findIndexConfigsByTypeAndNum(configType, number);
        if (!CollectionUtils.isEmpty(indexConfigs)) {
            //取出所有的goodsId
            List<Integer> ids = indexConfigs.stream().map(IndexConfig::getProductId).collect(Collectors.toList());
            List<Product> productList = productDao.selectByPrimaryKeys(ids);
            IndexConfigParam = BeanUtil.copyList(productList, IndexConfigParam.class);
            for (Product product : productList){
                for (IndexConfigParam indexConfig : IndexConfigParam) {
                    String productName = indexConfig.getProductName();
                    String description = indexConfig.getDescription();
                    // 字符串过长导致文字超出的问题
                    if (productName.length() > 30) {
                        productName = productName.substring(0, 30) + "...";
                        indexConfig.setProductName(productName);
                    }
                    if (description.length() > 22) {
                        description = description.substring(0, 22) + "...";
                        indexConfig.setDescription(description);
                    }
                    indexConfig.setProductId(product.getId());
                }
            }
        }
        return IndexConfigParam;
    }

}
