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
			Page : "department"
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
	if (RemarkHide) {
		$("#itRemarkDiv").hide();
		$("#iRemarkDiv").hide();
		$("#uRemarkDiv").hide();
	} else {
		$("#itRemarkLabel").text(RemarkName + "：");
		$("#iRemarkLabel").text(RemarkName + "：");
		$("#uRemarkLabel").text(RemarkName + "：");
	}
	;
	if (StandbyAHide) {
		$("#itStandbyADiv").hide();
		$("#iStandbyADiv").hide();
		$("#uStandbyADiv").hide();
	} else {
		$("#itStandbyALabel").text(StandbyAName + "：");
		$("#iStandbyALabel").text(StandbyAName + "：");
		$("#uStandbyALabel").text(StandbyAName + "：");
	}
	;
	if (StandbyBHide) {
		$("#itStandbyBDiv").hide();
		$("#iStandbyBDiv").hide();
		$("#uStandbyBDiv").hide();
	} else {
		$("#itStandbyBLabel").text(StandbyBName + "：");
		$("#iStandbyBLabel").text(StandbyBName + "：");
		$("#uStandbyBLabel").text(StandbyBName + "：");
	}
	;
	if (StandbyCHide) {
		$("#itStandbyCDiv").hide();
		$("#iStandbyCDiv").hide();
		$("#uStandbyCDiv").hide();
	} else {
		$("#itStandbyCLabel").text(StandbyCName + "：");
		$("#iStandbyCLabel").text(StandbyCName + "：");
		$("#uStandbyCLabel").text(StandbyCName + "：");
	}
	;
	if (StandbyDHide) {
		$("#itStandbyDDiv").hide();
		$("#iStandbyDDiv").hide();
		$("#uStandbyDDiv").hide();
	} else {
		$("#itStandbyDLabel").text(StandbyDName + "：");
		$("#iStandbyDLabel").text(StandbyDName + "：");
		$("#uStandbyDLabel").text(StandbyDName + "：");
	}
	;
	$("#sCompanyInnerId").combobox({
		url : "companyBox/select.html",
		valueField : "id",
		textField : "text",
		async : false,
		editable : false,

		onLoadSuccess : function() {
			var data = $('#sCompanyInnerId').combobox('getData');
			$("#sCompanyInnerId ").combobox('setValue', data[0].id);
			table();
		},
		onSelect : function() {
			table();
		},
	});
	
	$("#depConPower").combobox({
		data : [{"id":"1","text":"有权限"},{"id":"2","text":"无权限"}],
		valueField : "id",
		textField : "text",
		editable : false,
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

// 点击查询时更新表格
$("#select").click(function() {
	//$("#table").datagrid({pageNumber:1});
	table();
});

// 点击添加顶级部门时弹出框
$("#insertTop").click(function() {
	$("#itCompanyInnerId").val($("#sCompanyInnerId").combobox("getValue"));
	$("#itCompanyName").val($("#sCompanyInnerId").combobox("getText"));
	$("#insertTopDiv").modal("show");
});

// 点击添加下级部门时弹出框
$("#insert").click(function() {
	var Table = $("#table").treegrid("getSelected");
	if (Table) {
		$("#iCompanyInnerId").val($("#sCompanyInnerId").combobox("getValue"));
		$("#iCompanyName").val($("#sCompanyInnerId").combobox("getText"));
		$("#iUpInnerId").val(Table.DepartmentInnerId);
		$("#iUpName").val(Table.DepartmentName);
		$("#iDepartmentLevel").val(Table.DepartmentLevel + 1);
		$("#insertDiv").modal("show");
	} else {
		showMsg("提示信息", "请选择一条数据进行操作");
	}
});

// 点击修改时操作
$("#update").click(function() {
	var Table = $("#table").treegrid("getSelected");
	if (Table) {
		$.ajax({
			url : "CompanyPower.html",
			type : "post",
			data : {
				Flag : 'Department',
				InnerId : Table.DepartmentInnerId,
			},
			dataType : "json",
			success : function(data) {
					// 成功
				$("#depConPower").combobox('setValue',data.Power);
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
		
		
		$("#uCompanyInnerId").val(Table.CompanyInnerId);
		$("#uCompanyName").val($("#sCompanyInnerId").combobox("getText"));
		$("#uUpInnerId").val(Table.UpInnerId);
		$("#uDepartmentInnerId").val(Table.DepartmentInnerId);
		$("#uDepartmentLevel").val(Table.DepartmentLevel);
		$("#uDepartmentName").val(Table.DepartmentName);
		$("#uDepartmentId").val(Table.DepartmentId);
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
	var Table = $("#table").treegrid("getSelected");
	if (Table) {
		if (confirm("是否确认删除此部门？")) {
			var DepartmentLevel = Table.DepartmentLevel;
			var UpInnerId = Table.UpInnerId;
			// 提交按钮设置为不可用，防止重复提交
			$("#delete").attr("disabled", true);
			$.ajax({
				url : "delete.html",
				type : "post",
				data : {
					InnerId : Table.DepartmentInnerId,
					TableName : "System_Department"
				},
				dataType : "json",
				success : function(data) {
					// 提交按钮设置为可用
					$("#delete").removeAttr("disabled");
					if (data.code == 200) {
						// 成功
						showMsg("提示信息", data.msg);
						// 重载表格数据
						if (DepartmentLevel == 1) {
							table();
						} else {
							$("#table").treegrid('reload', UpInnerId);
						}
						// 取消表格选中
						$('#table').treegrid('clearSelections');
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

function table() {
	$('#table').treegrid({
		url : 'department/select.html',
		queryParams : {
			CompanyInnerId : $("#sCompanyInnerId").combobox("getValue"),
			UpInnerId : 0
		},
		height : TableSize * 35 + 68,
		rownumbers : true,
		fitColumns:true,
		idField : 'DepartmentInnerId',
		treeField : 'DepartmentId',
		columns : [ [ {
			field : 'DepartmentInnerId',
			hidden : true
		}, {
			field : 'CompanyInnerId',
			hidden : true
		}, {
			field : 'UpInnerId',
			hidden : true
		}, {
			field : 'DepartmentId',
			title : '部门编号',
			width : 200
		}, {
			field : 'DepartmentName',
			title : '部门名称',
			width : 200
		}, {
			field : 'DepartmentLevel',
			title : '部门级别',
			width : 200
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
		onBeforeExpand : function(row, param) {
			if (row) {
				$(this).treegrid('options').queryParams.UpInnerId = row.DepartmentInnerId;
			}
		},
		onLoadError : function() {
			showMsg("提示信息", "服务发生异常，请检查服务", true);
		}
	});
}

// 添加顶级部门提交
$("#insertTopSubmit").click(function() {
	$("#itDepartmentId").removeData("previousValue");
	$("#insertTopForm").validate({
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
			DepartmentName : {
				required : true,
				maxlength : 50,
				isContainsSpecialChar : true,
			},
			DepartmentId : {
				required : true,
				maxlength : 50,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#itDepartmentId").val().replace(/\s+/g,"");
						},
						InnerId : function() {
							return -1;
						},
						ColumnName : function() {
							return "DepartmentId";
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
			DepartmentName : {
				required : "请输入部门名称",
				maxlength : "最大不超过50个字符"
			},
			DepartmentId : {
				required : "请输入部门编号",
				maxlength : "最大不超过50个字符",
				remote : "部门编号重复,请重新输入"
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
			// 提交按钮设置为不可用，防止重复提交
			$("#insertTopSubmit").attr("disabled", true);
			$.ajax({
				url : "department/insert.html",
				type : "post",
				data : {
					DepartmentId : $("#itDepartmentId").val().replace(/\s+/g,""),
					DepartmentName : $("#itDepartmentName").val().replace(/\s+/g,""),
					CompanyInnerId : $("#itCompanyInnerId").val(),
					UpInnerId : 0,
					DepartmentLevel : 1,
					StandbyA : $("#itStandbyA").val().replace(/\s+/g,""),
					StandbyB : $("#itStandbyB").val().replace(/\s+/g,""),
					StandbyC : $("#itStandbyC").val().replace(/\s+/g,""),
					StandbyD : $("#itStandbyD").val().replace(/\s+/g,""),
					Remark : $("#itRemark").val().replace(/\s+/g,"")
				},
				dataType : "json",
				success : function(data) {
					// 提交按钮设置为可用
					$("#insertTopSubmit").removeAttr("disabled");
					if (data.code == 200) {
						// 成功
						showMsg("提示信息", data.msg);
						// 隐藏下拉框
						$("#insertTopDiv").modal("hide");
						// 重载表格数据
						table();
						$("#table").datagrid("clearSelections");
						// 清空form表单
						$(':input', '#insertTopForm').not(':button, :submit, :reset, :hidden').val('').removeAttr('checked').removeAttr('selected');
					} else {
						// 失败
						showMsg("提示信息", data.msg, true);
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					// 提交按钮设置为可用
					$("#insertTopSubmit").removeAttr("disabled");
					$("#table").datagrid("clearSelections");
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

// 添加下级部门提交
$("#insertSubmit").click(function() {
	$("#iDepartmentId").removeData("previousValue");
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
			DepartmentName : {
				required : true,
				maxlength : 50,
				isContainsSpecialChar : true,
			},
			DepartmentId : {
				required : true,
				maxlength : 50,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#iDepartmentId").val().replace(/\s+/g,"");
						},
						InnerId : function() {
							return -1;
						},
						ColumnName : function() {
							return "DepartmentId";
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
			DepartmentName : {
				required : "请输入部门名称",
				maxlength : "最大不超过50个字符"
			},
			DepartmentId : {
				required : "请输入部门编号",
				maxlength : "最大不超过50个字符",
				remote : "部门编号重复,请重新输入"
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
			// 提交按钮设置为不可用，防止重复提交
			$("#insertSubmit").attr("disabled", true);
			$.ajax({
				url : "department/insert.html",
				type : "post",
				data : {
					DepartmentId : $("#iDepartmentId").val().replace(/\s+/g,""),
					DepartmentName : $("#iDepartmentName").val().replace(/\s+/g,""),
					CompanyInnerId : $("#iCompanyInnerId").val(),
					UpInnerId : $("#iUpInnerId").val(),
					DepartmentLevel : $("#iDepartmentLevel").val(),
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
						if ($("#table").treegrid("getSelected").DepartmentLevel == 1) {
							table();
						} else {
							$("#table").treegrid('reload', $("#table").treegrid("getSelected").UpInnerId);
						}
						$("#table").datagrid("clearSelections");
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
					$("#table").datagrid("clearSelections");
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
	$("#uDepartmentId").removeData("previousValue");
	$("#updateForm").validate({
		
		submitHandler : function() {
			// 提交按钮设置为不可用，防止重复提交
			$("#updateSubmit").attr("disabled", true);
			
			$.ajax({
				url : "depCon/update.html",
				type : "post",
				data : {
					Flag : 'Department',
					InnerId : $("#uDepartmentInnerId").val(),
					ConPower :$("#depConPower").combobox("getValue")
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
