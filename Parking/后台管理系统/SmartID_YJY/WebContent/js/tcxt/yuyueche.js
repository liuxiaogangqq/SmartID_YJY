// 加载时载入表格
window.onload = function() {	
	table();
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
};
$("#iStartTime").focus(function() {
	WdatePicker({
		dateFmt : 'yyyy-MM-dd HH:mm:ss',
		maxDate : "#F{$dp.$D('iEndTime')}"
	});
});

$("#iEndTime").focus(function() {
	WdatePicker({
		dateFmt : 'yyyy-MM-dd HH:mm:ss',
		minDate : "#F{$dp.$D('iStartTime')}"
	});
});
$("#iStartTime").val(new Date().Format("yyyy-MM-dd HH:mm:ss"));

$(function() {
	document.onkeydown = function(e) {
		var ev = document.all ? window.event : e;
		if (ev.keyCode == 13) {
			table();
		}
	}
});

$("#sDepartmentInnerId").combotree({
	url : "companyDepartmentTree/select.html",
	editable : false,
	multiple : true
});
$("#iCarTypeInnerId").combobox({
	url : "carTypeBox/select.html",
	valueField : "id",
	textField : "text",
	editable : false,
	multiple : false,
});
$("#uVisState").combobox({
	data : [{"id":"0","text":"申请中"},{"id":"1","text":"待入场"},{"id":"2","text":"拒绝"},{"id":"3","text":"已取消"},{"id":"4","text":"已进场"},{"id":"5","text":"已支付"},{"id":"6","text":"已完成"},{"id":"7","text":"已出场"},{"id":"8","text":"已逾期"},{"id":"9","text":"待支付"}],
	valueField : "id",
	textField : "text",
	editable : false,
	multiple : false,
});
$("#uCarTypeInnerId").combotree({
	url : "carTypeBox/select.html",
	valueField : "id",
	textField : "text",
	editable : false,
	multiple : false,
});
$("#iGetMoney").combobox({
	data : [{"id":"1","text":"是"},{"id":"2","text":"否"}],
	valueField : "id",
	textField : "text",
	editable : false,
	multiple : false,
	onChange : function(){
		if($("#iGetMoney").combobox("getValue") == "1"){
			$("#outTime").css("display","none")
			$("#shoufei").css("display","")
			$("#bushou").css("display","none")
		}else{
			$("#outTime").css("display","")
			$("#shoufei").css("display","none")
			$("#bushou").css("display","")
		}
	}
});

function change(){
	
}

// 点击查询时更新表格
$("#select").click(function() {
	table();
});

$("#insert").click(function() { 
	$("#iCarTypeInnerId").combobox("setValue",2);
	$("#iGetMoney").combobox("setValue",1);
	$("#insertDiv").modal("show");
});
$("#changeState").click(function() { 
	var Table = $("#table").datagrid("getSelected");
	if (Table) {
		
		$("#uRecordInnerId").val(Table.RecordInnerId);
		$("#uUserName").val(Table.VisitorName);
		$("#uCompanyName").val(Table.VisitorCompany);
		$("#uCarNumber").val(Table.VisitorCarCode);
		$("#uCarType").val(Table.OrderTypeName);
		$("#uName").val(Table.UserName);
		$("#uDepName").val(Table.DepartMentName);
		$("#uVisDate").val(Table.RecordTime);
		$("#uVisState").combobox("setValue",Table.VisitorStateInnerId);
		$("#updateStateDiv").modal("show");
	}else{
		showMsg("提示信息", "请选择一条数据进行操作");
	}
});

