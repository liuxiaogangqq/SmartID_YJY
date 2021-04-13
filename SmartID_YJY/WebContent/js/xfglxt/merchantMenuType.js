// 加载时载入表格
window.onload = function() {
	initcombobox();
	table();
};

function initcombobox(){
	$("#iFEnableFlag").combobox({
		data:[{'id':0,'text':'启用'},{'id':1,'text':'禁用'}],
		valueField : "id",
		textField : "text",
		editable : false,
	});
	$("#uFEnableFlag").combobox({
		data:[{'id':0,'text':'启用'},{'id':1,'text':'禁用'}],
		valueField : "id",
		textField : "text",
		editable : false,
	});
	$("#iFMerchantInnerId").combotree({
		url : 'merchantAppTree/select.html',
		valueField : "id",
		textField : "text",
		editable : false,
	});
	$("#uFMerchantInnerId").combotree({
		url : 'merchantAppTree/select.html',
		valueField : "id",
		textField : "text",
		editable : false,
	});
	$("#iFEnableFlag").combobox('clear');
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
	table();
});

// 点击添加时弹出框
$("#insert").click(function() {
	initcombobox();
	$("#insertDiv").modal("show");
});

// 点击修改时操作
$("#update").click(function() {
	initcombobox();
	var Table = $("#table").datagrid("getSelected");
	if (Table) {
		$("#uFMenuTypeInnerId").val(Table.FMenuTypeInnerId);
		$("#uFMenuTypeName").val(Table.FMenuTypeName);
		$("#uFMenuTypeId").val(Table.FMenuTypeId);
		$("#uFMerchantInnerId").combotree("setValue","merchant"+Table.FMerchantInnerId);
		$("#uFEnableFlag").combobox("setValue", Table.FEnableFlag);
		$("#uFRemark").val(Table.FRemark);
		$("#updateDiv").modal("show");
	} else {
		showMsg("提示信息", "请选择一条数据进行操作");
	}
});

