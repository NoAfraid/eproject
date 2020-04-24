var baseURL = "/ycmh_api";
var client = "web";
var version = "1.0";
var timeOut = 60;

/**************************************时间格式化处理************************************/
function dateFtt(fmt, date) {
	var o = {
		"M+": date.getMonth() + 1, //月份
		"d+": date.getDate(), //日
		"H+": date.getHours(), //小时
		"m+": date.getMinutes(), //分
		"s+": date.getSeconds(), //秒
		"q+": Math.floor((date.getMonth() + 3) / 3), //季度
		"S": date.getMilliseconds() //毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
	for (var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}
/**
 * 当前时间
 * @param fmt 格式化,yyyy年MM月d日H时m分s秒M毫秒
 * @returns
 */
function getNow(fmt) {
	var crtTime = new Date();
	return top.dateFtt(fmt, crtTime);
}
/**
 * 判断字符是否为空的方法
 * @param obj
 * @returns
 */
function isEmpty(obj) {
	if (typeof obj == "undefined" || obj == null || obj == "") {
		return true;
	} else {
		return false;
	}
}
/**
 * 手机号验证
 * @param str
 * @returns {Boolean}
 */
function isPoneAvailable(str) {
	var myreg = /^[1][3,4,5,7,8][0-9]{9}$/;
	if (!myreg.test(str)) {
		return false;
	} else {
		return true;
	}
}

function passwordTest(password) {
	if (/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$/.test(password)) {
		return true;
	} else {
		return false;
	}
}
/**
 * 设置cookie
 * @param name
 * @param value
 */
function setCookie(name, value) {
	var Days = 1;
	var exp = new Date();
	exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
	document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString() + ";path=/";
}
/**
 * 获取cookie的值
 * @param name
 * @returns
 */
function getCookie(name) {
	var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
	if (arr = document.cookie.match(reg))
		return unescape(arr[2]);
	else
		return null;
}

//function setCookie(name,value,time){
//	var strsec = getsec(time);
//	var exp = new Date();
//	exp.setTime(exp.getTime() + strsec*1);
//	document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
//}

function delCookie(name) {
	/*var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval=getCookie(name);
    if(cval!=null)
        document.cookie= name + "="+cval+";expires="+exp.toGMTString(); */
	var d = new Date();
	d.setTime(d.getTime() + (-1 * 24 * 60 * 60 * 1000));
	var expires = "expires=" + d.toGMTString();
	var cvalue = null;
	document.cookie = name + "=" + cvalue + "; " + expires + ";path=/";

}

/**
 * 这是有设定过期时间的使用示例：
 * s20是代表20秒
 * h是指小时，如12小时则是：h12
 * d是天数，30天则：d30
 * @param str
 * @returns {Number}
 */
function getsec(str) {
	alert(str);
	var str1 = str.substring(1, str.length);
	var str2 = str.substring(0, 1);
	if (str2 == "s") {
		return str1 * 1000;
	} else if (str2 == "h") {
		return str1 * 60 * 60 * 1000;
	} else if (str2 == "d") {
		return str1 * 24 * 60 * 60 * 1000;
	}
}

function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if (r != null) return unescape(r[2]);
	return null;
}

function domainName(url) {
	var sign = "://";
	var pos = url.indexOf(sign);
	//如果以协议名开头,如：http://github.com/
	if (pos >= 0) {
		pos += sign.length;
		//截取协议名以后的部分,github.com/
		url = url.slice(pos);
	}
	//以小数点作分割
	var array = url.split(".");
	//如果是以3W开头，返回第二部分
	//如：www.github.com
	if (array[0] === "www") {
		return array[1];
	}
	//如果不是以3W开头，则返回第一部分
	//如：github.com/
	return array[0];
}

function loginout() {
	var result = confirm("确认退出？");
	if (!result) return;
	var accessToken = getCookie("accessToken");
	var vip = {
		accessToken: accessToken
	};
	var formData = JSON.stringify(vip);
	var now = getNow("yyyyMMddHHmmss");
	var sign = signString(formData, now);
	$.ajax({
		headers: {
			requestTime: now,
			sign: sign,
			accessToken: accessToken
		},
		type: "post",
		url: "http://localhost:8080/user/logout",
		contentType: "application/json;charset=utf-8",
		dataType: "json",
		data: formData,
		success: function(result) {
			delCookie("loginUser");
			delCookie("accessToken");
			//刷新当前页
			//window.location.reload();
			window.location.href = "index.html";
		}
	});
}

function signString(formData, now) {
	return hex_md5(encodeURIComponent(formData + 'dkh@34bsd^7..Wasaekudjksjk34534' + now));
}

function login() {
	window.location.href = '/login.html';
}

//jquery全局配置
var LOOKTG_KG = false;
$.ajaxSetup({
	dataType: "json",
	cache: false,
	headers: {
		"accessToken": getCookie("accessToken")
	},
	complete: function(xhr) {
		//token过期，则跳转到登录页面
		var response = JSON.parse(xhr.responseText);
		if (response.code == 4001 || response.code == 4002) {
			var result = confirm('您还没有登录,请先登录');  
			if(result){
				LOOKTG_KG = false;
				GetHtml = null;
				login();
			}else{
				if(LOOKTG_KG){
					window.history.go(-1);
				}
				GetHtml = null;
				LOOKTG_KG = false;
			}
			
		}
	}
});

//判断有无登录,登录跳转
function GetPDlogin(HtmlGet) {
	var now = getNow("yyyyMMddHHmmss");
	var t = {

	};
	var formData = JSON.stringify(t);
	var sign = signString(formData, now);
	$.ajax({
		headers: {
			client: client,
			version: version,
			requestTime: now,
			sign: sign,
		},
		data: formData,
		type: "post",
		url: baseURL + "/blog/PDlogin",
		contentType: "application/json;charset=utf-8",
		dataType: "json",
		success: function(result) {
			if (result.code == 0)
				window.location.href = HtmlGet;
		},
		error: function() {
			alert("失败");
		}
	});

}