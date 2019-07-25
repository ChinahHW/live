function getQueryString(name) {
    var result = window.location.search.match(new RegExp("[\?\&]" + name + "=([^\&]+)", "i"));
    if (result == null || result.length < 1) {
        return "";
    }
    return result[1];
}

var friendId = getQueryString("friendId");

$.ajax({
	        url: urlconfig.messageQueryBySIdAndRId,
	        async: true,
	        data:{"senderId":localStorage.getItem('userId'),"receiverId":friendId},
	        success: function(data) {
	        	var html = "";
	        	for(var i = 0;i < data.data.message.length;i++){
	        		if(data.data.message[i].senderid == localStorage.getItem('userId')){
	        			html += "<li class='item cl'> <a href='#'><i class='avatar size-L radius'><img alt='' style='width:80px;height:80px' src='img/"+data.data.sender[i].picture+"'></i></a>"
	        			+"<div class='comment-main'><header class='comment-header'>"
	        			+"<div class='comment-meta'><a class='comment-author' href='#'>"+data.data.sender[i].name+"</a> 评论于<time title='2014年8月31日 下午3:20' datetime='2014-08-31T03:54:20'>2014-8-31 15:20</time>"
	        			+"</div></header><div class='comment-body'><p><a href='#'>@"+data.data.receiver[i].name+"</a>&nbsp;"+data.data.message[i].content+"</p></div></div></li>"
	        		}else{
	        			html += "<li class='item cl comment-flip'> <a href='#'><i class='avatar size-L radius'><img alt='' style='width:80px;height:80px'  src='img/"+data.data.sender[i].picture+"'></i></a>"
	        			+"<div class='comment-main'><header class='comment-header'>"
	        			+"<div class='comment-meta'><a class='comment-author' href='#'>"+data.data.sender[i].name+"</a> 评论于<time title='2014年8月31日 下午3:20' datetime='2014-08-31T03:54:20'>2014-8-31 15:20</time>"
	        			+"</div></header><div class='comment-body'><p><a href='#'>@"+data.data.receiver[i].name+"</a>&nbsp;"+data.data.message[i].content+"</p></div></div></li>"
	        		}
	        	}
	        	$("#commentList").html(html);
	    	},
	    error: function(err) {
		    console.log(err)
	    }
});


function sendMessage(message){
	$.ajax({
		url: urlconfig.messageAdd,
		data: {"senderid":localStorage.getItem('userId'),"receiverid":friendId,"content":message},
		async: true,
		success: function(data) {
			document.getElementById("message").value = "";
			location.reload();
		},
		error: function(err) {
			layer.closeAll('loading');
			layer.msg('网络异常', {
				icon: 5
			});
			console.log(err)
		}
	});
}



/*<li class="item cl"> <a href="#"><i class="avatar size-L radius"><img alt="" src="/static-h-ui-net/h-ui/images/ucnter/avatar-default.jpg"></i></a>
		    <div class="comment-main">
		      <header class="comment-header">
		        <div class="comment-meta"><a class="comment-author" href="#">辉哥</a> 评论于
		          <time title="2014年8月31日 下午3:20" datetime="2014-08-31T03:54:20">2014-8-31 15:20</time>
		        </div>
		      </header>
		      <div class="comment-body">
		        <p><a href="#">@某人</a> 你是猴子派来的救兵吗？</p>
		      </div>
		    </div>
		  </li>
		  <li class="item cl comment-flip"> <a href="#"><i class="avatar size-L radius"><img alt="" src="/static-h-ui-net/h-ui/images/ucnter/avatar-default.jpg"></i></a>
		    <div class="comment-main">
		      <header class="comment-header">
		        <div class="comment-meta"><a class="comment-author" href="#">辉哥</a> 评论于
		          <time title="2014年8月31日 下午3:20" datetime="2014-08-31T03:54:20">2014-8-31 15:20</time>
		        </div>
		      </header>
		      <div class="comment-body">
		        <p><a href="#">@某人</a> 你是猴子派来的救兵吗？</p>
		      </div>
		    </div>
		  </li>*/