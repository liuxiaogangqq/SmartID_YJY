// 加载时载入表格
window.onload = function() {	
	table();
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
	$("#uStartTime").focus(function() {
		WdatePicker({
			dateFmt : 'yyyy-MM-dd HH:mm:ss',
			maxDate : "#F{$dp.$D('uEndTime')}"
		});
	});
	
	$("#uEndTime").focus(function() {
		WdatePicker({
			dateFmt : 'yyyy-MM-dd HH:mm:ss',
			minDate : "#F{$dp.$D('uStartTime')}"
		});
	});
	$("#tStartTime").focus(function() {
		WdatePicker({
			dateFmt : 'yyyy-MM-dd HH:mm:ss',
			maxDate : "#F{$dp.$D('tEndTime')}"
		});
	});
	
	$("#tEndTime").focus(function() {
		WdatePicker({
			dateFmt : 'yyyy-MM-dd HH:mm:ss',
			minDate : "#F{$dp.$D('tStartTime')}"
		});
	});
};

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
$("#sCarType").combobox({
	url : "carTypeBox/select.html",
	valueField : "id",
	textField : "text",
	editable : false,
	multiple : true,
});
$("#uCarTypeInnerId").combobox({
	url : "carTypeBox/select.html",
	valueField : "id",
	textField : "text",
	editable : false,
	multiple : false,
});

$("#iPark").combobox({
	data : [{"id":"1001","text":"地面长期车"},{"id":"1007","text":"B1地库长期车"},{"id":"1008","text":"E2地库长期车"}],
	valueField : "id",
	textField : "text",
	editable : false,
	multiple : false,
});
$("#uPark").combobox({
	data : [{"id":"1001","text":"地面长期车"},{"id":"1007","text":"B1地库长期车"},{"id":"1008","text":"E2地库长期车"}],
	valueField : "id",
	textField : "text",
	editable : false,
	multiple : false,
});


// 点击查询时更新表格
$("#select").click(function() {
	table();
});

// 点击添加时弹出框
$("#insert").click(function() {
	var Table = $("#table").datagrid("getSelected");
	if (Table) {
		if(Table.CarNumber == null){
			$("#iUserId").val(Table.UserId);
			$("#iUserName").val(Table.UserName);
			$("#iUserInnerId").val(Table.UserInnerId);
			$("#iDepartmentName").val(Table.DepartmentName);
			$("#iCarTypeInnerId").combobox("setValues","4");
			$("#insertDiv").modal("show");
		}else{
			showMsg("提示信息", "此人已存在车辆信息，请勿重复添加",true);
		}
	}else {
		showMsg("提示信息", "请选择一条数据进行操作");
	}
});

// 点击修改时操作
$("#update").click(function() {
	var Table = $("#table").datagrid("getSelected");
	if (Table) {
		$("#uUserId").val(Table.UserId);
		$("#uCarInfoInnerId").val(Table.CarInfoInnerId);
		$("#uUserName").val(Table.UserName);
		$("#uUserInnerId").val(Table.UserInnerId);
		$("#uDepartmentName").val(Table.DepartmentName);
		$("#uCarTypeInnerId").combobox('setValue',Table.CarTypeInnerId);
		$("#uCarNumber").val(Table.CarNumber);
		$("#uCarNumber1").val(Table.CarNumber1);
		$("#uCarNumber2").val(Table.CarNumber2);
		$("#uCarNumber3").val(Table.CarNumber3);
		$("#uCarNumber4").val(Table.CarNumber4);
		$("#uStartTime").val(Table.StartTime);
		$("#uEndTime").val(Table.EndTime);
		$("#uParkingNum").val(Table.ParkingNum);
		$("#uPark").combobox('setValue',Table.ParkInfo);
		$("#updateDiv").modal("show");
	} else {
		showMsg("提示信息", "请选择一条数据进行操作");
	}
});


$("#updateTime").click(function() {
	var Table = $("#table").datagrid("getChecked");
	var conLogList = [];
	var num = 0;
	$.each(Table, function(index, item) {
		if (item.CarInfoInnerId == null) {
			num++;
		} else {
			conLogList.push(item.CarInfoInnerId);
		}
	});
	if (num > 0) {
		showMsg("提示信息", "请勿选择无车牌的车辆信息", true);
		return false;
	} else {
		if (conLogList.length > 0) {
			$("#tCarInfoList").val(conLogList);
			$("#updateTimeDiv").modal("show");
		} else {
			showMsg("提示信息", "请选择要操作的信息", true);
		}
	}

});


