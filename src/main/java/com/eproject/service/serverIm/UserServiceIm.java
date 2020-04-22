package com.eproject.service.serverIm;

import com.eproject.common.PageQuery;
import com.eproject.common.PageResult;
import com.eproject.common.R;
import com.eproject.common.Result;
import com.eproject.dao.CollectDao;
import com.eproject.dao.FollowDao;
import com.eproject.dao.UserDao;
import com.eproject.entity.Collect;
import com.eproject.entity.Follow;
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

    @Resource
    private CollectDao collectDao;

    @Resource
    private FollowDao followDao;

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
    public User selectUserInfo(User userInfo){
        User selectInfo = userDao.selectUserInfo(userInfo);
        return selectInfo;
    }

    @Override
    public User updateUserInfo(User updateUser){
        userDao.updateUserInfo(updateUser);
        User UpdateUserInfo = userDao.selectUserInfo(updateUser);
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
                //R.error(-1,"原密码输入错误");
                updatePassword.setPassword(newPasswordMD5);
                userDao.updatePassword(updatePassword);
            }
        }
    }

    @Override
    public String updatePic(User updateUserPic){
        User selectInfo = userDao.selectUserInfo(updateUserPic);
        if (selectInfo == null){
            return Result.DATA_NOT_EXIST.getResult();
        }
        User updatePic = new User();
        updatePic.setPic(updateUserPic.getPic());
        if (userDao.updatePic(updateUserPic) > 0){
            return Result.SUCCESS.getResult();
        }else {
            return Result.OPERTE_ERROR.getResult();
        }
    }

    @Override
    public int selectCollectInfo(Integer id, Integer userId){
        //判断用户是否为null
        int count = userDao.countById(userId);
        if(count <= 0){
            return -2;
        }
        // 获取是否收藏过
        int num = collectDao.selectByCollect(id,userId);
        //判断是否存在已经收藏的，（已收藏：-1，未收藏：1）
        if (num > 0){
            return -1;
        }
        int collectInfo = collectDao.selectCollectInfo(id, userId);
        return 1;
    }

    @Override
    public int insertFollowInfo(Integer id, Integer userId){
        //判断用户是否为null
        int count = userDao.countById(userId);
        if(count <= 0){
            return -2;
        }
        // 获取是否收藏过
        int num = followDao.selectByFollow(id,userId);
        //判断是否存在已经收藏的，（已收藏：-1，未收藏：1）
        if (num > 0){
            return -1;
        }
        Follow follow = new Follow();
        int collectInfo = followDao.insertFollowInfo(id, userId);
        return 1;
    }

    @Override
    public int updateFollowStatus(Integer id, Integer status){
       return followDao.updateFollowStatus(id, status);
    }

    @Override
    public int updateCollectStatus(Integer id, Integer status){
        return collectDao.updateCollectStatus(id, status);
    }

    @Override
    public PageResult selectInfo(PageQuery pageQuery){
        List<Collect> collectList = collectDao.selectInfo(pageQuery);
        int total = collectList.size();
        PageResult pageResult = new PageResult(collectList, total, pageQuery.getLimit(), pageQuery.getPage());
        return pageResult;
    }

    @Override
    public PageResult selectFollowInfo(PageQuery pageQuery){
        List<Follow> followList = followDao.selectInfo(pageQuery);
        int total = followList.size();
        PageResult pageResult = new PageResult(followList, total, pageQuery.getLimit(), pageQuery.getPage());
        return pageResult;
    }

}
