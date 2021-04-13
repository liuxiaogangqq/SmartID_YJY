var RemarkHide;
var RemarkName;
var RemarkLength;
var StandbyAHide;
var StandbyAName;
var StandbyALength;
var StandbyBHide;
var StandbyBName;
var StandbyBLength;

// 加载时载入表格
window.onload = function() {
	$("#sDepartmentInnerId").combotree({
		url : "companyDepartmentTree/select.html",
		editable : false,
		multiple : true
	});
	// 请求后台，获得配置参数
	$.ajax({
		url : "configure/select.html",
		type : "post",
		async : false,
		data : {
			Page : "mark"
		},
		dataType : "json",
		success : function(data) {
			var arry = data.rows;
			$.each(arry, function(i, k) {
				if (arry[i].Property == "Remark") {
					RemarkHide = arry[i].Enabled;
					RemarkName = arry[i].Name;
					RemarkLength = arry[i].Length;
				}
				if (arry[i].Property == "StandbyA") {
					StandbyAHide = arry[i].Enabled;
					StandbyAName = arry[i].Name;
					StandbyALength = arry[i].Length;
				}
				if (arry[i].Property == "StandbyB") {
					StandbyBHide = arry[i].Enabled;
					StandbyBName = arry[i].Name;
					StandbyBLength = arry[i].Length;
				}
			});
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			// 出错
			if (XMLHttpRequest.readyState == 0) {
				// 常见错误，网络连不上或服务没有起来
				showMsg("提示信息", "找不到服务器，请检查网络连接", true);
			} else if (XMLHttpRequest.readyState == 4) {
				if (XMLHttpRequest.status == 404) {
					showMsg("提示信息", "找不到服务", true);
				} else {
					showMsg("提示信息", "程序出现异常，请重试", true);
				}
			} else {
				showMsg("提示信息", "程序出现异常，请重试", true);
			}
		}
	});
	//
	if (RemarkHide) {
		$("#sRemarkDiv").hide();
		$("#iRemarkDiv").hide();
		$("#uRemarkDiv").hide();
	} else {
		$("#sRemarkLabel").text(RemarkName + "：");
		$("#iRemarkLabel").text(RemarkName + "：");
		$("#uRemarkLabel").text(RemarkName + "：");
	}
	;
	if (StandbyAHide) {
		$("#sStandbyADiv").hide();
		$("#iStandbyADiv").hide();
		$("#uStandbyADiv").hide();
	} else {
		$("#sStandbyALabel").text(StandbyAName + "：");
		$("#iStandbyALabel").text(StandbyAName + "：");
		$("#uStandbyALabel").text(StandbyAName + "：");
	}
	;
	if (StandbyBHide) {
		$("#sStandbyBDiv").hide();
		$("#iStandbyBDiv").hide();
		$("#uStandbyBDiv").hide();
	} else {
		$("#sStandbyBLabel").text(StandbyBName + "：");
		$("#iStandbyBLabel").text(StandbyBName + "：");
		$("#uStandbyBLabel").text(StandbyBName + "：");
	}
	;
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

$("#sLogType").combobox({
	data : [{"id":"0","text":"开户"},{"id":"3","text":"挂失"},{"id":"4","text":"解挂"},{"id":"5","text":"退卡"},{"id":"6","text":"补卡"}],
	valueField : "id",
	textField : "text",
	editable : false,
	multiple : true,
});

// 点击查询时更新表格
$("#select").click(function() {
	//$("#table").datagrid({pageNumber:1});
	table();
});


// 表格
function table() {
	var DepartmentInnerId = "";
	var arry = $("#sDepartmentInnerId").combotree("getValues");
	$.each(arry, function(i, k) {
		if (arry[i].indexOf("department") >= 0) {
			DepartmentInnerId += arry[i].substring(10, arry[i].length) + ",";
		}
	});
	$("#table").datagrid("clearSelections");
	$("#table").datagrid({
		loadMsg : "正在加载数据，请稍等.....", // 在加载页面时显示数据
		url : "markLog/select.html", // 发送的url地址
		queryParams :
		// 参数列表
		{
			DepartmentList : DepartmentInnerId,
			StartTime : $('#sStartTime').val(),
			EndTime : $('#sEndTime').val(),
			LogType : $("#sLogType").combobox("getValues").toString(),
			UserId : $('#sUserId').val(),
			UserName : $('#sUserName').val(),
			MarkId : $('#sMarkId').val(),
			MarkName : $('#sMarkName').val(),
			Remark : $('#sRemark').val(),
			StandbyA : $('#sStandbyA').val(),
			StandbyB : $('#sStandbyB').val()
		},
		method : 'post',
		idField : 'MarkInnerId',
		fitColumns : true,
		// 获取分页对象
		pagination : true,
		rownumbers : true,
		singleSelect : true,
		height : TableSize * 35 + 68,
		pageSize : TableSize,// 10 325,15 450,20 575,25 700
		pageList : [ 10, 15, 20, 25, 50, 100, 200, 500 ],
		columns : [ [ {
			field : 'MarkInnerId',
			hidden : true
		}, {
			field : 'DepartmentInnerId',
			hidden : true
		}, {
			field : 'CompanyInnerId',
			hidden : true
		}, {
			field : 'MarkTypeInnerId',
			hidden : true
		}, {
			field : 'UserInnerId',
			hidden : true
		}, {
			field : 'UserName',
			title : '姓名',
			sortable : true,
			width : 100
		}, {
			field : 'UserId',
			title : '编号',
			sortable : true,
			width : 100
		}, {
			field : 'DepartmentName',
			title : '公司',
			sortable : true,
			width : 150
		}, {
			field : 'MarkId',
			title : '标识号',
			sortable : true,
			width : 100
		}, {
			field : 'MarkName',
			title : '标识名称',
			sortable : true,
			width : 100
		}, {
			field : 'MarkTypeName',
			title : '卡类型',
			sortable : true,
			width : 100
		},{
			field : 'MarkLogType',
			title : '操作',
			sortable : true,
			width : 100
		}, {
			field : 'OperatorName',
			title : '操作人',
			sortable : true,
			width : 100
		}, {
			field : 'OperatorDate',
			title : '操作时间',
			sortable : true,
			width : 150
		}, {
			field : 'Remark',
			title : RemarkName,
			sortable : true,
			hidden : RemarkHide,
			width : RemarkLength
		}, {
			field : 'StandbyA',
			title : StandbyAName,
			sortable : true,
			hidden : StandbyAHide,
			width : StandbyALength
		}, {
			field : 'StandbyB',
			title : StandbyBName,
			sortable : true,
			hidden : StandbyBHide,
			width : StandbyBLength
		} ] ],
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

