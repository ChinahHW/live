/**
 * Description:
 * @author zlp
 * @create 2018-09-07 16:47
 **/

var form;
//生产部门

function departType() {
	$.ajax({
		url: urlconfig.queryAllDepart,
		async: true,
		success: function(data) {
			var html = "";
			html += '        <option>' + "" + '</option>'
			for(var i = 0; i < data.data.length; i++) {
				html +=
					'        <option value="' + data.data[i] + '">' + data.data[i] + '</option>'
			}
			$("#projectDepart").html(html);
			form.render('select');
		},
		error: function(err) {
			console.log(err)
		}
	});
	//项目属性
	$.ajax({
		url: urlconfig.queryProjectType,
		async: true,
		success: function(data) {
			var html = "";
			html += '        <option>' + "" + '</option>'
			for(var i = 0; i < data.data.length; i++) {
				html +=
					'        <option value="' + data.data[i] + '">' + data.data[i] + '</option>'
			}
			$("#projectType").html(html);
			form.render('select');
		},
		error: function(err) {
			console.log(err)
		}
	});
}

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
		contractnumber: function(value) {
			if(value.length < 1) {
				return '项目合同号不能为空';
			}
		}
	});
	//监听提交
	form.on('submit(formSubmit)', function(data) {
		if($("#projectAddressPro").find("option:selected").text() !== "请选择省份") {
			data.field.projectAddressPro = $("#projectAddressPro").find("option:selected").text();
		} else {
			data.field.projectAddressPro = '';
		}
		if($("#projectAddressCity").find("option:selected").text() !== "请选择城市") {
			data.field.projectAddressCity = $("#projectAddressCity").find("option:selected").text();
		} else {
			data.field.projectAddressCity = '';
		}
		if($("#projectAddressCounty").find("option:selected").text() !== "请选择县区") {
			data.field.projectAddressCounty = $("#projectAddressCounty").find("option:selected").text();
		} else {
			data.field.projectAddressCounty = '';
		}
		console.log(data.field);
		add(data.field);
		return false;
	});
	form.on('select(projectAddressPro)', function(data) {
		showCity(data);
		form.render();
	});
	form.on('select(projectAddressCity)', function(data) {
		showCountry(data);
		form.render();
	});
	form.on('select(projectAddressCounty)', function(data) {
		selecCountry(data);
		form.render();
	});
	var uploadInst = upload.render({
		elem: '#piliang', //绑定元素
		url: urlconfig.projectUploadExcel, //上传接口
		accept: 'file',
		field: "fileName",
		before: function(obj) {

		},
		done: function(res) {
			if(res.code == 200) {
				var htmlfail;
				var htmlsuccess;
				for(var i = 0; i < res.data.fail.length; i++) {
					htmlfail += '<tr>' +
						'        <td>' + res.data.fail[i].projectCode + '</td>' +
						'        <td>' + res.data.fail[i].projectName + '</td>' +
						'    </tr>'
				}
				for(var i = 0; i < res.data.success.length; i++) {
					htmlsuccess += '<tr>' +
						'        <td>' + res.data.success[i].projectCode + '</td>' +
						'        <td>' + res.data.success[i].projectName + '</td>' +
						'    </tr>'
				}
				$("#detaExcelTbodySuccess").html(htmlsuccess);
				$("#detaExcelTbodyErr").html(htmlfail);
				console.log(res.data.success.length,res.data.success.length);
				if(res.data.success.length == 0) {
					$(".suc").hide()
				}else{
					$(".suc").show()
				}
				if(res.data.fail.length == 0) {
					$(".err").hide()
				}else{
					$(".err").show()
				}
				openUploadExcel()
			}else{
				layer.msg('格式不正确', {
					icon: 5
				});
			}
		},
		error: function(err) {
			//请求异常回调
			console.log(err);
		}
	});
	departType()
});

if(localStorage.getItem('vip') == 0) {
	$(".vip").css("display", "none");
}

function add(obj) {
	layer.load(2);
	$.ajax({
		url: urlconfig.projectAdd,
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

function openUploadExcel() {
	layui.use(['layer'], function() {
		var layer = layui.layer,
			$ = layui.$;
		layer.open({
			type: 1, //类型
			area: ['600px', '90%'], //定义宽和高
			title: '项目详情', //题目
			shadeClose: false, //点击遮罩层关闭
			content: $('#detaExcel') //打开的内容
		});
	})
}