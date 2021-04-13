var RemarkHide;
var RemarkName;
var RemarkLength;
var StandbyAHide;
var StandbyAName;
var StandbyALength;
var StandbyBHide;
var StandbyBName;
var StandbyBLength;
var StandbyCHide;
var StandbyCName;
var StandbyCLength;
var StandbyDHide;
var StandbyDName;
var StandbyDLength;
var sort;
var order;

// 加载时载入表格
window.onload = function() {
	$("#sStartTime").val(new Date().Format("yyyy-MM-dd"));
	$("#sEndTime").val(new Date().Format("yyyy-MM-dd"));
	table();
};


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
	//$("#table").datagrid({pageNumber:1});
	table();
});

// 表格
function table() {
	$("#table").datagrid({
		loadMsg : "正在加载数据，请稍等.....", // 在加载页面时显示数据
		url : "Settlement/select.html", // 发送的url地址
		queryParams :
		// 参数列表
		{
			StartTime : $('#sStartTime').val(),
			EndTime : $('#sEndTime').val(),
		},
		method : 'post',
		idField : 'FDateTime',
		fitColumns : true,
		// 获取分页对象
		rownumbers : true,
		singleSelect : true,
		height : TableSize * 35 + 68 ,
		columns : [ [ {
			field : 'CashMoney',
			title : '现金总额',
			sortable : true,
			width : 100
		}, {
			field : 'SubsidyMoney',
			title : '补贴总额',
			sortable : true,
			width : 100
		},/* {
			field : 'NumberMoney',
			title : '次总数',
			sortable : true,
			width : 100
		}, {
			field : 'CardMoney',
			title : '卡费总额',
			sortable : true,
			width : 100
		}, */{
			field : 'PeopleTotal',
			title : '总人数',
			sortable : true,
			width : 100
		}, {
			field : 'FDateTime',
			title : '时间',
			sortable : true,
			width : 100
		}] ],
		onLoadSuccess : function(data) {
			if (data.total == 0) {
				var body = $(this).data().datagrid.dc.body2;
				body.find('table tbody').append('<tr><td width="' + body.width() + '" style="height: 35px; text-align: center;"><h2>此条件下没有数据</h2></td></tr>');
			}
		}
	});
}


$("#export").click(function() {
	$("#export").attr("disabled", true);
	$.ajax({
		url : "accountLogExcel/select.html",
		type : "post",
		data : {
			StartTime : $('#sStartTime').val(),
			EndTime : $('#sEndTime').val(),
			sort : sort,
			order : order
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
