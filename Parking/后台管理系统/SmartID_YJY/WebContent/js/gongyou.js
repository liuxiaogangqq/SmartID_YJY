var kaifadianhua = "张工，电话：18614005386"
var weihudianhua = "刘工，电话：15210941708"
var kaifayouxiang = "邮箱：1171298788@qq.com"
var weihuyouxiang = "邮箱：595464675@qq.com"

function goIndex() {
	top.location.href = "index.html";
}

// 弹出框以及系统信息框
function showMsg(title, msg, isAlert) {
	if (isAlert !== undefined && isAlert) {
		$.messager.alert(title, msg);
	} else {
		$.messager.show({
			title : title,
			msg : msg,
			showType : 'show'
		});
	}
}

// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18
Date.prototype.Format = function(fmt)   
{ // author: meizz
var o = {   
 "M+" : this.getMonth()+1,                 // 月份
 "d+" : this.getDate(),                    // 日
 "H+" : this.getHours(),                   // 小时
 "m+" : this.getMinutes(),                 // 分
 "s+" : this.getSeconds(),                 // 秒
 "q+" : Math.floor((this.getMonth()+3)/3), // 季度
 "S"  : this.getMilliseconds()             // 毫秒
};   
if(/(y+)/.test(fmt))   
 fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
for(var k in o)   
 if(new RegExp("("+ k +")").test(fmt))   
fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
return fmt;   
} 

// 时间修改方法 d：初始时间
// type：类型 date天（1-31） month月份（0-11）year 年 hours 小时 (0-23) minutes
// 分钟（0-59）seconds 秒（0-59）milliseconds毫秒（0-999）

function dateChange(d, type, num) {
	var d = new Date(d);
	if (type == 'date') {
		d.setDate(d.getDate() + num);
	}else if(type == 'month'){
		d.setMonth(d.getMonth() + num);
	}else if(type == 'year'){
		d.setFullYear(d.getFullYear() + num);
	}else if(type == 'hours'){
		d.setHours(d.getHours() + num);
	}else if(type == 'minutes'){
		d.setMinutes(d.getMinutes() + num);
	}else if(type == 'seconds'){
		d.setSeconds(d.getSeconds() + num);
	}else if(type == 'milliseconds'){
		d.setMilliseconds(d.getMilliseconds() + num);
	}
	return d.Format("yyyy-MM-dd HH:mm:ss");
}
