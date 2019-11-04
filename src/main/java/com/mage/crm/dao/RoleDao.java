package com.mage.crm.dao;

import com.mage.crm.vo.Role;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleDao {
    @Select("select id,role_name as 'roleName' from t_role where is_valid = 1")
    List<Role> queryAllRoles();

}
