package com.eproject.service;

import com.eproject.common.PageQuery;
import com.eproject.common.PageResult;
import com.eproject.domain.IndexConfigParam;
import com.eproject.entity.IndexConfig;

import java.util.List;

public interface AdminIndexConfigService {

    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getConfigsPage(PageQuery pageUtil);

    String saveIndexConfig(IndexConfig indexConfig);

    String updateIndexConfig(IndexConfig indexConfig);

    IndexConfig getIndexConfigById(Integer id);

    Boolean deleteBatch(Integer[] ids);

    /**
     * 返回固定数量的首页配置商品对象(首页调用)
     *
     * @param number
     * @return
     */
    List<IndexConfigParam> getConfigGoodsesForIndex(int configType, int number);

}
