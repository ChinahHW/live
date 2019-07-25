/**
 * Description:
 * @author zlp
 * @create 2018-09-07 14:43
 **/
var urlHeader = 'http://127.0.0.1:8080';
var urlconfig = {	
	exit:urlHeader+'/user/exit',
    userLogin:urlHeader+'/user/login',
    userAdd:urlHeader+'/user/add',
    userDelete:urlHeader+'/user/delete',
    userUpdate:urlHeader+'/user/update',
    userList:urlHeader+'/user/queryByPage',
    queryUserById:urlHeader+'/user/queryById',
    fileUpload:urlHeader+'/user/upload',
    videoTopFive:urlHeader + '/video/queryBySearch',
    videoGetRecommend:urlHeader + '/video/getRecommend',
    videoGetRand:urlHeader + '/video/getRand',
    videoTheLatest:urlHeader + '/video/queryBySearch',
    videoUpload:urlHeader+'/user/uploadVideo',
    videoList:urlHeader+'/video/queryByPage',
    getCatetory:urlHeader + '/video/getCategory',
    getVideoById:urlHeader + '/video/getById',
    addVideo:urlHeader + '/video/add',
    videoUpdate:urlHeader + '/video/update',
    videoDelete:urlHeader + '/video/delete',
    commentTopThree:urlHeader + '/comment/queryUserAndComment',
    commentAdd:urlHeader + '/comment/add',
    commentAgree:urlHeader + '/comment/agree',
    commentDisagree:urlHeader + '/comment/disagree',
    getLive:urlHeader + '/live/getLive',
    queryLive:urlHeader + '/live/queryLive',
    addLive:urlHeader + '/live/addLive',
    updateLive:urlHeader + '/live/updateLive',
    delLive:urlHeader + '/live/delLive',
    sendBarrage:urlHeader + '/live/sendBarrage',
    getBarrage:urlHeader + '/live/getBarrage',
    queryCollection:urlHeader + '/collection/queryByUserName',
    addCollection:urlHeader + '/collection/add',
    updateCollection:urlHeader + '/collection/update',
    delCollection:urlHeader + '/collection/delete',
    queryHistoryByUserName:urlHeader + '/history/queryByUserName',
    queryHistoryByVideoId:urlHeader + '/history/queryByVideoId',
    addHistory:urlHeader + '/history/add',
    queryFriendByUserName:urlHeader + '/friend/queryByUserName',
    isFriend:urlHeader + '/friend/isFriend',
    addFriend:urlHeader + '/friend/add',
    delFriend:urlHeader + '/friend/delete',
    messageQueryBySIdAndRId:urlHeader + '/message/queryBySIdAndRId',
    messageAdd:urlHeader + '/message/add',
}
//ajax全局设置
var token = sessionStorage.getItem("live_token");
//如果存储了token值，为即将发送的ajax请求设置请求头token值
if(token){
    $.ajaxSetup({
        beforeSend:function (xhr) {
            xhr.setRequestHeader("live_token",token);
        }
    });
}
$.ajaxSetup({
    type:'POST',
    complete:function (xhr,status) {
        if(status === "success"){
            var res = xhr.responseText;
            res = JSON.parse(res);
            //处理为登录异常
            if(res.code===311){
                layer.msg("您没有该操作权限",{icon:5})
            }else if(res.code === 322){
                layer.msg("登录失效,请先登录",{icon:5})
                if(window.top!=null){
                    setTimeout(function(){
                        window.top.location = "login.html"
                    },2000)
                }
            }
        }
    },
    error:function (xhr,status,error) {
        layer.msg("服务器异常",{icon:5})
    }
});
function submitFunction() {
            //这里唯一需要注意的就是这个form-add的id
            var formData = new FormData($("#form-add")[0]);
            $.ajax({
            //接口地址
       		url: urlconfig.videoUpload ,
        	type: 'POST',
        	data: formData,
        	async: false,
        	cache: false,
        	contentType: false,
        	processData: false,
        	success: function (data) {
            //成功的回调
            if(data.code == 300){

            }

        },
        error: function (returndata) {
           //请求异常的回调
           // modals.warn("网络访问失败，请稍后重试!");
        }
    });
}