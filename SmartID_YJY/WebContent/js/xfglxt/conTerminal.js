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

// 加载时载入表格
window.onload = function() {
	// 请求后台，获得配置参数
	$.ajax({
		url : "configure/select.html",
		type : "post",
		async : false,
		data : {
			Page : "conTerminal"
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
				if (arry[i].Property == "StandbyC") {
					StandbyCHide = arry[i].Enabled;
					StandbyCName = arry[i].Name;
					StandbyCLength = arry[i].Length;
				}
				if (arry[i].Property == "StandbyD") {
					StandbyDHide = arry[i].Enabled;
					StandbyDName = arry[i].Name;
					StandbyDLength = arry[i].Length;
				}
			});
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
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
	table();
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
	if (StandbyCHide) {
		$("#sStandbyCDiv").hide();
		$("#iStandbyCDiv").hide();
		$("#uStandbyCDiv").hide();
	} else {
		$("#sStandbyCLabel").text(StandbyCName + "：");
		$("#iStandbyCLabel").text(StandbyCName + "：");
		$("#uStandbyCLabel").text(StandbyCName + "：");
	}
	;
	if (StandbyDHide) {
		$("#sStandbyDDiv").hide();
		$("#iStandbyDDiv").hide();
		$("#uStandbyDDiv").hide();
	} else {
		$("#sStandbyDLabel").text(StandbyDName + "：");
		$("#iStandbyDLabel").text(StandbyDName + "：");
		$("#uStandbyDLabel").text(StandbyDName + "：");
	}
	;
};

$("#iMerchantInnerId").combotree({
	url : "merchantAppTree/select.html",
	editable : false,
});

$("#iTerminalTypeInnerId").combobox({
	url : "terminalTypeBox/select.html",
	valueField : "id",
	textField : "text",
	editable : false,
});

$("#iConTypeList").combobox({
	url : "conTypeBox/select.html",
	valueField : "id",
	textField : "text",
	editable : false,
	multiple : true,
});

$("#uMerchantInnerId").combotree({
	url : "merchantAppTree/select.html",
	editable : false,
});

$("#uTerminalTypeInnerId").combobox({
	url : "terminalTypeBox/select.html",
	valueField : "id",
	textField : "text",
	editable : false,
});

$("#uConTypeList").combobox({
	url : "conTypeBox/select.html",
	valueField : "id",
	textField : "text",
	editable : false,
	multiple : true,
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

// 点击添加时弹出框
$("#insert").click(function() {
	$.ajax({
		url : "ConDeviceNums/select.html",
		type : "post",
		dataType : "json",
		success : function(data) {
			// 提交按钮设置为可用
			if (data.code == 500) {
				// 成功
				showMsg("提示信息", data.msg,true);
			} else {
				$("#insertDiv").modal("show");
			}
		}
	});
});

// 点击修改时操作
$("#update").click(function() {
	var Table = $("#table").datagrid("getSelected");
	if (Table) {
		$("#uConTerminalInnerId").val(Table.ConTerminalInnerId);
		$("#uConTerminalName").val(Table.ConTerminalName);
		$("#uConTerminalId").val(Table.ConTerminalId);
		$("#uComId").val(Table.ComId);
		$("#uComSerials").val(Table.ComSerials);
		$("#uMerchantInnerId").combotree("setValue", "merchant"+Table.MerchantInnerId);
		$("#uTerminalTypeInnerId").combobox("setValue", Table.TerminalTypeInnerId);
		$("#uConTypeList").combobox("setValues", Table.ConTypeList);
		$("#uStandbyA").val(Table.StandbyA);
		$("#uStandbyB").val(Table.StandbyB);
		$("#uStandbyC").val(Table.StandbyC);
		$("#uStandbyD").val(Table.StandbyD);
		$("#uRemark").val(Table.Remark);
		$("#updateDiv").modal("show");
	} else {
		showMsg("提示信息", "请选择一条数据进行操作");
	}
});

// 点击删除时操作
$("#delete").click(function() {
	var Table = $("#table").datagrid("getSelected");
	if (Table) {
		if (confirm("是否确认删除此设备？")) {
			// 提交按钮设置为不可用，防止重复提交
			$("#delete").attr("disabled", true);
			$.ajax({
				url : "delete.html",
				type : "post",
				data : {
					InnerId : Table.ConTerminalInnerId,
					TableName : "Consumption_Terminal"
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
		}
	} else {
		showMsg("提示信息", "请选择一条数据进行操作");
	}
});

// 表格
function table() {
	$("#table").datagrid("clearSelections");
	$("#table").datagrid({
		loadMsg : "正在加载数据，请稍等.....", // 在加载页面时显示数据
		url : "conTerminal/select.html", // 发送的url地址
		queryParams :
		// 参数列表
		{
			ConTerminalId : $('#sConTerminalId').val(),
			ConTerminalName : $('#sConTerminalName').val(),
			Remark : $('#sRemark').val(),
			StandbyA : $('#sStandbyA').val(),
			StandbyB : $('#sStandbyB').val(),
			StandbyC : $('#sStandbyC').val(),
			StandbyD : $('#sStandbyD').val()
		},
		method : 'post',
		idField : 'ConTerminalInnerId',
		fitColumns : true,
		// 获取分页对象
		pagination : true,
		rownumbers : true,
		singleSelect : true,
		height : TableSize * 35 + 68,
		pageSize : TableSize,// 10 325,15 450,20 575,25 700
		pageList : [ 10, 15, 20, 25, 50, 100, 200, 500 ],
		columns : [ [ {
			field : 'ConTerminalInnerId',
			hidden : true
		},{
			field : 'MerchantInnerId',
			hidden : true
		},{
			field : 'AppInnerId',
			hidden : true
		},{
			field : 'AreaInnerId',
			hidden : true
		},{
			field : 'TerminalTypeInnerId',
			hidden : true
		},{
			field : 'ConTypeList',
			hidden : true
		}, {
			field : 'ConTerminalId',
			title : '设备编号',
			sortable : true,
			width : 120
		}, {
			field : 'ConTerminalName',
			title : '设备名称',
			sortable : true,
			width : 120
		}, {
			field : 'TerminalTypeName',
			title : '设备类型',
			width : 120
		}, {
			field : 'AreaName',
			title : '区域',
			width : 120
		}, {
			field : 'AppName',
			title : '应用',
			width : 120
		},{
			field : 'MerchantName',
			title : '商户',
			width : 120
		}, {
			field : 'ComId',
			title : '注册码',
			sortable : true,
			width : 120
		}, {
			field : 'ComSerials',
			title : '注册标识',
			sortable : true,
			width : 120
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
		}, {
			field : 'StandbyC',
			title : StandbyCName,
			sortable : true,
			hidden : StandbyCHide,
			width : StandbyCLength
		}, {
			field : 'StandbyD',
			title : StandbyDName,
			sortable : true,
			hidden : StandbyDHide,
			width : StandbyDLength
		} ] ],
		onLoadSuccess : function(data) {
			if (data.rows.length == 0) {
				var body = $(this).data().datagrid.dc.body2;
				body.find('table tbody').append('<tr><td width="' + body.width() + '" style="height: 35px; text-align: center;"><h2>此条件下没有数据</h2></td></tr>');
			}
		}
	});
}

// 添加提交
$("#insertSubmit").click(function() {
	$("#iConTerminalName").removeData("previousValue");
	$("#iConTerminalId").removeData("previousValue");
	$("#iComId").removeData("previousValue");
	$("#iComSerials").removeData("previousValue");
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
			ConTerminalName : {
				required : true,
				maxlength : 50,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#iConTerminalName").val().replace(/\s+/g,"");
						},
						InnerId : function() {
							return -1;
						},
						ColumnName : function() {
							return "ConTerminalName";
						}
					}
				},
				isContainsSpecialChar : true,
			},
			ConTerminalId : {
				required : true,
				maxlength : 50,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#iConTerminalId").val().replace(/\s+/g,"");
						},
						InnerId : function() {
							return -1;
						},
						ColumnName : function() {
							return "ConTerminalId";
						}
					}
				},
				isContainsSpecialChar : true,
			},
			ComId : {
				required : true,
				maxlength : 4000,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#iComId").val().replace(/\s+/g,"");
						},
						InnerId : function() {
							return -1;
						},
						ColumnName : function() {
							return "ComId";
						}
					}
				},
				isContainsSpecialChar : true,
			},
			ComSerials : {
				required : true,
				maxlength : 4000,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#iComSerials").val().replace(/\s+/g,"");
						},
						InnerId : function() {
							return -1;
						},
						ColumnName : function() {
							return "ComSerials";
						}
					}
				},
				isContainsSpecialChar : true,
			},
			Remark : {
				maxlength : 4000,
				isContainsSpecialChar : true,
			},
			StandbyA : {
				maxlength : 4000,
				isContainsSpecialChar : true,
			},
			StandbyB : {
				maxlength : 4000,
				isContainsSpecialChar : true,
			},
			StandbyC : {
				maxlength : 4000,
				isContainsSpecialChar : true,
			},
			StandbyD : {
				maxlength : 4000,
				isContainsSpecialChar : true,
			}
		},
		messages : {
			ConTerminalName : {
				required : "请输入设备名称",
				maxlength : "最大不超过50个字符",
				remote : "设备名称重复,请重新输入"
			},
			ConTerminalId : {
				required : "请输入设备编号",
				maxlength : "最大不超过50个字符",
				remote : "设备编号重复,请重新输入"
			},
			ComId : {
				required : "请输入注册标识",
				maxlength : "最大不超过4000个字符",
				remote : "注册标识重复,请重新输入"
			},
			ComSerials : {
				required : "请输入注册码",
				maxlength : "最大不超过4000个字符",
				remote : "注册码重复,请重新输入"
			},
			Remark : {
				maxlength : "最大不超过4000个字符"
			},
			StandbyA : {
				maxlength : "最大不超过4000个字符"
			},
			StandbyB : {
				maxlength : "最大不超过4000个字符"
			},
			StandbyC : {
				maxlength : "最大不超过4000个字符"
			},
			StandbyD : {
				maxlength : "最大不超过4000个字符"
			}
		},
		submitHandler : function() {
			var MerchantInnerId = $("#iMerchantInnerId").combotree("getValue");
			if (!MerchantInnerId) {
				showMsg("提示信息", "请选择商户", true);
				return false;
			}
			//alert(MerchantInnerId);
			if (!(MerchantInnerId.indexOf("merchant") >= 0)) {
				showMsg("提示信息", "只能选择商户", true);
				return false;
			}
			if (!($("#iTerminalTypeInnerId").combobox("getValue"))) {
				showMsg("提示信息", "请选择设备类型", true);
				return false;
			}
			if(!($("#iConTypeList").combobox("getValue"))) {
				showMsg("提示信息", "请选择消费类型", true);
				return false;
			}
			// 提交按钮设置为不可用，防止重复提交
			$("#insertSubmit").attr("disabled", true);
			$.ajax({
				url : "conTerminal/insert.html",
				type : "post",
				data : {
					ConTerminalName : $("#iConTerminalName").val().replace(/\s+/g,""),
					ConTerminalId : $("#iConTerminalId").val().replace(/\s+/g,""),
					MerchantInnerId : MerchantInnerId.substring(8, MerchantInnerId.length),
					TerminalTypeInnerId : $("#iTerminalTypeInnerId").combobox("getValue"),
					ConTypeList : $("#iConTypeList").combobox("getValues").toString(),
					ComId : $("#iComId").val().replace(/\s+/g,""),
					ComSerials : $("#iComSerials").val().replace(/\s+/g,""),
					StandbyA : $("#iStandbyA").val().replace(/\s+/g,""),
					StandbyB : $("#iStandbyB").val().replace(/\s+/g,""),
					StandbyC : $("#iStandbyC").val().replace(/\s+/g,""),
					StandbyD : $("#iStandbyD").val().replace(/\s+/g,""),
					Remark : $("#iRemark").val().replace(/\s+/g,"")
				},
				dataType : "json",
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
						$("#iMerchantInnerId").combotree("clear");
						$("#iTerminalTypeInnerId").combobox("clear");
						$("#iConTypeList").combobox("clear");
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
		}
	});
});

