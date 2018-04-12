/*博客板块左侧菜单栏逻辑*/
$("#goBlogWrite").click(function() {
	window.location.href = "blogHandle";
});

$("#goDraft").click(function() {
	window.location.href = "blogDraft";
});


/*分类逻辑*/
$(function(){
	$getCategorys();
});

//获取分类
var $getCategorys = function() {
	$.ajax({
		type : "get",
		url : "getCategorys",
		async : true,
		success : function(data) {
			console.log(data);
			$("#t2").tmpl(data).appendTo("#post_categories");
		},
		error : function() {
			alert("数据获取失败");
		}
	});
}
