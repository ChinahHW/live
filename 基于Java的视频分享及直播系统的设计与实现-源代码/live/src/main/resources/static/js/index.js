var search = {};
search.currentPage = 1;
search.pageSize = 5;
search.orderName = 'recommend';
search.orderSort = 'desc';

$.ajax({
	url: urlconfig.videoTopFive,
	async: true,
	data:search,
	success: function(data) {
		var html = "";
		html += "<div class='zoom-caption'><span>全网最火</span>"
				+"<a href='video.html?videoId="+data.data.list[0].id+"'>"
				+"<i class='fa fa-play-circle-o fa-5x' style='color: #fff'></i></a><p>"+data.data.list[0].name+"</p></div>"
				+"<img src='img/"+data.data.list[0].videopicture+"'/>";
					
		$("#videoTopOne").html(html);
		
		html = "";
		var k = 1;
		for (var i = 0;i < 2;i++){
			html += "<div class='col-md-3'>"
			for(var j = 0; j < 2; j++){
				
				html += "<div class='zoom-container'>"
		            	+"<div class='zoom-caption'>"
		            	+"<span>全网最火</span><a href='video.html?videoId="+data.data.list[k].id+"'>"
		            	+"<i class='fa fa-play-circle-o fa-5x' style='color: #fff'></i></a>"
		            	+"<p>"+data.data.list[k].name+"</p></div>"
		            	+"<img src='img/"+data.data.list[k].videopicture+"' /></div>"
		        k += 1;
		}
			html +=		"</div>"
		}
		
		
		$("#videoTopTwoToFive").html(html);
		$("#name").text(localStorage.getItem('yonhuming')+": Welcome to 视界大不同!");
	},
	error: function(err) {
		console.log(err)
	}
});


$.ajax({
	url: urlconfig.videoGetRecommend,
	async: true,
	data:{"userName":localStorage.getItem('yonhuming')},
	success: function(data) {
		
		var html = "";
		for(var i=0;i<data.data.length;i++){
			html += "<div class='col-md-6'>"
					+"<div class='wrap-vid'>"
						+"<div class='zoom-container'>"
							+"<div class='zoom-caption'>"
								+"<span>猜你喜欢</span><a href='video.html?videoId="+data.data[i].id+"'>"
								+"<i class='fa fa-play-circle-o fa-5x' style='color: #fff'></i>"
								+"</a><p>"+data.data[i].name+"</p></div>"
								+"<img src='img/"+data.data[i].videopicture+"' /></div><h3 class='vid-name'><a href='#'>"+data.data[i].name+"</a></h3>"
								+"<div class='info'><h5>By <a href='#'>Kelvin</a></h5>"
								+"<span><i class='fa fa-calendar'></i>"+data.data[i].time+"</span> <span><i class='fa fa-heart'></i>"+data.data[i].recommend+"</span>"
								+"</div></div><p class='more'>"+data.data[i].content+"</p>"
								+"<a href='video.htm' class='btn btn-1'>Read More</a></div>";
		}
		$("#recomment").html(html);
	},
	error: function(err) {
		console.log(err)
	}
});


$.ajax({
	url: urlconfig.videoGetRand,
	async: true,
	success: function(data) {
		
		var html = "";
		
		var k = 0;
		for(var j = 0;j<data.data.length/2;j++){
			html += "<div class='col-md-4'>";
		
			for(var i=0;i<2;i++){	
				html +="<div class='wrap-vid'>"
			            	+"<div class='zoom-container'>"
			                	+"<div class='zoom-caption'>"
			                    	+"<span>随机推送</span><a href='video.html?videoId="+data.data[k].id+"'>"
			                    	+"<i class='fa fa-play-circle-o fa-5x' style='color: #fff'></i></a>"
			                    	+"<p>"+data.data[k].name+"</p></div><img src='img/"+data.data[k].videopicture+"' /></div>"
			                    	+"<h3 class='vid-name'><a href='#'>"+data.data[k].name+"</a></h3><div class='info'>"
			                    	+"<h5>By <a href='#'>Kelvin</a></h5><span><i class='fa fa-calendar'></i>"+data.data[k].time+"</span>"
			                    	+"<span><i class='fa fa-heart'></i>"+data.data[k].recommend+"</span></div></div>";
			                    	
			    k += 1;
		}
			html += "</div>";
		}
		
		$("#rand").html(html);
	},
	error: function(err) {
		console.log(err)
	}
});



