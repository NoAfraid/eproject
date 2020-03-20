package com.eproject.service.serverIm;

import com.eproject.common.R;
import com.eproject.common.Result;
import com.eproject.dao.UserDao;
import com.eproject.entity.User;
import com.eproject.service.UserService;
import com.eproject.util.MD5Encode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceIm implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public String register(User registerUser){
        registerUser.setCreateTime(new Date());
        String username = "";
//        String tel = userDao.selectByPhone(username, phone);
        List<User> num = userDao.selectByPhone(registerUser);
        Object[] tel = num.toArray();
        //验证用户名和手机号是否存在
       if (num.size() > 0){
               return Result.SAME_LOGIN_NAME_EXIST.getResult();
        }
        String passwordMD5 = MD5Encode.MD5Encode(registerUser.getPassword(),"UTF-8");
        registerUser.setPassword(passwordMD5);
        registerUser.setPhone(registerUser.getPhone());
        registerUser.setUsername(registerUser.getUsername());
        if (userDao.insertUser(registerUser) > 0){
            return Result.SUCCESS.getResult();
        }
        return Result.OPERTE_ERROR.getResult();
    }

    @Override
    public User  loginUser(User loginUser){
        User login = userDao.loginUser(loginUser);
//       if (login.getPassword().equals(loginUser.getPassword())){
//           return
//       }
        return login;
    }

    @Override
    public List<User> selectUserInfo(User userInfo){
        List<User> selectInfo = userDao.selectUserInfo(userInfo);
        return selectInfo;
    }

    @Override
    public List<User> updateUserInfo(User updateUser){
        userDao.updateUserInfo(updateUser);
        List<User> UpdateUserInfo = userDao.selectUserInfo(updateUser);
        return UpdateUserInfo;
    }

    @Override
    public void updatePassword(Integer id, String originalPassword, String newPassword){
        User updatePassword = userDao.selectPassword(id);
        if (updatePassword != null){
            String originalPasswordMd5 = MD5Encode.MD5Encode(originalPassword,"UTF-8");
            String newPasswordMD5 = MD5Encode.MD5Encode(newPassword,"UTF-8");
            //比较和原密码是否相同
            if (originalPasswordMd5.equals(updatePassword.getPassword())){
//                R.error(-1,"原密码输入错误");
                updatePassword.setPassword(newPasswordMD5);
                userDao.updatePassword(updatePassword);
            }
//            if (newPasswordMD5.equals(originalPasswordMd5)){
//                return R.error(-1,"新旧密码一致");
//            }
        }
    }
}
