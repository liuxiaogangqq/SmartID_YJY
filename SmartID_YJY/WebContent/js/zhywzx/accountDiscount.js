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
			Page : "accountDiscount"
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
	table();
};

$("#iBeginTime").focus(function() {
	WdatePicker({
		dateFmt : 'yyyy-MM-dd HH:mm:ss',
		maxDate : "#F{$dp.$D('iEndTime')}"
	});
});

$("#iEndTime").focus(function() {
	WdatePicker({
		dateFmt : 'yyyy-MM-dd HH:mm:ss',
		minDate : "#F{$dp.$D('iBeginTime')}"
	});
});

$("#uBeginTime").focus(function() {
	WdatePicker({
		dateFmt : 'yyyy-MM-dd HH:mm:ss',
		maxDate : "#F{$dp.$D('uEndTime')}"
	});
});

$("#uEndTime").focus(function() {
	WdatePicker({
		dateFmt : 'yyyy-MM-dd HH:mm:ss',
		minDate : "#F{$dp.$D('uBeginTime')}"
	});
});

$("#iUserTypeList").combobox({
	url : "userTypeBox/select.html",
	valueField : "id",
	textField : "text",
	editable : false,
});

$("#uUserTypeList").combobox({
	url : "userTypeBox/select.html",
	valueField : "id",
	textField : "text",
	editable : false,
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
		$("#uAccountDiscountInnerId").val(Table.AccountDiscountInnerId);
		$("#uAccountDiscountName").val(Table.AccountDiscountName);
		$("#uAccountDiscountId").val(Table.AccountDiscountId);
		$("#uUserTypeList").combobox("setValues", Table.UserTypeList);
		$("#uProportion").val(Table.Proportion);
		$("#uBeginTime").val(Table.BeginTime);
		$("#uEndTime").val(Table.EndTime);
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
		if (confirm("是否确认删除此优惠？")) {
			// 提交按钮设置为不可用，防止重复提交
			$("#delete").attr("disabled", true);
			$.ajax({
				url : "AccountDiscount/delete.html",
				type : "post",
				data : {
					AccountDiscountInnerId : Table.AccountDiscountInnerId
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
		url : "AccountDiscount/select.html", // 发送的url地址
		queryParams :
		// 参数列表
		{
			AccountDiscountId : $('#sAccountDiscountId').val(),
			AccountDiscountName : $('#sAccountDiscountName').val(),
			Remark : $('#sRemark').val(),
			StandbyA : $('#sStandbyA').val(),
			StandbyB : $('#sStandbyB').val(),
			StandbyC : $('#sStandbyC').val(),
			StandbyD : $('#sStandbyD').val()
		},
		method : 'post',
		idField : 'AccountDiscountInnerId',
		fitColumns : true,
		// 获取分页对象
		pagination : true,
		rownumbers : true,
		singleSelect : true,
		height : TableSize * 35 + 68,
		pageSize : TableSize,// 10 325,15 450,20 575,25 700
		pageList : [ 10, 15, 20, 25, 50, 100, 200, 500 ],
		columns : [ [ {
			field : 'AccountDiscountInnerId',
			hidden : true
		}, {
			field : 'UserTypeList',
			hidden : true
		}, {
			field : 'AccountDiscountId',
			title : '优惠编号',
			sortable : true,
			width : 150
		}, {
			field : 'AccountDiscountName',
			title : '优惠名称',
			sortable : true,
			width : 150
		}, {
			field : 'UserTypeListName',
			title : '适用人员',
			width : 150
		}, {
			field : 'Proportion',
			title : '优惠比例（%）',
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
			AccountDiscountName : {
				required : true,
				maxlength : 50,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#iAccountDiscountName").val();
						},
						InnerId : function() {
							return -1;
						},
						ColumnName : function() {
							return "AccountDiscountName";
						}
					}
				},
				isContainsSpecialChar : true,
			},
			AccountDiscountId : {
				required : true,
				maxlength : 50,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#iAccountDiscountId").val();
						},
						InnerId : function() {
							return -1;
						},
						ColumnName : function() {
							return "AccountDiscountId";
						}
					}
				},
				isContainsSpecialChar : true,
			},
			Proportion : {
				required : true,
				isIntGtZero : true,
				isInteger : true,
			},
			BeginTime : {
				required : true,
				maxlength : 50
			},
			EndTime : {
				required : true,
				maxlength : 50
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
			AccountDiscountName : {
				required : "请输入优惠名称",
				maxlength : "最大不超过50个字符",
				remote : "优惠名称重复,请重新输入"
			},
			AccountDiscountId : {
				required : "请输入优惠编号",
				maxlength : "最大不超过50个字符",
				remote : "优惠编号重复,请重新输入"
			},
			Proportion : {
				required : "请输入优惠比例",
				isIntGtZero : "必须是正整数",
				isInteger  : "必须是正整数",
			},
			BeginTime : {
				required : "请输入开始时间"
			},
			EndTime : {
				required : "请输入结束时间"
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
			if (!$("#iUserTypeList").combobox("getValue")) {
				showMsg("提示信息", "请选择适用人员", true);
				return false;
			}
			// 提交按钮设置为不可用，防止重复提交
			$("#insertSubmit").attr("disabled", true);
			$.ajax({
				url : "AccountDiscount/insert.html",
				type : "post",
				data : {
					AccountDiscountName : $("#iAccountDiscountName").val(),
					AccountDiscountId : $("#iAccountDiscountId").val(),
					UserTypeList : $("#iUserTypeList").combobox("getValues").toString(),
					Proportion : $("#iProportion").val()*100,
					BeginTime : $("#iBeginTime").val(),
					EndTime : $("#iEndTime").val(),
					StandbyA : $("#iStandbyA").val(),
					StandbyB : $("#iStandbyB").val(),
					StandbyC : $("#iStandbyC").val(),
					StandbyD : $("#iStandbyD").val(),
					Remark : $("#iRemark").val()
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

/*// 修改提交
$("#updateSubmit").click(function() {
	$("#updateForm").validate({
		rules : {
			AccountDiscountName : {
				required : true,
				maxlength : 50,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#uAccountDiscountName").val();
						},
						InnerId : function() {
							return $("#uAccountDiscountInnerId").val();
						},
						ColumnName : function() {
							return "AccountDiscountName";
						}
					}
				}
			},
			AccountDiscountId : {
				required : true,
				maxlength : 50,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#uAccountDiscountId").val();
						},
						InnerId : function() {
							return $("#uAccountDiscountInnerId").val();
						},
						ColumnName : function() {
							return "AccountDiscountId";
						}
					}
				}
			},
			BeginTime : {
				required : true,
				maxlength : 50
			},
			EndTime : {
				required : true,
				maxlength : 50
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
			AccountDiscountName : {
				required : "请输入优惠名称",
				maxlength : "最大不超过50个字符",
				remote : "优惠名称重复,请重新输入"
			},
			AccountDiscountId : {
				required : "请输入优惠编号",
				maxlength : "最大不超过50个字符",
				remote : "优惠编号重复,请重新输入"
			},
			BeginTime : {
				required : "请输入开始时间"
			},
			EndTime : {
				required : "请输入结束时间"
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
			$("#updateSubmit").attr("disabled", true);
			$.ajax({
				url : "AccountDiscount/update.html",
				type : "post",
				data : {
					AccountDiscountInnerId : $("#uAccountDiscountInnerId").val(),
					AccountDiscountName : $("#uAccountDiscountName").val(),
					AccountDiscountId : $("#uAccountDiscountId").val(),
					BeginTime : $("#uBeginTime").val(),
					EndTime : $("#uEndTime").val(),
					StandbyA : $("#uStandbyA").val(),
					StandbyB : $("#uStandbyB").val(),
					StandbyC : $("#uStandbyC").val(),
					StandbyD : $("#uStandbyD").val(),
					Remark : $("#uRemark").val()
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
});*/