// 表格
function table() {
	$("#table").datagrid("clearSelections");
	var DepartmentInnerId = "";
	var arry = $("#sDepartmentInnerId").combotree("getValues");
	$.each(arry, function(i, k) {
		if (arry[i].indexOf("department") >= 0) {
			DepartmentInnerId += arry[i].substring(10, arry[i].length) + ",";
		}
	});
	$("#table").datagrid({
		loadMsg : "正在加载数据，请稍等.....", // 在加载页面时显示数据
		url : "userCarInfo/select.html", // 发送的url地址
		queryParams :
		// 参数列表
		{
			UserId : $('#sUserId').val(),
			UserName : $('#sUserName').val(),
			CarNumber : $('#sCarNumber').val(),
			DepartmentInnerId : DepartmentInnerId,
			CarType : $("#sCarType").combobox('getValues').toString()
		},
		method : 'post',
		idField : 'CarInfoInnerId',
		fitColumns : true,
		// 获取分页对象
		pagination : true,
		rownumbers : true,
		singleSelect : false,
		height : TableSize * 35 + 68,
		pageSize : TableSize,// 10 325,15 450,20 575,25 700
		pageList : [ 10, 15, 20, 25, 50, 100, 200, 500 ],
		columns : [ [ {
            field : 'ck',
            checkbox : true
        },{
			field : 'CarInfoInnerId',
			hidden : true
        },{
        	field : 'ParkInfo',
        	hidden : true
		}, {
			field : 'CarTypeInnerId',
			hidden : true
		}, {
			field : 'UserId',
			title : '人员编号',
			sortable : true,
			width : 150
		}, {
			field : 'UserName',
			title : '人员名称',
			sortable : true,
			width : 150
		},{
			field : 'DepartmentName',
			title : '部门名称',
			sortable : true,
			width : 150
		},{
			field : 'CarTypeName',
			title : '车辆类型',
			sortable : true,
			width : 150
		},{
			field : 'CarNumber',
			title : '车牌号',
			sortable : true,
			width : 150
		},{
			field : 'ParkingNum',
			title : '车位数量',
			sortable : true,
			width : 150
		},{
			field : 'ParkInfoName',
			title : '停车场',
			sortable : true,
			width : 150
		},{
			field : 'StartTime',
			title : '开始时间',
			sortable : true,
			width : 150
		},{
			field : 'EndTime',
			title : '结束时间',
			sortable : true,
			width : 150
		},{
			field : 'CarNumber1',
			title : '备用车牌1',
			sortable : true,
			width : 150
		},{
			field : 'CarNumber2',
			title : '备用车牌2',
			sortable : true,
			width : 150
		},{
			field : 'CarNumber3',
			title : '备用车牌3',
			sortable : true,
			width : 150
		},{
			field : 'CarNumber4',
			title : '备用车牌4',
			sortable : true,
			width : 150
		}]],
		onLoadSuccess : function(data) {
			$("#table").datagrid('clearSelections');
			if (data.rows.length == 0) {
				var body = $(this).data().datagrid.dc.body2;
				body.find('table tbody').append('<tr><td width="' + body.width() + '" style="height: 35px; text-align: center;"><h2>此条件下没有数据</h2></td></tr>');
			}
		}
	});
}

