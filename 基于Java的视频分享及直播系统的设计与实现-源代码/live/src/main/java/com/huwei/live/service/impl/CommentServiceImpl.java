package com.huwei.live.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huwei.live.mapper.CommentMapper;
import com.huwei.live.mapper.UserMapper;
import com.huwei.live.pojo.*;
import com.huwei.live.request.CommentSearch;
import com.huwei.live.service.CommentService;
import com.huwei.live.service.UserService;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean add(Comment comment) {
        try {
            if(null != comment){
                int result = commentMapper.insertSelective(comment);
                if(result > 0){
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
    public boolean delete(Integer commentId) {
        try {
            if(null != commentId){
                Comment comment = new Comment();
                comment.setId(commentId);
                comment.setIsalive(0);
                int result = commentMapper.updateByPrimaryKeySelective(comment);
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
    public boolean update(Comment comment) {
        try {
            int result = commentMapper.updateByPrimaryKeySelective(comment);
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
    public PageInfo<Comment> queryByPage(CommentSearch commentSearch) {
        try {
            if(null != commentSearch){
                if(null != commentSearch.getCurrentPage() && null != commentSearch.getPageSize()){
                    PageHelper.startPage(commentSearch.getCurrentPage(),commentSearch.getPageSize());
                    CommentExample commentExample = new CommentExample();
                    CommentExample.Criteria criteria = commentExample.createCriteria();
                    criteria.andIsaliveEqualTo(1);
                    List<Comment> list = commentMapper.selectByExample(commentExample);
                    PageInfo<Comment> pageInfo = new PageInfo<>(list);
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
    public PageInfo<Comment> queryBySearch(CommentSearch commentSearch) {
        try {
            if(null != commentSearch){
                PageHelper.startPage(commentSearch.getCurrentPage(),commentSearch.getPageSize());
                List<Comment> list = commentMapper.selectBySearch(commentSearch);
                PageInfo<Comment> pageInfo = new PageInfo<>(list);
                return pageInfo;
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return null;
    }

    @Override
    public Map<String, List> queryUserAndVideoAndCommentById(Integer videoId) {
        try {
            if(0 != videoId){
                CommentSearch commentSearch = new CommentSearch();
                commentSearch.setVideoId(videoId);
                List<Comment> list = commentMapper.selectBySearch(commentSearch);
                List<User> users = new ArrayList<>();
                for (Comment comment : list) {
                    int userId = comment.getUserid();
                    User user = userMapper.selectByPrimaryKey(userId);
                    users.add(user);
                }
                Map<String,List> result = new HashMap<>();
                result.put("comment",list);
                result.put("user",users);
                return result;
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return null;
    }

    @Override
    public boolean agree(Integer commentId) {
        try {
            if(0 != commentId){
                Comment comment = commentMapper.selectByPrimaryKey(commentId);
                comment.setAgree(comment.getAgree()+1);
                int result = commentMapper.updateByPrimaryKeySelective(comment);
                if(result > 0){
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
    public boolean disagree(Integer commentId) {
        try {
            if(0 != commentId){
                Comment comment = commentMapper.selectByPrimaryKey(commentId);
                comment.setAgree(comment.getAgree()-1);
                int result = commentMapper.updateByPrimaryKeySelective(comment);
                if(result > 0){
                    return true;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return false;
    }
}
