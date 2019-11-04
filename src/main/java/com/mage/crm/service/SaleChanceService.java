package com.mage.crm.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mage.crm.dao.SaleChanceDao;
import com.mage.crm.query.SaleChanceQuery;
import com.mage.crm.util.AssertUtil;
import com.mage.crm.vo.SaleChance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SaleChanceService {
    @Resource
    private SaleChanceDao saleChanceDao;

    /**
     *  条件查询并分页显示
     * @param saleChanceQuery
     * @return
     */
    public Map<String, Object> querySaleChancesByParams(SaleChanceQuery saleChanceQuery) {
        // 分页获取page和rows
        PageHelper.startPage(saleChanceQuery.getPage(),saleChanceQuery.getRows());
        List<SaleChance> saleChanceList = saleChanceDao.querySaleChancesByParams(saleChanceQuery);
        PageInfo<SaleChance> saleChancePageInfo = new PageInfo<>(saleChanceList);
        List<SaleChance> list = saleChancePageInfo.getList();
        Map<String, Object> map = new HashMap<>();
        map.put("rows",list);
        map.put("total",saleChancePageInfo.getTotal());
        return map;
    }

    /**
     * 添加saleChance数据
     * @param saleChance
     */
    public void addSaleChance(SaleChance saleChance) {
        // 参数验证
        checkParams(saleChance);
        // 设置默认参数
        saleChance.setIsValid(1);
        saleChance.setDevResult(0);

        // 调用dao层方法
        AssertUtil.isTrue(saleChanceDao.addSaleChance(saleChance)<1,"营销机会记录添加失败");
    }

    /**
     *  修改营机会记录
     * @param saleChance
     */
    public void updateSaleChance(SaleChance saleChance) {
        // 参数非空验证
        checkParams(saleChance);
        // 调用dao层方法
        AssertUtil.isTrue(saleChanceDao.updateSaleChance(saleChance)<1,"营销机会记录修改失败");
    }

    public void deleteSaleChance(Integer[] id) {
        //参数非空验证
        AssertUtil.isTrue(id.length<1,"请至少选择一条删除记录");
        // 调用dao层方法
        AssertUtil.isTrue(saleChanceDao.deleteSaleChance(id)<id.length,"删除营销机会记录失败");
    }

    /**
     *  删除营销机会记录
     * @param saleChance
     * @return
     */
    private SaleChance checkParams(SaleChance saleChance) {
        AssertUtil.isTrue(StringUtils.isBlank(saleChance.getLinkMan()),"联系人不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(saleChance.getLinkPhone()),"联系电话不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(saleChance.getCgjl()),"成功几率不能为空");
        // 判断是否分配并设置参数
        if (StringUtils.isBlank(saleChance.getAssignMan())){
            saleChance.setState(0);
        }else {
            saleChance.setState(1);
        }
        return saleChance;
    }


    public SaleChance querySaleChancesById(Integer id) {
        return saleChanceDao.querySaleChancesById(id);
    }
}
