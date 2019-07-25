function getQueryString(name) {
    var result = window.location.search.match(new RegExp("[\?\&]" + name + "=([^\&]+)", "i"));
    if (result == null || result.length < 1) {
        return "";
    }
    return result[1];
}

var videoId = getQueryString("videoId");

var isAgree = 1;

$.ajax({
	url: urlconfig.getVideoById,
	async: true,
	data:{"videoId":videoId},
	success: function(data) {
		
		var html = "";
		
		html += "<video width='100%' height='400' src='"+data.data.url+"' controls='controls'  frameborder='0' allowfullscreen>"
			 		+"your browser does not support the video tag</video>"
			 		+"<div class='line'></div><h1 class='vid-name'><a href='#'>"+data.data.name+"</a></h1>"
			 		+"<div class='info'><h5>By <a href='#'>Kelvin</a></h5>"
			 		+"<span><i class='fa fa-calendar'></i>"+data.data.time+"</span><span><i class='fa fa-heart'></i>"+data.data.recommend+"</span>"
			 		+"</div>"
			 		+"<p style='margin-top: 20px'>"+data.data.content+"</p>";
		$.ajax({
	        url: urlconfig.commentTopThree,
	        async: true,
	        data:{"videoId":videoId},
	        success: function(data) {
		        for(var i=0;i<data.data.comment.length;i++){
			        html += "<div>" 
			                +"<table><tr><th rowspan='2'><a href='friend.html?friendId="+data.data.user[i].id+"'><img style='width:80px;height:80px' src='img/"+data.data.user[i].picture+"' alt='' /></a>&nbsp;&nbsp;&nbsp;&nbsp;</th>"
		            		+"<th colspan='4'><span>"+data.data.comment[i].content+"</span></th></tr>"
		                    +"<tr><td><label>5/4/2014 at 22:00</label></td><td><img src='http://localhost:8080/images/点赞.jpg' class='img-responsive' alt='' style='display:block'/></td><td style='padding-left:350px'></td><td><a id='agree"+data.data.comment[i].id+"' onclick=agree('"+data.data.comment[i].id+"') class='agree'>"
		                    +data.data.comment[i].agree
		                    +"</a></td><td>&nbsp;<a class='reply' onclick=reply('"+data.data.user[i].name+"') id='reply' style='color: #dfb636'>回复</a></td></tr></table>"
		                    +"<i class='rply-arrow'></i></div><div class='clearfix'></div></div></div>"
			 		        +"</div>";
		    }
		    $("#videoDetail").html(html);
		
	    },
	    error: function(err) {
		    console.log(err)
	    }
    });
	},
	error: function(err) {
		console.log(err)
	}
});

$(".btnSend").click(function() {
				$.ajax({
					url: urlconfig.commentAdd,
					async: true,
					data:{"userid":localStorage.getItem('userId'),"videoid":videoId,"content":document.getElementById("sender").value+" send to "+document.getElementById("receiver").value+" : "+document.getElementById("message").value},
					success: function(data) {
						document.getElementById("message").value = "";
						window.top.location = "video.html?videoId="+videoId;
					},
					error: function(err) {
						layer.closeAll('loading');
						layer.msg('网络异常', {
							icon: 5
						});
						console.log(err)
					}
				});
		})

function reply(receiverName){
	document.getElementById("receiver").value = receiverName;
	document.getElementById("sender").value = localStorage.getItem('yonhuming');
	window.scrollTo(0,document.body.scrollHeight);
}

function agree(commentId){
	if(isAgree == 1){
		$.ajax({
					url: urlconfig.commentAgree,
					async: true,
					data:{"commentId":commentId},
					success: function(data) {
						document.getElementById("agree"+commentId).innerHTML = parseInt(document.getElementById("agree"+commentId).innerHTML) + 1;
						isAgree = isAgree * (-1);
					},
					error: function(err) {
						layer.closeAll('loading');
						layer.msg('网络异常', {
							icon: 5
						});
						console.log(err)
					}
			});
	}else if(isAgree == -1){
		$.ajax({
					url: urlconfig.commentDisagree,
					async: true,
					data:{"commentId":commentId},
					success: function(data) {
						document.getElementById("agree"+commentId).innerHTML = parseInt(document.getElementById("agree"+commentId).innerHTML) - 1;
						isAgree = isAgree * (-1);
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
	
}


$.ajax({
	url: urlconfig.addHistory,
	async: true,
	data:{"videoid":videoId,"userid":localStorage.getItem('userId')},
	success: function(data) {
		
	},
	error: function(err) {
		console.log(err)
	}
});

$.ajax({
	url: urlconfig.queryHistoryByVideoId,
	async: true,
	data:{"videoId":videoId,"currentPage":1,"pageSize":5,"orderSort":"desc"},
	success: function(data) {
		var html = "";
		for (var i = 0;i<data.data.list.length;i++){
			html += "<li><table>"
	        			 +"<tr>"
	        			 +"<th rowspan='2'><a href='friend.html?friendId="+data.data.list[i].id+"'><img style='width:80px;height:80px' src='img/"+data.data.list[i].picture+"' alt='' /></a>&nbsp;&nbsp;&nbsp;&nbsp;</th>"
	        			 +"<th><span  style='padding-left: 10px;font-size:25px'>"+data.data.list[i].name+"</span></th>"
	        			 +"</tr>"
	        			 +"<tr>"
	        			 +"<td style='padding-left: 10px;font-size:16px'>内容</td>"
	        			 +"</tr>"
	        			 +"</table></li>";
		}
		$("#history").html(html);
	},
	error: function(err) {
		console.log(err)
	}
});