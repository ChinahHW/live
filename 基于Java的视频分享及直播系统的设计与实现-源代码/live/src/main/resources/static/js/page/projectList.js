/**
 * Description:
 * @author zlp
 * @create 2018-09-07 18:37
 **/
var form;
var dataList;
var search = {};
search.currentPage = 1;
search.pageSize = 10;
query(search);

function openModak() {
	layui.use(['layer'], function() {
		var $ = layui.$;
		layer.open({
			type: 1, //类型
			area: ['600px', '430px'], //定义宽和高
			title: '高级搜索', //题目
			shadeClose: false, //点击遮罩层关闭
			content: $('#motaikunag') //打开的内容
		});
	})
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

function opendetailsDataMock() {
	layui.use(['layer'], function() {
		var $ = layui.$;
		layer.open({
			type: 1, //类型
			area: ['600px', '95%'], //定义宽和高
			title: '项目详情', //题目
			shadeClose: false, //点击遮罩层关闭
			content: $('#detailsData') //打开的内容
		});
	})
}

document.getElementById("gaojisousuo").onclick = function() {
	openModak()
}

$("#codeSearch").bind("click", function() {
	var projectName = $("#projectCode").val();
	search = {};
	if(projectName) {
		search.projectName = projectName;
	}
	search.currentPage = 1;
	search.pageSize = 10;
	console.log(projectName);
	query(search)
})

layui.use('element', function() {
	var element = layui.element;

});

function layerInit(current, total) {
	layui.use(['form', 'layedit', 'laydate', 'laypage', 'table'], function() {
		form = layui.form,
			layedit = layui.layedit,
			laydate = layui.laydate,
			laypage = layui.laypage;

		//日期
		laydate.render({
			elem: '#projectStartDateMin'
		});
		laydate.render({
			elem: '#projectStartDateMax'
		});
		laydate.render({
			elem: '#projectStartDate'
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
		laypage.render({
			elem: 'tablePage', //注意，这里的 test1 是 ID，不用加 # 号
			limit: 10,
			curr: current,
			count: total, //数据总数，从服务端得到
			jump: function(obj, first) {
				//obj包含了当前分页的所有参数，比如：
//				console.log(obj.curr); //得到当前页，以便向服务端请求对应页的数据。
//				console.log(obj.limit); //得到每页显示的条数
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
			if($("#projectAddressPro").find("option:selected").text() !== "请选择省份") {
				data.field.projectAddressPro = $("#projectAddressPro").find("option:selected").text();
			} else {
				data.field.projectAddressPro = null;
			}
			if($("#projectAddressCity").find("option:selected").text() !== "请选择城市") {
				data.field.projectAddressCity = $("#projectAddressCity").find("option:selected").text();
			} else {
				data.field.projectAddressCity = null;
			}
			if($("#projectAddressCounty").find("option:selected").text() !== "请选择县区") {
				data.field.projectAddressCounty = $("#projectAddressCounty").find("option:selected").text();
			} else {
				data.field.projectAddressCounty = null;
			}
			search = data.field;
			search.currentPage = 1;
			search.pageSize = 10;
			console.log(search);
			query(search);
			return false;
		});
		form.on('submit(updateSubmit)', function(data) {
			console.log(data.field);
			update(data.field);
			return false;
		});

	});
}

if(localStorage.getItem('vip') == 0) {
	$(".vip").css("display", "none");
}

function tableInit(data) {
	var html = '';
	for(var i = 0; i < data.length; i++) {
		var btn = '<button data-id="' + data[i].projectId + '" class="layui-btn layui-btn-xs layui-btn-danger data-details">详情</button><button data-id="' + data[i].projectId + '" class="layui-btn layui-btn-xs data-update vip">修改</button><button data-id="' + data[i].projectId + '" class="layui-btn layui-btn-xs layui-btn-danger data-delete vip">删除</button>';
		html += '<tr>' +
			'        <td>' + data[i].projectCode + '</td>' +
			'        <td>' + data[i].projectName + '</td>' +
			'        <td>' + (data[i].projcetContact ? data[i].projcetContact : '') + '</td>' +
			'        <td>' + data[i].projectManager + '</td>' +
			'        <td>' + data[i].projectDepart + '</td>' +
			'        <td>' + data[i].projectType + '</td>' +
//			'        <td>' + (data[i].projectContactTel ? data[i].projectContactTel : '') + '</td>' +
			'        <td>' + (data[i].projectMoney ? data[i].projectMoney : '') + '</td>' +
			'        <td>' + (data[i].projectContactCard ? data[i].projectContactCard : '') + '</td>' +
			'        <td>' + data[i].projectStartDate + '</td>' +
			'        <td>' + data[i].projectEndDate + '</td>' +
			'        <td>' + data[i].projectAddressPro + '-' + data[i].projectAddressCity + '-' + data[i].projectAddressCounty + '</td>' +
			'        <td>' + data[i].projectDes + '</td>' +
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
			if(id == dataList[i].projectId) {
				var data = dataList[i];
				console.log(data);
				for(var name in data) {
					$("#" + name).val(data[name]);
				}
				form.render();
				break;
			}
		}
		openUpdateMock();
	});
	$(".data-details").unbind();
	$(".data-details").bind("click", function() {
		var id = $(this).attr("data-id");
		detaildata(id);
		opendetailsDataMock();
	});
	$(".data-delete").unbind();
	$(".data-delete").bind("click", function() {
		var id = $(this).attr("data-id");
		var obj = {};
		obj.projectId = id;
		layer.confirm('您确定要删除这条数据吗?', {
			btn: ['确定', '取消'] //按钮
		}, function() {
			dataDalete(obj);
		}, function() {

		});
	})
}

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
		$("#sprojectDepart").html(html);
	},
	error: function(err) {
		console.log(err)
	}
});

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
		$("#projectType1").html(html);
		$("#projectType").html(html);
		$("#sprojectType").html(html);
	},
	error: function(err) {
		
	}
});

