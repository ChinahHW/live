package com.huwei.live.controller;

import com.github.pagehelper.PageInfo;
import com.huwei.live.pojo.Video;
import com.huwei.live.request.VideoSearch;
import com.huwei.live.response.base.BaseResponse;
import com.huwei.live.service.VideoService;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/video")
public class VideoController extends BaseController {

    @Autowired
    private VideoService videoService;

    @PostMapping("/add")
    public BaseResponse add(Video video){
        BaseResponse baseResponse = new BaseResponse();
        try {
            if(videoService.add(video)){
                baseResponse.success();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @PostMapping("/update")
    public BaseResponse update(Video video){
        BaseResponse baseResponse = new BaseResponse();
        try {
            if(videoService.update(video)){
                baseResponse.success();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @PostMapping("/delete")
    public BaseResponse delete(Integer videoId){
        BaseResponse baseResponse = new BaseResponse();
        try {
            if(videoService.delete(videoId)){
                baseResponse.success();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @PostMapping("/queryByPage")
    public BaseResponse queryByPage(VideoSearch videoSearch){
        BaseResponse<PageInfo> baseResponse = new BaseResponse<>();
        try {
            PageInfo<Video> pageInfo = videoService.queryByPage(videoSearch);
            if(null != pageInfo){
                baseResponse.success(pageInfo);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @PostMapping("/queryBySearch")
    public BaseResponse queryBySearch(VideoSearch videoSearch){
        BaseResponse<PageInfo> baseResponse = new BaseResponse<>();
        try {
            PageInfo<Video> pageInfo = videoService.selectBySearch(videoSearch);
            if(null != pageInfo){
                baseResponse.success(pageInfo);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @PostMapping("/getRecommend")
    public BaseResponse getRecommend(String userName){
        BaseResponse<List> baseResponse = new BaseResponse<>();
        try {
           List<Video> videos = videoService.recommend(userName);
           if(null != videos){
               baseResponse.success(videos);
           }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @PostMapping("/getRand")
    public BaseResponse getRand(){
        BaseResponse<List> baseResponse = new BaseResponse<>();
        try {
            List<Video> videos = videoService.rand();
            if(null != videos){
                baseResponse.success(videos);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }


    @PostMapping("/getCategory")
    public BaseResponse getCategory(String category){
        BaseResponse<List> baseResponse = new BaseResponse<>();
        try {
            List<Video> videos = videoService.findByCategory(category);
            if(null != videos){
                baseResponse.success(videos);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @PostMapping("/getById")
    public BaseResponse getById(int videoId){
        BaseResponse<Video> baseResponse = new BaseResponse<>();
        try {
            Video video = videoService.getById(videoId);
            if(null != video){
                baseResponse.success(video);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }






}
