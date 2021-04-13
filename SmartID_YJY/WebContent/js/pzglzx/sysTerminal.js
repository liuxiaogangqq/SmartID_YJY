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
			Page : "sysTerminal"
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

$("#sAppInnerId").combotree({
	url : "areaAppTree/select.html?AppTypeInnerId=8",
	editable : false,
});

$("#iAppInnerId").combotree({
	url : "areaAppTree/select.html?AppTypeInnerId=8",
	editable : false,
});

$("#sTerminalTypeInnerId").combobox({
	url : "terminalTypeBox/select.html",
	valueField : "id",
	textField : "text",
	editable : false,
});

$("#iTerminalTypeInnerId").combobox({
	url : "terminalTypeBox/select.html",
	valueField : "id",
	textField : "text",
	editable : false,
});


$("#uAppInnerId").combotree({
	url : "areaAppTree/select.html?AppTypeInnerId=8",
	editable : false,
	readonly : true,
});

$("#uTerminalTypeInnerId").combobox({
	url : "terminalTypeBox/select.html",
	valueField : "id",
	textField : "text",
	editable : false,
	readonly : true,
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
	$("#insertDiv").modal("show");
});

// 点击修改时操作
$("#update").click(function() {
	var Table = $("#table").datagrid("getSelected");
	if (Table) {
		$("#uSysTerminalInnerId").val(Table.SysTerminalInnerId);
		$("#uSysTerminalName").val(Table.SysTerminalName);
		$("#uSysTerminalId").val(Table.SysTerminalId);
		$("#uComId").val(Table.ComId);
		$("#uComSerials").val(Table.ComSerials);
		$("#uAppInnerId").combotree("setValue", "app"+Table.AppInnerId);
		$("#uTerminalTypeInnerId").combobox("setValue", Table.TerminalTypeInnerId);
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
					InnerId : Table.SysTerminalInnerId,
					TableName : "System_Terminal"
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
	} else {
		showMsg("提示信息", "请选择一条数据进行操作");
	}
});

