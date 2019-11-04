package com.mage.crm.controller;

import com.mage.crm.base.BaseController;
import com.mage.crm.model.MessageModel;
import com.mage.crm.query.SaleChanceQuery;
import com.mage.crm.service.SaleChanceService;
import com.mage.crm.vo.SaleChance;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("saleChance")
public class SaleChanceController extends BaseController {
    @Resource
    private SaleChanceService saleChanceService;

    @RequestMapping("index/{id}")
    public String saleIndex(@PathVariable("id") String id){
        if ("1".equals(id)){
            return "sale_chance";
        }else if ("2".equals(id)){
            return "cus_dev_plan";
        }else {
            return "error";
        }
    }

    /**
     *  分页查询并初始化显示数据
     * @param saleChanceQuery
     * @return
     */
    @ResponseBody
    @RequestMapping("querySaleChancesByParams")
    public Map<String,Object> querySaleChancesByParams(SaleChanceQuery saleChanceQuery){
        // 调用Service层方法并返回给前台显示
        return saleChanceService.querySaleChancesByParams(saleChanceQuery);
    }

    /**
     *  添加saleChance数据
     * @param saleChance
     * @return
     */
    @ResponseBody
    @RequestMapping("add")
    public MessageModel addSaleChance(SaleChance saleChance){
        // 调用service方法
        saleChanceService.addSaleChance(saleChance);
        return createMessageModel("营销机会插入成功");
    }

    /**
     *  修改选中的营销机会记录数据
     * @param saleChance
     * @return
     */
    @ResponseBody
    @RequestMapping("updateSaleChance")
    public MessageModel updateSaleChance(SaleChance saleChance){
        // 调用service方法
        saleChanceService.updateSaleChance(saleChance);
        return createMessageModel("营销机会修改成功");
    }

    /**
     *  通过参数id删除营销机会记录
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("deleteSaleChance")
    public MessageModel deleteSaleChance(Integer[] id){
        // 调用service层方法
        saleChanceService.deleteSaleChance(id);
        return createMessageModel("删除营销机会成功");
    }

}
