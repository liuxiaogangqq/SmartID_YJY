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
	initcombobox();
	table();
};


$("#iFMerchantInnerId").combotree({
	url : 'merchantAppTree/select.html',
	valueField : "id",
	textField : "text",
	editable : false,
	onChange : function(){
		var MerchantInnerId = $("#iFMerchantInnerId").combotree("getValue");
		if(MerchantInnerId.length<8){
			showMsg("提示信息", "请选择正确的商户 ",true);
			return false;
		}
		$("#iMenuTypeInnerId").removeAttr("readOnly");
		$.ajax({
			url : 'MenuTypeBox/select.html',
			type : "post",
			data : {
				FMerchantInnerId : MerchantInnerId.substring(8, MerchantInnerId.length),
			},
			dataType : "json",
			success : function(data) {
				$("#iMenuTypeInnerId").combobox({
					data : data,
					valueField : "id",
					textField : "text",
					editable : false,
				});
			}
		});
	}
});

$("#uFMerchantInnerId").combotree({
	url : 'merchantAppTree/select.html',
	valueField : "id",
	textField : "text",
	editable : false,
	onChange : function(){
		$("#uMenuTypeInnerId").combobox("clear");
		var MerchantInnerId = $("#uFMerchantInnerId").combotree("getValue");
		if(MerchantInnerId.length<8){
			showMsg("提示信息", "请选择正确的商户 ",true);
			return false;
		}
		$("#uMenuTypeInnerId").removeAttr("readOnly");
		$.ajax({
			url : 'MenuTypeBox/select.html',
			type : "post",
			data : {
				FMerchantInnerId : MerchantInnerId.substring(8, MerchantInnerId.length),
			},
			dataType : "json",
			success : function(data) {
				$("#uMenuTypeInnerId").combobox({
					data : data,
					valueField : "id",
					textField : "text",
					editable : false,
				});
				if(flag == 0){
					$("#uMenuTypeInnerId").combobox("setValue",fMerchantInnerId);
				}
				flag++;
			}
		});
	}
});

var flag = 0;