$.ajax({
	url: urlconfig.videoTheLatest,
	async: true,
	data:{"currentPage":1,"pageSize":3,"orderName":"time","orderSort":"desc"},
	success: function(data) {
		var html = "";
		for(var i=0;i<data.data.list.length;i++){
			html += "<div class='col-md-4'>"
					+"<div class='wrap-vid'>"
						+"<div class='zoom-container'>"
							+"<div class='zoom-caption'>"
								+"<span>最近</span><a href='video.html?videoId="+data.data.list[i].id+"'>"
								+"<i class='fa fa-play-circle-o fa-5x' style='color: #fff'></i>"
								+"</a><p>"+data.data.list[i].name+"</p></div>"
								+"<img src='img/"+data.data.list[i].videopicture+"' /></div><h3 class='vid-name'><a href='#'>"+data.data.list[i].name+"</a></h3>"
								+"<div class='info'><h5>By <a href='#'>Kelvin</a></h5>"
								+"<span><i class='fa fa-calendar'></i>"+data.data.list[i].time+"</span> <span><i class='fa fa-heart'></i>"+data.data.list[i].recommend+"</span>"
								+"</div></div></div>";
		}
		$("#latest").html(html);
		
	},
	error: function(err) {
		console.log(err)
	}
});

function getCategory(category) {
	$.ajax({
		url: urlconfig.getCatetory,
		data: {"category":category},
		async: true,
		success: function(data) {
			var html = "";
		for(var i=0;i<data.data.length;i++){
			html += "<div class='post wrap-vid'>"
						+"<div class='zoom-container'>"
							+"<div class='zoom-caption'>"
								+"<span>"+category+"</span><a href='video.html?videoId="+data.data[i].id+"'>"
								+"<i class='fa fa-play-circle-o fa-5x' style='color: #fff'></i>"
								+"</a><p>"+data.data[i].name+"</p></div>"
								+"<img src='img/"+data.data[i].videopicture+"' /></div>"
								+"<div class='wrapper'><h5 class='vid-name'><a href='#'>"+data.data[i].name+"</a></h5>"
								+"<div class='info'><h5>By <a href='#'>Kelvin</a></h5>"
								+"<span><i class='fa fa-calendar'></i>"+data.data[i].time+"</span> <span><i class='fa fa-heart'></i>"+data.data[i].recommend+"</span>"
								+"</div></div></div>";
		}
		$("#category").html(html);
		},
		error: function(err) {
			layer.msg('网络异常', {
				icon: 5
			});
			console.log(err)
		}
	});
}


$.ajax({
	url: urlconfig.queryLive,
	async: true,
	success: function(data) {
		var html = "";
		for(var i=0;i<data.data.length;i++){
			html += "<div class='col-md-6'>"
					+"<div class='wrap-vid'>"
						+"<div class='zoom-container'>"
							+"<div class='zoom-caption'>"
								+"<span>正在直播</span><a href='liveindex.html?liveName="+data.data[i].userName+"'>"
								+"<i class='fa fa-play-circle-o fa-5x' style='color: #fff'></i>"
								+"</a><p>"+data.data[i].title+"</p></div>"
								+"<img src='img/"+data.data[i].picture+"' /></div><h3 class='vid-name'><a href='#'>"+data.data[i].title+"</a></h3>"
								+"<div class='info'><h5>By <a href='#'>Kelvin</a></h5>"
								+"<span><i class='fa fa-calendar'></i>时间</span> <span><i class='fa fa-heart'></i>评论</span>"
								+"</div></div><p class='more'>"+data.data[i].content+"</p>"
								+"<a href='video.htm' class='btn btn-1'>Read More</a></div>";
		}
		$("#live").html(html);
		
	},
	error: function(err) {
		console.log(err)
	}
});
/*
							<div class="post">
								<a href="single.html">
									<img src="img/user.png" />
								</a>
								<div class="wrapper">
									<a href="#"><h5>Curabitur tincidunt porta lorem.</h5></a>
									<ul class="list-inline">
										<li><i class="fa fa-calendar"></i>25/3/2015</li> 
										<li><i class="fa fa-comments"></i>1,200</li>
									</ul>
								</div>
							</div>
							* 
							* 
							* 
							* 
							* 
							* 
							* */