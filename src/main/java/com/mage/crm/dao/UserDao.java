package com.mage.crm.dao;

import com.mage.crm.dto.UserDto;
import com.mage.crm.query.UserQuery;
import com.mage.crm.vo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    public User queryUserByName(String userName);

    public User queryUserById(String deCode);

    public int updatePwd(@Param("id") String id,@Param("userPwd") String userPwd);

    List<User> queryAllCustomerManager();

    List<UserDto> queryUsersByParams(UserQuery userQuery);

    int insert(User user);

    int delete(Integer id);

    int update(User user);
}
