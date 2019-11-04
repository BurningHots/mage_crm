package com.mage.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mage.crm.dao.CustomerServeDao;
import com.mage.crm.dto.ServeTypeDto;
import com.mage.crm.query.CustomerServeQuery;
import com.mage.crm.util.AssertUtil;
import com.mage.crm.util.CookieUtil;
import com.mage.crm.vo.CustomerServe;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerServeService {

    @Resource
    private CustomerServeDao customerServeDao;

    public void insert(CustomerServe customerServe) {
        checkParams(customerServe.getServeType(),customerServe.getCustomer(),customerServe.getServiceRequest());
        customerServe.setIsValid(1);
        customerServe.setState("1");
        customerServe.setCreateDate(new Date());
        customerServe.setUpdateDate(new Date());
        AssertUtil.isTrue(customerServeDao.insert(customerServe)<1,"服务创建失败");
    }

    private void checkParams(String serveType, String customer, String serviceRequest) {
        AssertUtil.isTrue(StringUtils.isBlank(serveType),"服务类型不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(customer),"客户名称不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(serviceRequest),"服务内容不能为空");
    }

    public Map<String, Object> queryCustomerServeType() {
        List<ServeTypeDto> serveTypeDtos = customerServeDao.queryCustomerServeType();
        Map<String, Object> map = new HashMap<>();
        map.put("code",300);
        String[] types;
        ServeTypeDto[] datas;
        if (serveTypeDtos !=null|| serveTypeDtos.size()>0){
            types = new String[serveTypeDtos.size()];
            datas = new ServeTypeDto[serveTypeDtos.size()];
            for (int i =0;i<serveTypeDtos.size();i++){
                types[i] = serveTypeDtos.get(i).getName();
                datas[i] = serveTypeDtos.get(i);
            }
            map.put("types",types);
            map.put("datas",datas);
            map.put("code",200);
        }
        return map;
    }

    public Map<String, Object> queryCustomerServesByParams(CustomerServeQuery customerServeQuery) {
        PageHelper.startPage(customerServeQuery.getPage(),customerServeQuery.getRows());
        List<CustomerServe> customerServes = customerServeDao.queryCustomerServesByParams(customerServeQuery);
        PageInfo<CustomerServe> pageInfo = new PageInfo<>(customerServes);
        Map<String, Object> map = new HashMap<>();
        map.put("total",pageInfo.getTotal());
        map.put("rows",pageInfo.getList());
        return map;
    }

    public void update(CustomerServe customerServe, HttpServletRequest request) {
        customerServe.setUpdateDate(new Date());
        if (customerServe.getState().equals("2")){
            customerServe.setAssigner(CookieUtil.getCookieValue(request,"trueName"));
            customerServe.setAssignTime(new Date());
        }else if (customerServe.getState().equals("3")){
            AssertUtil.isTrue(StringUtils.isBlank(customerServe.getServiceProce()),"处理内容不能为空");
            customerServe.setServiceProceTime(new Date());
        }else if (customerServe.getState().equals("3")){
            AssertUtil.isTrue(StringUtils.isBlank(customerServe.getServiceProceResult()),"处理结果不能为空");
            AssertUtil.isTrue(StringUtils.isBlank(customerServe.getMyd()),"满意度不能为空");
            customerServe.setState("5");
        }
        AssertUtil.isTrue(customerServeDao.update(customerServe)<1,"服务分配失败");
    }
}
