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
	$("#sStartTime").val(dateChange(new Date(), 'month', -1));
	$("#sEndTime").val(new Date().Format("yyyy-MM-dd HH:mm:ss"));
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

$("#sTerminalTypeInnerId").combotree({
	url : "conTerminalMerchantTree/select.html",
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

$('#invoiceFlag').multiselect({
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
	
	var standbyB = ""
	if($("#invoiceFlag").val()){
		standbyB = $("#invoiceFlag").val().toString();
	}
	var ConTerminalInnerId = "";
	var arry = $("#sTerminalTypeInnerId").combotree("getValues");
	$.each(arry, function(i, k) {
		if (arry[i].indexOf("conTerminal") >= 0) {
			ConTerminalInnerId += arry[i].substring(11, arry[i].length) + ",";
		}
	});
	$("#select").attr("disabled", true);
	$("#table").datagrid({
		loadMsg : "正在加载数据，请稍等.....", // 在加载页面时显示数据
		url : "invoiceLog/select.html", // 发送的url地址
		queryParams :
		// 参数列表
		{
			UserId : $('#sUserId').val(),
			UserName : $('#sUserName').val(),
			ConTerminalInnerId : ConTerminalInnerId,
			StartTime : $('#sStartTime').val(),
			EndTime : $('#sEndTime').val(),
			ConTypeList : $("#sConTypeList").combobox("getValues").toString(),
			StandbyB : standbyB
		},
		method : 'post',
		idField : 'ConLogInnerId',
		fitColumns : true,
		// 获取分页对象
		pagination : true,
		rownumbers : true,
		singleSelect : false,
		multiSort : true,// 多排序
		height : TableSize * 35 + 68,
		pageSize : 1000,// 10 325,15 450,20 575,25 700
		pageList : [ 1000 ],
		onSortColumn : alertColumn,
		columns : [ [ {
            field : 'ck',
            checkbox : true
        },{
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
		}, /*{
			field : 'CompanyName',
			title : '公司',
			sortable : true,
			width : 100
		},*/  {
			field : 'DepartmentName',
			title : '公司',
			sortable : true,
			width : 100
		}, {
			field : 'Money',
			title : '商品原价',
			sortable : true,
			width : 80
		},  {
			field : 'Proportion',
			title : '折扣系数（%）',
			sortable : true,
			width : 100
		},  {
			field : 'DiscountMoney',
			title : '实际消费',
			sortable : true,
			width : 80
		}, {
			field : 'ConDatetime',
			title : '消费时间',
			sortable : true,
			width : 100
		}, {
			field : 'MerchantName',
			title : '商户',
			sortable : true,
			width : 100
		}, {
			field : 'ConTerminalName',
			title : '设备',
			sortable : true,
			width : 100
		}, {
			field : 'StandbyB',
			title : '开票标识',
			sortable : true,
			width : 100
		},
		] ],
		onLoadSuccess : function(data) {
			$("#table").datagrid('clearSelections');
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

$("#kaipiao").click(function() {
	var Table = $("#table").datagrid("getChecked");
	var conLogList = [];
	var money= 0;
	var UaseName = "";
	var nums = 0;
	$.each(Table, function(index, item){
		conLogList.push(item.ConLogInnerId);
		var bool = item.UserId.indexOf("合计");
		if(bool >= 0){
		}else{
			money += Number(item.DiscountMoney)*100;
		}
		if(item.StandbyB == "已开票"){
			nums++;
		}
	});
	if(nums > 0){
		showMsg("提示信息", "请勿选择已开票记录" ,true);
		return false;
	}
	if(conLogList.length>=1){
		$.messager.confirm("确认开票金额", "是否确认开票[开票金额：[<span style='font-size:18px;color:red'>" + money/100.0 + "元</span>]", function(r) {
			if(r){
				$("#kaipiao").attr("disabled", true);
				$.ajax({
					type : "Post",
					url : "invoice/update.html",
					dataType : 'json',
					data : {
						ConLogInnerId : conLogList.toString(),
						Money : money
					},
					success : function(data) {
						// 提交按钮设置为可用
						$("#kaipiao").removeAttr("disabled");
						table();
						if (data.code == 200) {
							showMsg("提示信息", data.msg);
						} else {
							// 失败
							showMsg("提示信息", data.msg);
						}
					},
					error : function() {
						table();
						// 提交按钮设置为可用
						$("#kaipiao").removeAttr("disabled");
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
	}else{
		showMsg("提示信息", "请选择完数据后在进行开票操作");
	}
});
