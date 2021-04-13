// 加载时载入表格
var sort;
var order;
window.onload = function() {	
	table();
};

$(function() {
	document.onkeydown = function(e) {
		var ev = document.all ? window.event : e;
		if (ev.keyCode == 13) {
			table();
		}
	}
});
$("#sStartTime").focus(function() {
	WdatePicker({
		dateFmt : 'yyyy-MM-dd HH:mm:ss',
		maxDate : "#F{$dp.$D('sEndTime')}"
	});
});

$("#sEndTime").focus(function() {
	WdatePicker({
		dateFmt : 'yyyy-MM-dd HH:mm:ss',
		minDate : "#F{$dp.$D('sStartTime')}"
	});
});

$("#sFlag").combobox({
	data : [{'id':'0','text':'固定车'},{'id':'1','text':'预约车'},{'id':'2','text':'特殊车辆'}],
	valueField : "id",
	textField : "text",
	editable : false,
	multiple : true,
});
$("#sChargeType").combobox({
	data : [{'id':'1','text':'人工收费'},{'id':'2','text':'自助缴费'},{'id':'3','text':'自动扣除'}],
	valueField : "id",
	textField : "text",
	editable : false,
	multiple : true,
});
// 点击查询时更新表格
$("#select").click(function() {
	table();
});


// 表格
function table() {
	$("#table").datagrid("clearSelections");
	$("#table").datagrid({
		loadMsg : "正在加载数据，请稍等.....", // 在加载页面时显示数据
		url : "chargeRecordInfo/select.html", // 发送的url地址
		queryParams :
		// 参数列表
		{
			UserName : $('#sUserName').val(),
			DepName : $('#sDepName').val(),
			StartTime : $('#sStartTime').val(),
			EndTime : $('#sEndTime').val(),
			CarNumber : $('#sCarNumber').val(),
			CarType : $('#sFlag').combobox("getValues").toString(),
			ChargeType : $('#sChargeType').combobox("getValues").toString()
		},
		method : 'post',
		idField : 'ParkInfoInnerId',
		fitColumns : true,
		// 获取分页对象
		pagination : true,
		rownumbers : true,
		singleSelect : true,
		height : TableSize * 35 + 68,
		pageSize : TableSize,// 10 325,15 450,20 575,25 700
		pageList : [ 10, 15, 20, 25, 50, 100, 200, 500 ],
		onSortColumn : alertColumn,
		columns : [ [ {
			field : 'ChargeRecordInnerId',
			hidden : true
		}, {
			field : 'Name',
			title : '姓名',
			sortable : true,
			width : 150
		}, {
			field : 'DepName',
			title : '部门',
			sortable : true,
			width : 150
		}, {
			field : 'CarNumber',
			title : '车牌号',
			sortable : true,
			width : 150
		}, {
			field : 'InTime',
			title : '进入时间',
			sortable : true,
			width : 150
		},{
			field : 'OutTime',
			title : '离开时间',
			sortable : true,
			width : 150
		},{
			field : 'Money',
			title : '收费金额',
			sortable : true,
			width : 150
		},{
			field : 'CarType',
			title : '车辆类型',
			sortable : true,
			width : 150
		},{
			field : 'ChargeType',
			title : '收费方式',
			sortable : true,
			width : 150
		},{
			field : 'ChargeTime',
			title : '收费时间',
			sortable : true,
			width : 150
		}] ],
		onLoadError : function() {
			showMsg("提示信息", "服务发生异常，请检查服务", true);
		},
		onLoadSuccess : function(data) {
			if (data.rows.length == 0) {
				var body = $(this).data().datagrid.dc.body2;
				body.find('table tbody').append('<tr><td width="' + body.width() + '" style="height: 35px; text-align: center;"><h2>此条件下没有数据</h2></td></tr>');
			}
		}
	});
}

function alertColumn(sort1, order1) {
	sort = sort1;
	order = order1;
}

$("#export").click(function() {
	/*var DepartmentInnerId = "";
	var arry = $("#sDepartmentInnerId").combotree("getValues");
	$.each(arry, function(i, k) {
		if (arry[i].indexOf("department") >= 0) {
			DepartmentInnerId += arry[i].substring(10, arry[i].length) + ",";
		}
	});*/
	$("#export").attr("disabled", true);
	$.ajax({
		url : "chargeRecordInfoExcel/select.html",
		type : "post",
		data : {
			StartTime : $('#sStartTime').val(),
			UserName : $('#sUserName').val(),
			DepName : $('#sDepName').val(),
			EndTime : $('#sEndTime').val(),
			CarNumber : $('#sCarNumber').val(),
			CarType : $('#sFlag').combobox("getValues").toString(),
			ChargeType : $('#sChargeType').combobox("getValues").toString(),
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
