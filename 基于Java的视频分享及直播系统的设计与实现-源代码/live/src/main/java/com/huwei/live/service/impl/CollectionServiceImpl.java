package com.huwei.live.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huwei.live.mapper.CollectionMapper;
import com.huwei.live.mapper.UserMapper;
import com.huwei.live.mapper.VideoMapper;
import com.huwei.live.pojo.*;
import com.huwei.live.request.CollectionSearch;
import com.huwei.live.service.CollectionService;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CollectionServiceImpl implements CollectionService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private CollectionMapper collectionMapper;

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean add(Collection collection) {
        try {
            if(null != collection){
                CollectionExample collectionExample = new CollectionExample();
                CollectionExample.Criteria criteria = collectionExample.createCriteria();
                criteria.andIsaliveEqualTo(1);
                criteria.andUseridEqualTo(collection.getUserid());
                criteria.andVideoidEqualTo(collection.getVideoid());
                List<Collection> collections = collectionMapper.selectByExample(collectionExample);
                if(collections.size() == 0){
                    int result = collectionMapper.insertSelective(collection);
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
    public boolean update(Collection collection) {
        try {
            if(null != collection){
                if(null != collection.getVideoid()){
                    CollectionExample collectionExample = new CollectionExample();
                    CollectionExample.Criteria criteria = collectionExample.createCriteria();
                    criteria.andIsaliveEqualTo(1);
                    criteria.andUseridEqualTo(collectionMapper.selectByPrimaryKey(collection.getId()).getUserid());
                    criteria.andVideoidEqualTo(collection.getVideoid());
                    List<Collection> collections = collectionMapper.selectByExample(collectionExample);
                    if(0 == collections.size() || collections.get(0).getId().equals(collection.getId())){
                        int result = collectionMapper.updateByPrimaryKeySelective(collection);
                        if(result > 0){
                            return true;
                        }
                    }
                }else{
                    int result = collectionMapper.updateByPrimaryKeySelective(collection);
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
    public boolean delete(Integer collectionId) {
        try {
            if(null != collectionId){
                Collection collection = new Collection();
                collection.setId(collectionId);
                collection.setIsalive(0);
                int result = collectionMapper.updateByPrimaryKeySelective(collection);
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
    public PageInfo<Collection> queryByPage(CollectionSearch collectionSearch) {
        try {
            if(null != collectionSearch){
                if(null != collectionSearch.getCurrentPage() && null != collectionSearch.getPageSize()){
                    PageHelper.startPage(collectionSearch.getCurrentPage(),collectionSearch.getPageSize());
                    CollectionExample collectionExample = new CollectionExample();
                    CollectionExample.Criteria criteria = collectionExample.createCriteria();
                    criteria.andIsaliveEqualTo(1);
                    List<Collection> list = collectionMapper.selectByExample(collectionExample);
                    PageInfo<Collection> pageInfo = new PageInfo<>(list);
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
    public PageInfo<Video> queryByUserId(CollectionSearch collectionSearch) {
        try {
            if(null != collectionSearch){
                if(null != collectionSearch.getCurrentPage() && null != collectionSearch.getPageSize()){
                    if(collectionSearch.getUserName() != null){
                        UserExample userExample = new UserExample();
                        UserExample.Criteria criteria = userExample.createCriteria();
                        criteria.andNameEqualTo(collectionSearch.getUserName());
                        criteria.andIsaliveEqualTo(1);
                        List<User> users = userMapper.selectByExample(userExample);
                        collectionSearch.setUserId(users.get(0).getId());
                    }
                    PageHelper.startPage(collectionSearch.getCurrentPage(),collectionSearch.getPageSize());
                    List<Collection> collections = collectionMapper.selectBySearch(collectionSearch);
                    List<Video> videos = new ArrayList<>();
                    for (Collection collection : collections) {
                        int videoId = collection.getVideoid();
                        Video video = videoMapper.selectByPrimaryKey(videoId);
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
}
