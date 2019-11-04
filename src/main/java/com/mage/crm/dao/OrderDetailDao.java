package com.mage.crm.dao;

import com.mage.crm.vo.OrderDetail;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OrderDetailDao {
    @Select("SELECT\n" +
            "\tid,\n" +
            "\tgoods_name 'goodsName',\n" +
            "\tgoods_num 'goodsNum',\n" +
            "\tunit,\n" +
            "\tprice,\n" +
            "\tsum\n" +
            "FROM\n" +
            "\tt_order_details\n" +
            "WHERE\n" +
            "\torder_id = #{orderId}")
    List<OrderDetail> queryOrderDetailsByOrderId(Integer orderId);
}
