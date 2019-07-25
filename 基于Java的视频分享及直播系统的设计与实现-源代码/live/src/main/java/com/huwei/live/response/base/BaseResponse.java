package com.huwei.live.response.base;


/**
 * @create with IntelliJ IDEA.
 * @author: zlp
 * @date : 2018/3/31  13:18
 * @description:
 */
public class BaseResponse<T> {
    /**
     * 提示信息
     */
    private String msg;

    /**
     * 状态码
     */
    private Integer code;
    /**
     * 返回的数据
     */
    private T data;

    public BaseResponse(){
        this.msg = "请求无法执行,请查看参数是否正确";
        this.code = 400;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 成功通用设置参数方法
     */
    public void success(){
        setMsg("ok");
        setCode(200);
    }

    /**
     * 但数据同一处理成功事件
     * @param t 小返回的数据
     */
    public void success(T t){
        setMsg("ok");
        setCode(200);
        setData(t);
    }

    /**
     * 同一处理无权限事件
     */
    public void unauth(){
        setMsg("您没有该权限");
        setCode(311);
    }

    /**
     * 同一处理登录失效，事件
     */
    public void unlogin(){
        setMsg("登录失效,请重新登录");
        setCode(322);
    }


}
