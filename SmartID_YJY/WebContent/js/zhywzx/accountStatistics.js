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
	$("#sStartTime").val(dateChange(new Date(), 'date', -1));
	$("#sEndTime").val(new Date().Format("yyyy-MM-dd HH:mm:ss"));
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
	// if (RemarkHide) {
	// $("#sRemarkDiv").css('display','none');
	// } else {
	// $("#sRemarkLabel").text(RemarkName + "：");
	// }
	// ;
	// if (StandbyAHide) {
	// $("#sStandbyADiv").css('display','none');
	// } else {
	// $("#sStandbyALabel").text(StandbyAName + "：");
	// }
	// ;
	// if (StandbyBHide) {
	// $("#sStandbyBDiv").css('display','none');
	// } else {
	// $("#sStandbyBLabel").text(StandbyBName + "：");
	// }
	// ;
	// if (StandbyCHide) {
	// $("#sStandbyCDiv").css('display','none');
	// } else {
	// $("#sStandbyCLabel").text(StandbyCName + "：");
	// }
	// ;
	// if (StandbyDHide) {
	// $("#sStandbyDDiv").css('display','none');
	// } else {
	// $("#sStandbyDLabel").text(StandbyDName + "：");
	// }
	// ;
	table();
};

$("#sOperator").combobox({
	url : "operatorAll/select.html",
	valueField : "id",
	textField : "text",
	editable : false,
	multiple : true,
});


$("#sStartTime").focus(function() {
	WdatePicker({
		dateFmt : 'yyyy-MM-dd HH:mm:ss',
		maxDate : "#F{$dp.$D('sEndTime')}"
	});
});

$("#sEndTime").focus(function() {
	WdatePicker({
		dateFmt : 'yyyy-MM-dd HH:mm:ss',
		minDate : "#F{$dp.$D('sStartTime')}"
	});
});

