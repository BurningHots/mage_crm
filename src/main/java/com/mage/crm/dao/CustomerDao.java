package com.mage.crm.dao;

import com.mage.crm.dto.CustomerDto;
import com.mage.crm.query.CustomerGCQuery;
import com.mage.crm.query.CustomerQuery;
import com.mage.crm.vo.Customer;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface CustomerDao {
    @Select("select id,name from t_customer where is_valid = 1 and state = 0")
    List<Customer> queryAllCustomers();

    List<Customer> queryCustomersByParams(CustomerQuery customerQuery);

    int insert(Customer customer);

    int update(Customer customer);

    int delete(Integer[] id);

    Customer queryCustomerById(Integer id);

    @Update("UPDATE t_customer c SET c.state=1 WHERE c.khno = #{cusNo} and is_valid=1")
    int updateCustomerState(Object cssNo);

    List<CustomerDto> queryCustomersContribution(CustomerGCQuery customerGCQuery);

    List<CustomerDto> queryCustomerGC();
}