// 添加提交
$("#insertSubmit").click(function() {
	$("#insertForm").validate({
		showErrors: function(errorMap, errorList) {
			$.each(this.successList, function(index, value) {
				return $(value).popover("hide");
			});
			return $.each(errorList, function(index, value) {
				var _popover;
				_popover = $(value.element).popover({
					trigger: "manual",
					placement: "right",
					content: value.message,
					template: "<div class=\"popover\" style=\"color:red;line-height: 40px;\"><div class=\"arrow\"></div> <div class=\"popover-inner\"><div class=\"popover-content\" style=\"padding:0px 14px; \"></div></div></div>"
				});
				_popover.data("bs.popover").options.content = value.message;
				return _popover.popover("show");
			});
		},
		rules : {
			CarNumber : {
				required : true,
				maxlength : 50,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#iCarNumber").val().replace(/\s+/g,"");
						},
						InnerId : function() {
							return -1;
						},
						ColumnName : function() {
							return "CarNumber";
						}
					}
				},
				isContainsSpecialChar : true,
			},
			StartTime : {
				required : true,
			},
			ParkingNum : {
				isIntGtZero:true,
				required : true
			},
			EndTime : {
				required : true,
			},
			CarTypeInnerId : {
				required : true,
			}
		},
		messages : {
			CarNumber : {
				required : "请输入车牌号",
				maxlength : "最大不超过50个字符",
				remote : "车牌号重复,请重新输入"
			},
			StartTime : {
				required : "请输入开始时间",
			},
			ParkingNum : {
				isIntGtZero:"请输入大于0的整数",
				required : "请输入车位数量",
			},
			EndTime : {
				required : "请输入结束时间",
			},
			CarTypeInnerId: {
				required : "请输入车辆类型",
			}
		},
		submitHandler : function() {
			var regex = /^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$/; 
			var regex6 = /^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{5}[A-Z0-9挂学警港澳]{1}$/; 
			var carnumber = $("#iCarNumber").val();
			if(carnumber != ""){
				if(!((carnumber.length == 7 && regex.test(carnumber)) || (carnumber.length == 8 && regex6.test(carnumber)))){
					showMsg("提示信息", "请检查车牌号是否正确", true);
					return false;
				}
			}else{
				showMsg("提示信息", "车牌号不能为空", true);
				return false;
			}
			var carnumber1 = $("#iCarNumber1").val();
			if(carnumber1 != ""){
				if(!((carnumber1.length == 7 && regex.test(carnumber1)) || (carnumber1.length == 8 && regex6.test(carnumber1)))){
					showMsg("提示信息", "请检查备用车牌1是否正确", true);
					return false;
				}
			}
			var carnumber2 = $("#iCarNumber2").val();
			if(carnumber2 != ""){
				if(!((carnumber2.length == 7 && regex.test(carnumber2)) || (carnumber2.length == 8 && regex6.test(carnumber2)))){
					showMsg("提示信息", "请检查备用车牌2是否正确", true);
					return false;
				}
			}
			var carnumber3 = $("#iCarNumber3").val();
			if(carnumber3 != ""){
				if(!((carnumber3.length == 7 && regex.test(carnumber3)) || (carnumber3.length == 8 && regex6.test(carnumber3)))){
					showMsg("提示信息", "请检查备用车牌3是否正确", true);
					return false;
				}
			}
			var carnumber4 = $("#iCarNumber4").val();
			if(carnumber4 != ""){
				if(!((carnumber4.length == 7 && regex.test(carnumber4)) || (carnumber4.length == 8 && regex6.test(carnumber4)))){
					showMsg("提示信息", "请检查备用车牌4是否正确", true);
					return false;
				}
			}
			
			// 提交按钮设置为不可用，防止重复提交
			$("#insertSubmit").attr("disabled", true);
			$.ajax({
				url : "carInfo/insert.html",
				type : "post",
				dataType : "json",
				data : {
					CarTypeInnerId : $("#iCarTypeInnerId").combobox("getValue").toString(),
					CarNumber : $("#iCarNumber").val().replace(/\s+/g,""),
					CarNumber1 : $("#iCarNumber1").val().replace(/\s+/g,""),
					CarNumber2 : $("#iCarNumber2").val().replace(/\s+/g,""),
					CarNumber3 : $("#iCarNumber3").val().replace(/\s+/g,""),
					CarNumber4 : $("#iCarNumber4").val().replace(/\s+/g,""),
					StartTime : $("#iStartTime").val(),
					EndTime : $("#iEndTime").val(),
					UserInnerId : $("#iUserInnerId").val(),
					ParkingNum : $("#iParkingNum").val(),
					ParkInfo : $("#iPark").combobox("getValue").toString(),
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
		}
	});
});