// 点击查询时更新表格
$("#StopCharging").click(function() {
	var Table = $("#table").datagrid("getSelected");
	if (Table) {
		if(confirm("確定提前结束预约？")){
			$.ajax({
				url : "StopCharging/update.html",
				type : "post",
				data : {
					CarNumber : Table.VisitorCarCode,
				},
				dataType : "json",
				success : function(data) {
					// 提交按钮设置为可用
					$("#delete").removeAttr("disabled");
					if (data.code == 200) {
						// 成功
						showMsg("提示信息", data.msg);
						// 取消表格选中
						$('#table').datagrid('clearSelections');
						// 重载表格数据
						table();
					} else {
						// 失败
						showMsg("提示信息", data.msg, true);
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					// 提交按钮设置为可用
					$("#delete").removeAttr("disabled");
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
		}
	}else{
		
	}
});

// 表格
function table() {
	$("#table").datagrid("clearSelections");
	$("#table").datagrid({
		loadMsg : "正在加载数据，请稍等.....", // 在加载页面时显示数据
		url : "ReservationCar/select.html", // 发送的url地址
		queryParams :
		{
			StartTime : $('#sStartTime').val()+" 00:00:00",
			EndTime : $('#sEndTime').val()+" 23:59:00",
			CarNumber : $('#sCarNumber').val(),
		},
		method : 'post',
		idField : 'ReservationCarInnerId',
		fitColumns : true,
		// 获取分页对象
		pagination : true,
		rownumbers : true,
		singleSelect : true,
		height : TableSize * 35 + 68,
		pageSize : TableSize,// 10 325,15 450,20 575,25 700
		pageList : [ 10, 15, 20, 25, 50, 100, 200, 500 ],
		columns : [ [ {
			field : 'RecordInnerId',
			hidden : true
		}, {
			field : 'VisitorStateInnerId',
			hidden : true
		}, {
			field : 'VisitorName',
			title : '预约人姓名',
			sortable : true,
			width : 150
		}, {
			field : 'VisitorCompany',
			title : '预约人公司',
			sortable : true,
			width : 150
		},{
			field : 'VisitorCarCode',
			title : '预约人车牌号',
			sortable : true,
			width : 150
		},{
			field : 'VisitorBeginTime',
			title : '开始时间',
			sortable : true,
			width : 150
		},{
			field : 'VisitorEndTime',
			title : '结束时间',
			sortable : true,
			width : 150
		},{
			field : 'OrderTypeName',
			title : '预约类型',
			sortable : true,
			width : 150
		},{
			field : 'UserName',
			title : '协助人',
			sortable : true,
			width : 150
		},{
			field : 'DepartMentName',
			title : '协助人部门',
			sortable : true,
			width : 150
		},{
			field : 'RecordTime',
			title : '预约时间',
			sortable : true,
			width : 150
		},{
			field : 'VisitorState',
			title : '预约状态',
			sortable : true,
			width : 150
		}]],
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

$("#insertSubmit").click(function() {
	var regex = /^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$/; 
	var regex6 = /^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{5}[A-Z0-9挂学警港澳]{1}$/; 
	var carnumber = $("#iCarNumber").val();
	if($("#iUserName").val() ==""){
		showMsg("提示信息", "预约人姓名不能为空", true);
		return false;
	}
	if($("#iCompanyName").val() ==""){
		showMsg("提示信息", "预约人公司不能为空", true);
		return false;
	}
	if(carnumber != ""){
		if(!((carnumber.length == 7 && regex.test(carnumber)) || (carnumber.length == 8 && regex6.test(carnumber)))){
			showMsg("提示信息", "请检查车牌号是否正确", true);
			return false;
		}
	}else{
		showMsg("提示信息", "车牌号不能为空", true);
		return false;
	}
	if($("#iStartTime").val() ==""){
		showMsg("进入时间不能为空", true);
		return false;
	}
	$.ajax({
		url : "Visitor_OrderRecord/insert.html",
		type : "post",
		dataType : "json",
		data : {
			CarTypeInnerId : $("#iCarTypeInnerId").combobox("getValue").toString(),
			CarNumber : $("#iCarNumber").val().replace(/\s+/g,""),
			StartTime : $("#iStartTime").val(),
			EndTime : $("#iEndTime").val(),
			CompanyName : $("#iCompanyName").val(),
			UserName : $("#iUserName").val(),
			VisThing : $("#iVisThing").val(),
			GetMoney : $("#iGetMoney").combobox("getValue").toString(),
		},
		success : function(data) {
			// 提交按钮设置为可用
			$("#insertSubmit").removeAttr("disabled");
			if (data.code == 200) {
				// 成功
				showMsg("提示信息", data.msg);
				// 隐藏下拉框
				$("#insertDiv").modal("hide");
				// 重载表格数据
				table();
				// 清空form表单
				$(':input', '#insertForm').not(':button, :submit, :reset, :hidden').val('').removeAttr('checked').removeAttr('selected');
			} else {
				// 失败
				showMsg("提示信息", data.msg, true);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			// 提交按钮设置为可用
			$("#insertSubmit").removeAttr("disabled");
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
})


$("#updateStateSubmit").click(function() {
	$("#updateStateSubmit").attr("disabled", true);
	$.ajax({
		url : "VisitorState/update.html",
		type : "post",
		dataType : "json",
		data : {
			VisState : $("#uVisState").combobox("getValue").toString(),
			RecordInnerId : $("#uRecordInnerId").val()
		},
		success : function(data) {
			// 提交按钮设置为可用
			$("#updateStateSubmit").removeAttr("disabled");
			if (data.code == 200) {
				// 成功
				showMsg("提示信息", data.msg);
				// 隐藏下拉框
				$("#updateStateDiv").modal("hide");
				// 重载表格数据
				table();
				// 清空form表单
				$(':input', '#VisStateForm').not(':button, :submit, :reset, :hidden').val('').removeAttr('checked').removeAttr('selected');
			} else {
				// 失败
				showMsg("提示信息", data.msg, true);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			// 提交按钮设置为可用
			$("#updateStateSubmit").removeAttr("disabled");
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
})


$("#export").click(function() {
	$("#export").attr("disabled", true);
	$.ajax({
		url : "YuyuecheExcel/select.html",
		type : "post",
		data : {
			StartTime : $('#sStartTime').val(),
			EndTime : $('#sEndTime').val(),
			CarNumber : $('#sCarNumber').val(),
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
