package com.mage.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mage.crm.dao.CustomerDevPlanDao;
import com.mage.crm.dao.SaleChanceDao;
import com.mage.crm.query.CusDevPlanQuery;
import com.mage.crm.util.AssertUtil;
import com.mage.crm.vo.CustomerDevPlan;
import com.mage.crm.vo.SaleChance;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerDevPlanService {
    @Resource
    private CustomerDevPlanDao customerDevPlanDao;
    @Resource
    private SaleChanceDao saleChanceDao;

    public Map<String, Object> queryCusDevPlans(CusDevPlanQuery cusDevPlanQuery) {
        PageHelper.startPage(cusDevPlanQuery.getPage(),cusDevPlanQuery.getRows());
        List<CustomerDevPlan> customerDevPlans = customerDevPlanDao.queryCusDevPlansBySaleChanceId(cusDevPlanQuery.getSaleChanceId());
        PageInfo<CustomerDevPlan> customerDevPlanPageInfo = new PageInfo<>(customerDevPlans);
        HashMap<String, Object> map = new HashMap<>();
        map.put("total",customerDevPlanPageInfo.getTotal());
        map.put("rows",customerDevPlanPageInfo.getList());
        return map;
    }

    public void insert(CustomerDevPlan customerDevPlan){
        SaleChance saleChance = saleChanceDao.querySaleChancesById(customerDevPlan.getSaleChanceId());
        check(saleChance);
        customerDevPlan.setIsValid(1);
        customerDevPlan.setCreateDate(new Date());
        customerDevPlan.setUpdateDate(new Date());
        AssertUtil.isTrue(customerDevPlanDao.insert(customerDevPlan)<1,"添加客户开发计划失败");
        if (saleChance.getDevResult()==0){
            saleChanceDao.updateDevResult(customerDevPlan.getSaleChanceId(),1);
        }
    }

    public void update(CustomerDevPlan customerDevPlan) {
        SaleChance saleChance = saleChanceDao.querySaleChancesById(customerDevPlan.getSaleChanceId());
        check(saleChance);
        customerDevPlan.setUpdateDate(new Date());
        AssertUtil.isTrue(customerDevPlanDao.update(customerDevPlan)<1,"客户开发计划更新失败");
    }

    public void check(SaleChance saleChance){
        AssertUtil.isTrue(saleChance == null,"营销机会已经不存在了");
        AssertUtil.isTrue(saleChance.getDevResult()==3,"营销机会已经完成");
        AssertUtil.isTrue(saleChance.getDevResult()==4,"营销机会已经失败");
    }

}
