package com.mage.crm.controller;

import com.mage.crm.base.BaseController;
import com.mage.crm.service.RoleService;
import com.mage.crm.vo.Role;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("role")
public class RoleController extends BaseController {
    @Resource
    private RoleService roleService;
    @ResponseBody
    @RequestMapping("queryAllRoles")
    public List<Role> queryAllRoles(){
        return roleService.queryAllRoles();
    }

}
