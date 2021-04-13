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
			Page : "conDiscount"
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
};

$("#iUserTypeList").combobox({
	url : "userTypeBox/select.html",
	valueField : "id",
	textField : "text",
	editable : false,
	multiple : true,
});

$("#uUserTypeList").combobox({
	url : "userTypeBox/select.html",
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
	$("#insertDiv").modal("show");
});

// 点击修改时操作
$("#update").click(function() {
	var Table = $("#table").datagrid("getSelected");
	if (Table) {
		$("#uConDiscountInnerId").val(Table.ConDiscountInnerId);
		$("#uConDiscountName").val(Table.ConDiscountName);
		$("#uConDiscountId").val(Table.ConDiscountId);
		$("#uUserTypeList").combobox("setValues", Table.UserTypeList);
		$("#uProportion1").val(Table.Proportion1);
		$("#uMoney1").val(Table.Money1);
		$("#uProportion2").val(Table.Proportion2);
		$("#uMoney2").val(Table.Money2);
		$("#uProportion3").val(Table.Proportion3);
		$("#uMoney3").val(Table.Money3);
		$("#uProportion4").val(Table.Proportion4);
		$("#uMoney4").val(Table.Money4);
		$("#uProportion5").val(Table.Proportion5);
		$("#uMoney5").val(Table.Money5);
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
				url : "ConDiscount/delete.html",
				type : "post",
				data : {
					ConDiscountInnerId : Table.ConDiscountInnerId
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
		url : "ConDiscount/select.html", // 发送的url地址
		queryParams :
		// 参数列表
		{
			ConDiscountId : $('#sConDiscountId').val(),
			ConDiscountName : $('#sConDiscountName').val(),
			Remark : $('#sRemark').val(),
			StandbyA : $('#sStandbyA').val(),
			StandbyB : $('#sStandbyB').val(),
			StandbyC : $('#sStandbyC').val(),
			StandbyD : $('#sStandbyD').val()
		},
		method : 'post',
		idField : 'ConDiscountInnerId',
		fitColumns : true,
		// 获取分页对象
		pagination : true,
		rownumbers : true,
		singleSelect : true,
		height : TableSize * 35 + 68,
		pageSize : TableSize,// 10 325,15 450,20 575,25 700
		pageList : [ 10, 15, 20, 25, 50, 100, 200, 500 ],
		columns : [ [ {
			field : 'ConDiscountInnerId',
			hidden : true
		}, {
			field : 'UserTypeList',
			hidden : true
		}, {
			field : 'ConDiscountId',
			title : '优惠编号',
			sortable : true,
			width : 150
		}, {
			field : 'ConDiscountName',
			title : '优惠名称',
			sortable : true,
			width : 150
		}, {
			field : 'UserTypeListName',
			title : '适用人员',
			sortable : true,
			width : 150
		}, {
			field : 'Money1',
			title : '积分一',
			sortable : true,
			width : 150
		}, {
			field : 'Proportion1',
			title : '折扣一（%）',
			sortable : true,
			width : 150
		}, {
			field : 'Money2',
			title : '积分二',
			sortable : true,
			width : 150
		}, {
			field : 'Proportion2',
			title : '折扣二（%）',
			sortable : true,
			width : 150
		}, {
			field : 'Money3',
			title : '积分三',
			sortable : true,
			width : 150
		}, {
			field : 'Proportion3',
			title : '折扣三（%）',
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

// 大于
jQuery.validator.addMethod("gt", function(value, element, param) {
	var id = param[0];
	var target = $("#" + id + "").val();
	return Number(value) >= Number(target);
}, $.validator.format("{1}"));
// 小于
jQuery.validator.addMethod("lt", function(value, element, param) {
	var id = param[0];
	var target = $("#" + id + "").val();
	var max = Number(target);
	if (max == 0)
		return true;
	return Number(value) <= Number(target);
}, $.validator.format("{1}"));

// 添加提交
$("#insertSubmit").click(function() {
	$("#insertForm").validate({
		rules : {
			ConDiscountName : {
				required : true,
				maxlength : 50,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#iConDiscountName").val();
						},
						InnerId : function() {
							return -1;
						},
						ColumnName : function() {
							return "ConDiscountName";
						}
					}
				}
			},
			ConDiscountId : {
				required : true,
				maxlength : 50,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#iConDiscountId").val();
						},
						InnerId : function() {
							return -1;
						},
						ColumnName : function() {
							return "ConDiscountId";
						}
					}
				}
			},
			Proportion1 : {
				required : true,
				isIntGtZero : true,
				gt : [ "iProportion2", "此折扣太小" ],
				max:99
			},
			Money1 : {
				required : true,
				isIntGtZero : true,
				lt : [ "iMoney2", "此积分太大" ]
			},
			Proportion2 : {
				required : true,
				isIntGtZero : true,
				gt : [ "iProportion3", "此折扣太小" ],
				lt : [ "iProportion1", "此折扣太大" ]
			},
			Money2 : {
				required : true,
				isIntGtZero : true,
				gt : [ "iMoney1", "此积分太小" ],
				lt : [ "iMoney3", "此积分太大" ]
			},
			Proportion3 : {
				required : true,
				isIntGtZero : true,
				lt : [ "iProportion2", "此折扣太大" ]
			},
			Money3 : {
				required : true,
				isIntGtZero : true,
				gt : [ "iMoney2", "此积分太小" ],
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
			ConDiscountName : {
				required : "请输入优惠名称",
				maxlength : "最大不超过50个字符",
				remote : "优惠名称重复,请重新输入"
			},
			ConDiscountId : {
				required : "请输入优惠编号",
				maxlength : "最大不超过50个字符",
				remote : "优惠编号重复,请重新输入"
			},
			Proportion1 : {
				required : "请输入折扣一",
				isIntGtZero : "必须是正整数",
				max:"最大为99折"
			},
			Money1 : {
				required : "请输入积分一",
				isIntGtZero : "必须是正整数"
			},
			Proportion2 : {
				required : "请输入折扣二",
				isIntGtZero : "必须是正整数"
			},
			Money2 : {
				required : "请输入积分二",
				isIntGtZero : "必须是正整数"
			},
			Proportion3 : {
				required : "请输入折扣三",
				isIntGtZero : "必须是正整数"
			},
			Money3 : {
				required : "请输入积分三",
				isIntGtZero : "必须是正整数"
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
				url : "ConDiscount/insert.html",
				type : "post",
				data : {
					ConDiscountName : $("#iConDiscountName").val(),
					ConDiscountId : $("#iConDiscountId").val(),
					UserTypeList : $("#iUserTypeList").combobox("getValues").toString(),
					Proportion1 : $("#iProportion1").val() * 100,
					Money1 : $("#iMoney1").val() * 100,
					Proportion2 : $("#iProportion2").val() * 100,
					Money2 : $("#iMoney2").val() * 100,
					Proportion3 : $("#iProportion3").val() * 100,
					Money3 : $("#iMoney3").val() * 100,
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

// 修改提交
$("#updateSubmit").click(function() {
	$("#updateForm").validate({
		rules : {
			ConDiscountName : {
				required : true,
				maxlength : 50,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#uConDiscountName").val();
						},
						InnerId : function() {
							return $("#uConDiscountInnerId").val();
						},
						ColumnName : function() {
							return "ConDiscountName";
						}
					}
				}
			},
			ConDiscountId : {
				required : true,
				maxlength : 50,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#uConDiscountId").val();
						},
						InnerId : function() {
							return $("#uConDiscountInnerId").val();
						},
						ColumnName : function() {
							return "ConDiscountId";
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
			ConDiscountName : {
				required : "请输入优惠名称",
				maxlength : "最大不超过50个字符",
				remote : "优惠名称重复,请重新输入"
			},
			ConDiscountId : {
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
				url : "ConDiscount/update.html",
				type : "post",
				data : {
					ConDiscountInnerId : $("#uConDiscountInnerId").val(),
					ConDiscountName : $("#uConDiscountName").val(),
					ConDiscountId : $("#uConDiscountId").val(),
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
});