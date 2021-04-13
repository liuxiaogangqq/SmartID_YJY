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
var OperatorPassword;

// 加载时载入表格
window.onload = function() {
	$.ajax({
		url : "systemConfigureById/select.html",
		type : "post",
		async : false,
		data : {
			ConfigureId : "OperatorPassword"
		},
		dataType : "json",
		success : function(data) {
			OperatorPassword = data.ConfigureValue;
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
	// 请求后台，获得配置参数
	$.ajax({
		url : "configure/select.html",
		type : "post",
		async : false,
		data : {
			Page : "operator"
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
	$("#iMerchantInnerId").combotree({
		url : "merchantAppTree/select.html",
		editable : false,
		multiple:true,
	});
	$("#uMerchantInnerId").combotree({
		url : "merchantAppTree/select.html",
		editable : false,
		multiple:true,
	});
	table();
};

$("#iAreaList").combotree({
	url : "areaAppTree/select.html",
	editable : false,
	multiple : true
});
$("#uAreaList").combotree({
	url : "areaAppTree/select.html",
	editable : false,
	multiple : true
});
$("#iCompanyList").combotree({
	url : "companyDepartmentTree/select.html",
	editable : false,
	multiple : true
});
$("#uCompanyList").combotree({
	url : "companyDepartmentTree/select.html",
	editable : false,
	multiple : true
});
$("#iPageList").combotree({
	url : "pageRightTree/select.html",
	editable : false,
	multiple : true
});
$("#uPageList").combotree({
	url : "pageRightTree/select.html",
	editable : false,
	multiple : true
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
		$("#uOperatorInnerId").val(Table.OperatorInnerId);
		$("#uOperatorName").val(Table.OperatorName);
		$("#uOperatorId").val(Table.OperatorId);
		$("#uTableSize").val(Table.TableSize);
		
		if(Table.AppList!=null){
			var AppList = Table.AppList.split(",");
			var AreaAppList = "";
			$.each(AppList, function(i, k) {
				AreaAppList += "app" + AppList[i] + ",";
			});
			$("#uAreaList").combotree("setValues", AreaAppList);
		}
		
		if(Table.DepartmentList!=null){
			var DepartmentList = Table.DepartmentList.split(",");
			var CompanyList = "";
			$.each(DepartmentList, function(i, k) {
				CompanyList += "department" + DepartmentList[i] + ",";
			});
			$("#uCompanyList").combotree("setValues", CompanyList);
		}

		if(Table.PageList!=null){
			var Pages = Table.PageList.split(",");
			var PageList = "";
			$.each(Pages, function(i, k) {
				if(Pages[i] == 1 || Pages[i] ==2 || Pages[i] == 3 || Pages[i] == 5){
					
				}else{
					PageList += Pages[i] + ",";
				}
			});
			$("#uPageList").combotree("setValues", PageList);
		}
		if(Table.MerchantList!=null){
			var MerchantInnerId = "";
			var MerchantList = Table.MerchantList.split(",");
			$.each(MerchantList, function(i, k) {
				MerchantInnerId += "merchant" + MerchantList[i] + ",";
			});
			$("#uMerchantInnerId").combotree("setValues", MerchantInnerId);
		}
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
		if (confirm("是否确认删除此操作员？")) {
			// 提交按钮设置为不可用，防止重复提交
			$("#delete").attr("disabled", true);
			$.ajax({
				url : "delete.html",
				type : "post",
				data : {
					InnerId : Table.OperatorInnerId,
					TableName : "System_Operator"
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

// 点击重置时操作
$("#reset").click(function() {
	var Table = $("#table").datagrid("getSelected");
	if (Table) {
		if (confirm("是否确认重置此操作员密码？")) {
			// 提交按钮设置为不可用，防止重复提交
			$("#reset").attr("disabled", true);
			$.ajax({
				url : "operator/updatePWD.html",
				type : "post",
				data : {
					OperatorInnerId : Table.OperatorInnerId,
					Password : Table.Password,
					PasswordNew : OperatorPassword
				},
				dataType : "json",
				success : function(data) {
					// 提交按钮设置为可用
					$("#reset").removeAttr("disabled");
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
					$("#reset").removeAttr("disabled");
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
	$("#table").datagrid({
		loadMsg : "正在加载数据，请稍等.....", // 在加载页面时显示数据
		url : "operator/select.html", // 发送的url地址
		queryParams :
		// 参数列表
		{
			OperatorId : $('#sOperatorId').val(),
			OperatorName : $('#sOperatorName').val(),
			Remark : $('#sRemark').val(),
			StandbyA : $('#sStandbyA').val(),
			StandbyB : $('#sStandbyB').val(),
			StandbyC : $('#sStandbyC').val(),
			StandbyD : $('#sStandbyD').val()
		},
		method : 'post',
		idField : 'OperatorInnerId',
		fitColumns : true,
		// 获取分页对象
		pagination : true,
		rownumbers : true,
		singleSelect : true,
		height : TableSize * 35 + 68,
		pageSize : TableSize,// 10 325,15 450,20 575,25 700
		pageList : [ 10, 15, 20, 25, 50, 100, 200, 500 ],
		columns : [ [ {
			field : 'OperatorInnerId',
			hidden : true
		}, {
			field : 'Password',
			hidden : true
		}, {
			field : 'PageList',
			hidden : true
		}, {
			field : 'AreaList',
			hidden : true
		}, {
			field : 'AppList',
			hidden : true
		}, {
			field : 'CompanyList',
			hidden : true
		}, {
			field : 'DepartmentList',
			hidden : true
		}, {
			field : 'MerchantList',
			hidden : true
		},{
			field : 'OperatorId',
			title : '操作员登录名',
			sortable : true,
			width : 150
		}, {
			field : 'OperatorName',
			title : '操作员姓名',
			sortable : true,
			width : 150
		}, {
			field : 'TableSize',
			title : '表格行数',
			sortable : true,
			width : 150
		},  {
			field : 'MerchantNameList',
			title : '商户权限',
			sortable : true,
			width : 150
		}, {
			field : 'PageRightNameList',
			title : '页面权限',
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
		onLoadError : function() {
			showMsg("提示信息", "服务发生异常，请重试", true);
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
	$("#iOperatorName").removeData("previousValue");
    $("#iOperatorId").removeData("previousValue");
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
			OperatorName : {
				required : true,
				maxlength : 50,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#iOperatorName").val().replace(/\s+/g,"");
						},
						InnerId : function() {
							return -1;
						},
						ColumnName : function() {
							return "OperatorName";
						}
					}
				},
				isContainsSpecialChar : true,
			},
			OperatorId : {
				required : true,
				maxlength : 50,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#iOperatorId").val().replace(/\s+/g,"");
						},
						InnerId : function() {
							return -1;
						},
						ColumnName : function() {
							return "OperatorId";
						}
					}
				},
				isContainsSpecialChar : true,
			},
			TableSize : {
				required : true
			},
			Password : {
				required : true
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
			OperatorName : {
				required : "请输入操作员姓名",
				maxlength : "最大不超过50个字符",
				remote : "操作员姓名重复,请重新输入"
			},
			OperatorId : {
				required : "请输入操作员登录名",
				maxlength : "最大不超过50个字符",
				remote : "操作员登录名重复,请重新输入"
			},
			TableSize : {
				required : "请选择表格行数"
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
			var AreaList = "";
			var AppList = "";
			var tree = $("#iAreaList").combotree('tree'); // 得到树对象
			var arry = tree.tree('getChecked', [ 'checked', 'indeterminate' ]);
			$.each(arry, function(i, k) {
				if (arry[i].id.indexOf("area") >= 0) {
					AreaList += arry[i].id.substring(4, arry[i].id.length) + ",";
				}
				if (arry[i].id.indexOf("app") >= 0) {
					AppList += arry[i].id.substring(3, arry[i].id.length) + ",";
				}
			});

			/*var CompanyList = "";*/
			/*var DepartmentList = "";*/
			/*var companyTree = $("#iCompanyList").combotree('tree'); // 得到树对象
			var companyArry = companyTree.tree('getChecked', [ 'checked', 'indeterminate' ]);
			$.each(companyArry, function(i, k) {
				if (companyArry[i].id.indexOf("company") >= 0) {
					CompanyList += companyArry[i].id.substring(7, companyArry[i].id.length) + ",";
				}
			});*/
			/*companyArry = companyTree.tree('getChecked', [ 'checked' ]);
			$.each(companyArry, function(i, k) {
				if (companyArry[i].id.indexOf("department") >= 0) {
					DepartmentList += companyArry[i].id.substring(10, companyArry[i].id.length) + ",";
				}
			});*/

			var PageList = "";
			var pageTree = $("#iPageList").combotree('tree'); // 得到树对象
			var pageArry = pageTree.tree('getChecked', ['checked','indeterminate']);
			$.each(pageArry, function(i, k) {
				PageList += pageArry[i].id + ",";
			});
			var MerchantInnerId = "";
			var tree = $("#iMerchantInnerId").combotree('tree'); // 得到树对象
			var arry = tree.tree('getChecked', [ 'checked', 'indeterminate' ]);
			$.each(arry, function(i, k) {
				if (arry[i].id.indexOf("merchant") >= 0) {
					MerchantInnerId += arry[i].id.substring(8, arry[i].id.length) + ",";
				}
			});
		    /*var telStr = /^[1](([3][0-9])|([4][5-9])|([5][0-3,5-9])|([6][5,6])|([7][0-8])|([8][0-9])|([9][1,8,9]))[0-9]{8}$/;
		    if (!(telStr.test($("#iOperatorId").val().replace(/\s+/g,"")))) {
		    	showMsg("提示信息", "登录名称必须为合法手机号，请重新输入" ,true);
		    	return false;
		    }*/
			
			// 提交按钮设置为不可用，防止重复提交
			$("#insertSubmit").attr("disabled", true);
			$.ajax({
				url : "operator/insert.html",
				type : "post",
				data : {
					OperatorName : $("#iOperatorName").val().replace(/\s+/g,""),
					OperatorId : $("#iOperatorId").val().replace(/\s+/g,""),
					Password : OperatorPassword,
					PageList : PageList,
					AreaList : AreaList,
					AppList : AppList,
					/*CompanyList : CompanyList,
					DepartmentList : DepartmentList,*/
					MerchantInnerId : MerchantInnerId,
					TableSize : $("#iTableSize").val(),
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
						$("#iAreaList").combotree("clear");
						$("#iCompanyList").combotree("clear");
						$("#iPageList").combotree("clear");
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
	$("#uOperatorName").removeData("previousValue");
    $("#uOperatorId").removeData("previousValue");
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
			OperatorName : {
				required : true,
				maxlength : 50,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#uOperatorName").val().replace(/\s+/g,"");
						},
						InnerId : function() {
							return $("#uOperatorInnerId").val();
						},
						ColumnName : function() {
							return "OperatorName";
						}
					}
				},
				isContainsSpecialChar : true,
			},
			OperatorId : {
				required : true,
				maxlength : 50,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#uOperatorId").val().replace(/\s+/g,"");
						},
						InnerId : function() {
							return $("#uOperatorInnerId").val();
						},
						ColumnName : function() {
							return "OperatorId";
						}
					}
				},
				isContainsSpecialChar : true,
			},
			TableSize : {
				required : true,
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
			OperatorName : {
				required : "请输入操作员姓名",
				maxlength : "最大不超过50个字符",
				remote : "操作员姓名重复,请重新输入"
			},
			OperatorId : {
				required : "请输入操作员登录名",
				maxlength : "最大不超过50个字符",
				remote : "操作员登录名重复,请重新输入"
			},
			TableSize : {
				required : "请选择表格行数",
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
			var AreaList = "";
			var AppList = "";
			var tree = $("#uAreaList").combotree('tree'); // 得到树对象
			var arry = tree.tree('getChecked', [ 'checked', 'indeterminate' ]);
			$.each(arry, function(i, k) {
				if (arry[i].id.indexOf("area") >= 0) {
					AreaList += arry[i].id.substring(4, arry[i].id.length) + ",";
				}
				if (arry[i].id.indexOf("app") >= 0) {
					AppList += arry[i].id.substring(3, arry[i].id.length) + ",";
				}
			});

			/*var CompanyList = "";
			var DepartmentList = "";
			var companyTree = $("#uCompanyList").combotree('tree'); // 得到树对象
			var companyArry = companyTree.tree('getChecked', [ 'checked', 'indeterminate' ]);
			$.each(companyArry, function(i, k) {
				if (companyArry[i].id.indexOf("company") >= 0) {
					CompanyList += companyArry[i].id.substring(7, companyArry[i].id.length) + ",";
				}
			});*/
			/*companyArry = companyTree.tree('getChecked', [ 'checked' ]);
			$.each(companyArry, function(i, k) {
				if (companyArry[i].id.indexOf("department") >= 0) {
					DepartmentList += companyArry[i].id.substring(10, companyArry[i].id.length) + ",";
				}
			});*/
			/*var telStr = /^[1](([3][0-9])|([4][5-9])|([5][0-3,5-9])|([6][5,6])|([7][0-8])|([8][0-9])|([9][1,8,9]))[0-9]{8}$/;
		    if (!(telStr.test($("#uOperatorId").val().replace(/\s+/g,"")))) {
		    	showMsg("提示信息", "登录名称必须为合法手机号，请重新输入" ,true);
		    	return false;
		    }*/
			var PageList = "";
			var pageTree = $("#uPageList").combotree('tree'); // 得到树对象
			var pageArry = pageTree.tree('getChecked', ['checked','indeterminate']);
			$.each(pageArry, function(i, k) {
				PageList += pageArry[i].id + ",";
			});
			var MerchantInnerId = "";
			var merchanttree = $("#uMerchantInnerId").combotree('tree'); // 得到树对象
			var arry = merchanttree.tree('getChecked', [ 'checked', 'indeterminate' ]);
			$.each(arry, function(i, k) {
				if (arry[i].id.indexOf("merchant") >= 0) {
					MerchantInnerId += arry[i].id.substring(8, arry[i].id.length) + ",";
				}
			});
			// 提交按钮设置为不可用，防止重复提交
			$("#updateSubmit").attr("disabled", true);
			$.ajax({
				url : "operator/update.html",
				type : "post",
				data : {
					OperatorInnerId : $("#uOperatorInnerId").val(),
					OperatorName : $("#uOperatorName").val().replace(/\s+/g,""),
					OperatorId : $("#uOperatorId").val().replace(/\s+/g,""),
					PageList : PageList,
					AreaList : AreaList,
					AppList : AppList,
					/*CompanyList : CompanyList,*/
					//DepartmentList : DepartmentList,
					MerchantInnerId : MerchantInnerId,
					TableSize : $("#uTableSize").val(),
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
						$("#uAreaList").combotree("clear");
						$("#uCompanyList").combotree("clear");
						$("#uPageList").combotree("clear");
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
