$("#tongbu").click(function() {
	if(confirm("是否需要同步人员数据")){
		$("#tongbu").attr("disabled", true);
		$.ajax({
			type : "Post",
			url : "tongburenyuanxinxi.html",
			dataType : 'json',
			success : function(data) {
				// 提交按钮设置为可用
				$("#tongbu").removeAttr("disabled");
				if (data.code == 200) {
					showMsg("提示信息", data.msg, true);
				} else {
					// 失败
					showMsg("提示信息", data.msg, true);
				}
			},
			error : function() {
				$("#tongbu").removeAttr("disabled");
				// 提交按钮设置为可用
				$("#rReadCard").removeAttr("disabled");
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

$("#rReadCard").click(function() {
	rlt=SmartID_zr.OpenReader();
	if(rlt!=0){
		alert('打开读卡器失败');
	}else{
		
		rlt=SmartID_zr.ReadCard();

			$("#rReadCard").attr("disabled", true);
			$.ajax({
				type : "Post",
				url : "readCard.html",
				dataType : 'json',
				data : {
					MarkId : rlt,
				},
				success : function(data) {
					// 提交按钮设置为可用
					$("#rReadCard").removeAttr("disabled");
					if (data.code == 200) {
						$("#rUserName").val(data.UserName);
						$("#rAccountInnerId").val(data.AccountInnerId);
						$("#rUserId").val(data.UserId);
						$("#rDepartmentName").val(data.DepartmentName);
						/*$("#rCompanyName").val(data.CompanyName);*/
						$("#rSubsidy").val(data.Subsidy);
						$("#rBeforeMoney").val(data.BeforeMoney);
						$("#rFavorableMoneyBefore").val(data.FavorableMoneyBefore);
						$("#rFavorableAccountInnerId").val(data.FavorableAccountInnerId);
						$("#rProportion").val(data.Proportion/10000.0);
					} else {
						// 失败
						showMsg("提示信息", data.msg, true);
					}
				},
				error : function() {
					// 提交按钮设置为可用
					$("#rReadCard").removeAttr("disabled");
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
			rlt=SmartID_zr.CloseReader;
		}
	
});

$("#dReadCard").click(function() {
	rlt=SmartID_zr.OpenReader();
	if(rlt!=0){
		alert('打开读卡器失败');
	}else{
			rlt=SmartID_zr.ReadCard();
			$("#dReadCard").attr("disabled", true);
			$.ajax({
				type : "Post",
				url : "readCard.html",
				dataType : 'json',
				data : {
					MarkId : rlt,
				},
				success : function(data) {
					// 提交按钮设置为可用
					$("#dReadCard").removeAttr("disabled");
					if (data.code == 200) {
						$("#dUserName").val(data.UserName);
						$("#dAccountInnerId").val(data.AccountInnerId);
						$("#dUserId").val(data.UserId);
						$("#dDepartmentName").val(data.DepartmentName);
						/*$("#dCompanyName").val(data.CompanyName);*/
						$("#dSubsidy").val(data.Subsidy);
						$("#dBeforeMoney").val(data.BeforeMoney);
					} else {
						// 失败
						showMsg("提示信息", data.msg, true);
					}
				},
				error : function() {
					// 提交按钮设置为可用
					$("#dReadCard").removeAttr("disabled");
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
			rlt = SmartID_cpu.SmartID_DeLinkReader();
		}
});

$(function() {
	document.onkeydown = function(e) {
		var ev = document.all ? window.event : e;
		if (ev.keyCode == 13) {
			recharge();
		}
	}
});

$("#Recharge").click(function() {
	recharge();
});
$("#rMoney").bind("input propertychange", function() {
	var exp = /^([1-9][\d]{0,5}|0)(\.[\d]{1,2})?$/;
	if (!exp.test($("#rMoney").val())) {
		showMsg("信息校验", "请输入0-999999.99的金额");
	} else {
		var Money = $("#rMoney").val();
		var Proportion = $("#rProportion").val();
		$("#rFavorableMoney").val((Money * Proportion).toFixed(2));
	}
});

function recharge() {
	if ($("#rAccountInnerId").val()) {
		var exp = /^([1-9][\d]{0,5}|0)(\.[\d]{1,2})?$/;
		if (!exp.test($("#rMoney").val())) {
			showMsg("信息校验", "请输入0-999999.99的金额");
		} else {
			var rMoney = $("#rMoney").val();
			var rFavorableMoney = $("#rFavorableMoney").val();
			var rBeforeMoney = $("#rBeforeMoney").val();
			var rAfterMoney = Number(rBeforeMoney) + Number(rFavorableMoney);
			if (rAfterMoney > 1000000) {
				showMsg("信息校验", "充值金额已到最大值");
			} else {
				$.messager.confirm("确认", "是否确认充值[姓名：[<span style='font-size:18px;color:red'>" + $("#rUserName").val() + "</span>],充值金额：[<span style='font-size:18px;color:red'>" + $("#rFavorableMoney").val() + "元</span>]", function(r) {
					if (r) {
						$("#Recharge").attr("disabled", true);
						$.ajax({
							type : "Post",
							url : "accountLog1/insert.html",
							dataType : 'json',
							data : {
								AccountInnerId1 : $("#rFavorableAccountInnerId").val(),
								AccountInnerId : $("#rAccountInnerId").val(),
								Money : (rFavorableMoney * 100).toFixed(0),
								InOrOut : 0,
								RechargeType : 0,
								MoneySource : $("#rMoneySource").val(),
								Remark : $("#rRemark").val()
							},
							success : function(data) {
								$("#Recharge").removeAttr("disabled");
								$("#rAccountInnerId").val("");
								$("#rFavorableAccountInnerId").val("");
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
								$("#Recharge").removeAttr("disabled");
								$("#rAccountInnerId").val("");
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
	} else {
		showMsg("提示信息", "请先读卡");
	}
}

$("#Decrease").click(function() {
	if ($("#dAccountInnerId").val()) {
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
						$("#Decrease").attr("disabled", true);
						$.ajax({
							type : "Post",
							url : "accountLog/insert.html",
							dataType : 'json',
							data : {
								AccountInnerId : $("#dAccountInnerId").val(),
								Money : -(dMoney * 100).toFixed(0),
								InOrOut : 1,
								RechargeType : 0,
								MoneySource : 0,
								Remark : $("#dRemark").val()
							},
							success : function(data) {
								$("#Decrease").removeAttr("disabled");
								$("#dAccountInnerId").val("");
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
								$("#Decrease").removeAttr("disabled");
								$("#dAccountInnerId").val("");
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
	} else {
		showMsg("提示信息", "请先读卡");
	}
});