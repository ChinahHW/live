package com.huwei.live.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huwei.live.mapper.UserMapper;
import com.huwei.live.pojo.User;
import com.huwei.live.pojo.UserExample;
import com.huwei.live.request.UserSearch;
import com.huwei.live.service.UserService;
import com.huwei.live.util.StringUtil;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String userAccount, String password) {
        try {
            if(StringUtil.isNotEmpty(userAccount)&&StringUtil.isNotEmpty(password)){
                UserExample userExample = new UserExample();
                UserExample.Criteria criteria = userExample.createCriteria();
                criteria.andIsaliveEqualTo(1);
                criteria.andNameEqualTo(userAccount);
                criteria.andPasswordEqualTo(password);
                List<User> list = userMapper.selectByExample(userExample);
                if(list.size()>0){
                    return list.get(0);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return null;
    }

    @Override
    public boolean add(User user) {
        try {
            if(null != user){
                UserExample userExample = new UserExample();
                UserExample.Criteria criteria = userExample.createCriteria();
                criteria.andNameEqualTo(user.getName());
                List<User> list = userMapper.selectByExample(userExample);
                if(list.size()==0){
                    int result = userMapper.insertSelective(user);
                    if(result>0){
                        return true;
                    }
                }

            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return false;
    }

    @Override
    public boolean update(User user) {
        try {
            if(null != user && null != user.getId()){
                User createUser = (User)SecurityUtils.getSubject().getPrincipal();
                if(null != user.getName()){
                    UserExample userExample = new UserExample();
                    UserExample.Criteria criteria = userExample.createCriteria();
                    criteria.andIsaliveEqualTo(1);
                    criteria.andNameEqualTo(user.getName());
                    List<User> list = userMapper.selectByExample(userExample);
                    if(list.size()==0 || list.get(0).getId().equals(user.getId())){
                        if(user.getPicture() != null){
                            String picture = user.getPicture().substring(user.getPicture().lastIndexOf("\\")+1);
                            user.setPicture(picture);
                        }
                        int result = userMapper.updateByPrimaryKeySelective(user);
                        if(result>0){
                            return true;
                        }
                    }
                }else {
                    int result = userMapper.updateByPrimaryKeySelective(user);
                    if(result>0){
                        return true;
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return false;
    }

    @Override
    public boolean delete(Integer userId) {
        try {
            if(null != userId){
                User user = new User();
                user.setId(userId);
                user.setIsalive(0);
                int result = userMapper.updateByPrimaryKeySelective(user);
                if(result>0){
                    return true;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return false;
    }

    @Override
    public PageInfo<User> queryByPage(UserSearch userSearch) {
        try {
            if(null != userSearch){
                if(null != userSearch.getCurrentPage() && null != userSearch.getPageSize()){
                    PageHelper.startPage(userSearch.getCurrentPage(),userSearch.getPageSize());
                    UserExample userExample = new UserExample();
                    UserExample.Criteria criteria = userExample.createCriteria();
                    if(userSearch.getUserName()!=null){
                        criteria.andNameEqualTo(userSearch.getUserName());
                    }
                    criteria.andIsaliveEqualTo(1);
                    List<User> list = userMapper.selectByExample(userExample);
                    PageInfo<User> pageInfo = new PageInfo<>(list);
                    return pageInfo;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return null;
    }

    @Override
    public User selectById(Integer userId) {
        try {
            if(null != userId){
                User user = userMapper.selectByPrimaryKey(userId);
                return user;
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return null;
    }
}
