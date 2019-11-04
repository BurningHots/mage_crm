package com.mage.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mage.crm.dao.CustomerRepriDao;
import com.mage.crm.query.CustomerRepriQuery;
import com.mage.crm.util.AssertUtil;
import com.mage.crm.vo.CustomerReprieve;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerRepriService {
    @Resource
    private CustomerLossService customerLossService;

    @Resource
    private CustomerRepriDao customerRepriDao;

    public Map<String, Object> customerReprieveByLossId(CustomerRepriQuery customerRepriQuery) {
        PageHelper.startPage(customerRepriQuery.getPage(),customerRepriQuery.getRows());
        List<CustomerReprieve> list = customerRepriDao.customerReprieveByLossId(customerRepriQuery);
        PageInfo<CustomerReprieve> reprievePageInfo = new PageInfo<>(list);
        Map<String, Object> map = new HashMap<>();
        map.put("total",reprievePageInfo.getTotal());
        map.put("rows",reprievePageInfo.getList());
        return map;
    }

    public void insertReprive(CustomerReprieve customerReprieve) {
        checkParams(customerReprieve.getLossId(),customerReprieve.getMeasure());
        // 设置默认参数
        customerReprieve.setIsValid(1);
        customerReprieve.setCreateDate(new Date());
        customerReprieve.setUpdateDate(new Date());
        AssertUtil.isTrue(customerRepriDao.insertReprive(customerReprieve)<1,"暂缓措施添加失败");
    }

    private void checkParams(Integer lossId, String measure) {
        AssertUtil.isTrue(StringUtils.isBlank(measure),"暂缓措施不能为空");
        Map<String, Object> map = customerLossService.queryCustomerLossesById(lossId);
        AssertUtil.isTrue(lossId==null||map==null||map.isEmpty(),"流失记录不存在");
    }

    public void updateReprive(CustomerReprieve customerReprieve) {
        checkParams(customerReprieve.getLossId(),customerReprieve.getMeasure());
        customerReprieve.setUpdateDate(new Date());
        AssertUtil.isTrue(customerRepriDao.updateReprive(customerReprieve)<1,"暂缓措施修改成功");
    }

    public void deleteReprive(Integer id) {
        AssertUtil.isTrue(customerRepriDao.deleteReprive(id)<1,"暂缓措施删除失败");
    }
}
