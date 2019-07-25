/**
 * Description:
 * @author zlp
 * @create 2018-09-08 17:10
 **/
var form;
var dataList;
var search = {};
search.currentPage = 1;
search.pageSize = 10;

$.ajax({
	url: urlconfig.projectAll,
	async: true,
	success: function(data) {
		var html = "";
		html += '        <option>' + "" + '</option>'
		for(var i = 0; i < data.data.length; i++) {
			html +=
				'        <option value="' + data.data[i].projectId + '">' + data.data[i].projectName + '</option>'
		}		
		$(".expendAttenProjectId").html(html);
		console.log($(".expendAttenProjectId"));
	},
	error: function(err) {
		console.log(err)
	}
});

$.ajax({
	url: urlconfig.expendType,
	async: true,
	success: function(data) {
		var html = "";
		html += '        <option>' + "" + '</option>'
		for(var i = 0; i < data.data.length; i++) {
			html +=
				'        <option value="' + data.data[i].expendTypeName + '">' + data.data[i].expendTypeName + '</option>'
		}		
		$("#expendStateCode").html(html);
		$("#expendType").html(html);
		$("#expendType1").html(html);
	},
	error: function(err) {
		console.log(err)
	}
});
query(search);
$("#add").bind("click", function() {
	openAddMock();
})
$("#codeSearch").bind("click", function() {
	var expendCode = $("#code").val();
	var expendCode1 = $("#expendStateCode").val();
	var expendCode2 = $("#expendTypeCode").val();
	search = {};
	if(expendCode || expendCode1 || expendCode2) {
		search.projectName = expendCode;
		search.expendType = expendCode1;
		search.expendState = expendCode2;
	}
	search.currentPage = 1;
	search.pageSize = 10;
	query(search)
})
document.getElementById("gaojisousuo").onclick = function() {
	openModak()
}

function layerInit(current, total) {
	layui.use(['form', 'layedit', 'laydate', 'laypage', 'table'], function() {
		form = layui.form,
			layedit = layui.layedit,
			laydate = layui.laydate,
			laypage = layui.laypage;
		//日期
		laydate.render({
			elem: '#expendCreateDateMin'
		});
		laydate.render({
			elem: '#expendCreateDateMax'
		});
		laypage.render({
			elem: 'tablePage', //注意，这里的 test1 是 ID，不用加 # 号
			limit: 10,
			curr: current,
			count: total, //数据总数，从服务端得到
			jump: function(obj, first) {
				//obj包含了当前分页的所有参数，比如：
				console.log(obj.curr); //得到当前页，以便向服务端请求对应页的数据。
				console.log(obj.limit); //得到每页显示的条数
				search.currentPage = obj.curr;
				search.pageSize = obj.limit;
				//首次不执行
				if(!first) {
					query(search);
				}
			}
		});
		//监听提交
		form.on('submit(demo1)', function(data) {
			search = data.field;
			search.currentPage = 1;
			search.pageSize = 10;
			console.log(search);
			query(search);
			return false;
		});
		form.on('submit(addSubmit)', function(data) {
			console.log(data.field);
			add(data.field);
			return false;
		});
		form.on('submit(updateSubmit)', function(data) {
			console.log(data.field);
			update(data.field);
			return false;
		});

	});
}

function tableInit(data) {
	console.log(data)
	var html = '';

	for(var i = 0; i < data.length; i++) {

		var btn = '<button data-id="' + data[i].expendId + '" class="layui-btn layui-btn-xs data-update vip">修改</button><button data-id="' + data[i].expendId + '" class="layui-btn layui-btn-xs layui-btn-danger data-delete vip">删除</button>';
		html += '<tr>' +
			/* '        <td>'+data[i].expendCode+'</td>' +*/
			'        <td>' + data[i].expendSerialCode + '</td>' +
			'        <td>' + data[i].expendCertificateCode + '</td>' +
			'        <td>' + data[i].expendType + '</td>' +
			'        <td>' + data[i].projectName + '</td>' +
			'        <td>' + data[i].expendState + '</td>' +
			'        <td>'+(data[i].expendMoney ? data[i].expendMoney:'')+'</td>' +
			'        <td>' + data[i].expendDes + '</td>' +
			'        <td>' + btn + '</td>' +
			'    </tr>'
	}

	$("#tableList").html(html);
	if(localStorage.getItem('vip') == 0) {
	$(".vip").css("display", "none");
}
	tableEvent();
}

