package com.eproject.service.serverIm;

import com.eproject.dao.UserReceiveAddressDao;
import com.eproject.entity.UserReceiveAddress;
import com.eproject.service.UserReceiveAddressService;
import com.eproject.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserReceiveAdressServiceIm implements UserReceiveAddressService {
    @Resource
    private UserReceiveAddressDao userReceiveAddressDao;

    @Resource
    private UserService userService;

    @Override
    public int add(UserReceiveAddress address){
        address.setUserId(address.getUserId());
        return userReceiveAddressDao.insert(address);
    }

    @Override
    public int delete(Integer id){
        UserReceiveAddress userAddress = new UserReceiveAddress();
        userAddress.setId(id);
        return userReceiveAddressDao.deleteByPrimaryKey(id);
    }

    @Override
    public int update(Integer id, UserReceiveAddress address){
        address.setId(id);
        return userReceiveAddressDao.updateByPrimaryKey(address);
    }

    @Override
    public List<UserReceiveAddress> list(UserReceiveAddress address){
        return userReceiveAddressDao.selectByExample(address);
    }

    @Override
    public UserReceiveAddress getItem(Integer userId){
        UserReceiveAddress userAddress = new UserReceiveAddress();
        userAddress.setUserId(userId);
        List<UserReceiveAddress> addressList = userReceiveAddressDao.selectByExample(userAddress);
        if (!CollectionUtils.isEmpty(addressList)){
            return addressList.get(0);
        }
        return null;
    }
}