// 修改提交
$("#updateSubmit").click(function() {
	$("#uConTerminalName").removeData("previousValue");
	$("#uConTerminalId").removeData("previousValue");
	$("#uComId").removeData("previousValue");
	$("#uComSerials").removeData("previousValue");
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
			ConTerminalName : {
				required : true,
				maxlength : 50,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#uConTerminalName").val().replace(/\s+/g,"");
						},
						InnerId : function() {
							return $("#uConTerminalInnerId").val();
						},
						ColumnName : function() {
							return "ConTerminalName";
						}
					}
				},
				isContainsSpecialChar : true,
			},
			ConTerminalId : {
				required : true,
				maxlength : 50,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#uConTerminalId").val().replace(/\s+/g,"");
						},
						InnerId : function() {
							return $("#uConTerminalInnerId").val();
						},
						ColumnName : function() {
							return "ConTerminalId";
						}
					}
				},
				isContainsSpecialChar : true,
			},
			ComId : {
				required : true,
				maxlength : 4000,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#uComId").val().replace(/\s+/g,"");
						},
						InnerId : function() {
							return $("#uConTerminalInnerId").val();
						},
						ColumnName : function() {
							return "ComId";
						}
					}
				},
				isContainsSpecialChar : true,
			},
			ComSerials : {
				required : true,
				maxlength : 4000,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#uComSerials").val().replace(/\s+/g,"");
						},
						InnerId : function() {
							return $("#uConTerminalInnerId").val();
						},
						ColumnName : function() {
							return "ComSerials";
						}
					}
				},
				isContainsSpecialChar : true,
			},
			Remark : {
				maxlength : 4000,
				isContainsSpecialChar : true,
			},
			StandbyA : {
				maxlength : 4000,
				isContainsSpecialChar : true,
			},
			StandbyB : {
				maxlength : 4000,
				isContainsSpecialChar : true,
			},
			StandbyC : {
				maxlength : 4000,
				isContainsSpecialChar : true,
			},
			StandbyD : {
				maxlength : 4000,
				isContainsSpecialChar : true,
			}
		},
		messages : {
			ConTerminalName : {
				required : "请输入设备名称",
				maxlength : "最大不超过50个字符",
				remote : "设备名称重复,请重新输入"
			},
			ConTerminalId : {
				required : "请输入设备编号",
				maxlength : "最大不超过50个字符",
				remote : "设备编号重复,请重新输入"
			},
			ComId : {
				required : "请输入注册标识",
				maxlength : "最大不超过4000个字符",
				remote : "注册标识重复,请重新输入"
			},
			ComSerials : {
				required : "请输入注册码",
				maxlength : "最大不超过4000个字符",
				remote : "注册码重复,请重新输入"
			},
			Remark : {
				maxlength : "最大不超过4000个字符"
			},
			StandbyA : {
				maxlength : "最大不超过4000个字符"
			},
			StandbyB : {
				maxlength : "最大不超过4000个字符"
			},
			StandbyC : {
				maxlength : "最大不超过4000个字符"
			},
			StandbyD : {
				maxlength : "最大不超过4000个字符"
			}
		},
		submitHandler : function() {
			var MerchantInnerId = $("#uMerchantInnerId").combotree("getValue");
			if (!MerchantInnerId) {
				showMsg("提示信息", "请选择商户", true);
				return false;
			}
			if (!(MerchantInnerId.indexOf("merchant") >= 0)) {
				showMsg("提示信息", "只能选择商户", true);
				return false;
			}
			if (!($("#uTerminalTypeInnerId").combobox("getValue"))) {
				showMsg("提示信息", "请选择设备类型", true);
				return false;
			}
			if(!($("#uConTypeList").combobox("getValue"))) {
				showMsg("提示信息", "请选择消费类型", true);
				return false;
			}
			// 提交按钮设置为不可用，防止重复提交
			$("#updateSubmit").attr("disabled", true);
			$.ajax({
				url : "conTerminal/update.html",
				type : "post",
				data : {
					ConTerminalInnerId : $("#uConTerminalInnerId").val(),
					ConTerminalName : $("#uConTerminalName").val().replace(/\s+/g,""),
					ConTerminalId : $("#uConTerminalId").val().replace(/\s+/g,""),
					MerchantInnerId : MerchantInnerId.substring(8, MerchantInnerId.length),
					TerminalTypeInnerId : $("#uTerminalTypeInnerId").combobox("getValue"),
					ConTypeList : $("#uConTypeList").combobox("getValues").toString(),
					ComId : $("#uComId").val().replace(/\s+/g,""),
					ComSerials : $("#uComSerials").val().replace(/\s+/g,""),
					StandbyA : $("#uStandbyA").val().replace(/\s+/g,""),
					StandbyB : $("#uStandbyB").val().replace(/\s+/g,""),
					StandbyC : $("#uStandbyC").val().replace(/\s+/g,""),
					StandbyD : $("#uStandbyD").val().replace(/\s+/g,""),
					Remark : $("#uRemark").val().replace(/\s+/g,"")
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
						$("#uMerchantInnerId").combotree("clear");
						$("#uTerminalTypeInnerId").combobox("clear");
						$("#uConTypeList").combobox("clear");
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
		}
	});
});
