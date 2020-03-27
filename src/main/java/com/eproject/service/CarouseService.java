package com.eproject.service;

import com.eproject.common.PageQuery;
import com.eproject.entity.Carouse;

import java.util.List;

public interface CarouseService {

    /**
     * 添加轮播
     */
    int addCarouse(Carouse carouse);
    /**
     * 获取轮播列表
     */
    List<Carouse> getCarouseList(PageQuery pageQuery);
    /**
     * 更新轮播
     */
    int updateCarouse(Carouse carouse);
    /**+
     * 批量删除轮播
     */
    int deleteCarouse(List<Integer> ids);
    /**
     * 根据轮播ID获取轮播
     */
    Carouse getCarouseById(Integer id);
    /**
     *
     *  返回固定数量的轮播图对象(首页调用)
     *
     * @param number
     * @return
     */
    List<Carouse>  getCarouseForIndex(Integer number);
}
