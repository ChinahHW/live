function getQueryString(name) {
    var result = window.location.search.match(new RegExp("[\?\&]" + name + "=([^\&]+)", "i"));
    if (result == null || result.length < 1) {
        return "";
    }
    return result[1];
}

var liveName = getQueryString("liveName");

var number = 0;

var barrageId = 0;

$.ajax({
	        url: urlconfig.getLive,
	        async: true,
	        data:{"liveName":liveName},
	        success: function(data) {
	        	if(data.data.userName == localStorage.getItem('yonhuming')){
	        		$("#closeLive").show();
	        		$("#changeLive").show();
	        	}
	        	var player = videojs('my-player');
			    var options = {};
			    var player = videojs('my-player', options, function onPlayerReady() {
				var myPlayer = this;
				myPlayer.src({src: "http://127.0.0.1:81/"+data.data.address, type: "application/x-mpegURL"});
				videojs.log('Your player is ready!');
                // In this context, `this` is the player that was created by Video.js.
                this.play();
                // How about an event listener?
                this.on('ended', function() {
                videojs.log('Awww...over so soon?!');
                });
           });
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
	
	
});

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

function add(obj) {
	layer.load(2);
	$.ajax({
		url: urlconfig.addLive,
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
				window.top.location = "../index.html";
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

function del(obj) {
	layer.load(2);
	$.ajax({
		url: urlconfig.delLive,
		data: {"liveName":liveName},
		async: true,
		success: function(data) {
			layer.closeAll('loading');
			if(data.code == 200) {
				layer.msg('删除成功', {
					icon: 1
				});
				setTimeout(function() {
					location.reload();
				}, 2000)
				window.top.location = "./index.html";
			} else {
				layer.msg('删除失败', {
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

function cha(obj) {
	layer.load(2);
	$.ajax({
		url: urlconfig.updateLive,
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


function change(obj) {
	openUpdateMock();
}

function sendBarrage(barrage){
	$.ajax({
		url: urlconfig.sendBarrage,
		data: {"liveName":liveName,"barrage":barrage},
		async: true,
		success: function(data) {
			var val = document.getElementById("show").value+"\n"+document.getElementById("write").value;
			document.getElementById("write").value = "";
			document.getElementById("show").value = val;
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



setInterval(function () {
    $.ajax({
		url: urlconfig.getBarrage,
		data: {"liveName":liveName},
		async: true,
		success: function(data) {
			var html = $("#barrages").html();
			for(var i = 0;i < data.data.length;i++){
				html += "<div id='barrage"+number+"' class='barrage'  style='top: "+(150+Math.random()*600)+"px;'>"+data.data[i]+"</div>"
				number += 1;
				
			}
			$("#barrages").html(html);
			for(var i = 0;i < data.data.length;i++){
				barrage("barrage"+barrageId);
				barrageId += 1;
			}
		},
		error: function(err) {
			layer.closeAll('loading');
			layer.msg('网络异常', {
				icon: 5
			});
			console.log(err)
		}
	})
},10000)
