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
var columns = new Array();
// 加载时载入表格
window.onload = function() {
	$("#sStartTime").val(new Date().Format("yyyy-MM-dd"));
	$("#sEndTime").val(new Date().Format("yyyy-MM-dd"));
	$.ajax({
		type : "Post",
		url : 'paymentTree/select.html',
		async : false,
		dataType : 'json',
		success : function(data) {
			checkColumns();
			var name1="";
			var name2="";
			var name3="";
			var name4="";
			var name5="";
			var name6="";
			var name7="";
			var name8="";
			for(var i=0;i<data.rows.length;i++){
				var map = data.rows[i];
				if(i == 0){
					name1 = data.rows[i].AppAName;
				}else if(i == 1){
					name2 = data.rows[i].AppBName;
				}else if(i == 2){
					name3 = data.rows[i].AppCName;
				}else if(i == 3){
					name4 = data.rows[i].AppDName;
				}else if(i == 4){
					name5 = data.rows[i].AppEName;
				}else if(i == 5){
					name6 = data.rows[i].AppFName;
				}else if(i == 6){
					name7 = data.rows[i].AppGName;
				}else if(i == 7){
					name8 = data.rows[i].AppHName;
				}
			}
			data.rows
			if(data.total == 1){
				ff1(name1);
			}else if(data.total == 2){
				ff1(name1);
				ff2(name2);
			}else if(data.total == 3){
				ff1(name1);
				ff2(name2);
				ff3(name3);
			}else if(data.total == 4){
				ff1(name1);
				ff2(name2);
				ff3(name3);
				ff4(name4);
			}else if(data.total == 5){
				ff1(name1);
				ff2(name2);
				ff3(name3);
				ff4(name4); 
				ff5(name5); 
			}else if(data.total == 6){
				ff1(name1);
				ff2(name2);
				ff3(name3);
				ff4(name4); 
				ff5(name5); 
				ff6(name6); 
			}else if(data.total == 7){
				ff1(name1);
				ff2(name2);
				ff3(name3);
				ff4(name4); 
				ff5(name5); 
				ff6(name6); 
				ff7(name7); 
			}else if(data.total == 8){
				ff1(name1);
				ff2(name2);
				ff3(name3);
				ff4(name4); 
				ff5(name5); 
				ff6(name6); 
				ff7(name7); 
				ff8(name8); 
			}
		}
	});
	table();
};

function ff1(name1){
	var column1 = {field : 'AppAPM',title : name1+"(现金)",sortable : true,width : 100};
	var column2 = {field : 'AppAAM',title : name1+"(补贴)",sortable : true,width : 100};
	columns.push(column1); 
	columns.push(column2); 
}
function ff2(name2){
	var column1 = {field : 'AppBPM',title : name2+"(现金)",sortable : true,width : 100};
	var column2 = {field : 'AppBAM',title : name2+"(补贴)",sortable : true,width : 100};
	columns.push(column1); 
	columns.push(column2); 
}
function ff3(name3){
	var column1 = {field : 'AppCPM',title : name3+"(现金)",sortable : true,width : 100};
	var column2 = {field : 'AppCAM',title : name3+"(补贴)",sortable : true,width : 100};
	columns.push(column1); 
	columns.push(column2); 
}
function ff4(name4){
	var column1 = {field : 'AppDPM',title : name4+"(现金)",sortable : true,width : 100};
	var column2 = {field : 'AppDAM',title : name4+"(补贴)",sortable : true,width : 100};
	columns.push(column1); 
	columns.push(column2); 
}
function ff5(name5){
	var column1 = {field : 'AppEPM',title : name5+"(现金)",sortable : true,width : 100};
	var column2 = {field : 'AppEAM',title : name5+"(补贴)",sortable : true,width : 100};
	columns.push(column1); 
	columns.push(column2); 
}
function ff6(name6){
	var column1 = {field : 'AppFPM',title : name6+"(现金)",sortable : true,width : 100};
	var column2 = {field : 'AppFAM',title : name6+"(补贴)",sortable : true,width : 100};
	columns.push(column1); 
	columns.push(column2); 
}
function ff7(name7){
	var column1 = {field : 'AppGPM',title : name7+"(现金)",sortable : true,width : 100};
	var column2 = {field : 'AppGAM',title : name7+"(补贴)",sortable : true,width : 100};
	columns.push(column1); 
	columns.push(column2); 
}
function ff8(name8){
	var column1 = {field : 'AppHPM',title : name8+"(现金)",sortable : true,width : 100};
	var column2 = {field : 'AppHAM',title : name8+"(补贴)",sortable : true,width : 100};
	columns.push(column1); 
	columns.push(column2); 
}

$("#sStartTime").focus(function() {
	WdatePicker({
		dateFmt : 'yyyy-MM-dd',
		maxDate : "#F{$dp.$D('sEndTime')}"
	});
});

$("#sEndTime").focus(function() {
	WdatePicker({
		dateFmt : 'yyyy-MM-dd',
		minDate : "#F{$dp.$D('sStartTime')}"
	});
});

$('#payFlag').multiselect({
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


// 点击查询时更新表格
$("#select").click(function() {
	//$("#table").datagrid({pageNumber:1});
	table();
});

function checkColumns(){
	var column1 = {field : 'ck',checkbox : true};
	columns.push(column1); 
	var column2 = {field : 'PaymentInnerId',hidden : true};
	columns.push(column2); 
	var column3 = {field : 'DateTime',title : '时间',sortable : true,width : 100};
	columns.push(column3);
	var column4 = {field : 'PaymentFlag',title :'交易状态',sortable : true,width : 100};
	columns.push(column4);
}


// 表格
function table() {
	
	var payFlag = ""
	if($("#payFlag").val()){
		payFlag = $("#payFlag").val().toString();
	}
	$("#select").attr("disabled", true);
	$("#table").datagrid({
		loadMsg : "正在加载数据，请稍等.....", // 在加载页面时显示数据
		url : "paymentLog/select.html", // 发送的url地址
		queryParams :
		// 参数列表
		{
			StartTime : $('#sStartTime').val(),
			EndTime : $('#sEndTime').val(),
			PaymentFlag : payFlag
		},
		method : 'post',
		idField : 'PaymentInnerId',
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
		columns : [ columns ],
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
	var Table = $("#table").datagrid("getSelections");
	var conLogList = [];
	var nums = 0;
	$.each(Table, function(index, item){
		conLogList.push(item.PaymentInnerId);
		if(item.PaymentFlag == "已付款"){
			nums++;
		}
	});
	if(nums > 0){
		showMsg("提示信息", "请勿选择已付款的记录" ,true);
		return false;
	}
	if(conLogList.length>=1){
		$.messager.confirm("确认付款操作", "是否确认付款？", function(r) {
			if(r){
				$("#kaipiao").attr("disabled", true);
				$.ajax({
					type : "Post",
					url : "fukuanPayment/update.html",
					dataType : 'json',
					data : {
						PaymentInnerId : conLogList.toString(),
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
