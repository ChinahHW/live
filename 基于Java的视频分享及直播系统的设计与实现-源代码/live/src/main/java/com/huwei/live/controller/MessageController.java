package com.huwei.live.controller;

import com.github.pagehelper.PageInfo;
import com.huwei.live.pojo.Message;
import com.huwei.live.request.MessageSearch;
import com.huwei.live.response.base.BaseResponse;
import com.huwei.live.service.MessageService;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/message")
public class MessageController extends BaseController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/add")
    public BaseResponse add(Message message){
        BaseResponse baseResponse = new BaseResponse();
        try {
            if(messageService.add(message)){
                baseResponse.success();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @PostMapping("/update")
    public BaseResponse update(Message message){
        BaseResponse baseResponse = new BaseResponse();
        try {
            if(messageService.update(message)){
                baseResponse.success();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @PostMapping("/delete")
    public BaseResponse delete(Integer messageId){
        BaseResponse baseResponse = new BaseResponse();
        try {
            if(messageService.delete(messageId)){
                baseResponse.success();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @PostMapping("/queryByPage")
    public BaseResponse queryByPage(MessageSearch messageSearch){
        BaseResponse<PageInfo> baseResponse = new BaseResponse<>();
        try {
            PageInfo<Message> pageInfo = messageService.queryByPage(messageSearch);
            if(null != pageInfo){
                baseResponse.success(pageInfo);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }


    @PostMapping("/queryBySIdAndRId")
    public BaseResponse queryBySIdAndRId(Integer senderId,Integer receiverId){
        BaseResponse<Map> baseResponse = new BaseResponse<>();
        try {
            Map<String,List> result = messageService.queryBySIdAndRId(senderId, receiverId);
            baseResponse.success(result);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }
}
