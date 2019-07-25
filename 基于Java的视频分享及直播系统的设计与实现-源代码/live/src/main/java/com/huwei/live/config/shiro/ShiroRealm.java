package com.huwei.live.config.shiro;

import com.huwei.live.pojo.User;
import com.huwei.live.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

public class ShiroRealm extends AuthorizingRealm {

    private static Logger logger = LoggerFactory.getLogger(ShiroRealm.class);


    @Resource
    private UserService userService;

    /**
     * 配置权限 注入权限
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals){
        System.out.println("--------权限配置-------");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User user = (User) principals.getPrimaryPrincipal();
        try {
            //注入角色

            //注入角色所有权限
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return authorizationInfo;
    }

    /**
     * 用户验证
     * @param token 账户数据
     * @return
     * @throws AuthenticationException 根据账户数据查询账户。根据账户状态抛出对应的异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        //获取用户的输入的账号
        String username = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());
        //通过username从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        User user = userService.login(username,password);
        if(null == user){
            throw new UnknownAccountException();
        }else {
            if(null != user){
                SimpleAuthenticationInfo authorizationInfo = new SimpleAuthenticationInfo(user,user.getPassword().toCharArray(),getName());
                return authorizationInfo;
            } else {
                throw new IncorrectCredentialsException();
            }
        }
    }
}
