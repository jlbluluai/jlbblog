/*头部菜单栏逻辑*/
$("#mainhome").click(function() {
	window.parent.location.href = "../main";
});

$("#myhome").click(function() {
	window.parent.location.href = "../blogPortal";
});

$("#newartical").click(function() {
	window.parent.location.href = "../blogHandle";
});

$("#contact").click(function() {
	alert("功能暂未实现");
});

$("#manage").click(function() {
	window.parent.location.href = "../blogManage";
});

$("#draft").click(function() {
	window.parent.location.href = "../blogDraft";
})

/*左侧公告逻辑*/
$("#nick").click(function(){
	window.parent.location.href = "../userinfo";
});

$("#garAge").click(function(){
	window.parent.location.href = "../userinfo";
});

$("#fan").click(function(){
	window.parent.location.href = "../userinfo?consule=%25E5%2585%25B3%25E6%25B3%25A8%25E4%25B8%258E%25E7%25B2%2589%25E4%25B8%259D";
});

$("#follow").click(function(){
	window.parent.location.href = "../userinfo?consule=%25E5%2585%25B3%25E6%25B3%25A8%25E4%25B8%258E%25E7%25B2%2589%25E4%25B8%259D";
});
