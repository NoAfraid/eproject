package com.eproject.dao;

import com.eproject.common.PageQuery;
import com.eproject.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDao {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    int countById(Integer userId);

    User selectPassword(Integer id);

    List<User> selectByPrimaryKey(User user);

    User selectUserInfo(User user);

    User selectUser(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int updateUserInfo(User user);

    int updatePassword(User user);

    int updatePic(User user);

    List<User> selectByPhone(User user);

    List<User> findUserList(PageQuery pageUtil);

    int lockUserBatch(@Param("ids") Integer[] ids, @Param("disableStatus") int disableStatus);

    User loginUser(User user);

    int insertUser(User user);
}