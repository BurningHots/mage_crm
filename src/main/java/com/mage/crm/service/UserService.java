package com.mage.crm.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mage.crm.dao.UserDao;
import com.mage.crm.dao.UserRoleDao;
import com.mage.crm.dto.UserDto;
import com.mage.crm.model.UserModel;
import com.mage.crm.query.UserQuery;
import com.mage.crm.util.AssertUtil;
import com.mage.crm.util.Base64Util;
import com.mage.crm.util.Md5Util;
import com.mage.crm.vo.User;
import com.mage.crm.vo.UserRole;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Resource
    private UserDao userDao;
    @Resource
    private UserRoleDao userRoleDao;

    /**
     *  用户登录
     * @param userName
     * @param userPwd
     * @return
     */
    public UserModel uesrLogin(String userName,String userPwd){
        User user = userDao.queryUserByName(userName);
        AssertUtil.isTrue(user == null,"用户不存在");
        // 密码加密
        userPwd = Md5Util.enCode(userPwd);
        AssertUtil.isTrue(!userPwd.equals(user.getUserPwd()),"用户名或密码错误");
        AssertUtil.isTrue("0".equals(user.getIsValid()),"用户已注销");
        return createUserModel(user);
    }

    private UserModel createUserModel(User user) {
        UserModel userModel = new UserModel();
        userModel.setId(Base64Util.enCode(user.getId()));
        userModel.setUserName(user.getUserName());
        userModel.setTrueName(user.getTrueName());
        return userModel;
    }

    public void updatePwd(String id, String oldPassWord, String newPassWord, String confirmPassWord) {
        AssertUtil.isTrue(StringUtils.isBlank(id),"id不存在");
        AssertUtil.isTrue(StringUtils.isBlank(newPassWord),"新密码不能为空");
        AssertUtil.isTrue(oldPassWord.equals(newPassWord),"新密码与原始密码不能一致");
        AssertUtil.isTrue(!newPassWord.equals(confirmPassWord),"新密码与确认密码不一致");
        User user = userDao.queryUserById(Base64Util.deCode(id));
        AssertUtil.isTrue(user==null,"用户不存在");
        AssertUtil.isTrue("0".equals(user.getIsValid()),"用户已注销");
        AssertUtil.isTrue(!Md5Util.enCode(oldPassWord).equals(user.getUserPwd()),"原始密码错误");
        AssertUtil.isTrue(userDao.updatePwd(user.getId(),Md5Util.enCode(newPassWord))<1,"用户密码修改失败");
    }

    public User queryUserById(String id){
        User user = userDao.queryUserById(id);
        return user;
    }

    public List<User> queryAllCustomerManager() {
        return userDao.queryAllCustomerManager();
    }

    public Map<String, Object> queryUsersByParams(UserQuery userQuery) {
        PageHelper.startPage(userQuery.getPage(),userQuery.getRows());
        List<UserDto> userDtos = userDao.queryUsersByParams(userQuery);
        if (userDtos!= null || userDtos.size()>0){
            for (UserDto userDto : userDtos) {
                String roleIdstr = userDto.getRoleIdsStr();
                if (roleIdstr != null){
                    String[] roleIdArray = roleIdstr.split(",");
                    for (int i =0;i<userDtos.size();i++){
                        List<Integer> roleIds = userDto.getRoleIds();
                        roleIds.add(Integer.parseInt(roleIdArray[i]));
                    }
                }
            }
        }
        PageInfo<UserDto> userDtoPageInfo = new PageInfo<>(userDtos);
        Map<String, Object> map = new HashMap<>();
        map.put("total",userDtoPageInfo.getTotal());
        map.put("rows",userDtoPageInfo.getList());
        return map;
    }

    public void insert(User user) {
        checkParams(user.getUserName(),user.getTrueName(),user.getPhone());
        AssertUtil.isTrue(userDao.queryUserByName(user.getUserName())!=null,"用户已存在");
        user.setIsValid("1");
        user.setUserPwd("123456");
        AssertUtil.isTrue(userDao.insert(user)<1,"用户添加失败");
    }

    private void checkParams(String userName, String trueName, String phone) {
        AssertUtil.isTrue(StringUtils.isBlank(userName),"用户名不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(trueName),"真实名称不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(phone),"联系方式不能为空");
    }

    public void delete(Integer id) {
        AssertUtil.isTrue(userDao.delete(id)<1,"用户数据删除失败");
        int count = userRoleDao.queryUserRoleCountsByUserId(id);
        if (count>0){
            AssertUtil.isTrue(userRoleDao.deleteUserRolesByUserId(id)<count,"用户角色级联删除失败");
        }
    }

    public void update(User user) {
        checkParams(user.getUserName(),user.getTrueName(),user.getPhone());
        User tempuser = userDao.queryUserByName(user.getUserName());
        AssertUtil.isTrue(tempuser!=null&&!user.getId().equals(tempuser.getId()),"用户名称已存在");
        AssertUtil.isTrue(userDao.update(user)<1,"用户信息修改失败");

        userRoleDao.deleteUserRolesByUserId(Integer.parseInt(user.getId()));
        List<Integer> roleIds = user.getRoleIds();
        // 级联操作，添加用户角色
        if (roleIds!=null||roleIds.size()>0) relateRoles(Integer.parseInt(user.getId()), roleIds);
    }

    private void relateRoles(Integer userId, List<Integer> roleIds) {
        List<UserRole> userRoles = new ArrayList<>();
        for (Integer roleId: roleIds) {
            UserRole userRole = new UserRole();
            userRole.setRoleId(roleId);
            userRole.setIsValid(1);
            userRole.setUserId(userId);
            userRoles.add(userRole);
        }
        AssertUtil.isTrue(userRoleDao.insertBatch(userRoles)<userRoles.size(),"用户角色添加失败");
    }
}
