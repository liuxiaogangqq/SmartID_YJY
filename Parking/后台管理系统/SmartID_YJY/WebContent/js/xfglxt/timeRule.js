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

	$("#iMerchantInnerId").combotree({
		url : "merchantAppTree/select.html",
		editable : false,
	});

	$("#uMerchantInnerId").combotree({
		url : "merchantAppTree/select.html",
		editable : false,
	});
}
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
	$("#table").datagrid({pageNumber:1});
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
		$("#uTimeRuleInnerId").val(Table.TimeRuleInnerId);
		$("#uTimeRuleName").val(Table.TimeRuleName);
		$("#uTimeRuleId").val(Table.TimeRuleId);
		$("#uMerchantInnerId").combotree("setValue", "merchant" + Table.MerchantInnerId);
		$("#uMinutes").val(Table.Minutes);
		$("#uMoney").val(Table.Money);
		$("#uIntpart").val(Table.Intpart);
		$("#uTimeout").val(Table.Timeout);
		$("#uMinMoney").val(Table.MinMoney);
		$("#uFreetime").val(Table.Freetime);
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
		url : "TimeRule/select.html", // 发送的url地址
		queryParams :
		// 参数列表
		{
			TimeRuleId : $('#sTimeRuleId').val(),
			TimeRuleName : $('#sTimeRuleName').val()
		},
		method : 'post',
		idField : 'TimeRuleInnerId',
		fitColumns : true,
		// 获取分页对象
		pagination : true,
		rownumbers : true,
		singleSelect : true,
		height : TableSize * 35 + 68,
		pageSize : TableSize,// 10 325,15 450,20 575,25 700
		pageList : [ 10, 15, 20, 25, 50, 100, 200, 500 ],
		columns : [ [ {
			field : 'TimeRuleInnerId',
			hidden : true
		}, {
			field : 'Intpart',
			hidden : true
		}, {
			field : 'MerchantInnerId',
			hidden : true
		}, {
			field : 'TimeRuleId',
			title : '规则编号',
			sortable : true,
			width : 150
		}, {
			field : 'TimeRuleName',
			title : '规则名称',
			sortable : true,
			width : 150
		}, {
			field : 'MerchantName',
			title : '商户',
			sortable : true,
			width : 150
		}, {
			field : 'Minutes',
			title : '计时单位（分钟）',
			sortable : true,
			width : 150
		}, {
			field : 'Money',
			title : '单位费用',
			sortable : true,
			width : 150
		}, {
			field : 'IntpartName',
			title : '取整方法',
			sortable : true,
			width : 150
		}, {
			field : 'Timeout',
			title : '超时时间（分钟）',
			sortable : true,
			width : 150
		}, {
			field : 'MinMoney',
			title : '最小进入金额',
			sortable : true,
			width : 150
		}, {
			field : 'Freetime',
			title : '免费时间（分钟）',
			sortable : true,
			width : 150
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
		rules : {
			TimeRuleName : {
				required : true,
				maxlength : 50,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#iTimeRuleName").val();
						},
						InnerId : function() {
							return -1;
						},
						ColumnName : function() {
							return "TimeRuleName";
						}
					}
				}
			},
			TimeRuleId : {
				required : true,
				maxlength : 50,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#iTimeRuleId").val();
						},
						InnerId : function() {
							return -1;
						},
						ColumnName : function() {
							return "TimeRuleId";
						}
					}
				}
			},
			Minutes : {
				required : true,
				isIntGteZero : true
			},
			Money : {
				required : true,
				isIntGteZero : true
			},
			Intpart : {
				required : true
			},
			Timeout : {
				required : true,
				isIntGteZero : true
			},
			MinMoney : {
				required : true,
				isIntGteZero : true
			},
			Freetime : {
				required : true,
				isIntGteZero : true
			}
		},
		messages : {
			TimeRuleName : {
				required : "请输入规则名称",
				maxlength : "最大不超过50个字符",
				remote : "规则名称重复,请重新输入"
			},
			TimeRuleId : {
				required : "请输入规则编号",
				maxlength : "最大不超过50个字符",
				remote : "规则编号重复,请重新输入"
			},
			Minutes : {
				required : "请输入计时单位",
				isIntGtZero : "必须是大于或等于0的整数"
			},
			Money : {
				required : "请输入单位费用",
				isIntGtZero : "必须是大于或等于0的整数"
			},
			Intpart : {
				required : "请输入取整方法"
			},
			Timeout : {
				required : "请输入超时时间",
				isIntGtZero : "必须是大于或等于0的整数"
			},
			MinMoney : {
				required : "请输入最小进入金额",
				isIntGtZero : "必须是大于或等于0的整数"
			},
			Freetime : {
				required : "请输入免费时间",
				isIntGtZero : "必须是大于或等于0的整数"
			}
		},
		submitHandler : function() {
			var MerchantInnerId = $("#iMerchantInnerId").combotree("getValue");
			if (!MerchantInnerId) {
				showMsg("提示信息", "请选择商户", true);
				return false;
			}
			if (!(MerchantInnerId.indexOf("merchant") >= 0)) {
				showMsg("提示信息", "只能选择商户", true);
				return false;
			}
			// 提交按钮设置为不可用，防止重复提交
			$("#insertSubmit").attr("disabled", true);
			$.ajax({
				url : "TimeRule/insert.html",
				type : "post",
				data : {
					TimeRuleName : $("#iTimeRuleName").val(),
					TimeRuleId : $("#iTimeRuleId").val(),
					MerchantInnerId : MerchantInnerId.substring(8, MerchantInnerId.length),
					Minutes : $("#iMinutes").val(),
					Money : $("#iMoney").val() * 100,
					Intpart : $("#iIntpart").val(),
					Timeout : $("#iTimeout").val(),
					MinMoney : $("#iMinMoney").val(),
					Freetime : $("#iFreetime").val()
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
			TimeRuleName : {
				required : true,
				maxlength : 50,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#uTimeRuleName").val();
						},
						InnerId : function() {
							return $("#uTimeRuleInnerId").val();
						},
						ColumnName : function() {
							return "TimeRuleName";
						}
					}
				}
			},
			TimeRuleId : {
				required : true,
				maxlength : 50,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#uTimeRuleId").val();
						},
						InnerId : function() {
							return $("#uTimeRuleInnerId").val();
						},
						ColumnName : function() {
							return "TimeRuleId";
						}
					}
				}
			},
			Minutes : {
				required : true,
				isIntGteZero : true
			},
			Money : {
				required : true,
				isIntGteZero : true
			},
			Intpart : {
				required : true
			},
			Timeout : {
				required : true,
				isIntGteZero : true
			},
			MinMoney : {
				required : true,
				isIntGteZero : true
			},
			Freetime : {
				required : true,
				isIntGteZero : true
			}
		},
		messages : {
			TimeRuleName : {
				required : "请输入规则名称",
				maxlength : "最大不超过50个字符",
				remote : "规则名称重复,请重新输入"
			},
			TimeRuleId : {
				required : "请输入规则编号",
				maxlength : "最大不超过50个字符",
				remote : "规则编号重复,请重新输入"
			},
			Minutes : {
				required : "请输入计时单位",
				isIntGtZero : "必须是大于或等于0的整数"
			},
			Money : {
				required : "请输入单位费用",
				isIntGtZero : "必须是大于或等于0的整数"
			},
			Intpart : {
				required : "请输入取整方法"
			},
			Timeout : {
				required : "请输入超时时间",
				isIntGtZero : "必须是大于或等于0的整数"
			},
			MinMoney : {
				required : "请输入最小进入金额",
				isIntGtZero : "必须是大于或等于0的整数"
			},
			Freetime : {
				required : "请输入免费时间",
				isIntGtZero : "必须是大于或等于0的整数"
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
			// 提交按钮设置为不可用，防止重复提交
			$("#updateSubmit").attr("disabled", true);
			$.ajax({
				url : "TimeRule/update.html",
				type : "post",
				data : {
					TimeRuleInnerId : $("#uTimeRuleInnerId").val(),
					TimeRuleName : $("#uTimeRuleName").val(),
					TimeRuleId : $("#uTimeRuleId").val(),
					MerchantInnerId : MerchantInnerId.substring(8, MerchantInnerId.length),
					Minutes : $("#uMinutes").val(),
					Money : $("#uMoney").val() * 100,
					Intpart : $("#uIntpart").val(),
					Timeout : $("#uTimeout").val(),
					MinMoney : $("#uMinMoney").val(),
					Freetime : $("#uFreetime").val()
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