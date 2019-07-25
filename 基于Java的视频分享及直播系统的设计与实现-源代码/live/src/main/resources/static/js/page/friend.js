function getQueryString(name) {
    var result = window.location.search.match(new RegExp("[\?\&]" + name + "=([^\&]+)", "i"));
    if (result == null || result.length < 1) {
        return "";
    }
    return result[1];
}

var friendId = getQueryString("friendId");

$.ajax({
	        url: urlconfig.queryUserById,
	        async: true,
	        data:{"userId":friendId,"currentPage":1,"pageSize":5},
	        success: function(data) {
	        	var html = "";
				html += "<a href='talk.html?friendId="+data.data.id+"' style='background-image:url(img/"+data.data.picture+")'><span>"+data.data.name+"</span>";
				$("#head").html(html);
	    	},
	    error: function(err) {
		    console.log(err)
	    }
});

$.ajax({
	        url: urlconfig.queryCollection,
	        async: true,
	        data:{"userId":friendId,"currentPage":1,"pageSize":5},
	        success: function(data) {
	        	var html = "";
	        	for (var i = 0; i < data.data.list.length; i++){
	        		html += "<h3>"+data.data.list[i].name+"</h3>"
						 +"<figure><img src='img/"+data.data.list[i].videopicture+"'></figure><ul>"
						 +"<p>"+data.data.list[i].content+"</p>"
						 +"<a title='/' href='video.html?videoId="+data.data.list[i].id+"' target='_blank' class='readmore'>前往观看>></a></ul>"
						 +"<p class='dateview'><span>"+data.data.list[i].time+"</span><span>"+data.data.list[i].clazz+"</span><span>"+data.data.list[i].recommend+"</span></p>";
	        	}
				
				$("#collection").html(html);
	    	},
	    error: function(err) {
		    console.log(err)
	    }
});

$.ajax({
	        url: urlconfig.queryHistoryByUserName,
	        async: true,
	        data:{"userId":friendId,"currentPage":1,"pageSize":5},
	        success: function(data) {
	        	var html = "";
	        	for (var i = 0; i < data.data.list.length; i++){
	        		html += "<li><a href='video.html?videoId="+data.data.list[i].id+"'>"+data.data.list[i].name+"</a></li>";
	        	}
				
				$("#history").html(html);
	    	},
	    error: function(err) {
		    console.log(err)
	    }
});



$.ajax({
	        url: urlconfig.isFriend,
	        async: true,
	        data:{"userName":localStorage.getItem('yonhuming'),"friendId":friendId},
	        success: function(data) {
	        	var html = "";
	        	/*if(data.data == null){
	        		html += "<button style='width: 80px;' onclick=addFriend()>关注</button>";
	        	}*/
	        	if(data.data[0].id != null){
					html += "<button style='width: 80px;' onclick=delFriend("+data.data[0].id+")>取消关注</button>";
	        	}
	        	
	        	
				$("#guanzhu").html(html);
	    	},
	    error: function(err) {
		    console.log(err)
	    }
});

function addFriend(){
	$.ajax({
	        url: urlconfig.addFriend,
	        async: true,
	        data:{"userid":localStorage.getItem('userId'),"friendid":friendId},
	        success: function(data) {
	        	if(data.code == 200){
	        		alert("add success")
	        		window.location.href = "friend.html?friendId="+friendId;
	        	}else{
	        		alert("请勿重复添加！")
	        	}
	        	
	    	},
	    error: function(err) {
		    console.log(err)
	    }
});
}

function delFriend(id){
	$.ajax({
	        url: urlconfig.delFriend,
	        async: true,
	        data:{"friendId":id},
	        success: function(data) {
	        	if(data.code == 200){
	        		alert("delete success")
	        		window.location.href = "friend.html?friendId="+friendId;
	        	}else{
	        		alert("删除失败！")
	        	}
	    	},
	    error: function(err) {
		    console.log(err)
	    }
});
}
