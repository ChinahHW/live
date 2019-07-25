var search = {};
search.currentPage = 1;
search.pageSize = 5;
search.userName = localStorage.getItem('yonhuming');


$.ajax({
	        url: urlconfig.userList,
	        async: true,
	        data:search,
	        success: function(data) {
	        	var html = "";
				html += "<a href='#' style='background-image:url(img/"+data.data.list[0].picture+")'><span>"+data.data.list[0].name+"</span>";
				$("#head").html(html);
	    	},
	    error: function(err) {
		    console.log(err)
	    }
});

$.ajax({
	        url: urlconfig.queryCollection,
	        async: true,
	        data:search,
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
	        data:search,
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
	        url: urlconfig.queryFriendByUserName,
	        async: true,
	        data:search,
	        success: function(data) {
	        	var html = "";
	        	for (var i = 0; i < data.data.list.length; i++){
	        		html += "<li><table>"
	        			 +"<tr>"
	        			 +"<th rowspan='2'><a href='friend.html?friendId="+data.data.list[i].id+"'><img src='img/"+data.data.list[i].picture+"' alt='' /></a>&nbsp;&nbsp;&nbsp;&nbsp;</th>"
	        			 +"<th><span  style='padding-left: 10px;font-size:25px'>"+data.data.list[i].name+"</span></th>"
	        			 +"</tr>"
	        			 +"<tr>"
	        			 +"<td style='padding-left: 10px;font-size:16px'>内容</td>"
	        			 +"</tr>"
	        			 +"</table></li>";
	        	}
				
				$("#friend").html(html);
	    	},
	    error: function(err) {
		    console.log(err)
	    }
});

layui.use(['form', 'layedit', 'laydate', 'upload'], function() {
	form = layui.form, layer = layui.layer, layedit = layui.layedit, laydate = layui.laydate, upload = layui.upload;

	//日期
	laydate.render({
		elem: '#projectStartDate'
	});
	//自定义验证规则
	form.verify({
		title: function(value) {
			if(value.length < 1) {
				return '标题不能为空';
			}
		},
		pass: [/(.+){6,12}$/, '密码必须6到12位'],
		content: function(value) {
			layedit.sync(editIndex);
		},
		money: function(value) {
			if(value.length < 1) {
				return '金额不能为空';
			}
		},
		iphone: function(value) {
			if(value.length < 1) {
				return '电话不能为空';
			}
		},
	});
	//监听提交
	form.on('submit(formSubmit)', function(data) {
		console.log(data.field);
		add(data.field);
		return false;
	});
	
	form.on('submit(updateSubmit)', function(data) {
		console.log(data.field);
		cha(data.field);
		return false;
	});
	
	form.on('submit(uploadSubmit)', function(data) {
		console.log(data.field);
		upl(data.field);
		return false;
	});
	
	
});

function upl(obj){
	layer.load(2);
	$.ajax({
		url: urlconfig.addVideo,
		data: obj,
		async: true,
		success: function(data) {
			layer.closeAll('loading');
			if(data.code == 200) {
				layer.msg('上传成功，待审核', {
					icon: 1
				});
				setTimeout(function() {
					location.reload();
				}, 2000)
			} else {
				layer.msg('上传失败', {
					icon: 5
				});
			}
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

function uploadVideo(obj){
	openUploadMock();
}


function openUploadMock() {
	layui.use(['layer'], function() {
		var $ = layui.$;
		layer.open({
			type: 1, //类型
			area: ['600px', '430px'], //定义宽和高
			title: '数据修改', //题目
			shadeClose: false, //点击遮罩层关闭
			content: $('#uploadData') //打开的内容
		});
	})
}

function change(obj) {
	openUpdateMock();
}

function cha(obj) {
	layer.load(2);
	$.ajax({
		url: urlconfig.userUpdate,
		data: obj,
		async: true,
		success: function(data) {
			layer.closeAll('loading');
			if(data.code == 200) {
				layer.msg('保存成功', {
					icon: 1
				});
				setTimeout(function() {
					location.reload();
				}, 2000)
			} else {
				layer.msg('保存失败', {
					icon: 5
				});
			}
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



function openUpdateMock() {
	layui.use(['layer'], function() {
		var $ = layui.$;
		layer.open({
			type: 1, //类型
			area: ['600px', '430px'], //定义宽和高
			title: '数据修改', //题目
			shadeClose: false, //点击遮罩层关闭
			content: $('#updateData') //打开的内容
		});
	})
}


function getFileUrl(sourceId) {
    var url;
    if (navigator.userAgent.indexOf("MSIE")>=1) { // IE
        url = document.getElementById(sourceId).value;
    } else if(navigator.userAgent.indexOf("Firefox")>0) { // Firefox
        url = window.URL.createObjectURL(document.getElementById(sourceId).files.item(0));
    } else if(navigator.userAgent.indexOf("Chrome")>0) { // Chrome
        url = window.URL.createObjectURL(document.getElementById(sourceId).files.item(0));
    }
    return url;
}

function preImg(sourceId, targetId) {
    var url = getFileUrl(sourceId);
    var imgPre = document.getElementById(targetId);
    imgPre.src = url;
}
