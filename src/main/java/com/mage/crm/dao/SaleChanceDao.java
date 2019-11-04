package com.mage.crm.dao;

import com.mage.crm.query.SaleChanceQuery;
import com.mage.crm.vo.SaleChance;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SaleChanceDao {

    List<SaleChance> querySaleChancesByParams(SaleChanceQuery saleChanceQuery);

    int addSaleChance(SaleChance saleChance);

    int updateSaleChance(SaleChance saleChance);

    int deleteSaleChance(Integer[] id);

    SaleChance querySaleChancesById(Integer id);

    int updateDevResult(@Param("id") Integer saleChanceId,@Param("dev") int i);
}
