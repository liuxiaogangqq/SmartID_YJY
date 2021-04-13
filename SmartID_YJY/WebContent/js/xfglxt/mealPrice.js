// 加载时载入表格
window.onload = function() {

	table();
}

$("#iMerchantInnerId").combotree({
	url : "merchantAppTree/select.html",
	editable : false,
	multiple : true,
	onLoadError : function() {
		showMsg("提示信息", "服务发生异常，请联系" + kaifadianhua, true);
	}
});

$("#uMerchantInnerId").combotree({
	url : "merchantAppTree/select.html",
	editable : false,
	multiple : true,
	onLoadError : function() {
		showMsg("提示信息", "服务发生异常，请联系" + kaifadianhua, true);
	}
});

$("#iUserTypeInerId").combobox({
	url : "userTypeBox/select.html",
	valueField : "id",
	textField : "text",
	editable : false,
	multiple : true,
	onLoadError : function() {
		showMsg("提示信息", "服务发生异常，请联系" + kaifadianhua, true);
	}
});

$("#uUserTypeInerId").combobox({
	url : "userTypeBox/select.html",
	valueField : "id",
	textField : "text",
	editable : false,
	multiple : true,
	onLoadError : function() {
		showMsg("提示信息", "服务发生异常，请联系" + kaifadianhua, true);
	}
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
		$("#uMealPriceInnerId").val(Table.MealPriceInnerId);
		if (Table.MerchantList != null) {
			var MerchantInnerId = "";
			var MerchantList = Table.MerchantList.split(",");
			$.each(MerchantList, function(i, k) {
				MerchantInnerId += "merchant" + MerchantList[i] + ",";
			});
			$("#uMerchantInnerId").combotree("setValues", MerchantInnerId);
		}
		$("#uUserTypeInerId").combobox("setValues", Table.UserTypeList);

		$("#uBreakfastFirst").val(Table.BreakfastFirst);
		$("#uBreakfastNext").val(Table.BreakfastNext);
		$("#uBreakfastNums").val(Table.BreakfastNums);
		$("#uLunchFirst").val(Table.LunchFirst);
		$("#uLunchNext").val(Table.LunchNext);
		$("#uLunchNums").val(Table.LunchNums);
		$("#uDinnerFirst").val(Table.DinnerFirst);
		$("#uDinnerNext").val(Table.DinnerNext);
		$("#uDinnerNums").val(Table.DinnerNums);
		$("#uNightFirst").val(Table.NightFirst);
		$("#uNightNext").val(Table.NightNext);
		$("#uNightNums").val(Table.NightNums);
		$("#updateDiv").modal("show");
	} else {
		showMsg("提示信息", "请选择一条数据进行操作");
	}
});