//导出数据
$("#daochu").bind("click", function() {
    var x;
    var dc = "?";
    var y=-1;
    /*for(x in search) {
        y++;
        if(y<1){
            dc += x+"="+search[x];
        }else{
            dc += "&"+x+"="+search[x];
        }

    }
    console.log(urlconfig.print+dc);
    window.location.href = urlconfig.print+dc;*/

    	$.ajax({
            type : "post",
    		url: urlconfig.print,
    		data:search,
    		async: true,
    		success: function(data) {
    			layer.closeAll();
    			if(data.code == 200) {
                    window.location.href = urlHeader+"/project/download?filePath="+data.data;
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
})

//$("#daochu1").click(function(){
//	window.location.href = urlHeader+"/person/download";
//})

function query(obj) {
	layer.load(2);
	$.ajax({
		url: urlconfig.projectList,
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

function detaildata(obj) {
	layer.load(2);
	$.ajax({
		url: urlconfig.detailAll,
		data: {
			projectId: obj
		},
		async: true,
		success: function(data) {
			console.log(urlconfig.detailAll);
			layer.closeAll("loading");
			if(data.code == 200) {
				for(var i = 0; i < data.data.length; i++) {
					if(obj == data.data[0].projectId) {
						for(var name in data.data[0]) {
							$("#s" + name).val(data.data[0][name]);
						}
						$("#waixiegongchengkuan").val(data.data[1].外协工程款);
						$("#lvyuejintuihui").val(data.data[1].履约保证金退回);
						$("#toubiaojintuichu").val(data.data[1].投标保证金退回);
						$("#cehuishouru").val(data.data[1].测绘收入);
						$("#xiangmuyidaozhang").val(data.data[1].项目已到账);
						$("#xiangmuyikaipiao").val(data.data[1].项目已开票);
						var all = data.data[3].allIncome - data.data[3].allExpend;
						$("#jieyu").val(all);
						$("#xiadaxiangmufeidanjia").val(data.data[2].下达项目费单价);
						$("#xiadaxiangmufeizong").val(data.data[2].下达项目费总);
						$("#zhongbiaofuwufei").val(data.data[2].中标服务费);
						$("#chushigongzi").val(data.data[2].厨师工资);
						$("#sijigongzi").val(data.data[2].司机工资);
						$("#yuangongfuli").val(data.data[2].员工福利);
						$("#zaibiangongjijin").val(data.data[2].在编人员公积金);
						$("#zaibiangongzi").val(data.data[2].在编人员工资成本);
						$("#zaibianshebao").val(data.data[2].在编人员社保);
						$("#zaibianbuzhu").val(data.data[2].在编人员餐费降温烤火等补助);
						$("#toubiaobaozhengjin").val(data.data[2].投标保证金支出);
						$("#shengchanchengben").val(data.data[2].生产成本);
						$("#bianwaigongjijin").val(data.data[2].编外人员公积金);
						$("#bianwaigongzi").val(data.data[2].编外人员工资成本);
						$("#bianwaishebao").val(data.data[2].编外人员社保);
						$("#bianwaibuzhu").val(data.data[2].编外人员餐费降温烤火等补助);
						$("#shebeizhejiufei").val(data.data[2].设备折旧费);
						$("#huafeibuzhu").val(data.data[2].话费补助);
						$("#lvyuebapzhengjin").val(data.data[2].项目履约保证金);
						break;
					}
				}
				form.render();
			} else {
				layer.msg('失败', {
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
		url: urlconfig.projectUpdate,
		data: obj,
		async: true,
		success: function(data) {
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
		url: urlconfig.projectDelete,
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