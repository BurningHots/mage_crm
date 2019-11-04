package com.mage.crm.controller;

import com.mage.crm.base.BaseController;
import com.mage.crm.model.MessageModel;
import com.mage.crm.query.CustomerServeQuery;
import com.mage.crm.service.CustomerServeService;
import com.mage.crm.vo.CustomerServe;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("customer_serve")
public class CustomerServeController extends BaseController {

    @Resource
    private CustomerServeService customerServeService;

    @RequestMapping("index/{type}")
    public String index(@PathVariable("type") Integer type){
        if (type == 1){
            return "customer_serve_create";
        }else if (type == 2){
            return "customer_serve_assign";
        }else if (type == 3){
            return "customer_serve_proceed";
        }else if (type == 4){
            return "customer_serve_feed_back";
        }else if (type == 5){
            return "customer_serve_archive";
        }else{
            return "error";
        }
    }

    @ResponseBody
    @RequestMapping("insert")
    public MessageModel insert(CustomerServe customerServe){
        customerServeService.insert(customerServe);
        return createMessageModel("服务创建成功");
    }

    @ResponseBody
    @RequestMapping("queryCustomerServeType")
    public Map<String,Object> queryCustomerServeType(){
        return customerServeService.queryCustomerServeType();
    }

    @ResponseBody
    @RequestMapping("queryCustomerServesByParams")
    public Map<String,Object> queryCustomerServesByParams(CustomerServeQuery customerServeQuery){
        return customerServeService.queryCustomerServesByParams(customerServeQuery);
    }
    @ResponseBody
    @RequestMapping("update")
    public MessageModel update(CustomerServe customerServe, HttpServletRequest request){
        customerServeService.update(customerServe,request);
        return createMessageModel("服务分配成功");
    }
}