// 修改提交
$("#updateSubmit").click(function() {
	$("#updateForm").validate({
		showErrors: function(errorMap, errorList) {
			$.each(this.successList, function(index, value) {
				return $(value).popover("hide");
			});
			return $.each(errorList, function(index, value) {
				var _popover;
				_popover = $(value.element).popover({
					trigger: "manual",
					placement: "right",
					content: value.message,
					template: "<div class=\"popover\" style=\"color:red;line-height: 40px;\"><div class=\"arrow\"></div> <div class=\"popover-inner\"><div class=\"popover-content\" style=\"padding:0px 14px; \"></div></div></div>"
				});
				_popover.data("bs.popover").options.content = value.message;
				return _popover.popover("show");
			});
		},
		rules : {
			CarNumber : {
				required : true,
				maxlength : 50,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#uCarNumber").val().replace(/\s+/g,"");
						},
						InnerId : function() {
							return $("#uCarInfoInnerId").val();
						},
						ColumnName : function() {
							return "CarNumber";
						}
					}
				},
			},
			ParkingNum : {
				isIntGtZero:true,
				required : true,
			},
			StartTime : {
				required : true,
			},
			EndTime : {
				required : true,
			},
			CarTypeInnerId : {
				required : true,
			}
		},
		messages : {
			CarNumber : {
				required : "请输入停车场名称",
				maxlength : "最大不超过50个字符",
				remote : "停车场名称重复,请重新输入",
			},
			ParkingNum : {
				isIntGtZero:"请输入大于0的整数",
				required : "请输入车位数量",
			},
			StartTime : {
				required : "请输入开始时间",
			},
			EndTime : {
				required : "请输入结束时间",
			},
			CarTypeInnerId: {
				required : "请输入车辆类型",
			}
		},
		submitHandler : function() {
			var regex = /^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$/; 
			var regex6 = /^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{5}[A-Z0-9挂学警港澳]{1}$/; 
			var carnumber = $("#uCarNumber").val();
			if(carnumber != ""){
				if(!((carnumber.length == 7 && regex.test(carnumber)) || (carnumber.length == 8 && regex6.test(carnumber)))){
					showMsg("提示信息", "请检查车牌号是否正确", true);
					return false;
				}
			}else{
				showMsg("提示信息", "车牌号不能为空", true);
				return false;
			}
			// 提交按钮设置为不可用，防止重复提交
			$("#updateSubmit").attr("disabled", true);
			$.ajax({
				url : "carInfo/update.html",
				type : "post",
				data : {
					CarInfoInnerId : $("#uCarInfoInnerId").val(),
					CarTypeInnerId : $("#uCarTypeInnerId").combobox("getValue").toString(),
					CarNumber : $("#uCarNumber").val(),
					CarNumber1 : $("#uCarNumber1").val(),
					CarNumber2 : $("#uCarNumber2").val(),
					CarNumber3 : $("#uCarNumber3").val(),
					CarNumber4 : $("#uCarNumber4").val(),
					StartTime : $("#uStartTime").val(),
					EndTime : $("#uEndTime").val(),
					ParkingNum : $("#uParkingNum").val(),
					ParkInfo : $("#uPark").combobox("getValue").toString()
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
		}
	});
});
// 修改提交
$("#updateTimeSubmit").click(function() {
	if($("#tStartTime").val() ==""){
		showMsg("提示信息", "开始时间不能为空", true);
		return false;
	}if($("#tEndTime").val() ==""){
		showMsg("提示信息", "结束时间不能为空", true);
		return false;
	}
	
			// 提交按钮设置为不可用，防止重复提交
			$("#updateTimeSubmit").attr("disabled", true);
			$.ajax({
				url : "carInfoTime/update.html",
				type : "post",
				data : {
					CarInfoInnerId : $("#tCarInfoList").val(),
					StartTime : $("#tStartTime").val(),
					EndTime : $("#tEndTime").val(),
				},
				dataType : "json",
				success : function(data) {
					// 提交按钮设置为可用
					$("#updateTimeSubmit").removeAttr("disabled");
					if (data.code == 200) {
						// 成功
						showMsg("提示信息", data.msg);
						// 隐藏下拉框
						$("#updateTimeDiv").modal("hide");
						// 重载表格数据
						table();
						// 清空form表单
						$(':input', '#updateTimeForm').not(':button, :submit, :reset, :hidden').val('').removeAttr('checked').removeAttr('selected');
					} else {
						// 失败
						showMsg("提示信息", data.msg, true);
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					// 提交按钮设置为可用
					$("#updateTimeSubmit").removeAttr("disabled");
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

$("#export").click(function() {
	var DepartmentInnerId = "";
	var arry = $("#sDepartmentInnerId").combotree("getValues");
	$.each(arry, function(i, k) {
		if (arry[i].indexOf("department") >= 0) {
			DepartmentInnerId += arry[i].substring(10, arry[i].length) + ",";
		}
	});
	$("#export").attr("disabled", true);
	$.ajax({
		url : "CarInfoExcel/select.html",
		type : "post",
		data : {
			UserId : $('#sUserId').val(),
			UserName : $('#sUserName').val(),
			CarNumber : $('#sCarNumber').val(),
			DepartmentInnerId : DepartmentInnerId,
			CarType : $("#sCarType").combobox('getValues').toString()
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