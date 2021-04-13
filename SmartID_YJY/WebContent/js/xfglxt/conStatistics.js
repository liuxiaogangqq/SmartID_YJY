var columns = new Array();
// 加载时载入表格
window.onload = function() {
	$("#sStartTime").val(dateChange(new Date(), 'date', -1));
	$("#sEndTime").val(new Date().Format("yyyy-MM-dd HH:mm:ss"));
	$.ajax({
		type : "Post",
		url : 'merchantAppTree/select.html',
		async : false,
		dataType : 'json',
		success : function(data) {
			// 生成第一层表头
			// 第一列，空，显示统计方式
			var cols = new Array();
			colData = new Object();
			colData.title = "";
			cols.push(colData);
			// 循环区域，放入第一层表头
			$.each(data, function(i, value) {
				colData = new Object();
				colData.title = value.text;
				var length = 1 + value.children.length;
				$.each(value.children, function(i, value) {
					// 循环本区域的应用
					length += value.children.length;

				});
				colData.colspan = length;
				cols.push(colData);
			});
			// 最后的总计
			colData = new Object();
			colData.title = "";
			cols.push(colData);
			columns.push(cols);

			// 生成第二层表头
			// 第一列，空，显示统计方式
			cols = new Array();
			colData = new Object();
			colData.title = "";
			cols.push(colData);
			$.each(data, function(i, value) {
				// 循环应用，放入第一层表头
				$.each(value.children, function(i, value1) {
					colData = new Object();
					colData.title = value1.text;
					colData.colspan = value1.children.length + 1;
					cols.push(colData);
				});
				colData = new Object();
				colData.title = value.text + "总计";
				cols.push(colData);
			});
			// 最后的总计
			colData = new Object();
			colData.title = "";
			cols.push(colData);
			columns.push(cols);

			// 生成第三层表头
			// 第一列，空，显示统计方式
			cols = new Array();
			colData = new Object();
			colData.field = "RowName";
			colData.title = "统计方式";
			cols.push(colData);
			// 循环商户，放入第三层表头
			$.each(data, function(i, value) {
				$.each(value.children, function(i, value1) {
					$.each(value1.children, function(i, value2) {
						colData = new Object();
						colData.field = value2.id;
						colData.title = value2.text;
						cols.push(colData);
					});
					colData = new Object();
					colData.styler = function(value, row, index) {
						return 'color:blue';
					};
					colData.field = value1.id;
					colData.title = value1.text + "总计";
					cols.push(colData);
				});
				colData = new Object();
				colData.styler = function(value, row, index) {
					return 'color:red';
				};
				colData.field = value.id;
				colData.title = value.text + "总计";
				cols.push(colData);
			});
			// 最后的总计
			colData = new Object();
			colData.styler = function(value, row, index) {
				return 'color:red';
			};
			colData.field = 'count';
			colData.title = "合计";
			cols.push(colData);
			columns.push(cols);
		}
	});
	table();
};

$("#sConTypeList").combobox({
	url : "conTypeBox/select.html",
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

$('#sConWay').multiselect({
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

$('#sConPattern').multiselect({
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
	$("#table").datagrid("clearSelections");
	if ($('#sRowName').val() == "Money" && $('#sType').val() == "count") {
		showMsg("提示信息", "按现金、补贴统计不支持人次报表");
	} else {
		var DepartmentInnerId = "";
		var arry = $("#sDepartmentInnerId").combotree("getValues");
		$.each(arry, function(i, k) {
			if (arry[i].indexOf("department") >= 0) {
				DepartmentInnerId += arry[i].substring(10, arry[i].length) + ",";
			}
		});
		var ConWay = $('#sConWay').val();
		if (ConWay) {
			ConWay = $('#sConWay').val().toString();
		} else {
			ConWay = "";
		}
		var ConPattern = $('#sConPattern').val();
		if (ConPattern) {
			ConPattern = $('#sConPattern').val().toString();
		} else {
			ConPattern = "";
		}
		$("#select").attr("disabled", true);
		$("#table").datagrid({
			loadMsg : "正在加载数据，请稍等.....", // 在加载页面时显示数据
			url : "conStatistics/select.html", // 发送的url地址
			queryParams :
			// 参数列表
			{
				UserName : $('#sUserName').val(),
				UserId : $('#sUserId').val(),
				DepartmentInnerId : DepartmentInnerId,
				StartTime : $('#sStartTime').val(),
				EndTime : $('#sEndTime').val(),
				TimeType : $('#sTimeType').val(),
				ConTypeList : $("#sConTypeList").combobox("getValues").toString(),
				ConWay : ConWay,
				ConPattern : ConPattern,
				RowName : $('#sRowName').val(),
				Type : $('#sType').val()
			},
			method : 'post',
			idField : 'ConLogInnerId',
			fitColumns : true,
			singleSelect : true,
			height : TableSize * 35 + 68,
			columns : columns,
			onLoadSuccess : function(data) {
				$("#select").removeAttr("disabled");
				if (data.rows.length == 0) {
					var body = $(this).data().datagrid.dc.body2;
					body.find('table tbody').append('<tr><td width="' + body.width() + '" style="height: 35px; text-align: center;"><h2>此条件下没有数据</h2></td></tr>');
				}
			}
		});
	}
}

$("#export").click(function() {
	if ($('#sRowName').val() == "Money" && $('#sType').val() == "count") {
		showMsg("提示信息", "按现金、补贴统计不支持人次报表");
	} else {
		var DepartmentInnerId = "";
		var arry = $("#sDepartmentInnerId").combotree("getValues");
		$.each(arry, function(i, k) {
			if (arry[i].indexOf("department") >= 0) {
				DepartmentInnerId += arry[i].substring(10, arry[i].length) + ",";
			}
		});
		var ConWay = $('#sConWay').val();
		if (ConWay) {
			ConWay = $('#sConWay').val().toString();
		} else {
			ConWay = "";
		}
		var ConPattern = $('#sConPattern').val();
		if (ConPattern) {
			ConPattern = $('#sConPattern').val().toString();
		} else {
			ConPattern = "";
		}
	$("#export").attr("disabled", true);
	$.ajax({
		url : "conStatisticsExcel/select.html",
		type : "post",
		data : {
			UserName : $('#sUserName').val(),
			UserId : $('#sUserId').val(),
			DepartmentInnerId : DepartmentInnerId,
			StartTime : $('#sStartTime').val(),
			EndTime : $('#sEndTime').val(),
			TimeType : $('#sTimeType').val(),
			ConTypeList : $("#sConTypeList").combobox("getValues").toString(),
			ConWay : ConWay,
			ConPattern : ConPattern,
			RowName : $('#sRowName').val(),
			Type : $('#sType').val()
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
	}
});
