package com.huwei.live.config.shiro;

import com.huwei.live.config.GlobalExceptionResolver;
import com.huwei.live.fitter.ShiroSessionManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {

        System.out.println("--------------------shiro filter-------------------");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 拦截器.
        //rest：比如/admins/user/**=rest[user],根据请求的方法，相当于/admins/user/**=perms[user：method] ,其中method为post，get，delete等。
        //port：比如/admins/user/**=port[8081],当请求的url的端口不是8081是跳转到schemal：//serverName：8081?queryString,其中schmal是协议http或https等，serverName是你访问的host,8081是url配置里port的端口，queryString是你访问的url里的？后面的参数。
        //perms：比如/admins/user/**=perms[user：add：*],perms参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，比如/admins/user/**=perms["user：add：*,user：modify：*"]，当有多个参数时必须每个参数都通过才通过，想当于isPermitedAll()方法。
        //roles：比如/admins/user/**=roles[admin],参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，当有多个参数时，比如/admins/user/**=roles["admin,guest"],每个参数通过才算通过，相当于hasAllRoles()方法。//要实现or的效果看http://zgzty.blog.163.com/blog/static/83831226201302983358670/
        //anon：比如/admins/**=anon 没有参数，表示可以匿名使用。
        //authc：比如/admins/user/**=authc表示需要认证才能使用，没有参数
        //authcBasic：比如/admins/user/**=authcBasic没有参数表示httpBasic认证
        //ssl：比如/admins/user/**=ssl没有参数，表示安全的url请求，协议为https
        //user：比如/admins/user/**=user没有参数表示必须存在用户，当登入操作时不做检查

        //注意过滤器配置顺序 不能颠倒
        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了，登出后跳转配置的loginUrl
        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/static/**", "anon");

        //前端静态文件不拦截
        filterChainDefinitionMap.put("/frame/**","anon");
        //前端静态文件不拦截
        filterChainDefinitionMap.put("/js/**","anon");
        filterChainDefinitionMap.put("/css/**","anon");
        filterChainDefinitionMap.put("/images/**","anon");
        filterChainDefinitionMap.put("/img/**","anon");
        filterChainDefinitionMap.put("/table/**","anon");
        filterChainDefinitionMap.put("/user/**","anon");
        filterChainDefinitionMap.put("/fonts/**","anon");
        filterChainDefinitionMap.put("/*.html","anon");
        filterChainDefinitionMap.put("/favicon.ico", "anon");
        filterChainDefinitionMap.put("/","anon");
        //druid监控界面不拦截
        //filterChainDefinitionMap.put("/druid/**","anon");
        //其他无需拦截接口
        //这段是配合 actuator框架使用的，配置相应的角色才能访问
//        filterChainDefinitionMap.put("/health", "roles[aix]");//服务器健康状况页面
//        filterChainDefinitionMap.put("/info", "roles[aix]");//服务器信息页面
//        filterChainDefinitionMap.put("/env", "roles[aix]");//应用程序的环境变量
//        filterChainDefinitionMap.put("/metrics", "roles[aix]");
//        filterChainDefinitionMap.put("/configprops", "roles[aix]");
        filterChainDefinitionMap.putAll(anonInterface());
        //拦截其他所以接口
        filterChainDefinitionMap.put("/**", "user");

        //配置shiro默认登录界面地址，前后端分离中登录界面跳转应由前端路由控制，后台仅返回json数据
        shiroFilterFactoryBean.setLoginUrl("/user/unlogin");
        // 登录成功后要跳转的链接 自行处理。不用shiro进行跳转
        // shiroFilterFactoryBean.setSuccessUrl("user/index");
        //未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/user/unauth");
        //退出登录。
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }



    /**
     * shiro 用户数据注入
     * @return
     */
    @Bean
    public ShiroRealm shiroRealm(){
        ShiroRealm shiroRealm = new ShiroRealm();
        //  shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return shiroRealm;
    }

    /**
     * 配置管理层。即安全控制层
     * @return
     */
    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm());
        //自定义session管理
        securityManager.setSessionManager(sessionManager());
        //自定义缓存实现
        securityManager.setCacheManager(ehCacheManager());
        //注入记住登录状态
        securityManager.setRememberMeManager(cookieRememberMeManager());
        return  securityManager;
    }

    /**
     * 自定义sessionManager
     * @return
     */
    @Bean
    public SessionManager sessionManager(){
        ShiroSessionManager shiroSessionManager = new ShiroSessionManager();
        shiroSessionManager.setSessionDAO(new EnterpriseCacheSessionDAO());
        return shiroSessionManager;
    }

    /**
     * 自定义sessionManager
     * @return
     */
   /* @Bean
    public SessionManager sessionManager(){
        ShiroSessionManager shiroSessionManager = new ShiroSessionManager();
        shiroSessionManager.setSessionDAO(new EnterpriseCacheSessionDAO());
        return shiroSessionManager;
    }*/

    /**
     * 开启缓存
     * shiro-ehcache实现
     * @return
     */
    @Bean
    public EhCacheManager ehCacheManager() {
        System.out.println("ShiroConfiguration.getEhCacheManager()");
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
        return ehCacheManager;
    }

    /**
     * 记住密码选项
     * @return
     */
    @Bean
    public SimpleCookie rememberMeCookie() {
        System.out.println("ShiroConfiguration.rememberMeCookie()");
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");

        //<!-- 记住我cookie生效时间30天 ,单位秒;-->
        simpleCookie.setMaxAge(259200);
        return simpleCookie;
    }

    //cookie管理对象;
    @Bean
    public CookieRememberMeManager cookieRememberMeManager() {
        System.out.println("ShiroConfiguration.rememberMeManager()");
        CookieRememberMeManager manager = new CookieRememberMeManager();
        manager.setCookie(rememberMeCookie());
        return manager;
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }
    /**
     * 开启shiro aop注解支持 使用代理方式所以需要开启代码支持
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 注册全局异常处理
     * @return
     */
    @Bean(name = "exceptionHandler")
    public HandlerExceptionResolver handlerExceptionResolver(){
        return new GlobalExceptionResolver();
    }


    public Map<String,String> anonInterface(){
        Map<String,String> map = new LinkedHashMap<>();
        //用户注册相关接口
        map.put("/user/login","anon");
        return map;
    }
}
