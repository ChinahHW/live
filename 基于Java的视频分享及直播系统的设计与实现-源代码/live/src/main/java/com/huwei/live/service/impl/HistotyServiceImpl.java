package com.huwei.live.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.huwei.live.mapper.HistoryMapper;
import com.huwei.live.mapper.UserMapper;
import com.huwei.live.mapper.VideoMapper;
import com.huwei.live.pojo.*;
import com.huwei.live.request.HistorySearch;
import com.huwei.live.service.HistoryService;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class HistotyServiceImpl implements HistoryService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private HistoryMapper historyMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private VideoMapper videoMapper;

    @Override
    public boolean add(History history) {
        try {
            if(null != history){
                HistoryExample historyExample = new HistoryExample();
                HistoryExample.Criteria criteria = historyExample.createCriteria();
                criteria.andIsaliveEqualTo(1);
                criteria.andUseridEqualTo(history.getUserid());
                criteria.andVideoidEqualTo(history.getVideoid());
                List<History> histories = historyMapper.selectByExample(historyExample);
                if(histories.size() == 0){
                    int result = historyMapper.insertSelective(history);
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
    public boolean update(History history) {
        try {
            int result = historyMapper.updateByPrimaryKeySelective(history);
            if(result > 0){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return false;
    }

    @Override
    public boolean delete(Integer historyId) {
        try {
            if(null != historyId){
                History history = new History();
                history.setId(historyId);
                history.setIsalive(0);
                int result = historyMapper.updateByPrimaryKeySelective(history);
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
    public PageInfo<History> queryByPage(HistorySearch historySearch) {
        try {
            if(null != historySearch){
                if(null != historySearch.getCurrentPage() && null != historySearch.getPageSize()){
                    PageHelper.startPage(historySearch.getCurrentPage(),historySearch.getPageSize());
                    HistoryExample historyExample = new HistoryExample();
                    HistoryExample.Criteria criteria = historyExample.createCriteria();
                    criteria.andIsaliveEqualTo(1);
                    List<History> list = historyMapper.selectByExample(historyExample);
                    PageInfo<History> pageInfo = new PageInfo<>(list);
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
    public PageInfo<Video> queryByUserName(HistorySearch historySearch) {
        try {
            if(null != historySearch){
                if(null != historySearch.getCurrentPage() && null != historySearch.getPageSize()){
                    PageHelper.startPage(historySearch.getCurrentPage(),historySearch.getPageSize());
                    if(historySearch.getUserName() != null){
                        UserExample userExample = new UserExample();
                        UserExample.Criteria criteria = userExample.createCriteria();
                        criteria.andIsaliveEqualTo(1);
                        criteria.andNameEqualTo(historySearch.getUserName());
                        List<User> users = userMapper.selectByExample(userExample);
                        historySearch.setUserId(users.get(0).getId());
                    }
                    List<History> histories = historyMapper.selectBySearch(historySearch);
                    List<Video> videos = new ArrayList<>();
                    for (History history : histories) {
                        Video video = videoMapper.selectByPrimaryKey(history.getVideoid());
                        videos.add(video);
                    }
                    PageInfo<Video> pageInfo = new PageInfo<>(videos);
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
    public PageInfo<User> queryByVideoId(HistorySearch historySearch) {
        try {
            if(null != historySearch){
                if(null != historySearch.getCurrentPage() && null != historySearch.getPageSize()){
                    PageHelper.startPage(historySearch.getCurrentPage(),historySearch.getPageSize());
                    List<History> histories = historyMapper.selectBySearch(historySearch);
                    List<User> users = new ArrayList<>();
                    for (History history : histories) {
                        User user = userMapper.selectByPrimaryKey(history.getUserid());
                        users.add(user);
                    }
                    PageInfo<User> pageInfo = new PageInfo<>(users);
                    return pageInfo;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return null;
    }
}
