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
var sort;
var order;

// 加载时载入表格
window.onload = function() {
	$("#sStartTime").val(dateChange(new Date(), 'date', -1));
	$("#sEndTime").val(new Date().Format("yyyy-MM-dd HH:mm:ss"));
	$("#sFlag").combobox("setValue",1);
	table();
};

$("#sFlag").combobox({
	data : [{'id':'1','text':'个人预约'},{'id':'2','text':'协助预约'}],
	valueField : "id",
	textField : "text",
	editable : false,
	multiple : false,
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

// 点击误消费补款时操作
$("#errorConLog").click(function() {
	var Table = $("#table").datagrid("getSelected");
	if (Table) {
		$("#eUserName").val(Table.UserName);
		$("#eUserId").val(Table.UserId);
		/*$("#eCompanyName").val(Table.CompanyName);*/
		$("#eDepartmentName").val(Table.DepartmentName);
		$("#eConLogInnerId").val(Table.ConLogInnerId);
		$("#eConTerminalName").val(Table.ConTerminalName);
		$("#ePersonMoney").val(Table.PersonMoney);
		$("#eAllowanceMoney").val(Table.AllowanceMoney);
		$("#eMoney").val((Table.PersonMoney + Table.AllowanceMoney).toFixed(2));
		$("#errorConLogDiv").modal("show");
	} else {
		showMsg("提示信息", "请选择一条数据进行操作");
	}
});

$("#errorConLogSubmit").click(function() {
	var exp = /^([1-9][\d]{0,5}|0)(\.[\d]{1,2})?$/;
	if (!exp.test($("#eErrorMoney").val())) {
		showMsg("信息校验", "请输入0-999999.99的金额");
	} else {
		var ErrorMoney = $("#eErrorMoney").val();
		var Money = $("#eMoney").val();
		if (Number(ErrorMoney) > Number(Money)) {
			showMsg("信息校验", "补回金额不得大于总消费金额");
		} else {
			$.messager.confirm("确认", "是否确认补款[姓名：[<span style='font-size:18px;color:red'>" + $("#eUserName").val() + "</span>],补回金额：[<span style='font-size:18px;color:red'>" + $("#eErrorMoney").val() + "元</span>]", function(r) {
				if (r) {
					$("#errorConLogSubmit").attr("disabled", true);
					$.ajax({
						type : "Post",
						url : "conLog/insert.html",
						dataType : 'json',
						data : {
							ConLogInnerId : $("#eConLogInnerId").val(),
							ErrorMoney : (ErrorMoney * 100).toFixed(0),
							Remark : $("#eRemark").val()
						},
						success : function(data) {
							$("#errorConLogSubmit").removeAttr("disabled");
							// 隐藏下拉框
							$("#errorConLogDiv").modal("hide");
							table();
							if (data.code == 200) {
								showMsg("提示信息", "补款成功");
							} else {
								// 失败
								showMsg("提示信息", data.msg, true);
							}
						},
						error : function() {
							// 提交按钮设置为可用
							$("#errorConLogSubmit").removeAttr("disabled");
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

// 表格
function table() {
	var DepartmentInnerId = "";
	var arry = $("#sDepartmentInnerId").combotree("getValues");
	$.each(arry, function(i, k) {
		if (arry[i].indexOf("department") >= 0) {
			DepartmentInnerId += arry[i].substring(10, arry[i].length) + ",";
		}
	});
	$("#select").attr("disabled", true);
	$("#table").datagrid({
		loadMsg : "正在加载数据，请稍等.....", // 在加载页面时显示数据
		url : "CarconLog/select.html", // 发送的url地址
		queryParams :
		// 参数列表
		{
			UserId : $('#sUserId').val(),
			UserName : $('#sUserName').val(),
			DepartmentInnerId : DepartmentInnerId,
			StartTime : $('#sStartTime').val(),
			EndTime : $('#sEndTime').val(),
			Remark : $('#sRemark').val(),
			ConPattern : 5,
			Flag :  $('#sFlag').combobox('getValue')
		},
		method : 'post',
		idField : 'ConLogInnerId',
		fitColumns : true,
		// 获取分页对象
		pagination : true,
		rownumbers : true,
		singleSelect : true,
		multiSort : true,// 多排序
		height : TableSize * 35 + 68,
		pageSize : TableSize,// 10 325,15 450,20 575,25 700
		pageList : [ 10, 15, 20, 25, 50, 100, 200, 500 ],
		onSortColumn : alertColumn,
		columns : [ [ {
			field : 'ConLogInnerId',
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
		},/* {
			field : 'DepartmentName',
			title : '部门',
			sortable : true,
			width : 100
		}, */{
			field : 'DepartmentName',
			title : '部门',
			sortable : true,
			width : 100
		}, {
			field : 'PersonMoney',
			title : '现金消费',
			sortable : true,
			width : 80
		}, {
			field : 'PersonBFMoney',
			title : '消费前现金金额',
			sortable : true,
			width : 100
		},{
			field : 'PersonAFMoney',
			title : '消费后现金金额',
			sortable : true,
			width : 100
		},{
			field : 'AllowanceMoney',
			title : '补贴消费',
			sortable : true,
			width : 80
		}, {
			field : 'AllowanceBFMoney',
			title : '消费前补贴金额',
			sortable : true,
			width : 100
		},{
			field : 'AllowanceAFMoney',
			title : '消费后补贴金额',
			sortable : true,
			width : 100
		}, {
			field : 'Money',
			title : '商品原价',
			sortable : true,
			width : 80
		}, {
			field : 'DiscountMoney',
			title : '实际消费',
			sortable : true,
			width : 80
		}, {
			field : 'Proportion',
			title : '折扣系数（%）',
			sortable : true,
			width : 100
		}, {
			field : 'ConDatetime',
			title : '消费时间',
			sortable : true,
			width : 100
		},/*, {
			field : 'UploadDatetime',
			title : '上传时间',
			sortable : true,
			width : 100
		}, {
			field : 'MerchantName',
			title : '商户',
			sortable : true,
			width : 100
		}, */{
			field : 'Remark',
			title : '备注',
			sortable : true,
			width : 100
		}
		] ],
		onLoadSuccess : function(data) {
			$("#select").removeAttr("disabled");
			if (data.rows.length == 0) {
				var body = $(this).data().datagrid.dc.body2;
				body.find('table tbody').append('<tr><td width="' + body.width() + '" style="height: 35px; text-align: center;"><h2>此条件下没有数据</h2></td></tr>');
			}
		}
	});
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
		url : "conLogExcel/select.html",
		type : "post",
		data : {
			UserId : $('#sUserId').val(),
			UserName : $('#sUserName').val(),
			DepartmentInnerId : DepartmentInnerId,
			StartTime : $('#sStartTime').val(),
			EndTime : $('#sEndTime').val(),
			ConWay : 5,
			ConPattern : 5,
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
