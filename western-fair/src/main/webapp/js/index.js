/*$(function(){
	$(".table").mouseover(function(){
		var child = $(this).children("tr");

		console.log(child);
		var ss = $(this).find('span');
		ss.toggleClass("litte_active_circle");
	});
	
})*/
function setTab(x){
	for(var i = 1;i<=4;i++){
		// $("#tag_"+i).slideToggle(1000);
		if(!$("#tag_"+i).is(":animated")){
			$("#tag_"+i).slideToggle();
		}
	}
}
function isBrowser() {
    var userAgent = navigator.userAgent;
    //微信内置浏览器
    if(userAgent.match(/MicroMessenger/i) == 'MicroMessenger') {
        return "MicroMessenger";
    }
    //QQ内置浏览器
    else if(userAgent.match(/QQ/i) == 'QQ') {
        return "QQ";
    }
    //Chrome
    else if(userAgent.match(/Chrome/i) == 'Chrome') {
        return "Chrome";
    }
    //Opera
    else if(userAgent.match(/Opera/i) == 'Opera') {
        return "Opera";
    }
    //Firefox
    else if(userAgent.match(/Firefox/i) == 'Firefox') {
        return "Firefox";
    }
    //Safari
    else if(userAgent.match(/Safari/i) == 'Safari') {
        return "Safari";
    }
    //IE
    else if(!!window.ActiveXObject || "ActiveXObject" in window) {
        return "IE";
    }
    else {
        return "未定义:"+userAgent;
    }
}
