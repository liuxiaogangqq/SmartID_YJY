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

$("#iStartTime").focus(function() {
	WdatePicker({
		dateFmt : 'HH:mm:ss',
	});
});

$("#iEndTime").focus(function() {
	WdatePicker({
		dateFmt : 'HH:mm:ss',
	});
});
$("#uStartTime").focus(function() {
	WdatePicker({
		dateFmt : 'HH:mm:ss',
	});
});

$("#uEndTime").focus(function() {
	WdatePicker({
		dateFmt : 'HH:mm:ss',
	});
});

$("#iCarTypeInnerId").combobox({
	url : "carTypeBox/select.html",
	valueField : "id",
	textField : "text",
	editable : false,
	multiple : false,
});
$("#uCarTypeInnerId").combobox({
	url : "carTypeBox/select.html",
	valueField : "id",
	textField : "text",
	editable : false,
	multiple : false,
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
		$("#uPassRulesInnerId").val(Table.PassRulesInnerId);
		$("#uPassRulesName").val(Table.PassRulesName);
		$("#uPassRulesId").val(Table.PassRulesId);
		$("#uStartTime").val(Table.StartTime);
		$("#uEndTime").val(Table.EndTime);
		$("#uRemark").val(Table.Remark);
		$("#uFreeTime").val(Table.FreeTime);
		$("#uMoney").val(Table.Money);
		$("#uCarTypeInnerId").combobox('setValue',Table.CarTypeInnerId)
		$("#updateDiv").modal("show");
	} else {
		showMsg("提示信息", "请选择一条数据进行操作");
	}
});

