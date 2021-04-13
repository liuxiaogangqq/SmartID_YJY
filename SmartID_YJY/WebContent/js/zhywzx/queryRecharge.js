// 加载时载入表格
window.onload = function() {

	$("#sUserTypeInnerId").combobox({
		url : "userTypeBox/select.html",
		valueField : "id",
		textField : "text",
		editable : false,
		multiple : true,
	});
	$("#sDepartmentInnerId").combotree({
		url : "companyDepartmentTree/select.html",
		editable : false,
		multiple : true
	});
	table();
};

// 表格
function table() {
	var DepartmentInnerId = "";
	var arry = $("#sDepartmentInnerId").combotree("getValues");
	$.each(arry, function(i, k) {
		if (arry[i].indexOf("department") >= 0) {
			DepartmentInnerId += arry[i].substring(10, arry[i].length) + ",";
		}
	});
	$("#table").datagrid("clearSelections");
	$("#table").datagrid({
		loadMsg : "正在加载数据，请稍等.....", // 在加载页面时显示数据
		url : "user/select.html", // 发送的url地址
		queryParams :
		// 参数列表
		{
			DepartmentList : DepartmentInnerId,
			UserTypeList : $("#sUserTypeInnerId").combobox("getValues").toString(),
			UserId : $('#sUserId').val(),
			UserName : $('#sUserName').val()
		},
		method : 'post',
		idField : 'UserInnerId',
		fitColumns : true,
		// 获取分页对象
		pagination : true,
		rownumbers : true,
		singleSelect : true,
		height : TableSize * 35 + 68,
		pageSize : TableSize,// 10 325,15 450,20 575,25 700
		pageList : [ 10, 15, 20, 25, 50, 100, 200, 500 ],
		columns : [ [ {
			field : 'UserInnerId',
			hidden : true
		}, {
			field : 'UserTypeInnerId',
			hidden : true
		}, {
			field : 'CompanyInnerId',
			hidden : true
		}, {
			field : 'DepartmentInnerId',
			hidden : true
		}, {
			field : 'AccountInnerId',
			hidden : true
		}, {
			field : 'AccountInnerId1',
			hidden : true
		}, {
			field : 'Proportion',
			hidden : true
		}, {
			field : 'CompanyName',
			hidden : true,
		},{
			field : 'UserId',
			title : '人员编号',
			sortable : true,
			width : 100
		}, {
			field : 'UserName',
			title : '人员姓名',
			sortable : true,
			width : 100
		}, {
			field : 'DepartmentName',
			title : '公司',
			sortable : true,
			width : 150
		}, {
			field : 'UserTypeName',
			title : '人员类型',
			sortable : true,
			width : 150
		}, {
			field : 'BeforeMoney',
			title : '现金余额',
			width : 100
		}, {
			field : 'Subsidy',
			title : '补贴余额',
			width : 100
		}/*, {
			field : 'FavorableMoneyBefore',
			title : '次账户',
			width : 100
		}*/ ] ],
		onLoadSuccess : function(data) {
			if (data.rows.length == 0) {
				var body = $(this).data().datagrid.dc.body2;
				body.find('table tbody').append('<tr><td width="' + body.width() + '" style="height: 35px; text-align: center;"><h2>此条件下没有数据</h2></td></tr>');
			}
		}
	});
}

