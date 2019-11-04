package com.mage.crm.dao;

import com.mage.crm.vo.UserRole;

import java.util.List;

public interface UserRoleDao {

    int queryUserRoleCountsByUserId(Integer userId);

    int deleteUserRolesByUserId(Integer useId);

    int insertBatch(List<UserRole> userRoles);
}