// 点击删除时操作
$("#delete").click(function() {
	var Table = $("#table").datagrid("getSelected");
	if (Table) {
		if (confirm("是否确认删除此通行规则？")) {
			// 提交按钮设置为不可用，防止重复提交
			$("#delete").attr("disabled", true);
			$.ajax({
				url : "delete.html",
				type : "post",
				data : {
					InnerId : Table.PassRulesInnerId,
					TableName : "Park_PassRule"
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
	}else {
		showMsg("提示信息", "请选择一条数据进行操作");
	}
});

// 表格
function table() {
	$("#table").datagrid("uncheckAll");
	$("#table").datagrid({
		loadMsg : "正在加载数据，请稍等.....", // 在加载页面时显示数据
		url : "passRules/select.html", // 发送的url地址
		queryParams :
		// 参数列表
		{
			PassRulesId : $('#sPassRulesId').val(),
			PassRulesName : $('#sPassRulesName').val(),
			Remark : $('#sRemark').val(),
		},
		method : 'post',
		idField : 'PassRulesInnerId',
		fitColumns : true,
		// 获取分页对象
		pagination : true,
		rownumbers : true,
		singleSelect : true,
		height:TableSize*35+68,
		pageSize : TableSize,// 10 325,15 450,20 575,25 700
		pageList : [ 10, 15, 20, 25, 50, 100, 200, 500 ],
		columns : [ [ {
			field : 'PassRulesInnerId',
			hidden : true
		}, {
			field : 'CarTypeInnerId',
			hidden : true
		}, {
			field : 'PassRulesId',
			title : '通行规则编号',
			sortable : true,
			width : 150
		}, {
			field : 'PassRulesName',
			title : '通行规则名称',
			sortable : true,
			width : 150
		}, {
			field : 'StartTime',
			title : '开始时间',
			sortable : true,
			width : 150
		}, {
			field : 'EndTime',
			title : '结束时间',
			sortable : true,
			width : 150
		}, {
			field : 'FreeTime',
			title : '免费停车时长',
			sortable : true,
			width : 150
		}, {
			field : 'Money',
			title : '收费金额（每15分钟）',
			sortable : true,
			width : 150
		}, {
			field : 'CarTypeName',
			title : '车辆类型',
			sortable : true,
			width : 150
		}, {
			field : 'Remark',
			title : '备注',
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

// 添加提交
$("#insertSubmit").click(function() {
	$("#iPassRulesId").removeData("previousValue");
	$("#iPassRulesName").removeData("previousValue");
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
			PassRulesName : {
				required : true,
				maxlength : 50,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#iPassRulesName").val().replace(/\s+/g,"");
						},
						InnerId : function() {
							return -1;
						},
						ColumnName : function() {
							return "PassRulesName";
						}
					}
				},
				isContainsSpecialChar : true,
			},
			PassRulesId : {
				required : true,
				maxlength : 50,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#iPassRulesId").val().replace(/\s+/g,"");
						},
						InnerId : function() {
							return -1;
						},
						ColumnName : function() {
							return "PassRulesId";
						}
					}
				},
				isContainsSpecialChar : true,
			},
			Remark : {
				maxlength : 4000,
			},StartTime : {
				required : true
			},EndTime : {
				required : true
			},FreeTime : {
				required : true
			},Money : {
				required : true
			},CarTypeInnerId : {
				required : true
			}
		},
		messages : {
			PassRulesName : {
				required : "请输入通行规则名称",
				maxlength : "最大不超过50个字符",
				remote : "通行规则名称重复,请重新输入"
			},
			PassRulesId : {
				required : "请输入通行规则编号",
				maxlength : "最大不超过50个字符",
				remote : "通行规则编号重复,请重新输入"
			},
			StartTime : {
				required : "开始时间不能为空"
			},
			EndTime : {
				required : "结束时间不能为空"
			},
			Remark : {
				maxlength : "最大不超过4000个字符"
			},
			Money : {
				required : "收费金额不能为空"
			},
			FreeTime : {
				required : "免费时长不能为空"
			},
			CarTypeInnerId : {
				required : "车辆类型不能为空"
			}
		},
		submitHandler : function() {
			// 提交按钮设置为不可用，防止重复提交
			$("#insertSubmit").attr("disabled", true);
			$.ajax({
				url : "passRules/insert.html",
				type : "post",
				data : {
					PassRulesName : $("#iPassRulesName").val().replace(/\s+/g,""),
					PassRulesId : $("#iPassRulesId").val().replace(/\s+/g,""),
					StartTime : $("#iStartTime").val(),
					EndTime : $("#iEndTime").val(),
					Remark : $("#iRemark").val().replace(/\s+/g,""),
					FreeTime : $("#iFreeTime").val().replace(/\s+/g,""),
					Money : $("#iMoney").val().replace(/\s+/g,""),
					CarTypeInnerId : $("#iCarTypeInnerId").combobox("getValue").toString(),
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
	$("#uPassRulesId").removeData("previousValue");
	$("#uPassRulesName").removeData("previousValue");
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
			PassRulesName : {
				required : true,
				maxlength : 50,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#uPassRulesName").val().replace(/\s+/g,"");
						},
						InnerId : function() {
							return $("#uPassRulesInnerId").val();
						},
						ColumnName : function() {
							return "PassRulesName";
						}
					}
				},
				isContainsSpecialChar : true,
			},
			PassRulesId : {
				required : true,
				maxlength : 50,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#uPassRulesId").val().replace(/\s+/g,"");
						},
						InnerId : function() {
							return $("#uPassRulesInnerId").val();
						},
						ColumnName : function() {
							return "PassRulesId";
						}
					}
				},
				isContainsSpecialChar : true,
			},
			Remark : {
				maxlength : 4000,
				isContainsSpecialChar : true,
			},StartTime : {
				required : true
			},EndTime : {
				required : true
			},FreeTime : {
				required : true
			},Money : {
				required : true
			},CarTypeInnerId : {
				required : true
			}
		},
		messages : {
			PassRulesName : {
				required : "请输入通行规则名称",
				maxlength : "最大不超过50个字符",
				remote : "通行规则名称重复,请重新输入"
			},
			PassRulesId : {
				required : "请输入通行规则编号",
				maxlength : "最大不超过50个字符",
				remote : "通行规则编号重复,请重新输入"
			},
			StartTime : {
				required : "开始时间不能为空"
			},
			EndTime : {
				required : "结束时间不能为空"
			},
			Remark : {
				maxlength : "最大不超过4000个字符"
			},
			Money : {
				required : "收费金额不能为空"
			},
			FreeTime : {
				required : "免费时长不能为空"
			},
			CarTypeInnerId : {
				required : "车辆类型不能为空"
			}
		},
		submitHandler : function() {
			// 提交按钮设置为不可用，防止重复提交
			$("#updateSubmit").attr("disabled", true);
			$.ajax({
				url : "passRules/update.html",
				type : "post",
				data : {
					PassRulesInnerId : $("#uPassRulesInnerId").val(),
					PassRulesName : $("#uPassRulesName").val().replace(/\s+/g,""),
					PassRulesId : $("#uPassRulesId").val().replace(/\s+/g,""),
					StartTime : $("#uStartTime").val(),
					EndTime : $("#uEndTime").val(),
					Remark : $("#uRemark").val().replace(/\s+/g,""),
					FreeTime : $("#uFreeTime").val().replace(/\s+/g,""),
					Money : $("#uMoney").val().replace(/\s+/g,""),
					CarTypeInnerId : $("#uCarTypeInnerId").combobox("getValue").toString(),
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
						$("#uAreaList").combotree("clear");
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