$('#sRechargeType').multiselect({
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

$('#sMoneySource').multiselect({
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

// 点击查询时更新表格
$("#select").click(function() {
	//$("#table").datagrid({pageNumber:1});
	table();
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
	var AccountTypeInnerId = $('#sAccountTypeInnerId').val();
	if (AccountTypeInnerId) {
		AccountTypeInnerId = $('#sAccountTypeInnerId').val().toString();
	} else {
		AccountTypeInnerId = "";
	}
	var InOrOut = $('#sInOrOut').val();
	if (InOrOut) {
		InOrOut = $('#sInOrOut').val().toString();
	} else {
		InOrOut = "";
	}
	var RechargeType = $('#sRechargeType').val();
	if (RechargeType) {
		RechargeType = $('#sRechargeType').val().toString();
	} else {
		RechargeType = "";
	}
	var MoneySource = $('#sMoneySource').val();
	if (MoneySource) {
		MoneySource = $('#sMoneySource').val().toString();
	} else {
		MoneySource = "";
	}
	$("#table").datagrid("clearSelections");
	$("#table").datagrid({
		loadMsg : "正在加载数据，请稍等.....", // 在加载页面时显示数据
		url : "accountStatistics/select.html", // 发送的url地址
		queryParams :
		// 参数列表
		{
			DepartmentList : DepartmentInnerId,
			UserId : $('#sUserId').val(),
			UserName : $('#sUserName').val(),
			StartTime : $('#sStartTime').val(),
			EndTime : $('#sEndTime').val(),
			RechargeType : RechargeType,
			MoneySource : MoneySource,
			Remark : $('#sRemark').val(),
			RowName : $('#sRowName').val(),
			Operator : $('#sOperator').combobox("getValues").toString(),
			Type : $('#sType').val(),
			StandbyA : $('#sStandbyA').val(),
			StandbyB : $('#sStandbyB').val(),
			StandbyC : $('#sStandbyC').val(),
			StandbyD : $('#sStandbyD').val()
		},
		method : 'post',
		idField : 'AccountLogInnerId',
		fitColumns : true,
		singleSelect : true,
		height : TableSize * 35 +68,
		columns : [ [ {
			title : ''
		}, {
			title : '现金账户',
			colspan : 3
		}, {
			title : '补贴账户',
			colspan : 3
		}, /*{
			title : '次账户',
			colspan : 3
		}, {
			title : '卡费',
			colspan : 3
		},*/ {
			title : ''
		} ], [ {
			title : '统计方式',
			field : 'RowName',
			width : 150
		}, {
			title : '充值',
			field : 'PersonIn',
			width : 100
		}, {
			title : '减值',
			field : 'PersonOut',
			width : 100
		}, {
			title : '合计',
			field : 'PersonCount',
			styler : function(value, row, index) {
				return 'color:red';
			},
			width : 100
		}, {
			title : '充值',
			field : 'AllowanceIn',
			width : 100
		}, {
			title : '减值',
			field : 'AllowanceOut',
			width : 100
		}, {
			title : '合计',
			field : 'AllowanceCount',
			styler : function(value, row, index) {
				return 'color:red';
			},
			width : 100
		}, /*{
			title : '充值',
			field : 'FavorableIn',
			width : 100
		}, {
			title : '减值',
			field : 'FavorableOut',
			width : 100
		}, {
			title : '合计',
			field : 'FavorableCount',
			styler : function(value, row, index) {
				return 'color:red';
			},
			width : 100
		}, {
			title : '充值',
			field : 'KaIn',
			width : 100
		}, {
			title : '减值',
			field : 'KaOut',
			width : 100
		}, {
			title : '合计',
			field : 'KaCount',
			styler : function(value, row, index) {
				return 'color:red';
			},
			width : 100
		},*/ {
			title : '合计',
			field : 'Count',
			styler : function(value, row, index) {
				return 'color:red';
			},
			width : 100
		} ] ],
		onLoadSuccess : function(data) {
			if (data.rows.length == 0) {
				var body = $(this).data().datagrid.dc.body2;
				body.find('table tbody').append('<tr><td width="' + body.width() + '" style="height: 35px; text-align: center;"><h2>此条件下没有数据</h2></td></tr>');
			}
		}
	});
}


$("#export").click(function() {
	var DepartmentInnerId = "";
	var arry = $("#sDepartmentInnerId").combotree("getValues");
	$.each(arry, function(i, k) {
		if (arry[i].indexOf("department") >= 0) {
			DepartmentInnerId += arry[i].substring(10, arry[i].length) + ",";
		}
	});
	var AccountTypeInnerId = $('#sAccountTypeInnerId').val();
	if (AccountTypeInnerId) {
		AccountTypeInnerId = $('#sAccountTypeInnerId').val().toString();
	} else {
		AccountTypeInnerId = "";
	}
	var InOrOut = $('#sInOrOut').val();
	if (InOrOut) {
		InOrOut = $('#sInOrOut').val().toString();
	} else {
		InOrOut = "";
	}
	var RechargeType = $('#sRechargeType').val();
	if (RechargeType) {
		RechargeType = $('#sRechargeType').val().toString();
	} else {
		RechargeType = "";
	}
	var MoneySource = $('#sMoneySource').val();
	if (MoneySource) {
		MoneySource = $('#sMoneySource').val().toString();
	} else {
		MoneySource = "";
	}
	$("#export").attr("disabled", true);
	$.ajax({
		url : "accountStatisticsExcel/select.html",
		type : "post",
		data : {
			DepartmentList : DepartmentInnerId,
			UserId : $('#sUserId').val(),
			UserName : $('#sUserName').val(),
			StartTime : $('#sStartTime').val(),
			EndTime : $('#sEndTime').val(),
			RechargeType : RechargeType,
			MoneySource : MoneySource,
			Remark : $('#sRemark').val(),
			RowName : $('#sRowName').val(),
			Operator : $('#sOperator').combobox("getValues").toString(),
			Type : $('#sType').val(),
			StandbyA : $('#sStandbyA').val(),
			StandbyB : $('#sStandbyB').val(),
			StandbyC : $('#sStandbyC').val(),
			StandbyD : $('#sStandbyD').val()
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
});
