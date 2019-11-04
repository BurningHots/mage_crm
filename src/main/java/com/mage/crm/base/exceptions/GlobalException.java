package com.mage.crm.base.exceptions;


import com.alibaba.fastjson.JSON;
import com.mage.crm.base.CrmConStant;
import com.mage.crm.model.MessageModel;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

@Component
public class GlobalException implements HandlerExceptionResolver {
    /***
     *  视图异常；return ModelAndView;
     *  Json异常：ResponseBody return null; MessageModel HttpServletResponse 写出去
     *  未登录异常：return modelAndView;
     * @param httpServletRequest
     * @param httpServletResponse
     * @param handler
     * @param e
     * @return
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest,
                 HttpServletResponse httpServletResponse, Object handler, Exception e) {
        ModelAndView modelAndView = createDefaultModelAndView(httpServletRequest);
        ParamsException paramsException;
        if (handler instanceof HandlerMethod){
            // 判断是否是未登录异常
            if (e instanceof ParamsException){
                // 类型转换
                paramsException =(ParamsException) e;
                if (paramsException.getCode()==CrmConStant.LOGIN_NO_CODE){
                    // 未登录异常
                    modelAndView.addObject("code",paramsException.getCode());
                    modelAndView.addObject("msg",paramsException.getMsg());
                    return modelAndView;
                }
            }

            // json异常
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            ResponseBody responseBody = method.getAnnotation(ResponseBody.class);

            // 判断是否有responseBody，存在：json异常，返回MessageModel
            if (responseBody!=null){
                MessageModel messageModel = new MessageModel();
                messageModel.setCode(CrmConStant.LOGIN_FALIED_CODE);
                messageModel.setMsg(CrmConStant.LOGIN_FALIED_MSG);

                if (e instanceof ParamsException){
                    // 类型转换
                    paramsException =(ParamsException) e;
                    messageModel.setCode(paramsException.getCode());
                    messageModel.setMsg(paramsException.getMsg());
                }
                httpServletResponse.setContentType("application/json;charset=utf-8");
                httpServletResponse.setCharacterEncoding("utf-8");
                PrintWriter printWrite = null;
                try {
                    printWrite = httpServletResponse.getWriter();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }finally{
                    if (printWrite!=null){
                        printWrite.write(JSON.toJSONString(messageModel));
                        printWrite.flush();
                        printWrite.close();
                    }
                }
                return  null;
            }else {
                // 视图异常
                if (e instanceof ParamsException){
                    paramsException = (ParamsException) e;
                    modelAndView.addObject("code",paramsException.getCode());
                    modelAndView.addObject("msg",paramsException.getMsg());
                    return modelAndView;
                }else {
                    return modelAndView;
                }
            }
        }
        return null;
    }

    private static ModelAndView createDefaultModelAndView(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("code", CrmConStant.OPS_FALIED_CODE);
        modelAndView.addObject("msg",CrmConStant.OPS_FALIED_MSG);
        // 为防止拦截器拦截(不放行)，没有controller，所以没有ctx
        modelAndView.addObject("ctx",request.getContextPath());
        modelAndView.addObject("uri",request.getRequestURI());
        return modelAndView;
    }
}