// 点击删除时操作
$("#delete").click(function() {
	var Table = $("#table").datagrid("getSelected");
	if (Table) {
		if (confirm("是否确认删除此菜类？")) {
			// 提交按钮设置为不可用，防止重复提交
			$("#delete").attr("disabled", true);
			$.ajax({
				url : "delete.html",
				type : "post",
				data : {
					InnerId : Table.FMenuTypeInnerId,
					TableName : "TCY_MenuType"
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
		url : "FMenuType/select.html", // 发送的url地址
		queryParams :
		// 参数列表
		{
			FMenuTypeId : $('#sFMenuTypeId').val(),
			FMenuTypeName : $('#sFMenuTypeName').val(),
			FRemark : $('#sFRemark').val()
		},
		method : 'post',
		idField : 'FMenuTypeInnerId',
		fitColumns : true,
		// 获取分页对象
		pagination : true,
		rownumbers : true,
		singleSelect : true,
		height:TableSize*35+68,
		pageSize : TableSize,// 10 325,15 450,20 575,25 700
		pageList : [ 10, 15, 20, 25, 50, 100, 200, 500 ],
		columns : [ [  {
			field : 'FMerchantInnerId',
			hidden : true
		},{
			field : 'FMenuTypeInnerId',
			hidden : true
		}, {
			field : 'FEnableFlag',
			hidden : true
		} ,{
			field : 'FMenuTypeId',
			title : '菜单编号',
			sortable : true,
			width : 120
		}, {
			field : 'FMenuTypeName',
			title : '菜单名称',
			sortable : true,
			width : 120
		}, {
			field : 'MerchantName',
			title : '所属商户',
			width : 120
		}, 
		{
			field : 'FEnableFlagName',
			title : '启用标识',
			width : 120
		},
		{
			field : 'FRemark',
			title : '备注',
			width : 100
		}] ],
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
	$("#iFMenuTypeName").removeData("previousValue");
	$("#iFMenuTypeId").removeData("previousValue");
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
			FMenuTypeName : {
				required : true,
				maxlength : 3,
				isContainsSpecialChar : true,
			},
			FMenuTypeId : {
				required : true,
				maxlength : 50,
				isContainsSpecialChar : true,
			},
			FRemark : {
				maxlength : 4000,
				isContainsSpecialChar : true,
			},
		},
		messages : {
			FMenuTypeName : {
				required : "请输入菜单类型名称",
				maxlength : "最大不超过3个字符",
			},
			FMenuTypeId : {
				required : "请输入菜单类型编号",
				maxlength : "最大不超过50个字符",
			},
			FRemark : {
				maxlength : "最大不超过4000个字符"
			},
		},
		submitHandler : function() {
			var MerchantInnerId = $("#iFMerchantInnerId").combotree("getValue");
			var fEnableFlag = $("#iFEnableFlag").combotree("getValue");
			if (!MerchantInnerId) {
				showMsg("提示信息", "请选择商户", true);
				return false;
			}
			if (MerchantInnerId.indexOf("app") >= 0||MerchantInnerId.indexOf("area") >= 0) {
				showMsg("提示信息", "请选择正确的商户", true);
				return false;
			}
			if(!fEnableFlag){
				showMsg("提示信息", "请选择启用标识", true);
				alert(fEnableFlag);
				return false;
			}
			// 提交按钮设置为不可用，防止重复提交
			$("#insertSubmit").attr("disabled", true);
			$.ajax({
				url : "menuType/insert.html",
				type : "post",
				data : {
					FEnableFlag : $("#iFEnableFlag").combobox("getValue").toString(),
					FMenuTypeName : $("#iFMenuTypeName").val().replace(/\s+/g,""),
					FMenuTypeId : $("#iFMenuTypeId").val().replace(/\s+/g,""),
					FMerchantInnerId : MerchantInnerId.substring(8, MerchantInnerId.length),
					FRemark : $("#iFRemark").val().replace(/\s+/g,"")
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
	$("#uFMenuTypeName").removeData("previousValue");
	$("#uFMenuTypeId").removeData("previousValue");
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
			FMenuTypeName : {
				required : true,
				maxlength : 3,
				isContainsSpecialChar : true,
			},
			FMenuTypeId : {
				required : true,
				maxlength : 50,
				isContainsSpecialChar : true,
			},
			FRemark : {
				maxlength : 4000,
				isContainsSpecialChar : true,
			},
		},
		messages : {
			FMenuTypeName : {
				required : "请输入菜单类型名称",
				maxlength : "最大不超过3个字符",
				remote : "菜单类型名称重复,请重新输入"
			},
			FMenuTypeId : {
				required : "请输入菜单类型编号",
				maxlength : "最大不超过50个字符",
				remote : "菜单类型编号重复,请重新输入"
			},
			FRemark : {
				maxlength : "最大不超过4000个字符"
			},
		},
		submitHandler : function() {
			var MerchantInnerId = $("#uFMerchantInnerId").combotree("getValue");
			if (!MerchantInnerId) {s
				showMsg("提示信息", "请选择商户", true);
				return false;
			}
			if (MerchantInnerId.indexOf("app") >= 0||MerchantInnerId.indexOf("area") >= 0) {
				showMsg("提示信息", "请选择正确的商户", true);
				return false;
			}
			// 提交按钮设置为不可用，防止重复提交
			$("#updateSubmit").attr("disabled", true);
			$.ajax({
				url : "menuType/update.html",
				type : "post",
				data : {
					FMenuTypeInnerId : $("#uFMenuTypeInnerId").val(),
					FMenuTypeId : $("#uFMenuTypeId").val().replace(/\s+/g,""),
					FMenuTypeName : $("#uFMenuTypeName").val().replace(/\s+/g,""),
					FRemark : $("#uFRemark").val().replace(/\s+/g,""),
					FMerchantInnerId :  MerchantInnerId.substring(8, MerchantInnerId.length),
					FEnableFlag : $("#uFEnableFlag").combobox("getValue").toString()
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
