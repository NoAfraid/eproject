package com.eproject.service.serverIm;

import com.eproject.dao.ManagerDao;
import com.eproject.entity.Manager;
import com.eproject.service.ManagerService;
import com.eproject.util.MD5Encode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ManagerServiceIm implements ManagerService{

    @Resource
    private ManagerDao managerDao;

    @Override
    public Manager login(String userName, String password){
        String passwordMd5 = MD5Encode.MD5Encode(password,"UTF-8");
        //更新登陆时间
        managerDao.updateLoginTime(userName,passwordMd5);
        return managerDao.login(userName, passwordMd5);
    }
}
