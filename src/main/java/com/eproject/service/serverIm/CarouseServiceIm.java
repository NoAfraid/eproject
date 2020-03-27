package com.eproject.service.serverIm;

import com.eproject.common.PageQuery;
import com.eproject.dao.CarouseDao;
import com.eproject.entity.Carouse;
import com.eproject.service.CarouseService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CarouseServiceIm implements CarouseService {

    @Resource
    private CarouseDao carouseDao;

    @Override
    public int addCarouse(Carouse carouse){
        carouse.setCreatTime(new Date());
        int num = carouseDao.insert(carouse);
        if (num > 0){
            return num;
        }
        return -1;
    }

    @Override
    public List<Carouse> getCarouseList(PageQuery pageQuery){
        List<Carouse> carouseList = carouseDao.selectCarouseList(pageQuery);
        if (carouseList.size() > 0){
            return carouseList;
        }
        return null;
    }

    @Override
    public int updateCarouse(Carouse carouse){
        carouse.setCarouseRank(carouse.getCarouseRank());
        carouse.setRedirectUrl(carouse.getRedirectUrl());
        carouse.setProductImg(carouse.getProductImg());
        carouse.setProductId(carouse.getProductId());
        carouse.setCarouseName(carouse.getCarouseName());
        carouse.setManagerId(carouse.getManagerId());
        carouse.setUpdateTime(new Date());
        int count = carouseDao.updateByPrimaryKeySelective(carouse);
        if (count > 0){
            return count;
        }
        return -1;
    }

    @Override
    public int deleteCarouse(List<Integer> ids){
        return carouseDao.deleteCarouse(ids);
    }

    @Override
    public Carouse getCarouseById(Integer id){
        Carouse count = carouseDao.selectByPrimaryKey(id);
        if (count == null){
            return null;
        }
        return count;
    }

    @Override
    public List<Carouse> getCarouseForIndex(Integer number){
        List<Carouse> carouseList = new ArrayList<>(number);
        List<Carouse> list = carouseDao.selectCarouseListByNumber(number);
        if (!CollectionUtils.isEmpty(list)){
            return list;
        }
        return null;
    }
}
