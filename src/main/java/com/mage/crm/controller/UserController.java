package com.mage.crm.controller;

import com.mage.crm.base.BaseController;
import com.mage.crm.base.CrmConStant;
//import com.mage.crm.base.exceptions.ParamsException;
import com.mage.crm.model.MessageModel;
import com.mage.crm.model.UserModel;
import com.mage.crm.query.UserQuery;
import com.mage.crm.service.UserService;
import com.mage.crm.util.CookieUtil;
import com.mage.crm.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController extends BaseController {
    @Resource
    private UserService userService;

    @RequestMapping("index")
    public String index(){
        return "user";
    }

    /**
     *  用户登录
     * @param userName
     * @param userPwd
     * @return
     */
    @ResponseBody
    @RequestMapping("userLogin")
    public MessageModel userLogin(String userName,String userPwd){
        MessageModel messageModel = new MessageModel();
        // 设置默认状态码和提示信息
        messageModel.setMsg(CrmConStant.OPS_SUCCESS_MSG);
        messageModel.setCode(CrmConStant.OPS_SUCCESS_CODE);
//        try{
            UserModel userModel = userService.uesrLogin(userName, userPwd);
            messageModel.setResult(userModel);
        /*}catch (ParamsException pe){
            pe.printStackTrace();
            messageModel.setCode(CrmConStant.LOGIN_FALIED_CODE);
            messageModel.setMsg(pe.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            messageModel.setCode(CrmConStant.LOGIN_FALIED_CODE);
            messageModel.setMsg(CrmConStant.LOGIN_FALIED_MSG);
        }*/
        return messageModel;
    }

    /**
     *  修改用户密码
     * @param request
     * @param oldPassword
     * @param newPassword
     * @param confirmPassword
     * @return
     */
    @RequestMapping("updatePwd")
    @ResponseBody
    public MessageModel updatePwd(HttpServletRequest request,String oldPassword,String newPassword,String confirmPassword){
        MessageModel messageModel = new MessageModel();
        String id = CookieUtil.getCookieValue(request, "id");
//        try{
            userService.updatePwd(id,oldPassword,newPassword,confirmPassword);
            messageModel.setMsg("用户密码修改成功");
       /* }catch (ParamsException pe){
            pe.printStackTrace();
            messageModel.setCode(CrmConStant.LOGIN_FALIED_CODE);
            messageModel.setMsg(pe.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            messageModel.setCode(CrmConStant.LOGIN_FALIED_CODE);
            messageModel.setMsg(CrmConStant.LOGIN_FALIED_MSG);
        }*/
        return messageModel;
    }

    /**
     * 查询所有的客户经理
     * @return
     */
    @ResponseBody
    @RequestMapping("queryAllCustomerManager")
    public List<User> queryAllCustomerManager(){
        return userService.queryAllCustomerManager();
    }

    /**
     *  查询用户信息
     * @param userQuery
     * @return
     */
    @ResponseBody
    @RequestMapping("queryUsersByParams")
    public Map<String,Object> queryUsersByParams(UserQuery userQuery){
        return userService.queryUsersByParams(userQuery);
    }

    /**
     * 添加用户信息
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping("insert")
    public MessageModel insert(User user){
        userService.insert(user);
        return createMessageModel("用户添加成功");
    }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping("update")
    public MessageModel update(User user){
        userService.update(user);
        return createMessageModel("用户修改成功");
    }

    /**
     * 删除用户信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("delete")
    public MessageModel delete(Integer id){
        userService.delete(id);
        return createMessageModel("用户信息删除成功");
    }
}
