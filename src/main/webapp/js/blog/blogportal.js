//url取参
var $getQueryString = function(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.parent.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}

var aid = $getQueryString("aid");
var uid = $getQueryString("uid");

/* 头部菜单栏逻辑 */
$("#mainhome").click(function() {
	window.parent.location.href = "../main";
});

$("#myhome").click(function() {
	window.parent.location.href = "../blogPortal?uid="+uid;
});

$("#newartical").click(function() {
	window.parent.location.href = "../blogHandle";
});

$("#contact").click(function() {
	alert("该板块尚未开发");
});

$("#manage").click(function() {
	window.parent.location.href = "../blogManage";
});

$("#draft").click(function() {
	window.parent.location.href = "../blogDraft";
})

/* 右侧公告逻辑 */
$(function(){
	$getTheBlogger();
});

var $getTheBlogger = function(){
	$.ajax({
		type : "get",
		url : "../getTheBlogger",
		dataType : "json",
		data : {
			aid : aid ,
			uid : uid
		},
		async : true,
		success : function(data) {
			console.log(data);
			uid = data.uid;
			$("#headTitle").text(data.nickname);
			$("#nick").text(data.nickname);
			$("#garAge").text(data.blogAge);
			$("#garAge").attr("title","入园时间："+data.makeDay);
			$("#fan").text(data.fans);
			$("#follow").text(data.follows);
		},
		error : function() {
			alert("数据获取失败");
		}
	});
}

$("#nick").click(function() {
	$.ajax({
		type : "get",
		url : "../verGoYuan",
		dataType : "json",
		data : {
		},
		async : true,
		success : function(data) {
			console.log(data);
			if(data == 1){
				jqalert({
					title: '提示弹窗',
					cont: '您未登录，无法前往园子'
				});
			} else{
				window.parent.location.href = "../userinfo?id="+uid;
			}
		},
		error : function() {
			alert("数据获取失败");
		}
	});
});

$("#garAge").click(function() {
	$.ajax({
		type : "get",
		url : "../verGoYuan",
		dataType : "json",
		data : {
		},
		async : true,
		success : function(data) {
			console.log(data);
			if(data == 1){
				jqalert({
					title: '提示弹窗',
					cont: '您未登录，无法前往园子'
				});
			} else{
				window.parent.location.href = "../userinfo?id="+uid;
			}
		},
		error : function() {
			alert("数据获取失败");
		}
	});
});

$("#fan")
		.click(
				function() {
					$.ajax({
						type : "get",
						url : "../verGoYuan",
						dataType : "json",
						data : {
						},
						async : true,
						success : function(data) {
							console.log(data);
							if(data == 1){
								jqalert({
									title: '提示弹窗',
									cont: '您未登录，无法前往园子'
								});
							} else{
								window.parent.location.href = "../userinfo?id="+uid+"&consule=%25E5%2585%25B3%25E6%25B3%25A8%25E4%25B8%258E%25E7%25B2%2589%25E4%25B8%259D";
							}
						},
						error : function() {
							alert("数据获取失败");
						}
					});
				});

$("#follow")
		.click(
				function() {
					$.ajax({
						type : "get",
						url : "../verGoYuan",
						dataType : "json",
						data : {
						},
						async : true,
						success : function(data) {
							console.log(data);
							if(data == 1){
								jqalert({
									title: '提示弹窗',
									cont: '您未登录，无法前往园子'
								});
							} else{
								window.parent.location.href = "../userinfo?id="+uid+"&consule=%25E5%2585%25B3%25E6%25B3%25A8%25E4%25B8%258E%25E7%25B2%2589%25E4%25B8%259D";
							}
						},
						error : function() {
							alert("数据获取失败");
						}
					});
				});


$("#addFollow").click(function(){
	$addFollow(uid);
});

//加关注
var $addFollow = function(uid){
	$.ajax({
		type : "post",
		url : "../addFollow",
		dataType : "json",
		data : {
			uid : uid
		},
		async : true,
		success : function(data) {
			console.log(data);
			if(data == 1){
				jqalert({
					title: '提示弹窗',
					cont: '您未登录，无法关注'
				});
			}else if(data == 2){
				jqalert({
					title: '提示弹窗',
					cont: '您本人无法关注自己'
				});
			}else if(data == 3){
				jqalert({
					title: '提示弹窗',
					cont: '关注成功'
				});
				$getTheBlogger();
			}else{
				jqalert({
					title: '提示弹窗',
					cont: '关注失败'
				});
			}
		},
		error : function() {
			jqalert({
				title: '提示弹窗',
				cont: '请勿重复关注'
			});
		}
	});
}


//文章档案跳转（后移至公共js）
$("#articalFileNav").click(function(){
	window.parent.location.href = "../blogPortal?uid="+uid+"&category=1";
});
