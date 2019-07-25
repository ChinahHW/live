package com.huwei.live.controller;

import com.github.pagehelper.PageInfo;
import com.huwei.live.pojo.Collection;
import com.huwei.live.pojo.Video;
import com.huwei.live.request.CollectionSearch;
import com.huwei.live.response.base.BaseResponse;
import com.huwei.live.service.CollectionService;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/collection")
public class CollectionController extends BaseController {

    @Autowired
    private CollectionService collectionService;

    @PostMapping("/add")
    public BaseResponse add(Collection collection){
        BaseResponse baseResponse = new BaseResponse();
        try {
            if(collectionService.add(collection)){
                baseResponse.success();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @PostMapping("/update")
    public BaseResponse update(Collection collection){
        BaseResponse baseResponse = new BaseResponse();
        try {
            if(collectionService.update(collection)){
                baseResponse.success();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @PostMapping("/delete")
    public BaseResponse delete(Integer collectionId){
        BaseResponse baseResponse = new BaseResponse();
        try {
            if(collectionService.delete(collectionId)){
                baseResponse.success();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @PostMapping("/queryByPage")
    public BaseResponse queryByPage(CollectionSearch collectionSearch){
        BaseResponse<PageInfo> baseResponse = new BaseResponse<>();
        try {
            PageInfo<Collection> pageInfo = collectionService.queryByPage(collectionSearch);
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
    public BaseResponse queryByUserName(CollectionSearch collectionSearch){
        BaseResponse<PageInfo> baseResponse = new BaseResponse<>();
        try {
            PageInfo<Video> pageInfo = collectionService.queryByUserId(collectionSearch);
            if(null != pageInfo){
                baseResponse.success(pageInfo);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }
}
