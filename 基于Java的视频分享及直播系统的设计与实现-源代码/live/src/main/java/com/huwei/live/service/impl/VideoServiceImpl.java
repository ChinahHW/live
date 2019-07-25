package com.huwei.live.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huwei.live.mapper.CollectionMapper;
import com.huwei.live.mapper.UserMapper;
import com.huwei.live.mapper.VideoMapper;
import com.huwei.live.pojo.*;
import com.huwei.live.request.VideoSearch;
import com.huwei.live.service.VideoService;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CollectionMapper collectionMapper;

    @Override
    public boolean add(Video video) {
        try {
            if(null != video){
                VideoExample videoExample = new VideoExample();
                VideoExample.Criteria criteria = videoExample.createCriteria();
                criteria.andNameEqualTo(video.getName());
                List<Video> list = videoMapper.selectByExample(videoExample);
                if(list.size()==0){
                    if(video.getVideopicture() != null){
                        String picture = video.getVideopicture().substring(video.getVideopicture().lastIndexOf("\\")+1);
                        video.setVideopicture(picture);
                    }
                    video.setIsalive(2);
                    int result = videoMapper.insertSelective(video);
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
    public boolean update(Video video) {
        try {
            if(null != video){
                if(null != video.getName()){
                    VideoExample videoExample = new VideoExample();
                    VideoExample.Criteria criteria = videoExample.createCriteria();
                    criteria.andIsaliveEqualTo(1);
                    criteria.andNameEqualTo(video.getName());
                    List<Video> videos = videoMapper.selectByExample(videoExample);
                    if(0 == videos.size() || videos.get(0).getId().equals(video.getId())){
                        int result = videoMapper.updateByPrimaryKeySelective(video);
                        if(result > 0){
                            return true;
                        }
                    }
                }else{
                    int result = videoMapper.updateByPrimaryKeySelective(video);
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
    public boolean delete(Integer videoId) {
        try {
            if(null != videoId){
                Video video = new Video();
                video.setId(videoId);
                video.setIsalive(0);
                int result = videoMapper.updateByPrimaryKeySelective(video);
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
    public PageInfo<Video> queryByPage(VideoSearch videoSearch) {
        try {
            if(null != videoSearch){
                if(null != videoSearch.getCurrentPage() && null != videoSearch.getPageSize()){
                    PageHelper.startPage(videoSearch.getCurrentPage(),videoSearch.getPageSize());
                    VideoExample videoExample = new VideoExample();
                    VideoExample.Criteria criteria = videoExample.createCriteria();

                    if(videoSearch.getIsAlive() != null){
                        criteria.andIsaliveEqualTo(videoSearch.getIsAlive());
                    }else{
                        criteria.andIsaliveEqualTo(1);
                    }
                    List<Video> list = videoMapper.selectByExample(videoExample);
                    PageInfo<Video> pageInfo = new PageInfo<>(list);
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
    public PageInfo<Video> selectBySearch(VideoSearch videoSearch) {
        try {
            if(null != videoSearch){
                PageHelper.startPage(videoSearch.getCurrentPage(),videoSearch.getPageSize());
                List<Video> list = videoMapper.selectBySearch(videoSearch);
                PageInfo<Video> pageInfo = new PageInfo<>(list);
                return pageInfo;
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return null;
    }

    @Override
    public List<Video> recommend(String userName) {
        try {
            if(null != userName){
                List<Video> recommentVideo = new ArrayList<>();
                UserExample userExample = new UserExample();
                UserExample.Criteria criteria = userExample.createCriteria();
                criteria.andNameEqualTo(userName);
                criteria.andIsaliveEqualTo(1);
                List<User> users = userMapper.selectByExample(userExample);
                int userId = 0;
                if(0 != users.size()){
                    userId = users.get(0).getId();
                    CollectionExample collectionExample = new CollectionExample();
                    CollectionExample.Criteria criteria1 = collectionExample.createCriteria();
                    criteria1.andIsaliveEqualTo(1);
                    criteria1.andUseridEqualTo(userId);
                    List<Collection> collections = collectionMapper.selectByExample(collectionExample);
                    List<Integer> videoIds = new ArrayList<>();
                    for (Collection collection : collections) {
                        videoIds.add(collection.getVideoid());
                    }


                    //取得有相同收藏的用户id
                    List<Integer> sameUserId = videoMapper.getRecomment(videoIds,videoIds.size(),userId);
                    if(0 != sameUserId.size()){
                        CollectionExample collectionExample2 = new CollectionExample();
                        CollectionExample.Criteria criteria2 = collectionExample2.createCriteria();
                        criteria2.andIsaliveEqualTo(1);
                        criteria2.andUseridEqualTo(sameUserId.get(0));
                        List<Collection> sameCollections = collectionMapper.selectByExample(collectionExample2);
                        List<Integer> sameVideoIds = new ArrayList<>();
                        if(0 != sameCollections.size()){
                            for (Collection sameCollection : sameCollections) {
                                sameVideoIds.add(sameCollection.getVideoid());
                            }
                            if(sameVideoIds.size() > videoIds.size()){
                                sameVideoIds.removeAll(videoIds);
                                for (Integer sameVideoId : sameVideoIds) {
                                    recommentVideo.add(videoMapper.selectByPrimaryKey(sameVideoId));
                                }
                                return recommentVideo;
                            }
                        }
                    }
                    VideoSearch videoSearch = new VideoSearch();
                    PageHelper.startPage(1,2);
                    recommentVideo = videoMapper.selectBySearch(videoSearch);
                    PageInfo<Video> recommentVideosInfo = new PageInfo<>(recommentVideo);
                    return recommentVideosInfo.getList();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return null;
    }

    @Override
    public List<Video> rand() {
        try {
            List<Video> randVideos = videoMapper.selectRand();
            if(null != randVideos){
                return randVideos;
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return null;
    }

    @Override
    public List<Video> findByCategory(String category) {
        try {
            VideoExample videoExample = new VideoExample();
            VideoExample.Criteria criteria = videoExample.createCriteria();
            criteria.andIsaliveEqualTo(1);
            criteria.andClazzLike("%"+category+"%");
            PageHelper.startPage(1,3);
            List<Video> videos = videoMapper.selectByExample(videoExample);
            PageInfo<Video> recommentVideosInfo = new PageInfo<>(videos);
            if(null != videos){
                return recommentVideosInfo.getList();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return null;
    }

    @Override
    public Video getById(int videoId) {
        try {
            if(0 != videoId){
                Video video = videoMapper.selectByPrimaryKey(videoId);
                return video;
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return null;
    }
}
