package com.huwei.live.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huwei.live.mapper.FriendMapper;
import com.huwei.live.mapper.UserMapper;
import com.huwei.live.pojo.Friend;
import com.huwei.live.pojo.FriendExample;
import com.huwei.live.pojo.User;
import com.huwei.live.pojo.UserExample;
import com.huwei.live.request.FriendSearch;
import com.huwei.live.service.FriendService;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class FriendServiceImpl implements FriendService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private FriendMapper friendMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean add(Friend friend) {
        try {
            if(null != friend){
                FriendExample friendExample = new FriendExample();
                FriendExample.Criteria criteria = friendExample.createCriteria();
                criteria.andUseridEqualTo(friend.getUserid());
                criteria.andFriendidEqualTo(friend.getFriendid());
                List<Friend> collections = friendMapper.selectByExample(friendExample);
                if(collections.size() == 0){
                    int result = friendMapper.insertSelective(friend);
                    if(result > 0){
                        return true;
                    }
                }else if(0 == collections.get(0).getIsalive()){
                    collections.get(0).setIsalive(1);
                    friendMapper.updateByPrimaryKeySelective(collections.get(0));
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
    public boolean update(Friend friend) {
        try {
            if(null != friend){
                if(null != friend.getFriendid()){
                    FriendExample friendExample = new FriendExample();
                    FriendExample.Criteria criteria = friendExample.createCriteria();
                    criteria.andIsaliveEqualTo(1);
                    criteria.andUseridEqualTo(friendMapper.selectByPrimaryKey(friend.getId()).getUserid());
                    criteria.andFriendidEqualTo(friend.getFriendid());
                    List<Friend> friends = friendMapper.selectByExample(friendExample);
                    if(0 == friends.size() || friends.get(0).getId().equals(friend.getId())){
                        int result = friendMapper.updateByPrimaryKeySelective(friend);
                        if(result > 0){
                            return true;
                        }
                    }
                }else{
                    int result = friendMapper.updateByPrimaryKeySelective(friend);
                    if(result > 0){
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
    public boolean delete(Integer friendId) {
        try {
            if(null != friendId){
                Friend friend = new Friend();
                friend.setId(friendId);
                friend.setIsalive(0);
                int result = friendMapper.updateByPrimaryKeySelective(friend);
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
    public PageInfo<Friend> queryByPage(FriendSearch friendSearch) {
        try {
            if(null != friendSearch){
                if(null != friendSearch.getCurrentPage() && null != friendSearch.getPageSize()){
                    PageHelper.startPage(friendSearch.getCurrentPage(),friendSearch.getPageSize());
                    FriendExample friendExample = new FriendExample();
                    FriendExample.Criteria criteria = friendExample.createCriteria();
                    criteria.andIsaliveEqualTo(1);
                    List<Friend> list = friendMapper.selectByExample(friendExample);
                    PageInfo<Friend> pageInfo = new PageInfo<>(list);
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
    public PageInfo<User> selectByUserName(FriendSearch friendSearch) {
        try {
            if(null != friendSearch){
                if(null != friendSearch.getCurrentPage() && null != friendSearch.getPageSize()){
                    PageHelper.startPage(friendSearch.getCurrentPage(),friendSearch.getPageSize());
                    UserExample userExample = new UserExample();
                    UserExample.Criteria criteria = userExample.createCriteria();
                    criteria.andIsaliveEqualTo(1);
                    criteria.andNameEqualTo(friendSearch.getUserName());
                    List<User> users = userMapper.selectByExample(userExample);
                    friendSearch.setUserId(users.get(0).getId());
                    List<Friend> friends = friendMapper.selectBySearch(friendSearch);
                    List<User> userList = new ArrayList<>();
                    for (Friend friend : friends) {
                        int friendId = friend.getFriendid();
                        User user = userMapper.selectByPrimaryKey(friendId);
                        userList.add(user);
                    }
                    PageInfo<User> pageInfo = new PageInfo<>(userList);
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
    public List<Friend> isFriend(String userName, Integer friendId) {
        try {
            if(null != userName && friendId != null){
                UserExample userExample = new UserExample();
                UserExample.Criteria criteria = userExample.createCriteria();
                criteria.andIsaliveEqualTo(1);
                criteria.andNameEqualTo(userName);
                List<User> users = userMapper.selectByExample(userExample);
                int userId = users.get(0).getId();
                FriendSearch friendSearch = new FriendSearch();
                friendSearch.setUserId(userId);
                friendSearch.setFriendId(friendId);
                List<Friend> friend = friendMapper.selectBySearch(friendSearch);
                if(0 != friend.size()){
                    return friend;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return null;
    }
}
