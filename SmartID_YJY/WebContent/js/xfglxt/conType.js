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
	// 请求后台，获得配置参数
	$.ajax({
		url : "configure/select.html",
		type : "post",
		async : false,
		data : {
			Page : "conType"
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
};

$("#iBeginTime").focus(function() {
	WdatePicker({
		dateFmt : 'HH:mm:ss',
	});
});
$("#iEndTime").focus(function() {
	WdatePicker({
		dateFmt : 'HH:mm:ss',
	});
});
$("#uBeginTime").focus(function() {
	WdatePicker({
		dateFmt : 'HH:mm:ss',
	});
});
$("#uEndTime").focus(function() {
	WdatePicker({
		dateFmt : 'HH:mm:ss',
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

// 点击添加时弹出框
$("#insert").click(function() {
	$("#insertDiv").modal("show");
});

// 点击修改时操作
$("#update").click(function() {
	var Table = $("#table").datagrid("getSelected");
	if (Table) {
		$.ajax({
			url : "selectTerminalByContype.html",
			type : "post",
			data : {
				InnerId : Table.ConTypeInnerId,
			},
			dataType : "json",
			success : function(data) {
				// 提交按钮设置为可用
				if (data.code == 200) {
					// 成功
					// 取消表格选中
					//$('#table').datagrid('clearSelections');
					
					$("#uConTypeInnerId").val(Table.ConTypeInnerId);
					$("#uConTypeName").val(Table.ConTypeName);
					$("#uConTypeId").val(Table.ConTypeId);
					$("#uBeginTime").val(Table.BeginTime);
					$("#uEndTime").val(Table.EndTime);
					$("#uStandbyA").val(Table.StandbyA);
					$("#uStandbyB").val(Table.StandbyB);
					$("#uRemark").val(Table.Remark);
					$("#updateDiv").modal("show");
				} else {
					// 失败
					showMsg("提示信息", data.msg, true);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				// 提交按钮设置为可用
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
	} else {
		showMsg("提示信息", "请选择一条数据进行操作");
	}
});

// 点击删除时操作
$("#delete").click(function() {
	var Table = $("#table").datagrid("getSelected");
	if (Table) {
		if (confirm("是否确认删除此消费类型？")) {
			// 提交按钮设置为不可用，防止重复提交
			$("#delete").attr("disabled", true);
			$.ajax({
				url : "delete.html",
				type : "post",
				data : {
					InnerId : Table.ConTypeInnerId,
					TableName : "Consumption_ConType"
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
	}else {
		showMsg("提示信息", "请选择一条数据进行操作");
	}
});

// 表格
function table() {
	$("#table").datagrid("clearSelections");
	$("#table").datagrid({
		loadMsg : "正在加载数据，请稍等.....", // 在加载页面时显示数据
		url : "conType/select.html", // 发送的url地址
		queryParams :
		// 参数列表
		{
			ConTypeId : $('#sConTypeId').val(),
			ConTypeName : $('#sConTypeName').val(),
			Remark : $('#sRemark').val(),
			StandbyA : $('#sStandbyA').val(),
			StandbyB : $('#sStandbyB').val()
		},
		method : 'post',
		idField : 'ConTypeInnerId',
		fitColumns : true,
		// 获取分页对象
		pagination : true,
		rownumbers : true,
		singleSelect : true,
		height : TableSize * 35 + 68,
		pageSize : TableSize,// 10 325,15 450,20 575,25 700
		pageList : [ 10, 15, 20, 25, 50, 100, 200, 500 ],
		columns : [ [ {
			field : 'ConTypeInnerId',
			hidden : true
		}, {
			field : 'ConTypeId',
			title : '消费类型编号',
			sortable : true,
			width : 150
		}, {
			field : 'ConTypeName',
			title : '消费类型名称',
			sortable : true,
			width : 150
		}, {
			field : 'BeginTime',
			title : '开始时间',
			sortable : true,
			width : 150
		}, {
			field : 'EndTime',
			title : '结束时间',
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
	$("#iConTypeName").removeData("previousValue");
	$("#iConTypeId").removeData("previousValue");
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
			ConTypeName : {
				required : true,
				maxlength : 50,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#iConTypeName").val().replace(/\s+/g,"");
						},
						InnerId : function() {
							return -1;
						},
						ColumnName : function() {
							return "ConTypeName";
						}
					}
				},
				isContainsSpecialChar : true,
			},
			ConTypeId : {
				required : true,
				maxlength : 50,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#iConTypeId").val().replace(/\s+/g,"");
						},
						InnerId : function() {
							return -1;
						},
						ColumnName : function() {
							return "ConTypeId";
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
			}
		},
		messages : {
			ConTypeName : {
				required : "请输入消费类型名称",
				maxlength : "最大不超过50个字符",
				remote : "消费类型名称重复,请重新输入"
			},
			ConTypeId : {
				required : "请输入消费类型编号",
				maxlength : "最大不超过50个字符",
				remote : "消费类型编号重复,请重新输入"
			},
			Remark : {
				maxlength : "最大不超过4000个字符"
			},
			StandbyA : {
				maxlength : "最大不超过4000个字符"
			},
			StandbyB : {
				maxlength : "最大不超过4000个字符"
			}
		},
		submitHandler : function() {
			// 提交按钮设置为不可用，防止重复提交
			if($("#iBeginTime").val()==""){
				showMsg("提示信息", "请选择开始时间", true);
				return false;
			}
			if($("#iEndTime").val()==""){
				showMsg("提示信息", "请选择结束时间", true);
				return false;
			}
			$("#insertSubmit").attr("disabled", true);
			$.ajax({
				url : "conType/insert.html",
				type : "post",
				data : {
					ConTypeName : $("#iConTypeName").val().replace(/\s+/g,""),
					ConTypeId : $("#iConTypeId").val().replace(/\s+/g,""),
					BeginTime : $("#iBeginTime").val(),
					EndTime : $("#iEndTime").val(),
					StandbyA : $("#iStandbyA").val().replace(/\s+/g,""),
					StandbyB : $("#iStandbyB").val().replace(/\s+/g,""),
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
	$("#uConTypeName").removeData("previousValue");
	$("#uConTypeId").removeData("previousValue");
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
			ConTypeName : {
				required : true,
				maxlength : 50,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#uConTypeName").val().replace(/\s+/g,"");
						},
						InnerId : function() {
							return $("#uConTypeInnerId").val();
						},
						ColumnName : function() {
							return "ConTypeName";
						}
					}
				},
				isContainsSpecialChar : true,
			},
			ConTypeId : {
				required : true,
				maxlength : 50,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#uConTypeId").val().replace(/\s+/g,"");
						},
						InnerId : function() {
							return $("#uConTypeInnerId").val();
						},
						ColumnName : function() {
							return "ConTypeId";
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
			}
		},
		messages : {
			ConTypeName : {
				required : "请输入消费类型名称",
				maxlength : "最大不超过50个字符",
				remote : "消费类型名称重复,请重新输入"
			},
			ConTypeId : {
				required : "请输入消费类型编号",
				maxlength : "最大不超过50个字符",
				remote : "消费类型编号重复,请重新输入"
			},
			Remark : {
				maxlength : "最大不超过4000个字符"
			},
			StandbyA : {
				maxlength : "最大不超过4000个字符"
			},
			StandbyB : {
				maxlength : "最大不超过4000个字符"
			}
		},
		submitHandler : function() {
			if($("#uBeginTime").val()==""){
				showMsg("提示信息", "请选择开始时间", true);
				return false;
			}
			if($("#uEndTime").val()==""){
				showMsg("提示信息", "请选择结束时间", true);
				return false;
			}
			// 提交按钮设置为不可用，防止重复提交
			$("#updateSubmit").attr("disabled", true);
			$.ajax({
				url : "conType/update.html",
				type : "post",
				data : {
					ConTypeInnerId : $("#uConTypeInnerId").val(),
					ConTypeName : $("#uConTypeName").val().replace(/\s+/g,""),
					ConTypeId : $("#uConTypeId").val().replace(/\s+/g,""),
					BeginTime : $("#uBeginTime").val(),
					EndTime : $("#uEndTime").val(),
					StandbyA : $("#uStandbyA").val().replace(/\s+/g,""),
					StandbyB : $("#uStandbyB").val().replace(/\s+/g,""),
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