// 点击充值时操作
$("#Recharge").click(function() {
	var Table = $("#table").datagrid("getSelected");
	if (Table) {
		if (Table.AccountInnerId) {
			$("#rechargeForm").form("clear");
			$("#rUserInnerId").val(Table.UserInnerId);
			$("#rUserName").val(Table.UserName);
			$("#rUserId").val(Table.UserId);
			$("#rAccountInnerId").val(Table.AccountInnerId);
			/*$("#rCompanyName").val(Table.CompanyName);*/
			$("#rDepartmentName").val(Table.DepartmentName);
			$("#rBeforeMoney").val(Table.BeforeMoney);
			$("#rSubsidy").val(Table.Subsidy);
			$("#rAccountInnerId1").val(Table.AccountInnerId1);
			$("#rProportion").val(Number(Table.Proportion)/10000.0);
			$("#rFavorableMoneyBefore").val(Table.FavorableMoneyBefore);
			$("#rMoneySource").val(0)
			$("#rechargeDiv").modal("show");
		} else {
			showMsg("提示信息", "此人员无现金账户");
		}
	} else {
		showMsg("提示信息", "请选择一条数据进行操作");
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

//点击查询时操作
$("#Select").click(function() {
	//$("#table").datagrid({pageNumber:1});
	table();
});

// 点击减值时操作
$("#Decrease").click(function() {
	var Table = $("#table").datagrid("getSelected");
	if (Table) {
		if (Table.AccountInnerId) {
			$("#decreaseForm").form("clear");
			$("#dUserInnerId").val(Table.UserInnerId);
			$("#dUserName").val(Table.UserName);
			$("#dUserId").val(Table.UserId);
			$("#dAccountInnerId").val(Table.AccountInnerId);
			/*$("#dCompanyName").val(Table.CompanyName);*/
			$("#dDepartmentName").val(Table.DepartmentName);
			$("#dBeforeMoney").val(Table.BeforeMoney);
			$("#dSubsidy").val(Table.Subsidy);
			$("#decreaseDiv").modal("show");
		} else {
			showMsg("提示信息", "此人员无现金账户");
		}
	} else {
		showMsg("提示信息", "请选择一条数据进行操作");
	}
});

$("#rMoney").bind("input propertychange", function() {
	var exp = /^([1-9][\d]{0,5}|0)(\.[\d]{1,2})?$/;
	if (!exp.test($("#rMoney").val())) {
		showMsg("信息校验", "请输入0-999999.99的金额");
	} else {
		var Money = $("#rMoney").val();
		var Proportion = $("#rProportion").val();
		$("#rFavorableMoney").val((Money * Proportion ).toFixed(2));
	}
});

$("#rechargeSubmit").click(function() {
	var exp = /^([1-9][\d]{0,5}|0)(\.[\d]{1,2})?$/;
	if (!exp.test($("#rMoney").val())) {
		showMsg("信息校验", "请输入0-999999.99的金额");
	} else {
		var rMoney = $("#rMoney").val();
		var favorableMoney = $("#rFavorableMoney").val();
		var rBeforeMoney = $("#rBeforeMoney").val();
		var rAfterMoney = Number(rBeforeMoney) + Number(rMoney);
		if (rAfterMoney > 1000000) {
			showMsg("信息校验", "充值金额已到最大值");
		} else {
			$.messager.confirm("确认", "是否确认充值[姓名：[<span style='font-size:18px;color:red'>" + $("#rUserName").val() + "</span>],充值金额：[<span style='font-size:18px;color:red'>" + $("#rFavorableMoney").val() + "元</span>]", function(r) {
				if (r) {
					$("#rechargeSubmit").attr("disabled", true);
					$.ajax({
						type : "Post",
						url : "accountLog1/insert.html",
						dataType : 'json',
						data : {
							AccountInnerId1 : $("#rAccountInnerId1").val(),
							Money1 : ($("#rFavorableMoney").val() * 100).toFixed(0),
							AccountInnerId : $("#rAccountInnerId").val(),
							Money : (favorableMoney * 100).toFixed(0),
							InOrOut : 0,
							RechargeType : 1,
							MoneySource : $("#rMoneySource").val(),
							Remark : $("#rRemark").val()
						},
						success : function(data) {
							$("#rechargeSubmit").removeAttr("disabled");
							// 隐藏下拉框
							$("#rechargeDiv").modal("hide");
							table();
							if (data.code == 200) {
								$("#rBeforeMoney").val((parseFloat($("#rFavorableMoney").val()) + parseFloat($("#rBeforeMoney").val())).toFixed(2));
								$("#rFavorableMoneyBefore").val((parseFloat($("#rFavorableMoneyBefore").val()) + parseFloat($("#rFavorableMoney").val())).toFixed(2));
								$("#rMoney").val("");
								$("#rFavorableMoney").val("");
								showMsg("提示信息", "充值成功");
							} else {
								// 失败
								showMsg("提示信息", data.msg, true);
							}
						},
						error : function() {
							// 提交按钮设置为可用
							$("#rechargeSubmit").removeAttr("disabled");
							// 隐藏下拉框
							$("#rechargeDiv").modal("hide");
							table();
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
		}
	}
});

$("#decreaseSubmit").click(function() {
	var exp = /^([1-9][\d]{0,5}|0)(\.[\d]{1,2})?$/;
	if (!exp.test($("#dMoney").val())) {
		showMsg("信息校验", "请输入0-999999.99的金额");
	} else {
		var dMoney = $("#dMoney").val();
		var dBeforeMoney = $("#dBeforeMoney").val();
		var dAfterMoney = Number(dBeforeMoney) - Number(dMoney);
		if (dAfterMoney < 0) {
			showMsg("信息校验", "减值金额不能超过账户余额");
		} else {
			$.messager.confirm("确认", "是否确认减值[姓名：[<span style='font-size:18px;color:red'>" + $("#dUserName").val() + "</span>],减值金额：[<span style='font-size:18px;color:red'>" + $("#dMoney").val() + "元</span>]", function(r) {
				if (r) {
					$("#decreaseSubmit").attr("disabled", true);
					$.ajax({
						type : "Post",
						url : "accountLog/insert.html",
						dataType : 'json',
						data : {
							AccountInnerId : $("#dAccountInnerId").val(),
							Money : -(dMoney * 100).toFixed(0),
							InOrOut : 1,
							RechargeType : 1,
							MoneySource : 0,
							Remark : $("#dRemark").val()
						},
						success : function(data) {
							$("#decreaseSubmit").removeAttr("disabled");
							// 隐藏下拉框
							$("#decreaseDiv").modal("hide");
							table();
							if (data.code == 200) {
								$("#dBeforeMoney").val((parseFloat($("#dBeforeMoney").val()) - parseFloat($("#dMoney").val())).toFixed(2));
								$("#dMoney").val("");
								showMsg("提示信息", "减值成功");
							} else {
								// 失败
								showMsg("提示信息", data.msg, true);
							}
						},
						error : function() {
							// 提交按钮设置为可用
							$("#decreaseSubmit").removeAttr("disabled");
							// 隐藏下拉框
							$("#decreaseDiv").modal("hide");
							table();
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
		}
	}
});