package com.mage.crm.dao;

import com.mage.crm.query.CustomerRepriQuery;
import com.mage.crm.vo.CustomerReprieve;

import java.util.List;

public interface CustomerRepriDao {
    List<CustomerReprieve> customerReprieveByLossId(CustomerRepriQuery customerRepriQuery);

    int insertReprive(CustomerReprieve customerReprieve);

    int deleteReprive(Integer id);

    int updateReprive(CustomerReprieve customerReprieve);
}
