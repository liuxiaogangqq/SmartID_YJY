// 加载时载入表格
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
$("#uInTime").focus(function() {
	WdatePicker({
		dateFmt : 'yyyy-MM-dd HH:mm:ss',
		maxDate : "#F{$dp.$D('uOutTime')}"
	});
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

$("#uOutTime").focus(function() {
	WdatePicker({
		dateFmt : 'yyyy-MM-dd HH:mm:ss',
		minDate : "#F{$dp.$D('uInTime')}"
	});
});
// 点击查询时更新表格
$("#select").click(function() {
	table();
});

// 点击添加时弹出框
$("#insert").click(function() {
	$("#insertDiv").modal("show");
});

// 点击修改时操作
$("#update").click(function() {
	var Table = $("#table").datagrid("getSelected");
	if (Table) {
		$("#uChargeRecordInnerId").val(Table.ChargeRecordInnerId);
		$("#uVisitorCompany").val(Table.VisitorCompany);
		$("#uVisitorName").val(Table.VisitorName);
		$("#uCarNumber").val(Table.CarNumber);
		$("#uInTime").val(Table.InTime);
		$("#uOutTime").val(Table.OutTime);
		$("#updateDiv").modal("show");
	} else {
		showMsg("提示信息", "请选择一条数据进行操作");
	}
});


// 表格
function table() {
	$("#table").datagrid("clearSelections");
	$("#table").datagrid({
		loadMsg : "正在加载数据，请稍等.....", // 在加载页面时显示数据
		url : "YCCarRecord/select.html", // 发送的url地址
		queryParams :
		// 参数列表
		{
			CarNumber : $('#sCarNumber').val(),
			StartTime : $('#sStartTime').val()+" 00:00:00",
			EndTime : $('#sEndTime').val()+" 23:59:00",
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
		columns : [ [ {
			field : 'ChargeRecordInnerId',
			hidden : true
		}, {
			field : 'VisitorName',
			title : '访客姓名',
			sortable : true,
			width : 150
		}, {
			field : 'VisitorCompany',
			title : '访客公司',
			sortable : true,
			width : 150
		}, {
			field : 'CarNumber',
			title : '访客车牌号',
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
			field : 'ChargeStateName',
			title : '状态',
			sortable : true,
			width : 150
		},{
			field : 'Money',
			title : '收费金额',
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

// 修改提交
$("#updateSubmit").click(function() {
	if($("#uInTime").val() == ""){
		showMsg("提示信息", "进入时间不能为空", true);
		return false;
	}
	if($("#uOutTime").val() == ""){
		showMsg("提示信息", "离开时间不能为空", true);
		return false;
	}
			$("#updateSubmit").attr("disabled", true);
			$.ajax({
				url : "YCchargeRecordInfo/update.html",
				type : "post",
				data : {
					ChargeRecordInnerId : $("#uChargeRecordInnerId").val(),
					InTime : $("#uInTime").val(),
					OutTime : $("#uOutTime").val()
				},
				dataType : "json",
				success : function(data) {
					// 提交按钮设置为可用
					$("#updateSubmit").removeAttr("disabled");
					if (data.code == 200) {
						// 成功
						showMsg("提示信息", data.msg);
						// 隐藏下拉框
						$("#updateDiv").modal("hide");
						// 重载表格数据
						table();
						// 清空form表单
						$(':input', '#updateForm').not(':button, :submit, :reset, :hidden').val('').removeAttr('checked').removeAttr('selected');
					} else {
						// 失败
						showMsg("提示信息", data.msg, true);
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					// 提交按钮设置为可用
					$("#updateSubmit").removeAttr("disabled");
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
});
