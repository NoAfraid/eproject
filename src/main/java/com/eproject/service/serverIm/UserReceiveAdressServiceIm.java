package com.eproject.service.serverIm;

import com.eproject.dao.TAddressCityDao;
import com.eproject.dao.TAddressProvinceDao;
import com.eproject.dao.TAddressTownDao;
import com.eproject.dao.UserReceiveAddressDao;
import com.eproject.entity.TAddressProvince;
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
    private TAddressProvinceDao provinceDao;

    @Resource
    private TAddressCityDao cityDao;

    @Resource
    private TAddressTownDao townDao;

    @Resource
    private UserService userService;

    @Override
    public int add(UserReceiveAddress address) {
        address.setUserId(address.getUserId());
        String province = provinceDao.selectByCode(address.getProvince());
        String city = cityDao.selectByCode(address.getCity());
        String region = townDao.selectByCode(address.getRegion());
        address.setCity(city);
        address.setProvince(province);
        address.setRegion(region);
        if (userReceiveAddressDao.selectById(address.getId()) != null){
            return userReceiveAddressDao.updateByPrimaryKeySelective(address);
        }
        return userReceiveAddressDao.insert(address);
    }

    @Override
    public int delete(Integer id) {
        UserReceiveAddress userAddress = new UserReceiveAddress();
        userAddress.setId(id);
        return userReceiveAddressDao.deleteByPrimaryKey(id);
    }

    @Override
    public int update(Integer id, UserReceiveAddress address) {
        address.setId(id);
        return userReceiveAddressDao.updateByPrimaryKey(address);
    }

    @Override
    public UserReceiveAddress selectById(Integer id){
        return userReceiveAddressDao.selectById(id);
    }
    @Override
    public List<UserReceiveAddress> list(UserReceiveAddress address) {
        List<UserReceiveAddress> userReceiveAddressList = userReceiveAddressDao.selectByExample(address);
//        if (userReceiveAddressList.size() > 0) {
//            for (UserReceiveAddress receiveAddress : userReceiveAddressList) {
//                String code = receiveAddress.getProvince();
//                String province = provinceDao.selectByCode(code);
//                String city = cityDao.selectByCode(receiveAddress.getCity());
//                String region = townDao.selectByCode(receiveAddress.getRegion());
//                receiveAddress.setProvince(province);
//                receiveAddress.setCity(city);
//                receiveAddress.setRegion(region);
//            }
//        }
        return userReceiveAddressList;
    }

    @Override
    public UserReceiveAddress getItem(Integer userId) {
        UserReceiveAddress userAddress = new UserReceiveAddress();
        userAddress.setUserId(userId);
        List<UserReceiveAddress> addressList = userReceiveAddressDao.selectByExample(userAddress);
        if (!CollectionUtils.isEmpty(addressList)) {
            return addressList.get(0);
        }
        return null;
    }

    @Override
    public void updateDefaultStatus(UserReceiveAddress address){
        List<UserReceiveAddress> addressList = userReceiveAddressDao.selectByPrimaryKey(address.getUserId());
        if (addressList != null){
            for (UserReceiveAddress receiveAddress : addressList){
                receiveAddress.setDefaultStatus(0);
                userReceiveAddressDao.updateByPrimaryKeySelective(receiveAddress);
            }
//            address.setDefaultStatus(1);
            userReceiveAddressDao.updateByPrimaryKeySelective(address);
        }
    }
}
