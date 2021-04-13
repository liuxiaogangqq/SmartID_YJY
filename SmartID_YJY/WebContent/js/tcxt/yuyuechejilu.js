// 加载时载入表格
window.onload = function() {	
	table();
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
	$("#sStartTime").val(new Date().Format("yyyy-MM-dd"));
	$("#sEndTime").val(new Date().Format("yyyy-MM-dd"));
};

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
	table();
});

// 表格
function table() {
	$("#table").datagrid("clearSelections");
	$("#table").datagrid({
		loadMsg : "正在加载数据，请稍等.....", // 在加载页面时显示数据
		url : "YYCarRecord/select.html", // 发送的url地址
		queryParams :
		{
			StartTime : $('#sStartTime').val()+" 00:00:00",
			EndTime : $('#sEndTime').val()+" 23:59:00",
			UserName : $('#sUserName').val(),
			CarNumber : $('#sCarNumber').val(),
		},
		method : 'post',
		idField : 'CarRecordInnerId',
		fitColumns : true,
		// 获取分页对象
		pagination : true,
		rownumbers : true,
		singleSelect : true,
		height : TableSize * 35 + 68,
		pageSize : TableSize,// 10 325,15 450,20 575,25 700
		pageList : [ 10, 15, 20, 25, 50, 100, 200, 500 ],
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


