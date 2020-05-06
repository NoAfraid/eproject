package com.eproject.service.serverIm;

import com.eproject.dao.TAddressCityDao;
import com.eproject.entity.TAddressCity;
import com.eproject.service.CityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CityServiceIm implements CityService {

    @Resource
    private TAddressCityDao cityDao;

    @Override
    public List<TAddressCity> findByProvinceCode(int provinceCode){
        return cityDao.findByProvinceCode(provinceCode);
    }
}
