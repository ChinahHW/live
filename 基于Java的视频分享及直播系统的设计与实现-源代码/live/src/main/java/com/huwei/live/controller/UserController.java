package com.huwei.live.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.huwei.live.pojo.User;
import com.huwei.live.request.UserSearch;
import com.huwei.live.response.base.BaseResponse;
import com.huwei.live.response.data.UserShow;
import com.huwei.live.service.UserService;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public BaseResponse login(String name, String password, HttpServletRequest request){
        BaseResponse<JSONObject> baseResponse = new BaseResponse<>();
        try {
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            if(null != user){
                baseResponse.setCode(300);
                baseResponse.setMsg("系统同时只允许一个用户在线");
            }else {
                Subject subject = SecurityUtils.getSubject();
                UsernamePasswordToken token = new UsernamePasswordToken(name,password);
                subject.login(token);
                SecurityUtils.getSubject().getSession().setTimeout(18000000);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("token",request.getSession().getId());
                User user1 = userService.login(name,password);
                jsonObject.put("userId",user1.getId());
                baseResponse.success(jsonObject);
            }
        }catch (IncorrectCredentialsException e){
            baseResponse.setMsg("用户名或密码错误");
        }catch (UnknownAccountException e){
            baseResponse.setMsg("用户名错误");
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    /**
     * 无权限反馈接口
     * @return
     */
    @RequestMapping("/unauth")
    public BaseResponse unauth(){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.unauth();
        return baseResponse;
    }

    @RequestMapping("/unlogin")
    public BaseResponse unlogin(){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.unlogin();
        return baseResponse;
    }

    @PostMapping("/exit")
    public BaseResponse exit(){
        BaseResponse<UserShow> baseResponse = new BaseResponse<>();
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.logout();
            baseResponse.success();
        }catch (Exception e){
            error(e);
        }
        baseResponse.success();
        return baseResponse;
    }

    @PostMapping("/add")
    public BaseResponse add(User user){
        BaseResponse baseResponse = new BaseResponse();
        try {
            if(userService.add(user)){
                baseResponse.success();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }
    @PostMapping("/update")
    public BaseResponse update(User user){
        BaseResponse baseResponse = new BaseResponse();
        try {

            if(userService.update(user)){

                baseResponse.success();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public BaseResponse uploadExcel(@RequestParam("picture") MultipartFile file, HttpServletRequest request){
        BaseResponse baseResponse = new BaseResponse<>();
        try {
            if(file.isEmpty()){
                baseResponse.setMsg("文件名不能为空");
                return baseResponse;
            }
            String fileName = file.getOriginalFilename();//文件名
            System.out.println(fileName);
            /* String suffixName = fileName.substring(fileName.lastIndexOf("."));//后缀名*/
            /*String filePath = "G://IdeaWorkSpace//live//src//main//resources//static//img//";//上传路径*/
            String filePath = "G://HBuliderWorkspace//live//img//";//上传路径
            /*fileName = suffixName;//新文件名*/
            File dest = new File(filePath + fileName);
            if(!dest.getParentFile().exists()){
                dest.getParentFile().mkdirs();
            }
            file.transferTo(dest);

            baseResponse.success();
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }


    @RequestMapping(value = "/uploadVideo", method = RequestMethod.POST)
    public BaseResponse uploadVideo(@RequestParam("videopicture") MultipartFile file,@RequestParam("url") MultipartFile file2, HttpServletRequest request){
        BaseResponse baseResponse = new BaseResponse<>();
        try {
            if(file.isEmpty() && file2.isEmpty()){
                    baseResponse.setMsg("文件名不能为空");
            return baseResponse;
        }
        String fileName = file.getOriginalFilename();//文件名
        System.out.println(fileName);
//        String suffixName = fileName.substring(fileName.lastIndexOf("."));//后缀名
        /*String filePath = "G://IdeaWorkSpace//live//src//main//resources//static//img//";//上传路径*/
        String filePath = "G://HBuliderWorkspace//live//img//";//上传路径
//        fileName = suffixName;//新文件名
        File dest = new File(filePath + fileName);
        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdirs();
        }
        file.transferTo(dest);

            String fileName2 = file2.getOriginalFilename();//文件名
            System.out.println(fileName2);
            String suffixName = fileName.substring(fileName.lastIndexOf("."));//后缀名*/
            /*String filePath = "G://IdeaWorkSpace//live//src//main//resources//static//img//";//上传路径*/
            String filePath2 = "E://nginx-1.7.11.3-Gryphon//html//hls//";//上传路径
            fileName2 = UUID.randomUUID()+suffixName;//新文件名
            File dest2 = new File(filePath2 + fileName2);
            if(!dest2.getParentFile().exists()){
                dest2.getParentFile().mkdirs();
            }
            file2.transferTo(dest2);
            baseResponse.success();
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }


    @PostMapping("/delete")
    public BaseResponse delete(Integer userId){
        BaseResponse baseResponse = new BaseResponse();
        try {
            if(userService.delete(userId)){
                baseResponse.success();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }
    @PostMapping("/queryByPage")
    public BaseResponse queryByPage(UserSearch userSearch){
        BaseResponse<PageInfo> baseResponse = new BaseResponse<>();
        try {
            PageInfo<User> pageInfo = userService.queryByPage(userSearch);
            if(null != pageInfo){
                baseResponse.success(pageInfo);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }

    @PostMapping("/queryById")
    public BaseResponse queryById(Integer userId){
        BaseResponse<Object> baseResponse = new BaseResponse<>();
        try {
            User user = userService.selectById(userId);
            if(null != user){
                baseResponse.success(user);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
        return baseResponse;
    }
}
