package com.huwei.live.controller;

import com.github.pagehelper.PageInfo;
import com.huwei.live.pojo.Comment;
import com.huwei.live.request.CommentSearch;
import com.huwei.live.response.base.BaseResponse;
import com.huwei.live.service.CommentService;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comment")
public class CommentController extends BaseController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/add")
    public BaseResponse add(Comment comment){
        BaseResponse baseResponse = new BaseResponse();
        try {
            if(commentService.add(comment)){
                baseResponse.success();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @PostMapping("/update")
    public BaseResponse update(Comment comment){
        BaseResponse baseResponse = new BaseResponse();
        try {
            if(commentService.update(comment)){
                baseResponse.success();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @PostMapping("/delete")
    public BaseResponse delete(Integer commentId){
        BaseResponse baseResponse = new BaseResponse();
        try {
            if(commentService.delete(commentId)){
                baseResponse.success();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @PostMapping("/queryByPage")
    public BaseResponse queryByPage(CommentSearch commentSearch){
        BaseResponse<PageInfo> baseResponse = new BaseResponse<>();
        try {
            PageInfo<Comment> pageInfo = commentService.queryByPage(commentSearch);
            if(null != pageInfo){
                baseResponse.success(pageInfo);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @PostMapping("/queryUserAndComment")
    public BaseResponse queryUserAndComment(Integer videoId){
        BaseResponse<Map> baseResponse = new BaseResponse<>();
        try {
            Map<String, List> result = commentService.queryUserAndVideoAndCommentById(videoId);
            if(null != result){
                baseResponse.success(result);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @PostMapping("/agree")
    public BaseResponse agree(Integer commentId){
        BaseResponse baseResponse = new BaseResponse<>();
        try {
            if(commentService.agree(commentId)){
                baseResponse.success();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @PostMapping("/disagree")
    public BaseResponse disagree(Integer commentId){
        BaseResponse baseResponse = new BaseResponse<>();
        try {
            if(commentService.disagree(commentId)){
                baseResponse.success();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }
}
