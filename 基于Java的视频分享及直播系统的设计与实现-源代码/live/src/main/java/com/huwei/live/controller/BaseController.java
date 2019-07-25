package com.huwei.live.controller;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取用户访问对应的session
     * @return
     */
    protected HttpSession getSession(){
        return getRequest().getSession();
    }

    /**
     * 获取HttpServletRequest 对象
     * @return
     */
    protected HttpServletRequest getRequest(){

        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     *获取用登录的ip地址
     * @return
     */
    protected String getUserIp(){
        String value = getRequest().getHeader("X-Real-IP");
        if(StringUtils.isNotEmpty(value) && !"unknown".equals(value)){
            return value;
        }else{
            return getRequest().getRemoteAddr();
        }
    }

    protected void error(Exception e){
        e.printStackTrace();
        logger.error(ExceptionUtils.getFullStackTrace(e));
    }
}
