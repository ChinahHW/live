/**
 * Description:
 * @author zlp
 * @create 2018-09-07 14:51
 **/
layui.use('form', function() {
	var form = layui.form;
	//监听提交
	form.on('submit(*)', function(data) {
		var obj = data.field;
		if(obj.name == localStorage.getItem('yonhuming')) {
					exit()
					login(obj);
				}else{
					login(obj);
				}
		
		return false;
	});
});

function login(obj) {
	layer.load(2);
	$.ajax({
		url: urlconfig.userLogin,
		data: obj,
		async: true,
		success: function(data) {
			layer.closeAll('loading');
			if(data.code == 200) {
				localStorage.setItem('yonhuming', obj.name);
				localStorage.setItem('userId', data.data.userId);
				sessionStorage.setItem("live_token", data.data.token);
				if(data.data.userId == 1){
					window.location.href = "adminMain.html";
				}else{
					window.location.href = "index.html";
				}
				console.log(data.data.userId)
			} else if(data.code == 300) {
					layer.msg('系统同时只允许单个用户登陆', {
						icon: 5
					});
				
			} else {
				layer.msg('用户名或密码错误', {
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

function exit() {
	$.ajax({
		url: urlconfig.exit,
		async: true,
		success: function(data) {

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