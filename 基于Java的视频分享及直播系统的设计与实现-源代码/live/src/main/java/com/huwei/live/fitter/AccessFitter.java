package com.huwei.live.fitter;

import org.apache.commons.httpclient.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description: 解决跨域请求问题。
 *
 * @author zlp
 * @create 2018-07-02 11:18
 **/
@Component
public class AccessFitter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        res.setContentType("text/html;charset=UTF-8");
        res.addHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
        res.addHeader("Access-Control-Allow-Methods", "*");
        res.addHeader("Access-Control-Max-Age", "3600");
        res.addHeader("Access-Control-Allow-Credentials", "true");
        res.addHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,live_token");
        req.setCharacterEncoding("UTF-8");
        if (req.getMethod().equals( "OPTIONS" )) {
            res.setStatus(HttpStatus.SC_OK);
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
