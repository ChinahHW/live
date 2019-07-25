package com.huwei.live.controller;

import com.huwei.live.pojo.Live;
import com.huwei.live.response.base.BaseResponse;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/live")
public class LiveController extends BaseController {

    @Autowired
    RedisTemplate redisTemplate;

    Map<String,List<String>> barrages = new HashMap<>();

    @PostMapping("/getLive")
    public BaseResponse getLive(String liveName){
        BaseResponse baseResponse = new BaseResponse();
        try {
            Live live = (Live) redisTemplate.opsForValue().get(liveName);
            baseResponse.success(live);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @PostMapping("/queryLive")
    public BaseResponse queryLive(){
        BaseResponse baseResponse = new BaseResponse();
        try {
            List<Live> lives = new ArrayList<>();
            for (Object key : redisTemplate.keys("*")) {
                Live live = (Live) redisTemplate.opsForValue().get(key);
                lives.add(live);
            }
            baseResponse.success(lives);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @PostMapping("/addLive")
    public BaseResponse addLive(String liveName,String liveAddress,String liveTitle){
        BaseResponse baseResponse = new BaseResponse();
        try {
            Live live = new Live();
            live.setUserName(liveName);
            live.setTitle(liveTitle);
            live.setAddress(liveAddress);
            redisTemplate.opsForValue().set(liveName,live);
            baseResponse.success();
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @PostMapping("/updateLive")
    public BaseResponse updateLive(String liveName,String liveTitle,String liveAddress){
        BaseResponse baseResponse = new BaseResponse();
        try {
            Live live = new Live();
            live.setUserName(liveName);
            live.setTitle(liveTitle);
            live.setAddress(liveAddress);
           redisTemplate.opsForValue().set(liveName,live);
           baseResponse.success();
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @PostMapping("/delLive")
    public BaseResponse delLive(String liveName){
        BaseResponse baseResponse = new BaseResponse();
        try {
            redisTemplate.delete(liveName);
            baseResponse.success();
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @PostMapping("/sendBarrage")
    public BaseResponse sendBarrage(String liveName,String barrage){
        BaseResponse baseResponse = new BaseResponse();
        try {
            if(barrages.get(liveName) != null){
                List<String> b = barrages.get(liveName);
                b.add(barrage);
                barrages.put(liveName,b);
            }else{
                List<String> b = new ArrayList<>();
                b.add(barrage);
                barrages.put(liveName,b);
            }
            baseResponse.success();
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @PostMapping("/getBarrage")
    public BaseResponse getBarrage(String liveName){
        BaseResponse baseResponse = new BaseResponse();
        try {
            if(barrages.get(liveName) != null){
                List<String> result = barrages.get(liveName);
                barrages.remove(liveName);
                baseResponse.success(result);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

}