// 点击删除时操作
$("#delete").click(function() {
	var Table = $("#table").datagrid("getSelected");
	if (Table) {
		if (confirm("是否确认删除此餐次价格？")) {
			// 提交按钮设置为不可用，防止重复提交
			$("#delete").attr("disabled", true);
			$.ajax({
				url : "MealPrice/delete.html",
				type : "post",
				data : {
					MealPriceInnerId : Table.MealPriceInnerId
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
						showMsg("提示信息", "找不到服务器，请检查网络或联系" + weihudianhua, true);
					} else if (XMLHttpRequest.readyState == 4) {
						if (XMLHttpRequest.status == 404) {
							showMsg("提示信息", "找不到服务，请联系" + weihudianhua, true);
						} else {
							showMsg("提示信息", "程序出现异常，请联系" + kaifadianhua, true);
						}
					} else {
						showMsg("提示信息", "程序出现异常，请联系" + kaifadianhua, true);
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
		url : "MealPrice/select.html", // 发送的url地址
		method : 'post',
		idField : 'MealPriceInnerId',
		fitColumns : false,
		// 获取分页对象
		pagination : true,
		rownumbers : true,
		singleSelect : true,
		height : TableSize * 35 + 68,
		pageSize : TableSize,// 10 325,15 450,20 575,25 700
		pageList : [ 10, 15, 20, 25, 50, 100, 200, 500 ],
		columns : [ [ {
			field : 'MealPriceInnerId',
			hidden : true
		}, {
			field : 'MerchantList',
			hidden : true
		}, {
			field : 'UserTypeList',
			hidden : true
		}, {
			field : 'UserTypeListName',
			title : '人员类型',
			sortable : true,
			width : 150
		}, {
			field : 'MerchantListName',
			title : '商户',
			sortable : true,
			width : 150
		}, {
			field : 'BreakfastFirst',
			title : '早餐首次价格',
			sortable : true,
			width : 100
		}, {
			field : 'BreakfastNext',
			title : '早餐多次价格',
			sortable : true,
			width : 100
		}, {
			field : 'BreakfastNums',
			title : '早餐最多次数',
			sortable : true,
			width : 100
		}, {
			field : 'LunchFirst',
			title : '午餐首次价格',
			sortable : true,
			width : 100
		}, {
			field : 'LunchNext',
			title : '午餐多次价格',
			sortable : true,
			width : 100
		}, {
			field : 'LunchNums',
			title : '午餐最多次数',
			sortable : true,
			width : 100
		}, {
			field : 'DinnerFirst',
			title : '晚餐首次价格',
			sortable : true,
			width : 100
		}, {
			field : 'DinnerNext',
			title : '晚餐多次价格',
			sortable : true,
			width : 100
		}, {
			field : 'DinnerNums',
			title : '晚餐最多次数',
			sortable : true,
			width : 100
		}, {
			field : 'NightFirst',
			title : '夜餐首次价格',
			sortable : true,
			width : 100
		}, {
			field : 'NightNext',
			title : '夜餐多次价格',
			sortable : true,
			width : 100
		}, {
			field : 'NightNums',
			title : '夜餐最多次数',
			sortable : true,
			width : 100
		}  ] ],
		onLoadError : function() {
			showMsg("提示信息", "服务发生异常，请联系" + kaifadianhua, true);
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
	$("#insertForm").validate({
		submitHandler : function() {
			var MerchantInnerId = "";
			var tree = $("#iMerchantInnerId").combotree('tree'); // 得到树对象
			var arry = tree.tree('getChecked', [ 'checked', 'indeterminate' ]);
			$.each(arry, function(i, k) {
				if (arry[i].id.indexOf("merchant") >= 0) {
					MerchantInnerId += arry[i].id.substring(8, arry[i].id.length) + ",";
				}
			});
			if (MerchantInnerId == "") {
				showMsg("提示信息", "请选择商户", true);
				return false;
			}
			if (!$("#iUserTypeInerId").combobox("getValue")) {
				showMsg("提示信息", "请选择人员类型", true);
				return false;
			}
			var exp = /^([1-9][\d]{0,5}|0)(\.[\d]{1,2})?$/;
			var exp1 = /^([1-9][\d]{0,8}|0)$/;
			if (!exp.test($("#iBreakfastFirst").val())) {
				showMsg("信息校验", "早餐首次请输入0-999999.99的金额" , true);
				return false;
			}
			if (!exp.test($("#iBreakfastNext").val())) {
				showMsg("信息校验", "早餐多次请输入0-999999.99的金额" ,true);
				return false;
			}
			if (!exp.test($("#iLunchFirst").val())) {
				showMsg("信息校验", "午餐首次请输入0-999999.99的金额",true);
				return false;
			}
			if (!exp.test($("#iLunchNext").val())) {
				showMsg("信息校验", "午餐多次请输入0-999999.99的金额",true);
				return false;
			}
			if (!exp.test($("#iDinnerFirst").val())) {
				showMsg("信息校验", "晚餐首次请输入0-999999.99的金额",true);
				return false;
			}
			if (!exp.test($("#iDinnerNext").val())) {
				showMsg("信息校验", "晚餐多次请输入0-999999.99的金额",true);
				return false;
			}
			if (!exp.test($("#iNightFirst").val())) {
				showMsg("信息校验", "夜餐首次请输入0-999999.99的金额",true);
				return false;
			}
			if (!exp.test($("#iNightNext").val())) {
				showMsg("信息校验", "夜餐多次请输入0-999999.99的金额",true);
				return false;
			}
			if (!exp1.test($("#iBreakfastNums").val())) {
				showMsg("信息校验", "早餐次数请输入整数",true);
				return false;
			}
			if (!exp1.test($("#iLunchNums").val())) {
				showMsg("信息校验", "午餐次数请输入整数",true);
				return false;
			}
			if (!exp1.test($("#iDinnerNums").val())) {
				showMsg("信息校验", "晚餐次数请输入整数",true);
				return false;
			}
			if (!exp1.test($("#iNightNums").val())) {
				showMsg("信息校验", "夜餐次数请输入整数",true);
				return false;
			}
			// 提交按钮设置为不可用，防止重复提交
			$("#insertSubmit").attr("disabled", true);
			$.ajax({
				url : "MealPrice/insert.html",
				type : "post",
				data : {
					UserTypeList : $("#iUserTypeInerId").combobox("getValues").toString(),
					MerchantList : MerchantInnerId,
					BreakfastFirst : ($("#iBreakfastFirst").val() * 100).toFixed(0),
					BreakfastNext : ($("#iBreakfastNext").val() * 100).toFixed(0),
					LunchFirst : ($("#iLunchFirst").val() * 100).toFixed(0),
					LunchNext : ($("#iLunchNext").val() * 100).toFixed(0),
					DinnerFirst : ($("#iDinnerFirst").val() * 100).toFixed(0),
					DinnerNext : ($("#iDinnerNext").val() * 100).toFixed(0),
					NightFirst : ($("#iNightFirst").val() * 100).toFixed(0),
					NightNext : ($("#iNightNext").val() * 100).toFixed(0),
					BreakfastNums : $("#iBreakfastNums").val(),
					LunchNums : $("#iLunchNums").val(),
					DinnerNums : $("#iDinnerNums").val(),
					NightNums : $("#iNightNums").val(),
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
						$("#iMerchantInnerId").combotree("clear");
						$("#iUserTypeInerId").combobox("clear");
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
						showMsg("提示信息", "找不到服务器，请检查网络或联系" + weihudianhua, true);
					} else if (XMLHttpRequest.readyState == 4) {
						if (XMLHttpRequest.status == 404) {
							showMsg("提示信息", "找不到服务，请联系" + weihudianhua, true);
						} else {
							showMsg("提示信息", "程序出现异常，请联系" + kaifadianhua, true);
						}
					} else {
						showMsg("提示信息", "程序出现异常，请联系" + kaifadianhua, true);
					}
				}
			});
		}
	});
});

// 修改提交
$("#updateSubmit").click(function() {
	$("#updateForm").validate({
		submitHandler : function() {
			var AreaList = "";
			var MerchantInnerId = "";
			var tree = $("#uMerchantInnerId").combotree('tree'); // 得到树对象
			var arry = tree.tree('getChecked', [ 'checked', 'indeterminate' ]);
			$.each(arry, function(i, k) {
				if (arry[i].id.indexOf("merchant") >= 0) {
					MerchantInnerId += arry[i].id.substring(8, arry[i].id.length) + ",";
				}
			});
			if (MerchantInnerId == "") {
				showMsg("提示信息", "请选择商户", true);
				return false;
			}
			if (!$("#uUserTypeInerId").combobox("getValue")) {
				showMsg("提示信息", "请选择人员类型", true);
				return false;
			}
			var exp = /^([1-9][\d]{0,5}|0)(\.[\d]{1,2})?$/;
			var exp1 = /^([1-9][\d]{0,8}|0)$/;
			if (!exp.test($("#uBreakfastFirst").val())) {
				showMsg("信息校验", "早餐首次请输入0-999999.99的金额" , true);
				return false;
			}
			if (!exp.test($("#uBreakfastNext").val())) {
				showMsg("信息校验", "早餐多次请输入0-999999.99的金额" ,true);
				return false;
			}
			if (!exp.test($("#uLunchFirst").val())) {
				showMsg("信息校验", "午餐首次请输入0-999999.99的金额",true);
				return false;
			}
			if (!exp.test($("#uLunchNext").val())) {
				showMsg("信息校验", "午餐多次请输入0-999999.99的金额",true);
				return false;
			}
			if (!exp.test($("#uDinnerFirst").val())) {
				showMsg("信息校验", "晚餐首次请输入0-999999.99的金额",true);
				return false;
			}
			if (!exp.test($("#uDinnerNext").val())) {
				showMsg("信息校验", "晚餐多次请输入0-999999.99的金额",true);
				return false;
			}
			if (!exp.test($("#uNightFirst").val())) {
				showMsg("信息校验", "夜餐首次请输入0-999999.99的金额",true);
				return false;
			}
			if (!exp.test($("#uNightNext").val())) {
				showMsg("信息校验", "夜餐多次请输入0-999999.99的金额",true);
				return false;
			}
			if (!exp1.test($("#uBreakfastNums").val())) {
				showMsg("信息校验", "早餐次数请输入整数",true);
				return false;
			}
			if (!exp1.test($("#uLunchNums").val())) {
				showMsg("信息校验", "午餐次数请输入整数",true);
				return false;
			}
			if (!exp1.test($("#uDinnerNums").val())) {
				showMsg("信息校验", "晚餐次数请输入整数",true);
				return false;
			}
			if (!exp1.test($("#uNightNums").val())) {
				showMsg("信息校验", "夜餐次数请输入整数",true);
				return false;
			}
			// 提交按钮设置为不可用，防止重复提交
			$("#updateSubmit").attr("disabled", true);
			$.ajax({
				url : "MealPrice/update.html",
				type : "post",
				data : {
					MealPriceInnerId : $("#uMealPriceInnerId").val(),
					UserTypeList : $("#uUserTypeInerId").combobox("getValues").toString(),
					MerchantList : MerchantInnerId,
					BreakfastFirst : ($("#uBreakfastFirst").val() * 100).toFixed(0),
					BreakfastNext : ($("#uBreakfastNext").val() * 100).toFixed(0),
					BreakfastNums : $("#uBreakfastNums").val(),
					LunchFirst : ($("#uLunchFirst").val() * 100).toFixed(0),
					LunchNext : ($("#uLunchNext").val() * 100).toFixed(0),
					LunchNums : $("#uLunchNums").val(),
					DinnerFirst : ($("#uDinnerFirst").val() * 100).toFixed(0),
					DinnerNext : ($("#uDinnerNext").val() * 100).toFixed(0),
					DinnerNums : $("#uDinnerNums").val(),
					NightFirst : ($("#uNightFirst").val() * 100).toFixed(0),
					NightNext : ($("#uNightNext").val() * 100).toFixed(0),
					NightNums : $("#uNightNums").val()
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
						$("#uMerchantInnerId").combotree("clear");
						$("#uUserTypeInerId").combobox("clear");
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
						showMsg("提示信息", "找不到服务器，请检查网络或联系" + weihudianhua, true);
					} else if (XMLHttpRequest.readyState == 4) {
						if (XMLHttpRequest.status == 404) {
							showMsg("提示信息", "找不到服务，请联系" + weihudianhua, true);
						} else {
							showMsg("提示信息", "程序出现异常，请联系" + kaifadianhua, true);
						}
					} else {
						showMsg("提示信息", "程序出现异常，请联系" + kaifadianhua, true);
					}
				}
			});
		}
	});
});
