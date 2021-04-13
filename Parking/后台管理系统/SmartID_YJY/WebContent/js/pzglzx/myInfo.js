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
		$("#RemarkDiv").hide();
	} else {
		$("#RemarkLabel").text(RemarkName + "：");
	}
	;
	if (StandbyAHide) {
		$("#StandbyADiv").hide();
	} else {
		$("#StandbyALabel").text(StandbyAName + "：");
	}
	;
	if (StandbyBHide) {
		$("#StandbyBDiv").hide();
	} else {
		$("#StandbyBLabel").text(StandbyBName + "：");
	}
	;
	if (StandbyCHide) {
		$("#StandbyCDiv").hide();
	} else {
		$("#StandbyCLabel").text(StandbyCName + "：");
	}
	;
	if (StandbyDHide) {
		$("#StandbyDDiv").hide();
	} else {
		$("#StandbyDLabel").text(StandbyDName + "：");
	}
	;
	$.ajax({
		url : "operator/selectNow.html",
		type : "post",
		dataType : "json",
		success : function(data) {
			$("#OperatorInnerId").val(data.OperatorInnerId);
			$("#OperatorId").val(data.OperatorId);
			$("#OperatorName").val(data.OperatorName);
			$("#TableSize").val(data.TableSize);
			$("#PageList").val(data.PageList);
			$("#AreaList").val(data.AreaList);
			$("#AppList").val(data.AppList);
			$("#CompanyList").val(data.CompanyList);
			$("#DepartmentList").val(data.DepartmentList);
			$("#Remark").val(data.Remark);
			$("#StandbyA").val(data.StandbyA);
			$("#StandbyB").val(data.StandbyB);
			$("#StandbyC").val(data.StandbyC);
			$("#StandbyD").val(data.StandbyD);
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

};

// 点击修改时操作
$("#update").click(function() {
	if (confirm("是否确认修改基本信息？")) {
		// 提交按钮设置为不可用，防止重复提交
		$("#update").attr("disabled", true);
		$.ajax({
			url : "operator/update.html",
			type : "post",
			data : {
				OperatorInnerId : $("#OperatorInnerId").val(),
				OperatorName : $("#OperatorName").val(),
				OperatorId : $("#OperatorId").val(),
				PageList : $("#PageList").val(),
				AreaList : $("#AreaList").val(),
				AppList : $("#AppList").val(),
				CompanyList : $("#CompanyList").val(),
				DepartmentList : $("#DepartmentList").val(),
				TableSize : $("#TableSize").val(),
				StandbyA : $("#StandbyA").val(),
				StandbyB : $("#StandbyB").val(),
				StandbyC : $("#StandbyC").val(),
				StandbyD : $("#StandbyD").val(),
				Remark : $("#Remark").val()
			},
			dataType : "json",
			success : function(data) {
				// 提交按钮设置为可用
				$("#update").removeAttr("disabled");
				if (data.code == 200) {
					// 成功
					showMsg("提示信息", data.msg);
					//top.location = 'login.html';
				} else {
					// 失败
					showMsg("提示信息", data.msg, true);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				// 提交按钮设置为可用
				$("#update").removeAttr("disabled");
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

// 点击修改时操作
$("#updatePWD").click(function() {
	if (!$("#beforePassword").val()) {
		showMsg("提示信息", "请输入原密码");
	} else if (!$("#Password").val()) {
		showMsg("提示信息", "请输入新密码");
	} else if ($("#Password").val() != $("#affirmPassword").val()) {
		showMsg("提示信息", "两次密码输入不一致");
	} else {
		if (confirm("是否确认修改密码？")) {
			// 提交按钮设置为不可用，防止重复提交
			$("#updatePWD").attr("disabled", true);
			$.ajax({
				url : "operator/updatePWD.html",
				type : "post",
				data : {
					OperatorInnerId : $("#OperatorInnerId").val(),
					Password : $("#beforePassword").val(),
					PasswordNew : $("#Password").val()
				},
				dataType : "json",
				success : function(data) {
					// 提交按钮设置为可用
					$("#updatePWD").removeAttr("disabled");
					if (data.code == 200) {
						// 成功
						showMsg("提示信息", data.msg);
						top.location = 'login.html';
					} else {
						// 失败
						showMsg("提示信息", data.msg, true);
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					// 提交按钮设置为可用
					$("#updatePWD").removeAttr("disabled");
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
	}
});
