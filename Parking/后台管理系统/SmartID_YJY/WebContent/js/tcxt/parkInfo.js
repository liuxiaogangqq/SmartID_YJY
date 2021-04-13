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

// 点击查询时更新表格
$("#select").click(function() {
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
		$("#uParkInfoInnerId").val(Table.ParkInfoInnerId);
		$("#uParkInfoName").val(Table.ParkInfoName);
		$("#uParkInfoId").val(Table.ParkInfoId);
		$("#uCarsNumber").val(Table.CarsNumber);
		$("#uVisNumber").val(Table.VisNumber);
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
		if (confirm("是否确认删除此车场信息？")) {
			// 提交按钮设置为不可用，防止重复提交
			$("#delete").attr("disabled", true);
			$.ajax({
				url : "delete.html",
				type : "post",
				data : {
					InnerId : Table.ParkInfoInnerId,
					TableName : "Park_Info"
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
	$("#table").datagrid("clearSelections");
	$("#table").datagrid({
		loadMsg : "正在加载数据，请稍等.....", // 在加载页面时显示数据
		url : "parkInfo/select.html", // 发送的url地址
		queryParams :
		// 参数列表
		{
			ParkInfoId : $('#sParkInfoId').val(),
			ParkInfoName : $('#sParkInfoName').val(),
			Remark : $('#sRemark').val(),
		},
		method : 'post',
		idField : 'ParkInfoInnerId',
		fitColumns : true,
		// 获取分页对象
		pagination : true,
		rownumbers : true,
		singleSelect : true,
		height : TableSize * 35 + 68,
		pageSize : TableSize,// 10 325,15 450,20 575,25 700
		pageList : [ 10, 15, 20, 25, 50, 100, 200, 500 ],
		columns : [ [ {
			field : 'ParkInfoInnerId',
			hidden : true
		}, {
			field : 'ParkInfoId',
			title : '车场编号',
			sortable : true,
			width : 150
		}, {
			field : 'ParkInfoName',
			title : '车场名称',
			sortable : true,
			width : 150
		}, {
			field : 'CarsNumber',
			title : '临时车位总数',
			sortable : true,
			width : 150
		}, {
			field : 'VisNumber',
			title : '访客车位总数',
			sortable : true,
			width : 150
		},{
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
	$("#iParkInfoName").removeData("previousValue");
    $("#iParkInfoId").removeData("previousValue");
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
			ParkInfoName : {
				required : true,
				maxlength : 50,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#iParkInfoName").val().replace(/\s+/g,"");
						},
						InnerId : function() {
							return -1;
						},
						ColumnName : function() {
							return "ParkInfoName";
						}
					}
				},
				isContainsSpecialChar : true,
			},
			ParkInfoId : {
				required : true,
				maxlength : 50,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#iParkInfoId").val().replace(/\s+/g,"");
						},
						InnerId : function() {
							return -1;
						},
						ColumnName : function() {
							return "ParkInfoId";
						}
					}
				},
				isContainsSpecialChar : true,
			},
			CarsNumber : {
				required : true,
			},
			VisNumber : {
				required : true,
			},
			Remark : {
				maxlength : 4000,
				isContainsSpecialChar : true,
			}
		},
		messages : {
			ParkInfoName : {
				required : "请输入车位名称",
				maxlength : "最大不超过50个字符",
				remote : "车位名称重复,请重新输入"
			},
			ParkInfoId : {
				required : "请输入车位编号",
				maxlength : "最大不超过50个字符",
				remote : "车位编号重复,请重新输入"
			},
			CarsNumber : {
				required : "请输入临时车位数",
			},
			VisNumber : {
				required : "请输入访客车位数",
			},
			Remark : {
				maxlength : "最大不超过4000个字符"
			}
		},
		submitHandler : function() {
			// 提交按钮设置为不可用，防止重复提交
			$("#insertSubmit").attr("disabled", true);
			$.ajax({
				url : "parkInfo/insert.html",
				type : "post",
				dataType : "json",
				data : {
					ParkInfoName : $("#iParkInfoName").val().replace(/\s+/g,""),
					ParkInfoId : $("#iParkInfoId").val().replace(/\s+/g,""),
					CarsNumber : $("#iCarsNumber").val().replace(/\s+/g,""),
					CurrentCarNumber : $("#iCarsNumber").val().replace(/\s+/g,""),
					VisNumber : $("#iVisNumber").val().replace(/\s+/g,""),
					CurrentVisNumber : $("#iVisNumber").val().replace(/\s+/g,""),
					Remark : $("#iRemark").val().replace(/\s+/g,""),
				},
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
	$("#uParkInfoName").removeData("previousValue");
    $("#uParkInfoId").removeData("previousValue");
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
			ParkInfoName : {
				required : true,
				maxlength : 50,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#uParkInfoName").val().replace(/\s+/g,"");
						},
						InnerId : function() {
							return $("#uParkInfoInnerId").val();
						},
						ColumnName : function() {
							return "ParkInfoName";
						}
					}
				},
				isContainsSpecialChar : true,
			},
			ParkInfoId : {
				required : true,
				maxlength : 50,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#uParkInfoId").val().replace(/\s+/g,"");
						},
						InnerId : function() {
							return $("#uParkInfoInnerId").val();
						},
						ColumnName : function() {
							return "ParkInfoId";
						}
					}
				},
				isContainsSpecialChar : true,
			},
			CarsNumber : {
				required : true,
			},
			VisNumber : {
				required : true,
			},
			Remark : {
				maxlength : 4000,
				isContainsSpecialChar : true,
			}
		},
		messages : {
			ParkInfoName : {
				required : "请输入停车场名称",
				maxlength : "最大不超过50个字符",
				remote : "停车场名称重复,请重新输入"
			},
			ParkInfoId : {
				required : "请输入停车场编号",
				maxlength : "最大不超过50个字符",
				remote : "停车场编号重复,请重新输入"
			},
			CarsNumber : {
				required : "请输入临时车位数",
			},
			VisNumber : {
				required : "请输入访客车位数",
			},
			Remark : {
				maxlength : "最大不超过4000个字符"
			}
		},
		submitHandler : function() {
			// 提交按钮设置为不可用，防止重复提交
			$("#updateSubmit").attr("disabled", true);
			$.ajax({
				url : "parkInfo/update.html",
				type : "post",
				data : {
					ParkInfoInnerId : $("#uParkInfoInnerId").val(),
					ParkInfoName : $("#uParkInfoName").val().replace(/\s+/g,""),
					ParkInfoId : $("#uParkInfoId").val().replace(/\s+/g,""),
					CarsNumber : $("#uCarsNumber").val().replace(/\s+/g,""),
					VisNumber : $("#uVisNumber").val().replace(/\s+/g,""),
					Remark : $("#uRemark").val().replace(/\s+/g,""),
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
