package com.eproject.service.serverIm;

import com.eproject.dao.ManagerDao;
import com.eproject.entity.Manager;
import com.eproject.service.ManagerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ManagerServiceIm implements ManagerService{

    @Resource
    private ManagerDao managerDao;

    @Override
    public Manager login(String userName, String password){
//        String passwordMd5 = MD5Util.MD
        return managerDao.login(userName, password);
    }
}
