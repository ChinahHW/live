package com.huwei.live.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.huwei.live.response.base.BaseResponse;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: 异常同一处理
 *
 * @author zlp
 * @create 2018-06-07 11:12
 **/
public class GlobalExceptionResolver implements HandlerExceptionResolver {

    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView mv;
        logger.error(ExceptionUtils.getFullStackTrace(ex));
        if(ex instanceof UnauthorizedException){

            mv = new ModelAndView("/user/unauth");
            return mv;
        }else {
            mv = new ModelAndView();
            FastJsonJsonView view = new FastJsonJsonView();
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setMsg("服务器异常");
            ex.printStackTrace();
            Map<String,Object> map = new HashMap<>();
            String beanString = JSON.toJSONString(baseResponse);
            map = JSON.parseObject(beanString,Map.class);
            view.setAttributesMap(map);
            mv.setView(view);
            return mv;

        }

    }
}
