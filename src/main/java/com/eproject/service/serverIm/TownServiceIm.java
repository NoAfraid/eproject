package com.eproject.service.serverIm;

import com.eproject.dao.TAddressTownDao;
import com.eproject.entity.TAddressTown;
import com.eproject.service.TownService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TownServiceIm implements TownService {

    @Resource
    private TAddressTownDao townDao;

    @Override
    public List<TAddressTown> findByCityCode(int cityCode){
        return townDao.findByCityCode(cityCode);
    }
}
