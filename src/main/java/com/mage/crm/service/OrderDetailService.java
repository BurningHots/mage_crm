package com.mage.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mage.crm.dao.OrderDetailDao;
import com.mage.crm.query.OrderDetailQuery;
import com.mage.crm.vo.OrderDetail;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderDetailService {
    @Resource
    private OrderDetailDao orderDetailDao;

    public Map<String, Object> queryOrderDetailsByOrderId(OrderDetailQuery orderDetailQuery) {
        PageHelper.startPage(orderDetailQuery.getPage(), orderDetailQuery.getRows());
        // 调用dao方法
        List<OrderDetail> orderDetails = orderDetailDao.queryOrderDetailsByOrderId(orderDetailQuery.getOrderId());
        PageInfo<OrderDetail> orderDetailPageInfo = new PageInfo<>(orderDetails);
        Map<String, Object> map = new HashMap<>();
        map.put("total",orderDetailPageInfo.getTotal());
        map.put("rows",orderDetailPageInfo.getList());
        return map;
    }
}