function tableEvent() {
	
	$(".data-update").unbind();
	$(".data-update").bind("click", function() {
		var id = $(this).attr("data-id");
		console.log(id);
		for(var i = 0; i < dataList.length; i++) {
			if(id == dataList[i].expendId) {
				var data = dataList[i];
				console.log(data);
				for(var name in data) {
					$("#" + name).val(data[name]);
				}
				$("input[value='"+data.expendState+"']").prop('checked','true');
				form.render();
				break;
			}
		}
		openUpdateMock();
	});
	$(".data-delete").unbind();
	$(".data-delete").bind("click", function() {
		var id = $(this).attr("data-id");
		var obj = {};
		obj.expendId = id;
		layer.confirm('您确定要删除这条数据吗?', {
			btn: ['确定', '取消'] //按钮
		}, function() {
			dataDalete(obj);
		}, function() {

		});
	})
}
$(".project-name").blur(function() {
	var type = $(this).attr("data-type");
	var obj = {};
	obj.projectCode = $(this).val();
	queryByCode(obj, type);
})
if(localStorage.getItem('vip') == 0) {
	$(".vip").css("display", "none");
}
function showName(name, type, isRight) {
	if(isRight) {
		if(type == "add") {
			$("#projectNameAdd").text(name);
			$("#projectNameAddNull").css("display", "none");
			$("#projectNameAdd").css("display", "block");
		} else {
			$("#projectNameUpdate").text(name);
			$("#projectNameUpdate").css("display", "block");
			$("#projectNameUpdateNull").css("display", "none");
		}
	} else {
		if(type == "add") {
			$("#projectNameAdd").css("display", "none");
			$("#projectNameAddNull").css("display", "block");
		} else {
			$("#projectNameUpdateNull").css("display", "block");
			$("#projectNameUpdate").css("display", "none");
		}
	}
}

function queryByCode(obj, type) {
	$.ajax({
		url: urlconfig.projectCode,
		data: obj,
		async: true,
		success: function(data) {
			if(data.code == 200) {
				showName(data.data, type, true);
			} else {
				showName("", type, false);
			}
		},
		error: function(err) {
			layer.msg('网络异常', {
				icon: 5
			});
			console.log(err)
		}
	});
}

function add(obj) {
	layer.load(2);
	$.ajax({
		url: urlconfig.expendAdd,
		data: obj,
		async: true,
		success: function(data) {
			layer.closeAll('loading');
			if(data.code == 200) {
				layer.closeAll();
				layer.msg('保存成功', {
					icon: 1
				});
				query(search);
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

function query(obj) {
	layer.load(2);
	$.ajax({
		url: urlconfig.expendList,
		data: obj,
		async: true,
		success: function(data) {
			layer.closeAll();
			if(data.code == 200) {
				dataList = data.data.list;
				tableInit(data.data.list);
				layerInit(obj.currentPage, data.data.total);
			} else {
				layer.msg('获取数据失败', {
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

function update(obj) {
	layer.load(2);
	$.ajax({
		url: urlconfig.expendUpdate,
		data: obj,
		async: true,
		success: function(data) {
			console.log(obj);
			layer.closeAll("loading");
			if(data.code == 200) {
				layer.closeAll();
				query(search);
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

function dataDalete(obj) {
	layer.load(2);
	$.ajax({
		url: urlconfig.expendDelete,
		data: obj,
		async: true,
		success: function(data) {
			layer.closeAll();
			if(data.code == 200) {
				layer.msg('删除成功', {
					icon: 1
				});
				query(search);
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

function openModak() {
	layer.open({
		type: 1, //类型
		area: ['600px', '230px'], //定义宽和高
		title: '高级搜索', //题目
		shadeClose: false, //点击遮罩层关闭
		content: $('#motaikunag') //打开的内容
	});
	/*layui.use(['layer'], function () {
	    var layer = layui.layer, $ = layui.$;
	    layer.open({
	        type: 1,//类型
	        area: ['600px', '230px'],//定义宽和高
	        title: '高级搜索',//题目
	        shadeClose: false,//点击遮罩层关闭
	        content: $('#motaikunag')//打开的内容
	    });
	})*/
}

function openAddMock() {
	layui.use(['layer'], function() {
		var layer = layui.layer,
			$ = layui.$;
		layer.open({
			type: 1, //类型
			area: ['600px', '430px'], //定义宽和高
			title: '添加数据', //题目
			shadeClose: false, //点击遮罩层关闭
			content: $('#addData') //打开的内容
		});
	})
}

function openUpdateMock() {
	layui.use(['layer'], function() {
		var layer = layui.layer,
			$ = layui.$;
		layer.open({
			type: 1, //类型
			area: ['600px', '430px'], //定义宽和高
			title: '修改', //题目
			shadeClose: false, //点击遮罩层关闭
			content: $('#updateData') //打开的内容
		});
	})
}