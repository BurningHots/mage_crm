package com.mage.crm.dao;

import com.mage.crm.dto.ServeTypeDto;
import com.mage.crm.query.CustomerServeQuery;
import com.mage.crm.vo.CustomerServe;

import java.util.List;
import java.util.Map;

public interface CustomerServeDao {

    int insert(CustomerServe customerServe);

    List<ServeTypeDto> queryCustomerServeType();

    List<CustomerServe> queryCustomerServesByParams(CustomerServeQuery customerServeQuery);

    int update(CustomerServe customerServe);
}
