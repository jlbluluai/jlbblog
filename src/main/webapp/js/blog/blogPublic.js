/*头部菜单栏逻辑*/
$("#goPortal").click(function() {
	window.location.href = "main";
});

$("#goUserInfo").click(function() {
	window.location.href = "userinfo?id="+uid;
});

$("#goLooking").click(function() {
	window.location.href = "looking";
})

/*logo及昵称标签逻辑*/
$("#headLogo").click(function() {
	window.location.href = "main";
});

$("#headNick").click(function() {
	window.location.href = "blogPortal?uid="+uid;
});

/*主体上部菜单栏逻辑*/
$("#blogManage").click(function() {
	window.location.href = "blogManage";
});

$("#blogPhoto").click(function() {
	window.location.href = "blogPhotos";
});

$("#blogFile").click(function() {
	window.location.href = "blogFiles";
});

$("#blogSetting").click(function() {
	alert("该板块尚未开放")
	//window.location.href = "blogSettings";
});

$(function(){
	$getTheBlogger();
});

var uid;
var nickname;

var $getTheBlogger = function(){
	$.ajax({
		type : "get",
		url : "getBehindBlogger",
		dataType : "json",
		data : {
		},
		async : true,
		success : function(data) {
			console.log(data);
			$("#headNick").text(data.nickname);
			nickname = data.nickname;
			uid = data.uid;
		},
		error : function() {
			alert("数据获取失败");
		}
	});
}