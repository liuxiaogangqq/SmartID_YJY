var RemarkHide;
var RemarkName;
var RemarkLength;
var IdTypeHide;
var IdTypeName;
var IdTypeLength;
var IdNumberHide;
var IdNumberName;
var IdNumberLength;
var SexHide;
var SexName;
var SexLength;
var BirthdayHide;
var BirthdayName;
var BirthdayLength;
var MobileHide;
var MobileName;
var MobileLength;
var PhoneHide;
var PhoneName;
var PhoneLength;
var EmailHide;
var EmailName;
var EmailLength;
var JobHide;
var JobName;
var JobLength;
var JobLevelHide;
var JobLevelName;
var JobLevelLength;
var EducationHide;
var EducationName;
var EducationLength;
var NationHide;
var NationName;
var NationLength;
var CountryHide;
var CountryName;
var CountryLength;
var NativePlaceHide;
var NativePlaceName;
var NativePlaceLength;
var AddressHide;
var AddressName;
var AddressLength;
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
var UserPassword;
var sort;
var order;
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

	$.ajax({
		url : "systemConfigureById/select.html",
		type : "post",
		async : false,
		data : {
			ConfigureId : "UserPassword"
		},
		dataType : "json",
		success : function(data) {
			UserPassword = data.ConfigureValue;
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
			Page : "user"
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
				if (arry[i].Property == "IdType") {
					IdTypeHide = arry[i].Enabled;
					IdTypeName = arry[i].Name;
					IdTypeLength = arry[i].Length;
				}
				if (arry[i].Property == "IdNumber") {
					IdNumberHide = arry[i].Enabled;
					IdNumberName = arry[i].Name;
					IdNumberLength = arry[i].Length;
				}
				if (arry[i].Property == "Sex") {
					SexHide = arry[i].Enabled;
					SexName = arry[i].Name;
					SexLength = arry[i].Length;
				}
				if (arry[i].Property == "Birthday") {
					BirthdayHide = arry[i].Enabled;
					BirthdayName = arry[i].Name;
					BirthdayLength = arry[i].Length;
				}
				if (arry[i].Property == "Mobile") {
					MobileHide = arry[i].Enabled;
					MobileName = arry[i].Name;
					MobileLength = arry[i].Length;
				}
				if (arry[i].Property == "Phone") {
					PhoneHide = arry[i].Enabled;
					PhoneName = arry[i].Name;
					PhoneLength = arry[i].Length;
				}
				if (arry[i].Property == "Email") {
					EmailHide = arry[i].Enabled;
					EmailName = arry[i].Name;
					EmailLength = arry[i].Length;
				}
				if (arry[i].Property == "Job") {
					JobHide = arry[i].Enabled;
					JobName = arry[i].Name;
					JobLength = arry[i].Length;
				}
				if (arry[i].Property == "JobLevel") {
					JobLevelHide = arry[i].Enabled;
					JobLevelName = arry[i].Name;
					JobLevelLength = arry[i].Length;
				}
				if (arry[i].Property == "Education") {
					EducationHide = arry[i].Enabled;
					EducationName = arry[i].Name;
					EducationLength = arry[i].Length;
				}
				if (arry[i].Property == "Nation") {
					NationHide = arry[i].Enabled;
					NationName = arry[i].Name;
					NationLength = arry[i].Length;
				}
				if (arry[i].Property == "Country") {
					CountryHide = arry[i].Enabled;
					CountryName = arry[i].Name;
					CountryLength = arry[i].Length;
				}
				if (arry[i].Property == "NativePlace") {
					NativePlaceHide = arry[i].Enabled;
					NativePlaceName = arry[i].Name;
					NativePlaceLength = arry[i].Length;
				}
				if (arry[i].Property == "Address") {
					AddressHide = arry[i].Enabled;
					AddressName = arry[i].Name;
					AddressLength = arry[i].Length;
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
	table();
	loadData();
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
	if (IdTypeHide) {
		$("#iIdTypeDiv").hide();
		$("#uIdTypeDiv").hide();
	} else {
		$("#iIdTypeLabel").text(IdTypeName + "：");
		$("#uIdTypeLabel").text(IdTypeName + "：");
	}
	;
	if (IdNumberHide) {
		$("#iIdNumberDiv").hide();
		$("#uIdNumberDiv").hide();
	} else {
		$("#iIdNumberLabel").text(IdNumberName + "：");
		$("#uIdNumberLabel").text(IdNumberName + "：");
	}
	;
	if (SexHide) {
		$("#iSexDiv").hide();
		$("#uSexDiv").hide();
	} else {
		$("#iSexLabel").text(SexName + "：");
		$("#uSexLabel").text(SexName + "：");
	}
	;
	if (BirthdayHide) {
		$("#iBirthdayDiv").hide();
		$("#uBirthdayDiv").hide();
	} else {
		$("#iBirthdayLabel").text(BirthdayName + "：");
		$("#uBirthdayLabel").text(BirthdayName + "：");
	}
	;
	if (MobileHide) {
		$("#sMobileDiv").hide();
		$("#iMobileDiv").hide();
		$("#uMobileDiv").hide();
	} else {
		$("#sMobileLabel").text(MobileName + "：");
		$("#iMobileLabel").text(MobileName + "：");
		$("#uMobileLabel").text(MobileName + "：");
	}
	;
	if (PhoneHide) {
		$("#iPhoneDiv").hide();
		$("#uPhoneDiv").hide();
	} else {
		$("#iPhoneLabel").text(PhoneName + "：");
		$("#uPhoneLabel").text(PhoneName + "：");
	}
	;
	if (EmailHide) {
		$("#iEmailDiv").hide();
		$("#uEmailDiv").hide();
	} else {
		$("#iEmailLabel").text(EmailName + "：");
		$("#uEmailLabel").text(EmailName + "：");
	}
	;
	if (JobHide) {
		$("#iJobDiv").hide();
		$("#uJobDiv").hide();
	} else {
		$("#iJobLabel").text(JobName + "：");
		$("#uJobLabel").text(JobName + "：");
	}
	;
	if (JobLevelHide) {
		$("#iJobLevelDiv").hide();
		$("#uJobLevelDiv").hide();
	} else {
		$("#iJobLevelLabel").text(JobLevelName + "：");
		$("#uJobLevelLabel").text(JobLevelName + "：");
	}
	;
	if (EducationHide) {
		$("#iEducationDiv").hide();
		$("#uEducationDiv").hide();
	} else {
		$("#iEducationLabel").text(EducationName + "：");
		$("#uEducationLabel").text(EducationName + "：");
	}
	;
	if (NationHide) {
		$("#iNationDiv").hide();
		$("#uNationDiv").hide();
	} else {
		$("#iNationLabel").text(NationName + "：");
		$("#uNationLabel").text(NationName + "：");
	}
	;
	if (CountryHide) {
		$("#iCountryDiv").hide();
		$("#uCountryDiv").hide();
	} else {
		$("#iCountryLabel").text(CountryName + "：");
		$("#uCountryLabel").text(CountryName + "：");
	}
	;
	if (NativePlaceHide) {
		$("#iNativePlaceDiv").hide();
		$("#uNativePlaceDiv").hide();
	} else {
		$("#iNativePlaceLabel").text(NativePlaceName + "：");
		$("#uNativePlaceLabel").text(NativePlaceName + "：");
	}
	;
	if (AddressHide) {
		$("#iAddressDiv").hide();
		$("#uAddressDiv").hide();
	} else {
		$("#iAddressLabel").text(AddressName + "：");
		$("#uAddressLabel").text(AddressName + "：");
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
};

function loadData() {
	$("#UserTypeInnerId").combobox({
		url : "userTypeBox/select.html",
		valueField : "id",
		textField : "text",
		editable : false,
		onLoadSuccess : function() {
			var data = $('#iUserTypeInnerId').combobox('getData');
			$("#iUserTypeInnerId ").combobox('setValue', data[0].id);
		},
		onLoadError : function() {
			showMsg("提示信息", "服务发生异常", true);
		}
	});

	$("#Department").combotree({
		url : "companyDepartmentTree/select.html",
		editable : false
	});

	$("#uUserTypeInnerId").combobox({
		url : "userTypeBox/select.html",
		valueField : "id",
		textField : "text",
		editable : false,
	});
	$("#uXgUserTypeInnerId").combobox({
		url : "userTypeBox/select.html",
		valueField : "id",
		textField : "text",
		editable : false,
	});

	$("#uDepartment").combotree({
		url : "companyDepartmentTree/select.html",
		editable : false
	});
	$("#uXgDepartment").combotree({
		url : "companyDepartmentTree/select.html",
		editable : false
	});

	$("#uAreaList").combotree({
		url : "areaAppTree/select.html",
		editable : false,
		multiple : true,
	});
	$("#uXgAreaList").combotree({
		url : "areaAppTree/select.html",
		editable : false,
		multiple : true,
	});
}

$("#StartTime").focus(function() {
	WdatePicker({
		dateFmt : 'yyyy-MM-dd HH:mm:ss',
		maxDate : "#F{$dp.$D('EndTime')}"
	});
});

$("#EndTime").focus(function() {
	WdatePicker({
		dateFmt : 'yyyy-MM-dd HH:mm:ss',
		minDate : "#F{$dp.$D('StartTime')}"
	});
});

$("#Birthday").focus(function() {
	WdatePicker({
		dateFmt : 'yyyy-MM-dd',
		maxDate : new Date()
	});
});

$("#uStartTime").focus(function() {
	WdatePicker({
		dateFmt : 'yyyy-MM-dd HH:mm:ss',
		maxDate : "#F{$dp.$D('uEndTime')}"
	});
});

$("#uEndTime").focus(function() {
	WdatePicker({
		dateFmt : 'yyyy-MM-dd HH:mm:ss',
		minDate : "#F{$dp.$D('uStartTime')}"
	});
});

$("#uBirthday").focus(function() {
	WdatePicker({
		dateFmt : 'yyyy-MM-dd',
		maxDate : new Date()
	});
});

// 给添加表单默认赋值
function defaultValue() {
	var data = $('#UserTypeInnerId').combobox('getData');
	$("#UserTypeInnerId ").combobox('setValue', data[0].id);
	$("#StartTime").val(new Date().Format("yyyy-MM-dd HH:mm:ss"));
	$("#EndTime").val(dateChange(new Date(), 'year', 20));
	$("#IdType option:first").prop("selected", 'selected');
	$("#Sex option:first").prop("selected", 'selected');
	$("#Education option:first").prop("selected", 'selected');
}

$(function() {
	document.onkeydown = function(e) {
		var ev = document.all ? window.event : e;
		if (ev.keyCode == 13) {
			table();
		}
	}
});

$("#readCard").click(function() {
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
					$("#readCard").removeAttr("disabled");
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

// 点击查询时更新表格
$("#select").click(function() {
	//$("#table").datagrid({pageNumber:1});
	table();
});

// 点击添加时弹出框
$("#insert").click(function() {
	$.ajax({
		type : "Post",
		url : "userMaxmum/select.html",
		dataType : 'json',
		success : function(data) {
			// 提交按钮设置为可用
			$("#readCard").removeAttr("disabled");
			if (data.code == 200) {
				$("#insertDiv").modal("show");
				defaultValue();
			} else {
				// 失败
				showMsg("提示信息", data.msg, true);
			}
		}
	});
});

// 点击修改时操作
$("#update").click(function() {
	var Table = $("#table").datagrid("getSelected");
	if (Table) {
		$("#uUserInnerId").val(Table.UserInnerId);
		$("#uUserName").val(Table.UserName);
		$("#uUserId").val(Table.UserId);
		$("#uUserTypeInnerId").combobox("setValue", Table.UserTypeInnerId);
		$("#uDepartment").combotree("setValue", "department" + Table.DepartmentInnerId);
		$("#uSex").val(Table.Sex);
		if(Table.UState == "有"){
			$("#uUState").val(3);
		}else{
			$("#uUState").val(1);
		}
		$("#uRemark").val(Table.Remark);

		if (Table.AppList != null) {
			var AreaAppList = "";
			var AppList = Table.AppList.split(",");
			$.each(AppList, function(i, k) {
				AreaAppList += "app" + AppList[i] + ",";
			});
			$("#uAreaList").combotree("setValues", AreaAppList);
		}

		$("#updateDiv").modal("show");
	} else {
		showMsg("提示信息", "请选择一条数据进行操作");
	}
});
// 点击修改时操作
$("#xiugai").click(function() {
	var Table = $("#table").datagrid("getSelected");
	if (Table) {
		document.getElementById("uXgImages").src = Table.ImageUrl;
		$("#uXgUserInnerId").val(Table.UserInnerId);
		$("#uXgUserName").val(Table.UserName);
		$("#uXgUserId").val(Table.UserId);
		$("#uXgUserTypeInnerId").combobox("setValue", Table.UserTypeInnerId);
		//$("#uConTimeInnerId").combobox("setValue", Table.ConTimeInnerId);
		$("#uXgDepartment").combotree("setValue", "department" + Table.DepartmentInnerId);
		$("#uXgStartTime").val(Table.StartTime);
		$("#uXgEndTime").val(Table.EndTime);
		$("#uXgIdType").val(Table.IdType);
		$("#uXgIdNumber").val(Table.IdNumber);
		$("#uXgSex").val(Table.Sex);
		$("#uXgBirthday").val(Table.Birthday);
		$("#uXgMobile").val(Table.Mobile);
		$("#uXgPhone").val(Table.Phone);
		$("#uXgEmail").val(Table.Email);
		$("#uXgJob").val(Table.Job);
		$("#uEducation").val(Table.Education);
		$("#uXgNation").val(Table.Nation);
		$("#uXgCountry").val(Table.Country);
		$("#uXgNativePlace").val(Table.NativePlace);
		$("#uXgAddress").val(Table.Address);
		$("#uXgStandbyA").val(Table.StandbyA);
		$("#uXgStandbyB").val(Table.StandbyB);
		$("#uXgStandbyC").val(Table.StandbyC);
		$("#uXgStandbyD").val(Table.StandbyD);
		$("#uXgRemark").val(Table.Remark);

		if (Table.AppList != null) {
			var AreaAppList = "";
			var AppList = Table.AppList.split(",");
			$.each(AppList, function(i, k) {
				AreaAppList += "app" + AppList[i] + ",";
			});
			$("#uXgAreaList").combotree("setValues", AreaAppList);
		}

		$("#updateXgDiv").modal("show");
	} else {
		showMsg("提示信息", "请选择一条数据进行操作");
	}
});

// 点击删除时操作
$("#delete").click(function() {
	var Table = $("#table").datagrid("getSelected");
	if (Table) {
		if (confirm("是否确认删除此人员？")) {
			// 提交按钮设置为不可用，防止重复提交
			$("#delete").attr("disabled", true);
			$.ajax({
				url : "delete.html",
				type : "post",
				data : {
					InnerId : Table.UserInnerId,
					TableName : "System_UserInfo"
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
		if (confirm("是否确认重置此人员密码？")) {
			// 提交按钮设置为不可用，防止重复提交
			$("#reset").attr("disabled", true);
			$.ajax({
				url : "user/updatePWD.html",
				type : "post",
				data : {
					UserInnerId : Table.UserInnerId,
					Password : Table.Password,
					PasswordNew : UserPassword
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
	var DepartmentInnerId = "";
	var arry = $("#sDepartmentInnerId").combotree("getValues");
	$.each(arry, function(i, k) {
		if (arry[i].indexOf("department") >= 0) {
			DepartmentInnerId += arry[i].substring(10, arry[i].length) + ",";
		}
	});
	$("#table").datagrid({
		loadMsg : "正在加载数据，请稍等.....", // 在加载页面时显示数据
		url : "user/select.html", // 发送的url地址
		queryParams :
		// 参数列表
		{
			DepartmentList : DepartmentInnerId,
			UserTypeList : $("#sUserTypeInnerId").combobox("getValues").toString(),
			UserId : $('#sUserId').val(),
			UserName : $('#sUserName').val(),
			Remark : $('#sRemark').val(),
			Mobile : $('#sMobile').val(),
			StandbyA : $('#sStandbyA').val(),
			StandbyB : $('#sStandbyB').val(),
			StandbyC : $('#sStandbyC').val(),
			StandbyD : $('#sStandbyD').val()
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
		onSortColumn : alertColumn,
		columns : [ [ {
			field : 'UserInnerId',
			hidden : true
		}, {
			field : 'Sex',
			hidden : true
		}, {
			field : 'Password',
			hidden : true
		}, {
			field : 'AreaList',
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
			field : 'AppList',
			hidden : true
		}, {
			field : 'StartTime',
			hidden : true
		}, {
			field : 'EndTime',
			hidden : true
		}, {
			field : 'ImageUrl',
			hidden : true
		}, {
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
		},/* {
			field : 'DepartmentName',
			title : '部门',
			sortable : true,
			width : 150
		},*/ {
			field : 'UserTypeName',
			title : '人员类型',
			sortable : true,
			width : 150
		}, {
			field : 'Remark',
			title : RemarkName,
			sortable : true,
			hidden : RemarkHide,
			width : RemarkLength
		}, {
			field : 'IdType',
			title : IdTypeName,
			sortable : true,
			hidden : IdTypeHide,
			width : IdTypeLength
		}, {
			field : 'IdNumber',
			title : IdNumberName,
			sortable : true,
			hidden : IdNumberHide,
			width : IdNumberLength
		}, {
			field : 'SexCh',
			title : SexName,
			sortable : true,
			hidden : SexHide,
			width : SexLength
		}, {
			field : 'Birthday',
			title : BirthdayName,
			sortable : true,
			hidden : BirthdayHide,
			width : BirthdayLength
		}, {
			field : 'Mobile',
			title : MobileName,
			sortable : true,
			hidden : MobileHide,
			width : MobileLength
		}, {
			field : 'Phone',
			title : PhoneName,
			sortable : true,
			hidden : PhoneHide,
			width : PhoneLength
		}, {
			field : 'Email',
			title : EmailName,
			sortable : true,
			hidden : EmailHide,
			width : EmailLength
		}, {
			field : 'Job',
			title : JobName,
			sortable : true,
			hidden : JobHide,
			width : JobLength
		}, {
			field : 'JobLevel',
			title : JobLevelName,
			sortable : true,
			hidden : JobLevelHide,
			width : JobLevelLength
		}, {
			field : 'Education',
			title : EducationName,
			sortable : true,
			hidden : EducationHide,
			width : EducationLength
		}, {
			field : 'Nation',
			title : NationName,
			sortable : true,
			hidden : NationHide,
			width : NationLength
		}, {
			field : 'Country',
			title : CountryName,
			sortable : true,
			hidden : CountryHide,
			width : CountryLength
		}, {
			field : 'NativePlace',
			title : NativePlaceName,
			sortable : true,
			hidden : NativePlaceHide,
			width : NativePlaceLength
		}, {
			field : 'Address',
			title : AddressName,
			sortable : true,
			hidden : AddressHide,
			width : AddressLength
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
		}, {
			field : 'UState',
			title : '预约权限',
			width : 100
		} ] ],
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
	$("#table").datagrid("clearSelections");
}
function alertColumn(sort1, order1) {
	sort = sort1;
	order = order1;
}

$("#export").click(function() {
	var DepartmentInnerId = "";
	var arry = $("#sDepartmentInnerId").combotree("getValues");
	$.each(arry, function(i, k) {
		if (arry[i].indexOf("department") >= 0) {
			DepartmentInnerId += arry[i].substring(10, arry[i].length) + ",";
		}
	});
	$("#export").attr("disabled", true);
	$.ajax({
		url : "userExcel/select.html",
		type : "post",
		data : {
			DepartmentList : DepartmentInnerId,
			UserTypeList : $("#sUserTypeInnerId").combobox("getValues").toString(),
			UserId : $('#sUserId').val(),
			UserName : $('#sUserName').val(),
			Remark : $('#sRemark').val(),
			StandbyA : $('#sStandbyA').val(),
			StandbyB : $('#sStandbyB').val(),
			StandbyC : $('#sStandbyC').val(),
			StandbyD : $('#sStandbyD').val(),
			sort : sort,
			order : order
		},
		dataType : "json",
		beforeSend : function() {
			var h = document.body.clientHeight;
			$("<div class=\"datagrid-mask\"></div>").css({
				display : "block",
				width : "100%",
				height : h
			}).appendTo("body");
			$("<div class=\"datagrid-mask-msg\"></div>").html("正在处理，请稍候。。。").appendTo("body").css({
				display : "block",
				left : ($(document.body).outerWidth(true) - 190) / 2,
				top : (h - 175) / 2,
				"font-size" : 12,
				"height" : "40px"
			});
		},
		success : function(data) {
			// 提交按钮设置为可用
			$("#export").removeAttr("disabled");
			$('.datagrid-mask-msg').remove();
			$('.datagrid-mask').remove();
			var html = "";
			if (data.code == 200) {
				// 成功
				showMsg("提示信息", data.msg);
				$("#xiazai").html("数据已导出：<a href='" + data.data + "'>下载</a>");
				$("#myXiazai").modal("show");
			} else {
				// 失败
				showMsg("提示信息", data.msg, true);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			// 提交按钮设置为可用
			$("#export").removeAttr("disabled");
			$('.datagrid-mask-msg').remove();
			$('.datagrid-mask').remove();
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
});

//修改提交
$("#updateXgSubmit").click(function() {
	$("#uXgUserId").removeData("previousValue");
	$("#uXgMobile").removeData("previousValue");
	$("#updateFormXg").validate({
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
			UserName : {
				required : true,
				maxlength : 50,
				isContainsSpecialChar : true,
			},
			UserId : {
				required : true,
				maxlength : 50,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#uXgUserId").val().replace(/\s+/g,"");
						},
						InnerId : function() {
							return $("#uXgUserInnerId").val();
						},
						ColumnName : function() {
							return "UserId";
						}
					}
				},
				isContainsSpecialChar : true,
			},
			StartTime : {
				required : true
			},
			EndTime : {
				required : true
			},
			Remark : {
				maxlength : 4000,
				isContainsSpecialChar : true,
			},
			IdType : {
				maxlength : 4000
			},
			IdNumber : {
				maxlength : 4000,
				isContainsSpecialChar : true,
			},
			Sex : {
				maxlength : 4000
			},
			Birthday : {
				maxlength : 4000
			},
			Mobile : {
				maxlength : 4000,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#uXgMobile").val().replace(/\s+/g,"");
						},
						InnerId : function() {
							return $("#uXgUserInnerId").val();
						},
						ColumnName : function() {
							return "Mobile";
						}
					}
				},
				isContainsSpecialChar : true,
				isMobile : true,
			},
			Phone : {
				maxlength : 4000,
				isContainsSpecialChar : true,
			},
			Email : {
				maxlength : 4000
			},
			Job : {
				maxlength : 4000,
				isContainsSpecialChar : true,
			},
			JobLevel : {
				maxlength : 4000,
				isContainsSpecialChar : true,
			},
			Education : {
				maxlength : 4000,
				isContainsSpecialChar : true,
			},
			Nation : {
				maxlength : 4000,
				isContainsSpecialChar : true,
			},
			Country : {
				maxlength : 4000,
				isContainsSpecialChar : true,
			},
			NativePlace : {
				maxlength : 4000,
				isContainsSpecialChar : true,
			},
			Address : {
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
			UserName : {
				required : "请输入人员姓名",
				maxlength : "最大不超过50个字符"
			},
			UserId : {
				required : "请输入人员编号",
				maxlength : "最大不超过50个字符",
				remote : "人员编号重复,请重新输入"
			},
			StartTime : {
				required : "请选择有效期开始时间"
			},
			EndTime : {
				required : "请选择有效期结束时间"
			},
			Remark : {
				maxlength : "最大不超过4000个字符"
			},
			IdType : {
				maxlength : "最大不超过4000个字符"
			},
			IdNumber : {
				maxlength : "最大不超过4000个字符"
			},
			Sex : {
				maxlength : "最大不超过4000个字符"
			},
			Birthday : {
				maxlength : "最大不超过4000个字符"
			},
			Mobile : {
				maxlength : "最大不超过4000个字符",
				remote : "手机号重复,请重新输入"
			},
			Phone : {
				maxlength : "最大不超过4000个字符"
			},
			Email : {
				maxlength : "最大不超过4000个字符"
			},
			Job : {
				maxlength : "最大不超过4000个字符"
			},
			JobLevel : {
				maxlength : "最大不超过4000个字符"
			},
			Education : {
				maxlength : "最大不超过4000个字符"
			},
			Nation : {
				maxlength : "最大不超过4000个字符"
			},
			Country : {
				maxlength : "最大不超过4000个字符"
			},
			NativePlace : {
				maxlength : "最大不超过4000个字符"
			},
			Address : {
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
			if (!$("#uXgUserTypeInnerId").combobox("getValue")) {
				showMsg("提示信息", "请选择人员类型", true);
				return false;
			}
			if (!$("#uXgDepartment").combobox("getValue")) {
				showMsg("提示信息", "请选择部门", true);
				return false;
			}
			var DepartmentInnerId = $("#uXgDepartment").combotree("getValue");
			if (DepartmentInnerId.indexOf("company") >= 0) {
				showMsg("提示信息", "请选择正确的部门 信息", true);
				return false;
			}
			/*if (!$("#uConTimeInnerId").combobox("getValue")) {
				showMsg("提示信息", "请选择就餐时间段", true);
				return false;
			}*/
			if ($("#uXgHeadFile").val() == "") {
				var AreaList = "";
				var AppList = "";
				var tree = $("#uXgAreaList").combotree('tree'); // 得到树对象
				var arry = tree.tree('getChecked', [ 'checked', 'indeterminate' ]);
				$.each(arry, function(i, k) {
					if (arry[i].id.indexOf("area") >= 0) {
						AreaList += arry[i].id.substring(4, arry[i].id.length) + ",";
					}
					if (arry[i].id.indexOf("app") >= 0) {
						AppList += arry[i].id.substring(3, arry[i].id.length) + ",";
					}
				});
				// 提交按钮设置为不可用，防止重复提交
				$("#updateXgSubmit").attr("disabled", true);
				$.ajax({
					url : "user/update.html",
					type : "post",
					data : {
						UserInnerId : $("#uXgUserInnerId").val(),
						UserName : $("#uXgUserName").val().replace(/\s+/g,""),
						UserId : $("#uXgUserId").val().replace(/\s+/g,""),
						UserTypeInnerId : $("#uXgUserTypeInnerId").combobox("getValue"),
						DepartmentInnerId : DepartmentInnerId.substring(10, DepartmentInnerId.length),
						AreaList : AreaList,
						AppList : AppList,
						StartTime : $("#uXgStartTime").val(),
						EndTime : $("#uXgEndTime").val(),
						IdType : $("#uXgIdType").val(),
						IdNumber : $("#uXgIdNumber").val().replace(/\s+/g,""),
						Sex : $("#uXgSex").val(),
						Birthday : $("#uXgBirthday").val(),
						Mobile : $("#uXgMobile").val().replace(/\s+/g,""),
						Phone : $("#uXgPhone").val().replace(/\s+/g,""),
						Email : $("#uXgEmail").val().replace(/\s+/g,""),
						Job : $("#uXgJob").val().replace(/\s+/g,""),
						Education : $("#uXgEducation").val().replace(/\s+/g,""),
						Nation : $("#uXgNation").val().replace(/\s+/g,""),
						Country : $("#uXgCountry").val().replace(/\s+/g,""),
						NativePlace : $("#uXgNativePlace").val().replace(/\s+/g,""),
						Address : $("#uXgAddress").val().replace(/\s+/g,""),
						StandbyA : $("#uXgStandbyA").val().replace(/\s+/g,""),
						StandbyB : $("#uXgStandbyB").val().replace(/\s+/g,""),
						StandbyC : $("#uXgStandbyC").val().replace(/\s+/g,""),
						StandbyD : $("#uXgStandbyD").val().replace(/\s+/g,""),
						Remark : $("#uXgRemark").val().replace(/\s+/g,""),
						//ConTimeInnerId : $("#uConTimeInnerId").combobox("getValue")
					},
					dataType : "json",
					success : function(data) {
						// 提交按钮设置为可用
						$("#updateXgSubmit").removeAttr("disabled");
						if (data.code == 200) {
							// 成功
							showMsg("提示信息", data.msg);
							// 隐藏下拉框
							$("#updateXgDiv").modal("hide");
							// 重载表格数据
							table();
							// 清空form表单
							$(':input', '#updateFormXg').not(':button, :submit, :reset, :hidden').val('').removeAttr('checked').removeAttr('selected');
							$("#uXgUserTypeInnerId").combobox("clear");
							$("#uXgDepartment").combotree("clear");
							$('#uXgImages').attr('src', "");
						} else {
							// 失败
							showMsg("提示信息", data.msg, true);
						}
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						// 提交按钮设置为可用
						$("#updateXgSubmit").removeAttr("disabled");
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
			} else {
				$("#updateXgSubmit").attr("disabled", true);
				$("#uXgDepartmentInnerId").val(DepartmentInnerId.substring(10, DepartmentInnerId.length));
				$("#updateFormXg").ajaxSubmit({
					url : "user/update.html",
					type : "post",
					dataType : "json",
					beforeSubmit : function() {
					},
					success : function(data) {
						// 提交按钮设置为可用
						$("#updateXgSubmit").removeAttr("disabled");
						if (data.code == 200) {
							// 成功
							showMsg("提示信息", data.msg);
							// 隐藏下拉框
							$("#updateXgDiv").modal("hide");
							// 重载表格数据
							table();
							// 清空form表单
							$(':input', '#insertForm').not(':button, :submit, :reset, :hidden').val('').removeAttr('checked').removeAttr('selected');
							$("#uXgUserTypeInnerId").combobox("clear");
							$("#uXgDepartment").combotree("clear");
							$('#uXgImages').attr('src', "");
						} else {
							// 失败
							showMsg("提示信息", data.msg, true);
						}
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						// 提交按钮设置为可用
						$("#updateXgSubmit").removeAttr("disabled");
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
		}
	});
});


// 添加提交
$("#insertSubmit").click(function() {
	$("#UserId").removeData("previousValue");
	$("#Mobile").removeData("previousValue");
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
			UserName : {
				required : true,
				maxlength : 50,
				isContainsSpecialChar : true,
			},
			UserId : {
				required : true,
				maxlength : 50,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#UserId").val().replace(/\s+/g,"");
						},
						InnerId : function() {
							return -1;
						},
						ColumnName : function() {
							return "UserId";
						}
					}
				},
				isContainsSpecialChar : true,
			},
			StartTime : {
				required : true
			},
			EndTime : {
				required : true
			},
			Remark : {
				maxlength : 4000,
				isContainsSpecialChar : true,
			},
			IdType : {
				maxlength : 4000,
				isContainsSpecialChar : true,
			},
			IdNumber : {
				maxlength : 4000,
				isContainsSpecialChar : true,
			},
			Sex : {
				maxlength : 4000
			},
			Birthday : {
				maxlength : 4000
			},
			Mobile : {
				maxlength : 4000,
				remote : {
					type : "POST",
					url : "remoteColumn.html",
					data : {
						Column : function() {
							return $("#Mobile").val().replace(/\s+/g,"");
						},
						InnerId : function() {
							return -1;
						},
						ColumnName : function() {
							return "Mobile";
						}
					}
				},
				isContainsSpecialChar : true,
				isMobile : true,
			},
			Phone : {
				maxlength : 4000,
				isContainsSpecialChar : true,
			},
			Email : {
				maxlength : 4000
			},
			Job : {
				maxlength : 4000,
				isContainsSpecialChar : true,
			},
			JobLevel : {
				maxlength : 4000,
				isContainsSpecialChar : true,
			},
			Education : {
				maxlength : 4000,
				isContainsSpecialChar : true,
			},
			Nation : {
				maxlength : 4000,
				isContainsSpecialChar : true,
			},
			Country : {
				maxlength : 4000,
				isContainsSpecialChar : true,
			},
			NativePlace : {
				maxlength : 4000,
				isContainsSpecialChar : true,
			},
			Address : {
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
			UserName : {
				required : "请输入人员姓名",
				maxlength : "最大不超过50个字符"
			},
			UserId : {
				required : "请输入人员编号",
				maxlength : "最大不超过50个字符",
				remote : "人员编号重复,请重新输入"
			},
			StartTime : {
				required : "必须选择有效期开始时间"
			},
			EndTime : {
				required : "必须选择有效期结束时间"
			},
			Remark : {
				maxlength : "最大不超过4000个字符"
			},
			IdType : {
				maxlength : "最大不超过4000个字符"
			},
			IdNumber : {
				maxlength : "最大不超过4000个字符"
			},
			Sex : {
				maxlength : "最大不超过4000个字符"
			},
			Birthday : {
				maxlength : "最大不超过4000个字符"
			},
			Mobile : {
				maxlength : "最大不超过4000个字符",
				remote : "手机号重复,请重新输入"
			},
			Phone : {
				maxlength : "最大不超过4000个字符"
			},
			Email : {
				maxlength : "最大不超过4000个字符"
			},
			Job : {
				maxlength : "最大不超过4000个字符"
			},
			JobLevel : {
				maxlength : "最大不超过4000个字符"
			},
			Education : {
				maxlength : "最大不超过4000个字符"
			},
			Nation : {
				maxlength : "最大不超过4000个字符"
			},
			Country : {
				maxlength : "最大不超过4000个字符"
			},
			NativePlace : {
				maxlength : "最大不超过4000个字符"
			},
			Address : {
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
			if (!$("#UserTypeInnerId").combobox("getValue")) {
				showMsg("提示信息", "请选择人员类型", true);
				return false;
			}
			if (!$("#Department").combotree("getValue")) {
				showMsg("提示信息", "请选择部门", true);
				return false;
			}
			var Department = $("#Department").combotree("getValue");
			if (Department.indexOf("company") >= 0) {
				showMsg("提示信息", "请选择正确的 部门信息", true);
				return false;
			}
			if ($("#HeadFile").val() == "") {
				// 提交按钮设置为不可用，防止重复提交
				$("#insertSubmit").attr("disabled", true);
				$.ajax({
					url : "user/insert.html",
					type : "post",
					data : {
						UserName : $("#UserName").val().replace(/\s+/g,""),
						UserId : $("#UserId").val().replace(/\s+/g,""),
						UserTypeInnerId : $("#UserTypeInnerId").combobox("getValue"),
						DepartmentInnerId : Department.substring(10, Department.length),
						Password : UserPassword,
						StartTime : $("#StartTime").val(),
						EndTime : $("#EndTime").val(),
						IdType : $("#IdType").val(),
						IdNumber : $("#IdNumber").val().replace(/\s+/g,""),
						Sex : $("#Sex").val(),
						Birthday : $("#Birthday").val(),
						Mobile : $("#Mobile").val().replace(/\s+/g,""),
						Phone : $("#Phone").val().replace(/\s+/g,""),
						Email : $("#Email").val().replace(/\s+/g,""),
						Job : $("#Job").val().replace(/\s+/g,""),
						JobLevel : $("#JobLevel").val().replace(/\s+/g,""),
						Education : $("#Education").val().replace(/\s+/g,""),
						Nation : $("#Nation").val().replace(/\s+/g,""),
						Country : $("#Country").val().replace(/\s+/g,""),
						NativePlace : $("#NativePlace").val().replace(/\s+/g,""),
						Address : $("#Address").val().replace(/\s+/g,""),
						StandbyA : $("#StandbyA").val().replace(/\s+/g,""),
						StandbyB : $("#StandbyB").val().replace(/\s+/g,""),
						StandbyC : $("#StandbyC").val().replace(/\s+/g,""),
						StandbyD : $("#StandbyD").val().replace(/\s+/g,""),
						Remark : $("#Remark").val().replace(/\s+/g,"")
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
							$("#UserTypeInnerId").combobox("clear");
							$("#Department").combotree("clear");
							$('#Images').attr('src', "");
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
			} else {
				$("#insertSubmit").attr("disabled", true);
				$("#DepartmentInnerId").val(Department.substring(10, Department.length));
				$("#Password").val(UserPassword);
				$("#insertForm").ajaxSubmit({
					url : "user/insert.html",
					type : "post",
					dataType : "json",
					beforeSubmit : function() {
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
							$("#UserTypeInnerId").combobox("clear");
							$("#Department").combotree("clear");
							$('#Images').attr('src', "");
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

		}
	});
});

// 修改提交
$("#updateSubmit").click(function() {
	
				$("#updateSubmit").attr("disabled", true);
				$.ajax({
					url : "userShouquan/update.html",
					type : "post",
					data : {
						UserInnerId : $("#uUserInnerId").val(),
						UState : $("#uUState").val(),
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
							$("#uUserTypeInnerId").combobox("clear");
							$("#uDepartment").combotree("clear");
							$('#uImages').attr('src', "");
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
			
});

function isValidateFile(obj) {
	var extend = obj.value.substring(obj.value.lastIndexOf(".") + 1);
	if (extend == "") {
		// $.messager.alert('温馨提示', '请选择要上传的头像');
	} else {
		if (!(extend == "jpg" || extend == "JPG")) {
			$.messager.alert('温馨提示', '请上传后缀名为jpg的文件!');
			obj.value = "";
			var nf = obj.cloneNode(true);
			nf.value = '';
			obj.parentNode.replaceChild(nf, obj);
			return false;
		} else if (obj.files[0].size / 1024 > 2048) {
			$.messager.alert('温馨提示', '您上传的文件大小超出了2M限制');
			obj.value = "";
			return false;
		} else {
			if (obj.files && obj.files[0]) {
				var reader = new FileReader();
				reader.onload = function(e) {
					$('#Images').attr('src', e.target.result);
				};
				reader.readAsDataURL(obj.files[0]);
			}
		}
	}
	return true;
}

function uisValidateFile(obj) {
	var extend = obj.value.substring(obj.value.lastIndexOf(".") + 1);
	if (extend == "") {
		// $.messager.alert('温馨提示', '请选择要上传的头像');
	} else {
		if (!(extend == "jpg" || extend == "JPG")) {
			$.messager.alert('温馨提示', '请上传后缀名为jpg的文件!');
			obj.value = "";
			var nf = obj.cloneNode(true);
			nf.value = '';
			obj.parentNode.replaceChild(nf, obj);
			return false;
		} else if (obj.files[0].size / 1024 > 2048) {
			$.messager.alert('温馨提示', '您上传的文件大小超出了2M限制');
			obj.value = "";
			return false;
		} else {
			if (obj.files && obj.files[0]) {
				var reader = new FileReader();
				reader.onload = function(e) {
					$('#uImages').attr('src', e.target.result);
				};
				reader.readAsDataURL(obj.files[0]);
			}
		}
	}
	return true;
}

// 点击各应用有效期时操作
$("#time").click(function() {
	var Table = $("#table").datagrid("getSelected");
	if (Table) {
		$("#time").attr("disabled", true);
		$.ajax({
			url : "userPer/select.html",
			type : "post",
			data : {
				UserInnerId : Table.UserInnerId
			},
			dataType : "json",
			success : function(data) {
				// 提交按钮设置为可用
				$("#time").removeAttr("disabled");
				var html = "";
				$.each(data, function(key, value) {
					if(value.AppName != "无"){
						
						html += "<tr>" + "<td  style='display: none'>" + value.UserPerInnerId + "</td>" + "<td>" + value.UserName + "</td>" + "<td>" + value.AreaName + "</td>" + "<td>" + value.AppName + "</td>" + "<td>" + "<input type='text'  id=" + "Start" + value.UserPerInnerId + " onfocus='WdatePicker({})'  style='width:160px;' disabled>" + "</td>" + "<td>" + "<input type='text'  id=" + "End" + value.UserPerInnerId + " onfocus='WdatePicker({})'  style='width:160px;' disabled>" + "</td>" + "</tr>";
					}

				});
				$("#timeTable").html(html);

				$.each(data, function(key, value) {

					$("#Start" + value.UserPerInnerId).val(value.StartTime);
					$("#End" + value.UserPerInnerId).val(value.EndTime);
				});
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				// 提交按钮设置为可用
				$("#time").removeAttr("disabled");
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
		$("#timeDiv").modal("show");
	} else {
		showMsg("提示信息", "请选择一条数据进行操作");
	}
});

$("#timeSubmit").click(function() {
	str = '';
	$("#timeTable").find("td").each(function(i) {
		if (i % 6 == 0) {
			str += $(this).text() + ",";
			t = $(this).text();
			str += $("#Start" + t).val() + ",";
			str += $("#End" + t).val() + ";";
		}
	});
	if (str == '') {
		showMsg("提示信息", "没有任何应用，不用提交");
	} else {
		$("#timeSubmit").attr("disabled", true);
		$.ajax({
			url : "userPer/update.html",
			type : "post",
			data : {
				str : str
			},
			dataType : "json",
			success : function(data) {
				// 提交按钮设置为可用
				$("#timeSubmit").removeAttr("disabled");
				if (data.code == 200) {
					// 成功
					showMsg("提示信息", data.msg);
					// 隐藏下拉框
					$("#timeDiv").modal("hide");
				} else {
					// 失败
					showMsg("提示信息", data.msg, true);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				// 提交按钮设置为可用
				$("#timeSubmit").removeAttr("disabled");
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