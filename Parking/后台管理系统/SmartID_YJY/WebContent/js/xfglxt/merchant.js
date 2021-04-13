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
			Page : "merchant"
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
};

$("#iAppInnerId").combotree({
	url : "areaAppTree/select.html?AppTypeInnerId=1",
	editable : false
});
$("#iOperatorInerId").combobox({
	url : "operatorBox/select.html",
	valueField : "id",
	textField : "text",
	editable : false,
	multiple : true,
});

$("#uAppInnerId").combotree({
	url : "areaAppTree/select.html?AppTypeInnerId=1",
	editable : false
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
		$("#uMerchantInnerId").val(Table.MerchantInnerId);
		$("#uMerchantName").val(Table.MerchantName);
		$("#uMerchantId").val(Table.MerchantId);
		$("#uMerchantType").val(Table.MerchantType);
		/*$("#uProportion").val(Table.Proportion);*/
		$("#uAppInnerId").combotree("setValue","app"+Table.AppInnerId);
		$("#uStandbyA").val(Table.StandbyA);
		$("#uStandbyB").val(Table.StandbyB);
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
		if (confirm("是否确认删除此商户？")) {
			// 提交按钮设置为不可用，防止重复提交
			$("#delete").attr("disabled", true);
			$.ajax({
				url : "delete.html",
				type : "post",
				data : {
					InnerId : Table.MerchantInnerId,
					TableName : "Consumption_Merchant"
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
		url : "merchant/select.html", // 发送的url地址
		queryParams :
		// 参数列表
		{
			MerchantId : $('#sMerchantId').val(),
			MerchantName : $('#sMerchantName').val(),
			Remark : $('#sRemark').val(),
			StandbyA : $('#sStandbyA').val(),
			StandbyB : $('#sStandbyB').val()
		},
		method : 'post',
		idField : 'MerchantInnerId',
		fitColumns : true,
		// 获取分页对象
		pagination : true,
		rownumbers : true,
		singleSelect : true,
		height:TableSize*35+68,
		pageSize : TableSize,// 10 325,15 450,20 575,25 700
		pageList : [ 10, 15, 20, 25, 50, 100, 200, 500 ],
		columns : [ [ {
			field : 'MerchantInnerId',
			hidden : true
		}, {
			field : 'AppInnerId',
			hidden : true
		}, {
			field : 'AreaInnerId',
			hidden : true
		}, {
			field : 'MerchantType',
			hidden : true
		}, {
			field : 'MerchantId',
			title : '商户编号',
			sortable : true,
			width : 120
		}, {
			field : 'MerchantName',
			title : '商户名称',
			sortable : true,
			width : 120
		}, /*{
			field : 'Proportion',
			title : '折扣比例',
			sortable : true,
			width : 120
		},*/ {
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
			field : 'MerchantTypeName',
			title : '商户类型',
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
	$("#iMerchantName").removeData("previousValue");
	$("#iMerchantId").removeData("previousValue");
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
			MerchantName : {
				required : true,
				maxlength : 50,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#iMerchantName").val().replace(/\s+/g,"");
						},
						InnerId : function() {
							return -1;
						},
						ColumnName : function() {
							return "MerchantName";
						}
					}
				},
				isContainsSpecialChar : true,
			},
			MerchantId : {
				required : true,
				maxlength : 50,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#iMerchantId").val().replace(/\s+/g,"");
						},
						InnerId : function() {
							return -1;
						},
						ColumnName : function() {
							return "MerchantId";
						}
					}
				},
				isContainsSpecialChar : true,
			},
			Proportion:{
				required : true,
				isIntGtZero : true,
				isInteger : true,
				max:100
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
			MerchantName : {
				required : "请输入商户名称",
				maxlength : "最大不超过50个字符",
				remote : "商户名称重复,请重新输入"
			},
			MerchantId : {
				required : "请输入商户编号",
				maxlength : "最大不超过50个字符",
				remote : "商户编号重复,请重新输入"
			},
			Proportion : {
				required : "请输入折扣比例",
				isIntGtZero : "必须是正整数",
				isInteger  : "必须是正整数",
				max:"最大为100"
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
			var AppInnerId = $("#iAppInnerId").combotree("getValue");
			var operatorInerId = $("#iOperatorInerId").combotree("getValue");
			if (!AppInnerId) {
				showMsg("提示信息", "请选择应用", true);
				return false;
			}
			if (AppInnerId.indexOf("area") >= 0) {
				showMsg("提示信息", "请选择正确的应用", true);
				return false;
			}
			if($("#iMerchantType").val() == ""){
				showMsg("提示信息", "请选择商户类型", true);
				return false;
			}
			if (!operatorInerId) {
				showMsg("提示信息", "请选择操作员", true);
				return false;
			}
			// 提交按钮设置为不可用，防止重复提交
			$("#insertSubmit").attr("disabled", true);
			$.ajax({
				url : "merchant/insert.html",
				type : "post",
				data : {
					OperatorInerId : $("#iOperatorInerId").combobox("getValues").toString(),
					MerchantName : $("#iMerchantName").val().replace(/\s+/g,""),
					MerchantId : $("#iMerchantId").val().replace(/\s+/g,""),
					MerchantType : $("#iMerchantType").val(),
					AppInnerId : AppInnerId.substring(3, AppInnerId.length),
					StandbyA : $("#iStandbyA").val().replace(/\s+/g,""),
					StandbyB : $("#iStandbyB").val().replace(/\s+/g,""),
					Proportion : 100,
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
	$("#uMerchantName").removeData("previousValue");
	$("#uMerchantId").removeData("previousValue");
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
			MerchantName : {
				required : true,
				maxlength : 50,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#uMerchantName").val().replace(/\s+/g,"");
						},
						InnerId : function() {
							return $("#uMerchantInnerId").val();
						},
						ColumnName : function() {
							return "MerchantName";
						}
					}
				},
				isContainsSpecialChar : true,
			},
			MerchantId : {
				required : true,
				maxlength : 50,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#uMerchantId").val().replace(/\s+/g,"");
						},
						InnerId : function() {
							return $("#uMerchantInnerId").val();
						},
						ColumnName : function() {
							return "MerchantId";
						}
					}
				},
				isContainsSpecialChar : true,
			},
			Proportion:{
				required : true,
				isIntGtZero : true,
				isInteger : true,
				max:100
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
			MerchantName : {
				required : "请输入商户名称",
				maxlength : "最大不超过50个字符",
				remote : "商户名称重复,请重新输入"
			},
			MerchantId : {
				required : "请输入商户编号",
				maxlength : "最大不超过50个字符",
				remote : "商户编号重复,请重新输入"
			},
			Proportion : {
				required : "请输入折扣比例",
				isIntGtZero : "必须是正整数",
				isInteger  : "必须是正整数",
				max:"最大为100"
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
			var AppInnerId = $("#uAppInnerId").combotree("getValue");
		
			if (!AppInnerId) {
				showMsg("提示信息", "请选择应用", true);
				return false;
			}
			if (AppInnerId.indexOf("area") >= 0) {
				showMsg("提示信息", "请选择正确的应用", true);
				return false;
			}
			if($("#iMerchantType").val() == ""){
				showMsg("提示信息", "请选择商户类型", true);
				return false;
			}
			
			// 提交按钮设置为不可用，防止重复提交
			$("#updateSubmit").attr("disabled", true);
			$.ajax({
				url : "merchant/update.html",
				type : "post",
				data : {
					MerchantInnerId : $("#uMerchantInnerId").val(),
					MerchantName : $("#uMerchantName").val().replace(/\s+/g,""),
					MerchantId : $("#uMerchantId").val().replace(/\s+/g,""),
					MerchantType : $("#uMerchantType").val(),
					AppInnerId : AppInnerId.substring(3, AppInnerId.length),
					StandbyA : $("#uStandbyA").val().replace(/\s+/g,""),
					StandbyB : $("#uStandbyB").val().replace(/\s+/g,""),
					Proportion : 100,
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
