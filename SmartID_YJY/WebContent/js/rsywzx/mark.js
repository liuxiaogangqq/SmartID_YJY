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
	$("#readobject").hide();
	$("#sDepartmentInnerId").combotree({
		url : "companyDepartmentTree/select.html",
		editable : false,
		multiple : true
	});
	// 请求后台，获得配置参数
	$.ajax({
		url : "configure/select.html",
		type : "post",
		async : false,
		data : {
			Page : "mark"
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
	//
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
	table();
};

$("#iMarkTypeInnerId").combobox({
	url : "markTypeBox/select.html",
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

$("#sReadCard").click(function() {
	rlt=SmartID_zr.OpenReader();
	if(rlt!=0){
		alert('打开读卡器失败');
	}else{
		
		rlt=SmartID_zr.ReadCard();
			$("#readCard").attr("disabled", true);
			$.ajax({
				type : "Post",
				url : "readCard.html",
				dataType : 'json',
				data : {
					MarkId : rlt,
				},
				success : function(data) {
					// 提交按钮设置为可用
					$("#readCard").removeAttr("disabled");
					if (data.code == 200) {
						$("#sUserName").val(data.UserName);
						$("#sUserId").val(data.UserId);
					} else {
						// 失败
						showMsg("提示信息", data.msg, true);
					}
				},
				error : function() {
					// 提交按钮设置为可用
					$("#sReadCard").removeAttr("disabled");
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
			 rlt=SmartID_zr.CloseReader;
		
	}
});

$('#sMarkState').multiselect({
	buttonWidth : '170px',
	buttonHeight : '28px',
	nonSelectedText : '全选',// 没有值的时候button显示值
	nSelectedText : '个被选中',// 有n个值的时候显示n个被选中
	allSelectedText : '全选',// 所有被选中的时候 全选（n）
	numberDisplayed : 1000,// 当超过1000个标签的时候显示n个被选中
	templates : {
		button : '<button type="button" class="multiselect dropdown-toggle" data-toggle="dropdown" style="text-align:left;background-color: #ffffff;border: 1px solid #e5e5e5;"><span class="multiselect-selected-text"></span></button>',
		ul : '<ul class="multiselect-container dropdown-menu" style="max-height: 170px;overflow-x: hidden;overflow-y: auto;-webkit-tap-highlight-color: rgba(0,0,0,0);"></ul>',
		filter : '<li class="multiselect-item multiselect-filter"><div class="input-group"><span class="input-group-addon"><i class="glyphicon glyphicon-search"></i></span><input class="form-control multiselect-search" type="text"></div></li>',
		filterClearBtn : '<span class="input-group-btn"></span>',
		li : '<li><a tabindex="0"><label style="margin-left:0%;"></label></a></li>',
		divider : '<li class="multiselect-item divider"></li>',
		liGroup : '<li class="multiselect-item multiselect-group"><label></label></li>'
	}

});

// 点击查询时更新表格
$("#select").click(function() {
	table();
});

// 点击添加时弹出框
$("#insert").click(function() {
	var Table = $("#table").datagrid("getSelected");
	if (Table) {
		if(Table.DepartmentInnerId != 42){
			showMsg("提示信息", "只有临时人员才能发卡");
		}else{
			$("#insertForm").form("clear");
			$("#iUserName").val(Table.UserName);
			$("#iUserInnerId").val(Table.UserInnerId);
			$("#iUserId").val(Table.UserId);
			$("#insertDiv").modal("show");
		}
	} else {
		showMsg("提示信息", "请选择一条数据进行操作");
	}
});

$("#readCard").click(function() {
	rlt=SmartID_zr.OpenReader();
	if(rlt!=0){
		alert('打开读卡器失败');
	}else{
		
		 rlt=SmartID_zr.ReadCard();
			$("#iMarkId").val(rlt);
			 rlt=SmartID_zr.CloseReader;
		
	}
});

// 点击修改时操作
$("#update").click(function() {
	var Table = $("#table").datagrid("getSelected");
	if (Table) {
		$("#uMarkInnerId").val(Table.MarkInnerId);
		$("#uMarkName").val(Table.MarkName);
		$("#uMarkId").val(Table.MarkId);
		$("#uStandbyA").val(Table.StandbyA);
		$("#uStandbyB").val(Table.StandbyB);
		$("#uRemark").val(Table.Remark);
		$("#updateDiv").modal("show");
	} else {
		showMsg("提示信息", "请选择一条数据进行操作");
	}
});

// 点击挂失时操作
$("#lossOf").click(function() {
	var Table = $("#table").datagrid("getSelected");
	if (Table) {
		if(!(Table.MarkStateName=="正常")){
			showMsg("提示信息", "只有正常卡才可以挂失");
			return false;
		}
		if (confirm("是否确认挂失？")) {
			// 提交按钮设置为不可用，防止重复提交
			$("#delete").attr("disabled", true);
			$.ajax({
				url : "markState/update.html",
				type : "post",
				data : {
					MarkInnerId : Table.MarkInnerId,
					MarkState : 1,
					DelFlag : 0
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
		showMsg("提示信息", "请选择标识数据进行操作");
	}
});

// 点击解挂时操作
$("#seekOut").click(function() {
	var Table = $("#table").datagrid("getSelected");
	if (Table) {
		if(!(Table.MarkStateName=="已挂失")){
			showMsg("提示信息", "只有挂失卡才可以解挂");
			return false;
		}
		if (confirm("是否确认解挂？")) {
			// 提交按钮设置为不可用，防止重复提交
			$("#delete").attr("disabled", true);
			$.ajax({
				url : "markState/update.html",
				type : "post",
				data : {
					MarkInnerId : Table.MarkInnerId,
					MarkState : 0,
					DelFlag : 0
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
		showMsg("提示信息", "请选择标识数据进行操作");
	}
});

// 点击退卡时操作
$("#sendBack").click(function() {
	var Table = $("#table").datagrid("getSelected");
	if (Table) {
		if(!(Table.MarkStateName=="正常"||Table.MarkStateName=="已挂失")){
			showMsg("提示信息", "此卡已退卡，不用重复操作");
			return false;
		}
		if (confirm("是否确认退卡？")) {
			// 提交按钮设置为不可用，防止重复提交
			$("#delete").attr("disabled", true);
			$.ajax({
				url : "markState/update.html",
				type : "post",
				data : {
					MarkInnerId : Table.MarkInnerId,
					MarkState : 2,
					DelFlag : 1
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
	}else{
		showMsg("提示信息", "请选择一条数据进行操作");
		return false;
	}
});

// 表格
function table() {
	var MarkState = $('#sMarkState').val();
	if (MarkState) {
		MarkState = $('#sMarkState').val().toString();
	} else {
		MarkState = "";
	}
	var DepartmentInnerId = "";
	var arry = $("#sDepartmentInnerId").combotree("getValues");
	$.each(arry, function(i, k) {
		if (arry[i].indexOf("department") >= 0) {
			DepartmentInnerId += arry[i].substring(10, arry[i].length) + ",";
		}
	});
	//$("#table").datagrid("clearSelections");
	$("#table").datagrid({
		loadMsg : "正在加载数据，请稍等.....", // 在加载页面时显示数据
		url : "mark/select.html", // 发送的url地址
		queryParams :
		// 参数列表
		{
			DepartmentList : DepartmentInnerId,
			UserId : $('#sUserId').val(),
			UserName : $('#sUserName').val(),
			MarkId : $('#sMarkId').val(),
			MarkName : $('#sMarkName').val(),
			Remark : $('#sRemark').val(),
			MarkState : MarkState,
			StandbyA : $('#sStandbyA').val(),
			StandbyB : $('#sStandbyB').val()
		},
		method : 'post',
		idField : 'MarkInnerId',
		fitColumns : true,
		// 获取分页对象
		pagination : true,
		rownumbers : true,
		singleSelect : true,
		height : TableSize * 35 + 68,
		pageSize : TableSize,// 10 325,15 450,20 575,25 700
		pageList : [ 10, 15, 20, 25, 50, 100, 200, 500 ],
		columns : [ [ {
			field : 'MarkInnerId',
			hidden : true
		}, {
			field : 'DepartmentInnerId',
			hidden : true
		}, {
			field : 'CompanyInnerId',
			hidden : true
		}, {
			field : 'MarkTypeInnerId',
			hidden : true
		}, {
			field : 'UserInnerId',
			hidden : true
		}, {
			field : 'UserName',
			title : '姓名',
			sortable : true,
			width : 100
		}, {
			field : 'UserId',
			title : '编号',
			sortable : true,
			width : 100
		},/* {
			field : 'DepartmentName',
			title : '部门',
			sortable : true,
			width : 150
		},*/ {
			field : 'DepartmentName',
			title : '公司',
			sortable : true,
			width : 150
		}, {
			field : 'MarkId',
			title : '标识号',
			sortable : true,
			width : 100
		}, {
			field : 'MarkName',
			title : '标识名称',
			sortable : true,
			width : 100
		}, {
			field : 'MarkStateName',
			title : '标识状态',
			sortable : true,
			width : 100
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
		onLoadError : function() {
			showMsg("提示信息", "服务发生异常，请检查服务", true);
		},
		onLoadSuccess : function(data) {
			$('#table').datagrid('clearSelections');
			if (data.rows.length == 0) {
				var body = $(this).data().datagrid.dc.body2;
				body.find('table tbody').append('<tr><td width="' + body.width() + '" style="height: 35px; text-align: center;"><h2>此条件下没有数据</h2></td></tr>');
			}
		}
	});
}

// 添加提交
$("#insertSubmit").click(function() {
	$("#iMarkId").removeData("previousValue");
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
			MarkName : {
				required : true,
				maxlength : 50,
				isContainsSpecialChar : true,
			},
			MarkId : {
				required : true,
				maxlength : 50,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#iMarkId").val();
						},
						InnerId : function() {
							return -1;
						},
						ColumnName : function() {
							return "MarkId";
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
			MarkName : {
				required : "请输入标识名称",
				maxlength : "最大不超过50个字符"
			},
			MarkId : {
				required : "请输入标识号",
				maxlength : "最大不超过50个字符",
				remote : "标识号重复,请重新输入"
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
			if($("#iMarkTypeInnerId").combobox("getValue") == ""){
				showMsg("提示信息", "标识类型不能为空",true);
				return false;
			}
			if($("#iMarkId").val() == "0"){
				showMsg("提示信息", "卡号不合法",true);
				return false;
			}
			/*var exp = /^([1-9][\d]{0,5}|0)(\.[\d]{1,2})?$/;*/
			/*if($("#iMarkMoney").val()!=""){
				if (!exp.test($("#iMarkMoney").val())) {
					showMsg("信息校验", "请输入0-999999.99的金额");
					return false;
				} 
			}*/
			$("#insertSubmit").attr("disabled", true);
			$.ajax({
				url : "mark/insert.html",
				type : "post",
				data : {
					UserInnerId : $("#iUserInnerId").val(),
					MarkTypeInnerId : $("#iMarkTypeInnerId").combobox("getValue"),
					MarkName : $("#iMarkName").val().replace(/\s+/g,""),
					MarkCode : $("#iMarkCode").val().replace(/\s+/g,""),
					MarkId : $("#iMarkId").val().replace(/\s+/g,""),
					/*MarkMoney : Number($("#iMarkMoney").val())*100,*/
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
						// $(':input', '#insertForm').not(':button, :submit,
						// :reset,
						// :hidden').val('').removeAttr('checked').removeAttr('selected');
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
			MarkName : {
				required : true,
				maxlength : 50,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#uMarkName").val().replace(/\s+/g,"");
						},
						InnerId : function() {
							return $("#uMarkInnerId").val();
						},
						ColumnName : function() {
							return "MarkName";
						}
					}
				}
			},
			MarkId : {
				required : true,
				maxlength : 50,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#uMarkId").val().replace(/\s+/g,"");
						},
						InnerId : function() {
							return $("#uMarkInnerId").val();
						},
						ColumnName : function() {
							return "MarkId";
						}
					}
				}
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
			MarkName : {
				required : "请输入标识名称",
				maxlength : "最大不超过50个字符",
				remote : "标识名称重复,请重新输入"
			},
			MarkId : {
				required : "请输入标识号",
				maxlength : "最大不超过50个字符",
				remote : "标识号重复,请重新输入"
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
			$("#updateSubmit").attr("disabled", true);
			$.ajax({
				url : "Mark/update.html",
				type : "post",
				data : {
					MarkInnerId : $("#uMarkInnerId").val(),
					MarkName : $("#uMarkName").val().replace(/\s+/g,""),
					MarkId : $("#uMarkId").val().replace(/\s+/g,""),
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
$("#buCard").click(function(){
	var Table = $("#table").datagrid("getSelected");
	if(Table){
		if(Table.MarkStateName=="已挂失" || Table.MarkStateName=="已退卡"){
			$("#buCardForm").form("clear");
			$("#buUserName").val(Table.UserName);
			$("#buUserInnerId").val(Table.UserInnerId);
			$("#buUserId").val(Table.UserId);
			$("#yMarkId").val(Table.MarkId);
			$("#buMarkInnerId").val(Table.MarkInnerId);
			$("#buCardDiv").modal("show");
			
		}else{
			showMsg("提示信息", "该卡状态不可补卡", true);
		}
	}else{
		showMsg("提示信息", "请选择一条数据进行操作");	
	}
})
$("#bureadCard").click(function() {
	
		rlt=SmartID_zr.OpenReader();
		if(rlt!=0){
			alert('打开读卡器失败');
		}else{
			rlt=SmartID_zr.ReadCard();
			$("#xMarkId").val(rlt);
			rlt=SmartID_zr.CloseReader;
		}
});
//添加提交
$("#buCardSubmit").click(function() {
	$("#buCardForm").validate({
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
			MarkName : {
				required : true,
				maxlength : 50,
				isContainsSpecialChar : true,
			},
			MarkId : {
				required : true,
				maxlength : 50,
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
			MarkName : {
				required : "请输入标识名称",
				maxlength : "最大不超过50个字符"
			},
			MarkId : {
				required : "请输入标识号",
				maxlength : "最大不超过50个字符"
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
			if($("#xMarkId").val()=="" || $("#xMarkId").val()=="0"){
				showMsg("提示信息", "非法卡号，请重新读卡",true);
				return false;
			}
			/*var exp = /^([1-9][\d]{0,5}|0)(\.[\d]{1,2})?$/;
			if($("#huankaMoney").val()!=""){
				if (!exp.test($("#huankaMoney").val())) {
					showMsg("信息校验", "请输入0-999999.99的金额");
					return false;
				}
			}*/
			// 提交按钮设置为不可用，防止重复提交
			$("#buCardSubmit").attr("disabled", true);
			$.ajax({
				url : "mark/buCard.html",
				type : "post",
				data : {
					UserInnerId : $("#buUserInnerId").val(),
					MarkId : $("#xMarkId").val(),
					MarkInnerId : $("#buMarkInnerId").val(),
					HKMoney : ($("#huankaMoney").val()*100).toFixed(0),
				},
				dataType : "json",
				success : function(data) {
					// 提交按钮设置为可用
					$("#buCardSubmit").removeAttr("disabled");
					if (data.code == 200) {
						// 成功
						showMsg("提示信息", data.msg);
						// 隐藏下拉框
						$("#buCardDiv").modal("hide");
						// 重载表格数据
						table();
						// 清空form表单
						// $(':input', '#insertForm').not(':button, :submit,
						// :reset,
						// :hidden').val('').removeAttr('checked').removeAttr('selected');
					} else {
						// 失败
						showMsg("提示信息", data.msg, true);
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					// 提交按钮设置为可用
					$("#buCardSubmit").removeAttr("disabled");
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