// 表格
function table() {
	$("#table").datagrid("uncheckAll");
	var value = $("#sAppInnerId").combotree("getValue");
	var AppInnerId;
	var AreaInnerId;
	if (value) {
		if (value.indexOf("app") >= 0) {
			AppInnerId = value.substring(3, value.length);
			AreaInnerId = '';
		}else{
			AppInnerId = '';
			AreaInnerId = value.substring(4, value.length);
		}
		
	}
	$("#table").datagrid({
		loadMsg : "正在加载数据，请稍等.....", // 在加载页面时显示数据
		url : "sysTerminal/select.html", // 发送的url地址
		queryParams :
		// 参数列表
		{
			SysTerminalId : $('#sSysTerminalId').val(),
			SysTerminalName : $('#sSysTerminalName').val(),
			Remark : $('#sRemark').val(),
			StandbyA : $('#sStandbyA').val(),
			StandbyB : $('#sStandbyB').val(),
			StandbyC : $('#sStandbyC').val(),
			StandbyD : $('#sStandbyD').val(),
			AppInnerId:AppInnerId,
			AreaInnerId:AreaInnerId,
			TerminalTypeInnerId:$("#sTerminalTypeInnerId").combobox("getValue")
		},
		method : 'post',
		idField : 'SysTerminalInnerId',
		fitColumns : false,
		// 获取分页对象
		pagination : true,
		rownumbers : true,
		singleSelect : true,
		height : TableSize * 35 + 68,
		pageSize : TableSize,// 10 325,15 450,20 575,25 700
		pageList : [ 10, 15, 20, 25, 50, 100, 200, 500 ],
		columns : [ [ {
			field : 'SysTerminalInnerId',
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
		}, {
			field : 'SysTerminalId',
			title : '设备编号',
			sortable : true,
			width : 120
		}, {
			field : 'SysTerminalName',
			title : '设备名称',
			sortable : true,
			width : 120
		}, {
			field : 'AreaName',
			title : '区域',
			sortable : true,
			width : 120
		}, {
			field : 'AppName',
			title : '应用',
			sortable : true,
			width : 120
		}, {
			field : 'TerminalTypeName',
			title : '设备类型',
			sortable : true,
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

// 添加提交
$("#insertSubmit").click(function() {
	$("#insertForm").validate({
		rules : {
			SysTerminalName : {
				required : true,
				maxlength : 50,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#iSysTerminalName").val().replace(/\s+/g,"");
						},
						InnerId : function() {
							return -1;
						},
						ColumnName : function() {
							return "SysTerminalName";
						}
					}
				}
			},
			SysTerminalId : {
				required : true,
				maxlength : 50,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#iSysTerminalId").val().replace(/\s+/g,"");
						},
						InnerId : function() {
							return -1;
						},
						ColumnName : function() {
							return "SysTerminalId";
						}
					}
				}
			},
			ComId : {
				maxlength : 4000
			},
			ComSerials : {
				maxlength : 4000
			},
			Remark : {
				maxlength : 4000
			},
			StandbyA : {
				maxlength : 4000
			},
			StandbyB : {
				maxlength : 4000
			},
			StandbyC : {
				maxlength : 4000
			},
			StandbyD : {
				maxlength : 4000
			}
		},
		messages : {
			SysTerminalName : {
				required : "请输入设备名称",
				maxlength : "最大不超过50个字符",
				remote : "设备名称重复,请重新输入"
			},
			SysTerminalId : {
				required : "请输入设备编号",
				maxlength : "最大不超过50个字符",
				remote : "设备编号重复,请重新输入"
			},
			ComId : {
				maxlength : "最大不超过4000个字符"
			},
			ComSerials : {
				maxlength : "最大不超过4000个字符"
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
			var AppInnerId = $("#iAppInnerId").combotree("getValue");
			if (!AppInnerId) {
				showMsg("提示信息", "请选择应用", true);
				return false;
			}
			if (!(AppInnerId.indexOf("app") >= 0)) {
				showMsg("提示信息", "只能选择应用", true);
				return false;
			}
			if (!($("#iTerminalTypeInnerId").combobox("getValue"))) {
				showMsg("提示信息", "请选择设备类型", true);
				return false;
			}
			// 提交按钮设置为不可用，防止重复提交
			$("#insertSubmit").attr("disabled", true);
			$.ajax({
				url : "sysTerminal/insert.html",
				type : "post",
				data : {
					SysTerminalName : $("#iSysTerminalName").val().replace(/\s+/g,""),
					SysTerminalId : $("#iSysTerminalId").val().replace(/\s+/g,""),
					AppInnerId : AppInnerId.substring(3, AppInnerId.length),
					TerminalTypeInnerId : $("#iTerminalTypeInnerId").combobox("getValue"),
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
						$("#iAppInnerId").combotree("clear");
						$("#iTerminalTypeInnerId").combobox("clear");
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
		rules : {
			SysTerminalName : {
				required : true,
				maxlength : 50,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#uSysTerminalName").val().replace(/\s+/g,"");
						},
						InnerId : function() {
							return $("#uSysTerminalInnerId").val();
						},
						ColumnName : function() {
							return "SysTerminalName";
						}
					}
				}
			},
			SysTerminalId : {
				required : true,
				maxlength : 50,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#uSysTerminalId").val().replace(/\s+/g,"");
						},
						InnerId : function() {
							return $("#uSysTerminalInnerId").val();
						},
						ColumnName : function() {
							return "SysTerminalId";
						}
					}
				}
			},
			ComId : {
				maxlength : 4000
			},
			ComSerials : {
				maxlength : 4000
			},
			Remark : {
				maxlength : 4000
			},
			StandbyA : {
				maxlength : 4000
			},
			StandbyB : {
				maxlength : 4000
			},
			StandbyC : {
				maxlength : 4000
			},
			StandbyD : {
				maxlength : 4000
			}
		},
		messages : {
			SysTerminalName : {
				required : "请输入设备名称",
				maxlength : "最大不超过50个字符",
				remote : "设备名称重复,请重新输入"
			},
			SysTerminalId : {
				required : "请输入设备编号",
				maxlength : "最大不超过50个字符",
				remote : "设备编号重复,请重新输入"
			},
			ComId : {
				maxlength : "最大不超过4000个字符"
			},
			ComSerials : {
				maxlength : "最大不超过4000个字符"
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
			var AppInnerId = $("#uAppInnerId").combotree("getValue");
			if (!AppInnerId) {
				showMsg("提示信息", "请选择应用", true);
				return false;
			}
			if (!(AppInnerId.indexOf("app") >= 0)) {
				showMsg("提示信息", "只能选择应用", true);
				return false;
			}
			if (!($("#uTerminalTypeInnerId").combobox("getValue"))) {
				showMsg("提示信息", "请选择设备类型", true);
				return false;
			}
			// 提交按钮设置为不可用，防止重复提交
			$("#updateSubmit").attr("disabled", true);
			$.ajax({
				url : "sysTerminal/update.html",
				type : "post",
				data : {
					SysTerminalInnerId : $("#uSysTerminalInnerId").val(),
					SysTerminalName : $("#uSysTerminalName").val().replace(/\s+/g,""),
					SysTerminalId : $("#uSysTerminalId").val().replace(/\s+/g,""),
					AppInnerId : AppInnerId.substring(3, AppInnerId.length),
					TerminalTypeInnerId : $("#uTerminalTypeInnerId").combobox("getValue"),
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
						$("#uAppInnerId").combotree("clear");
						$("#uTerminalTypeInnerId").combobox("clear");
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
