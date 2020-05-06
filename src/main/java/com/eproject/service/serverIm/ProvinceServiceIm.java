package com.eproject.service.serverIm;

import com.eproject.dao.TAddressProvinceDao;
import com.eproject.entity.TAddressProvince;
import com.eproject.service.ProvinceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProvinceServiceIm implements ProvinceService {

    @Resource
    private TAddressProvinceDao provinceDao;

    @Override
    public List<TAddressProvince> selectAll(){
        return provinceDao.selectAll();
    }

}
