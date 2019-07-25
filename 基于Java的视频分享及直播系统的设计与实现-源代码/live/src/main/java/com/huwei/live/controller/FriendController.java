package com.huwei.live.controller;

import com.github.pagehelper.PageInfo;
import com.huwei.live.pojo.Friend;
import com.huwei.live.pojo.User;
import com.huwei.live.request.FriendSearch;
import com.huwei.live.response.base.BaseResponse;
import com.huwei.live.service.FriendService;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.UUID;

@RestController
@RequestMapping("/friend")
public class FriendController extends BaseController {

    @Autowired
    private FriendService friendService;

    @PostMapping("/add")
    public BaseResponse add(Friend friend){
        BaseResponse baseResponse = new BaseResponse();
        try {
            if(friendService.add(friend)){
                baseResponse.success();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @PostMapping("/update")
    public BaseResponse update(Friend friend){
        BaseResponse baseResponse = new BaseResponse();
        try {
            if(friendService.update(friend)){
                baseResponse.success();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @PostMapping("/delete")
    public BaseResponse delete(Integer friendId){
        BaseResponse baseResponse = new BaseResponse();
        try {
            if(friendService.delete(friendId)){
                baseResponse.success();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @PostMapping("/queryByPage")
    public BaseResponse queryByPage(FriendSearch friendSearch){
        BaseResponse<PageInfo> baseResponse = new BaseResponse<>();
        try {
            PageInfo<Friend> pageInfo = friendService.queryByPage(friendSearch);
            if(null != pageInfo){
                baseResponse.success(pageInfo);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @PostMapping("/queryByUserName")
    public BaseResponse queryByUserName(FriendSearch friendSearch){
        BaseResponse<PageInfo> baseResponse = new BaseResponse<>();
        try {
            PageInfo<User> pageInfo = friendService.selectByUserName(friendSearch);
            if(null != pageInfo){
                baseResponse.success(pageInfo);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }


    @PostMapping("/isFriend")
    public BaseResponse isFriend(String userName, Integer friendId){
        BaseResponse baseResponse = new BaseResponse<>();
        try {
            baseResponse.success(friendService.isFriend(userName,friendId));
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }
}
