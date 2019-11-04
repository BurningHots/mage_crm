package com.mage.crm.service;

import com.mage.crm.dao.RoleDao;
import com.mage.crm.vo.Role;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleService {

    @Resource
    private RoleDao roleDao;

    public List<Role> queryAllRoles() {
        return roleDao.queryAllRoles();

    }
}
