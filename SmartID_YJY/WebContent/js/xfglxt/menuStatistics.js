// 加载时载入表格
window.onload = function() {
	table();
};

$("#sTerminalTypeInnerId").combotree({
	url : "conTerminalMerchantTree/select.html",
	editable : false,
	multiple : true
});

$("#sStartTime").focus(function() {
	WdatePicker({
		dateFmt : 'yyyy-MM-dd',
		maxDate : "#F{$dp.$D('sEndTime')}"
	});
});

$("#sEndTime").focus(function() {
	WdatePicker({
		dateFmt : 'yyyy-MM-dd',
		minDate : "#F{$dp.$D('sStartTime')}"
	});
});
$("#sStartTime").val(new Date().Format("yyyy-MM-dd"));
$("#sEndTime").val(new Date().Format("yyyy-MM-dd"));

$(function() {
	document.onkeydown = function(e) {
		var ev = document.all ? window.event : e;
		if (ev.keyCode == 13) {
			table();
		}
	}
});

// 点击查询时更新表格
$("#select").click(function() {
	table();
});


// 表格
function table() {
	var ConTerminalInnerId = "";
	var arry = $("#sTerminalTypeInnerId").combotree("getValues");
	$.each(arry, function(i, k) {
		if (arry[i].indexOf("conTerminal") >= 0) {
			ConTerminalInnerId += arry[i].substring(11, arry[i].length) + ",";
		}
	});
	$("#select").attr("disabled", true);
	$("#table").datagrid({
		loadMsg : "正在加载数据，请稍等.....", // 在加载页面时显示数据
		url : "conMenu/select.html", // 发送的url地址
		queryParams :
		// 参数列表
		{
			ConTerminalInnerId : ConTerminalInnerId,
			StartTime : $('#sStartTime').val(),
			EndTime : $('#sEndTime').val(),
			TimeType : $('#sTimeType').val(),
		},
		method : 'post',
		idField : 'ConMenuInnerId',
		fitColumns : true,
		// 获取分页对象
		//pagination : true,
		rownumbers : true,
		singleSelect : true,
		//multiSort : true,// 多排序
		height : 593,
		/*pageSize : TableSize,// 10 325,15 450,20 575,25 700
		pageList : [ 10, 15, 20, 25, 50, 100, 200, 500 ],
		onSortColumn : alertColumn,*/
		columns : [ [  {
			field : 'MenuName',
			title : '菜品名称',
			sortable : true,
			width : 100
		}, {
			field : 'Number',
			title : '数量(份)',
			sortable : true,
			width : 100
		}] ],
		onLoadSuccess : function(data) {
			$("#select").removeAttr("disabled");
			if (data.rows.length == 0) {
				var body = $(this).data().datagrid.dc.body2;
				body.find('table tbody').append('<tr><td width="' + body.width() + '" style="height: 35px; text-align: center;"><h2>此条件下没有数据</h2></td></tr>');
			}
		}
	});
}


$("#export").click(function() {
	var ConTerminalInnerId = "";
	var arry = $("#sTerminalTypeInnerId").combotree("getValues");
	$.each(arry, function(i, k) {
		if (arry[i].indexOf("conTerminal") >= 0) {
			ConTerminalInnerId += arry[i].substring(11, arry[i].length) + ",";
		}
	});
	$("#export").attr("disabled", true);
	$.ajax({
		url : "conMenuExcel/select.html",
		type : "post",
		data : {
			ConTerminalInnerId : ConTerminalInnerId,
			StartTime : $('#sStartTime').val(),
			EndTime : $('#sEndTime').val(),
			TimeType : $('#sTimeType').val(),
		},
		dataType : "json",
		beforeSend : function() {
			var h = document.body.clientHeight;
			$("<div class=\"datagrid-mask\"></div>").css({
				display : "block",
				width : "100%",
				height : h
			}).appendTo("body");
			$("<div class=\"datagrid-mask-msg\"></div>").html("正在处理，请稍候。。。").appendTo("body").css({
				display : "block",
				left : ($(document.body).outerWidth(true) - 190) / 2,
				top : (h - 175) / 2,
				"font-size" : 12,
				"height" : "40px"
			});
		},
		success : function(data) {
			// 提交按钮设置为可用
			$("#export").removeAttr("disabled");
			$('.datagrid-mask-msg').remove();
			$('.datagrid-mask').remove();
			var html = "";
			if (data.code == 200) {
				// 成功
				showMsg("提示信息", data.msg);
				$("#xiazai").html("数据已导出：<a href='" + data.data + "'>下载</a>");
				$("#myXiazai").modal("show");
			} else {
				// 失败
				showMsg("提示信息", data.msg, true);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			// 提交按钮设置为可用
			$("#export").removeAttr("disabled");
			$('.datagrid-mask-msg').remove();
			$('.datagrid-mask').remove();
			// 出错
			if (XMLHttpRequest.readyState == 0) {
				// 常见错误，网络连不上或服务没有起来
				showMsg("提示信息", "找不到服务器，请检查服务是否正常", true);
			} else if (XMLHttpRequest.readyState == 4) {
				if (XMLHttpRequest.status == 404) {
					showMsg("提示信息", "找不到服务，请检查服务是否正常", true);
				} else {
					showMsg("提示信息", "程序出现异常", true);
				}
			} else {
				showMsg("提示信息", "程序出现异常", true);
			}
		}
	});
});
