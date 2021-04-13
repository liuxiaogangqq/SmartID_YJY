// 加载时载入表格
window.onload = function() {	
	cheweishengyu();
	lstable();
	yytable();
};

function cheweishengyu(){
	$.ajax({
		url : "carStopShengyu/select.html",
		type : "post",
		dataType : "json",
		success : function(data) {
			// 提交按钮设置为可用
			if (data.code == 200) {
				$("#fangke").html(data.fangke);
				$("#yuangong").html(data.yuangong);
				$("#yichang").html(data.yichang);
				$("#zhanyong").html(data.zhanyong);
			} 
		}
	});
}

// 表格
function lstable() {
	$("#lstable").datagrid({
		loadMsg : "正在加载数据，请稍等.....", // 在加载页面时显示数据
		url : "SLSCarRecord/select.html", // 发送的url地址
		method : 'post',
		idField : 'CarRecordInnerId',
		singleSelect : true,
		fitColumns : true,
		rownumbers : true,
		columns : [ [ {
			field : 'CarRecordInnerId',
			hidden : true
		}, {
			field : 'VisitorName',
			title : '姓名',
			sortable : true,
			width : 150
		}, {
			field : 'VisitorCompany',
			title : '公司',
			sortable : true,
			width : 150
		}, {
			field : 'CarNumber',
			title : '车牌号',
			sortable : true,
			width : 150
		},{
			field : 'EntryTime',
			title : '通行时间',
			sortable : true,
			width : 150
		},{
			field : 'InOrOut',
			title : '进出标识',
			sortable : true,
			width : 150
		}]],
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
}
// 表格
function yytable() {
	$("#yytable").datagrid({
		loadMsg : "正在加载数据，请稍等.....", // 在加载页面时显示数据
		url : "SYYCarRecord/select.html", // 发送的url地址
		method : 'post',
		idField : 'CarRecordInnerId',
		singleSelect : true,
		fitColumns : true,
		rownumbers : true,
		columns : [ [ {
			field : 'CarRecordInnerId',
			hidden : true
		}, {
			field : 'VisitorName',
			title : '姓名',
			sortable : true,
			width : 150
		}, {
			field : 'VisitorCompany',
			title : '公司',
			sortable : true,
			width : 150
		}, {
			field : 'CarNumber',
			title : '车牌号',
			sortable : true,
			width : 150
		},{
			field : 'EntryTime',
			title : '通行时间',
			sortable : true,
			width : 150
		},{
			field : 'InOrOut',
			title : '进出标识',
			sortable : true,
			width : 150
		}]],
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
}