function initcombobox(){
	$("#iFEnableFlag").combobox({
		data:[{'id':0,'text':'启用','selected':'selected'},{'id':1,'text':'禁用'}],
		valueField : "id",
		textField : "text",
		editable : false,
	});
	$("#sFEnableFlag").combobox({
		data:[{'id':0,'text':'启用'},{'id':1,'text':'禁用'}],
		valueField : "id",
		textField : "text",
		editable : false,
		multiple : true
	});
	$("#uFEnableFlag").combobox({
		data:[{'id':0,'text':'启用','selected':'selected'},{'id':1,'text':'禁用'}],
		valueField : "id",
		textField : "text",
		editable : false,
	});
	$("#sMenuTypeInnerId").combobox({
		url : "MenuTypeBox/select.html",
		valueField : "id",
		textField : "text",
		editable : false,
		multiple : true
	});

	$("#uMenuTypeInnerId").combobox({
		url : "MenuTypeBox/select.html",
		valueField : "id",
		textField : "text",
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
	//$("#table").datagrid({pageNumber:1});
	table();
});

// 点击添加时弹出框
$("#insert").click(function() {
	initcombobox();
	$("#insertDiv").modal("show");
});

var fMerchantInnerId = "";

// 点击修改时操作
$("#update").click(function() {
	flag = 0;
	var Table = $("#table").datagrid("getSelected");
	initcombobox();
	if (Table) {
		fMerchantInnerId = Table.FMenuTypeInnerId
		$("#uFMenuInnerId").val(Table.FMenuInnerId);
		$("#uMenuId").val(Table.FMenuId);
		$("#uMenuName").val(Table.FMenuName);
		$("#uMerchantId").val(Table.FMenuId);
		$("#uMenuPrice").val(Table.FMenuPrice);
		$("#uFMerchantInnerId").combotree("setValue","merchant"+Table.FMerchantInnerId);
		$("#uFEnableFlag").combobox("setValue",Table.FEnableFlag);
		$("#uStandbyA").val(Table.FStandbyA);
		$("#uStandbyB").val(Table.FStandbyB);
		$("#uFRemark").val(Table.FRemark);
		$("#uMenuTypeInnerId").combobox("setValue",Table.FMenuTypeInnerId);
		$("#updateDiv").modal("show");
	} else {
		showMsg("提示信息", "请选择一条数据进行操作");
	}
});
// 点击删除时操作
$("#delete").click(function() {
	var Table = $("#table").datagrid("getSelected");
	if (Table) {
		if (confirm("是否确认删除此菜品？")) {
			// 提交按钮设置为不可用，防止重复提交
			$("#delete").attr("disabled", true);
			$.ajax({
				url : "delete.html",
				type : "post",
				data : {
					InnerId : Table.FMenuInnerId,
					TableName : "TCY_MenuList"
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
		url : "MenuList/select.html", // 发送的url地址
		queryParams :
		// 参数列表
		{
			FMenuId : $('#sFMenuId').val(),
			FMenuName : $('#sFMenuName').val(),
			FMenuTypeInnerId : $('#sMenuTypeInnerId').combobox("getValues").toString(),
			FEnableFlag : $('#sFEnableFlag').combobox("getValues").toString(),
			FRemark : $('#sFRemark').val(),
			FStandbyA : $('#sFStandbyA').val(),
			FStandbyB : $('#sFStandbyB').val()
		},
		method : 'post',
		idField : 'FMenuInnerId',
		fitColumns : true,
		// 获取分页对象
		pagination : true,
		rownumbers : true,
		singleSelect : true,
		height:TableSize*35+68,
		pageSize : TableSize,// 10 325,15 450,20 575,25 700
		pageList : [ 10, 15, 20, 25, 50, 100, 200, 500 ],
		columns : [ [ {
			field : 'FMenuInnerId',
			hidden : true
		}, {
			field : 'FMenuTypeInnerId',
			hidden : true
		}, {
			field : 'FEnableFlag',
			hidden : true
		}, {
			field : 'FMerchantInnerId',
			hidden : true
		}, {
			field : 'FMenuId',
			title : '菜品编号',
			sortable : true,
			width : 150
		}, {
			field : 'FMenuName',
			title : '菜品名称',
			sortable : true,
			width : 150
		}, {
			field : 'FMenuTypeName',
			title : '菜品类型',
			width : 150
		},{
			field : 'FMenuPrice',
			title : '价格/元',
			sortable : true,
			width : 150
		}, {
			field : 'MerchantName',
			title : '所属商户',
			width : 150
		}, {
			field : 'FEnableFlagName',
			title : '启用标识',
			width : 150
		},{
			field : 'FRemark',
			title : '备注',
			sortable : true,
			width : 150
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
	$("#iMenuId").removeData("previousValue");
	$("#iMenuName").removeData("previousValue");
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
			FMenuId : {
				required : true,
				maxlength : 50,
				isContainsSpecialChar : true,
			},
			FMenuName : {
				required : true,
				maxlength : 6,
				isContainsSpecialChar : true,
			},
			FRemark : {
				maxlength : 4000,
				isContainsSpecialChar : true,
			},
			MenuPrice : {
				maxlength :9,
				isSal : true,
			},
		},
		messages : {
			FMenuId : {
				required : "请输入菜品编号",
				maxlength : "最大不超过50个字符",
			},
			FMenuName : {
				required : "请填写菜品名称",
				maxlength : "最大不超过6个字符",
			},
			FRemark : {
				maxlength : "最大不超过4000个字符"
			},
			MenuPrice : {
				maxlength : "最大不超过9个字符",
				isSal : "请输入合法的价格",
			},
		},
		submitHandler : function() {
			if($("#iMenuPrice").val() == ""){
				showMsg("提示信息", "请填写菜品价格", true);
				return false;
			}
			
			var regu = /^[0-9]+\.?[0-9]{0,2}$/;
			if(!regu.test($("#iMenuPrice").val())){
				showMsg("信息校验", "请输入一个整数或者保留两位小数",true);
				return false;
			}
			var MerchantInnerId = $("#iFMerchantInnerId").combotree("getValue");
			if(MerchantInnerId == "" || MerchantInnerId == null){
				showMsg("提示信息", "请选择商户", true);
				return false;
			}
			var menuTypeInnerId = $("#iMenuTypeInnerId").combobox("getValue");
			if (menuTypeInnerId == null || menuTypeInnerId == "") {
				showMsg("提示信息", "请选择菜品类型", true);
				return false;
			}
			if($("#iFEnableFlag").combobox("getValue") == ""){
				showMsg("提示信息", "请选择启用标识", true);
				return false;
			}
			var money = $("#iMenuPrice").val();
			// 提交按钮设置为不可用，防止重复提交
			$("#insertSubmit").attr("disabled", true);
			$.ajax({
				url : "menuList/insert.html",
				type : "post",
				data : {
					FMenuId : $("#iMenuId").val().replace(/\s+/g,""),
					FMenuName : $("#iMenuName").val().replace(/\s+/g,""),
					FMenuTypeInnerId : $("#iMenuTypeInnerId").combobox("getValue").toString(),
					FEnableFlag : $("#iFEnableFlag").combobox("getValue").toString(),
					FMenuPrice : (money*100).toFixed(0),
					FRemark : $("#iRemark").val().replace(/\s+/g,""),
					FMerchantInnerId : MerchantInnerId.substring(8, MerchantInnerId.length),
					FStandbyA : $("#iStandbyA").val().replace(/\s+/g,""),
					FStandbyB : $("#iStandbyB").val().replace(/\s+/g,""),
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
					}else {
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
	$("#uMenuId").removeData("previousValue");
	$("#uMenuName").removeData("previousValue");
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
			FMenuId : {
				required : true,
				maxlength : 50,
				isContainsSpecialChar : true,
			},
			FMenuName : {
				required : true,
				maxlength : 6,
				isContainsSpecialChar : true,
			},
			FRemark : {
				maxlength : 4000,
				isContainsSpecialChar : true,
			},
			MenuPrice : {
				maxlength :9,
				isSal : true,
			},
		},
		messages : {
			FMenuId : {
				required : "请输入菜品编号",
				maxlength : "最大不超过50个字符",
			},
			FMenuName : {
				required : "请填写菜品名称",
				maxlength : "最大不超过6个字符",
			},
			FRemark : {
				maxlength : "最大不超过4000个字符"
			},
			MenuPrice : {
				maxlength : "最大不超过9个字符",
				isSal : "请输入合法的价格",
			},
		},
		submitHandler : function() {
			if($("#uMenuPrice").val() == ""){
				showMsg("提示信息", "请填写菜品价格", true);
				return false;
			}
			var regu = /^[0-9]+\.?[0-9]{0,2}$/;
			if(!regu.test($("#uMenuPrice").val())){
				showMsg("信息校验", "请输入一个整数或者保留两位小数",true);
				return false;
			}
			var menuTypeInnerId = $("#uMenuTypeInnerId").combobox("getValue");
			if (menuTypeInnerId == null || menuTypeInnerId == "") {
				showMsg("提示信息", "请选择菜品类型", true);
				return false;
			}
			if($("#iFEnableFlag").combobox("getValue") == ""){
				showMsg("提示信息", "请选择启用标识", true);
				return false;
			}
			var MerchantInnerId = $("#uFMerchantInnerId").combotree("getValue");
			var money =  $("#uMenuPrice").val();
			// 提交按钮设置为不可用，防止重复提交
			$("#updateSubmit").attr("disabled", true);
			$.ajax({
				url : "menuList/update.html",
				type : "post",
				data : {
					FMenuInnerId : $("#uFMenuInnerId").val(),
					FMenuId : $("#uMenuId").val().replace(/\s+/g,""),
					FMenuName : $("#uMenuName").val().replace(/\s+/g,""),
					FMenuTypeInnerId : $("#uMenuTypeInnerId").combobox("getValue").toString(),
					FEnableFlag : $("#uFEnableFlag").combobox("getValue").toString(),
					FMenuPrice :(money*100).toFixed(0),
					FRemark : $("#uFRemark").val().replace(/\s+/g,""),
					FMerchantInnerId : MerchantInnerId.substring(8, MerchantInnerId.length),
					FStandbyA : $("#uStandbyA").val().replace(/\s+/g,""),
					FStandbyB : $("#uStandbyB").val().replace(/\s+/g,""),
				},
				dataType : "json",
				success : function(data) {
					// 提交按钮设置为可用
					$("#updateSubmit").removeAttr("disabled");
					if (data.code == 200) {
						flag = 0;
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
					flag=0;
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
