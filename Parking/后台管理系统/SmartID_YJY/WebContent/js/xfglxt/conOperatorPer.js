// 加载时载入表格
window.onload = function() {
	table();
}

$("#uMerchantInnerId").combotree({
	url : "merchantAppTree/select.html",
	editable : false,
	multiple:true,
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

// 点击修改时操作
$("#update").click(function() {
	var Table = $("#table").datagrid("getSelected");
	if (Table) {
		$("#uOperatorInnerId").val(Table.OperatorInnerId);
		$("#uOperatorId").val(Table.OperatorId);
		$("#uOperatorName").val(Table.OperatorName);
		if(Table.MerchantList!=null){
			var MerchantInnerId = "";
			var MerchantList = Table.MerchantList.split(",");
			$.each(MerchantList, function(i, k) {
				MerchantInnerId += "merchant" + MerchantList[i] + ",";
			});
			$("#uMerchantInnerId").combotree("setValues", MerchantInnerId);
		}
		$("#updateDiv").modal("show");
	} else {
		showMsg("提示信息", "请选择一条数据进行操作");
	}
});

// 表格
function table() {
	$("#table").datagrid("clearSelections");
	$("#table").datagrid({
		loadMsg : "正在加载数据，请稍等.....", // 在加载页面时显示数据
		url : "conOperatorPer/select.html", // 发送的url地址
		queryParams :
		// 参数列表
		{
			OperatorId : $('#sOperatorId').val(),
			OperatorName : $('#sOperatorName').val(),
			Remark : $('#sRemark').val()
		},
		method : 'post',
		idField : 'ConTerminalInnerId',
		fitColumns : true,
		// 获取分页对象
		pagination : true,
		rownumbers : true,
		singleSelect : true,
		height : TableSize * 35 + 68,
		pageSize : TableSize,// 10 325,15 450,20 575,25 700
		pageList : [ 10, 15, 20, 25, 50, 100, 200, 500 ],
		columns : [ [ {
			field : 'OperatorInnerId',
			hidden : true
		}, {
			field : 'MerchantList',
			hidden : true
		}, {
			field : 'OperatorId',
			title : '登录名',
			sortable : true,
			width : 150
		}, {
			field : 'OperatorName',
			title : '姓名',
			sortable : true,
			width : 150
		}, {
			field : 'MerchantNameList',
			title : '商户权限',
			sortable : true,
			width : 150
		} ] ],
		onLoadSuccess : function(data) {
			if (data.rows.length == 0) {
				var body = $(this).data().datagrid.dc.body2;
				body.find('table tbody').append('<tr><td width="' + body.width() + '" style="height: 35px; text-align: center;"><h2>此条件下没有数据</h2></td></tr>');
			}
		}
	});
}

// 修改提交
$("#updateSubmit").click(function() {
	$("#updateForm").validate({
		submitHandler : function() {
			var MerchantInnerId = "";
			var tree = $("#uMerchantInnerId").combotree('tree'); // 得到树对象
			var arry = tree.tree('getChecked', [ 'checked', 'indeterminate' ]);
			$.each(arry, function(i, k) {
				if (arry[i].id.indexOf("merchant") >= 0) {
					MerchantInnerId += arry[i].id.substring(8, arry[i].id.length) + ",";
				}
			});
			// 提交按钮设置为不可用，防止重复提交
			$("#updateSubmit").attr("disabled", true);
			$.ajax({
				url : "conOperatorPer/update.html",
				type : "post",
				data : {
					OperatorInnerId : $("#uOperatorInnerId").val(),
					MerchantList : MerchantInnerId
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
						$("#uMerchantInnerId").combotree("clear");
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
});
