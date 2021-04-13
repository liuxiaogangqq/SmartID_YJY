// 加载时载入表格
window.onload = function() {
	table();
};


$("#Time").focus(function() {
	WdatePicker({
		dateFmt : 'yyyy-MM-dd',
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


// 点击修改时操作
$("#update").click(function() {
	var Table = $("#table").datagrid("getSelected");
	if (Table) {
		$("#uParkDate").val(Table.ParkDate);
		$("#uParkTypeName").val(Table.ParkTypeName);
		$("#uParkNum").val(Table.ParkNum);
		$("#uParkResidue").val(Table.ParkResidue);
		$("#uParkType").val(Table.ParkType);
		$("#updateDiv").modal("show");
	} else {
		showMsg("提示信息", "请选择一条数据进行操作");
	}
});

// 表格
function table() {
	$("#table").datagrid("uncheckAll");
	$("#table").datagrid({
		loadMsg : "正在加载数据，请稍等.....", // 在加载页面时显示数据
		url : "ParkNumberDate/select.html", // 发送的url地址
		queryParams :
		// 参数列表
		{
			DateTime : $('#Time').val(),
		},
		method : 'post',
		idField : 'ParkDate',
		fitColumns : true,
		singleSelect : true,
		height:TableSize*35+68,
		pageSize : TableSize,// 10 325,15 450,20 575,25 700
		pageList : [ 10, 15, 20, 25, 50, 100, 200, 500 ],
		columns : [ [ {
			field : 'ParkType',
			hidden : true
		},  {
			field : 'ParkDate',
			title : '日期',
			sortable : true,
			width : 150
		}, {
			field : 'ParkTypeName',
			title : '车位类型',
			sortable : true,
			width : 150
		}, {
			field : 'ParkNum',
			title : '车位总数',
			sortable : true,
			width : 150
		}, {
			field : 'ParkResidue',
			title : '剩余车位数量',
			sortable : true,
			width : 150
		}] ],
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

// 修改提交
$("#updateSubmit").click(function() {
	
	$("#updateForm").validate({
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
			ParkNum : {
				required : true,
				isInteger : true,
				isNewContainsSpecialChar : true
			},
			ParkResidue : {
				required : true,
				isInteger : true,
				isNewContainsSpecialChar : true
			}
		},
		messages : {
			ParkNum : {
				required : "请输入车位总数量",
				isInteger : "请输入正整数",
			},
			ParkResidue : {
				required : "请输入剩余车位数量",
				isInteger : "请输入正整数",
			}
		},
		submitHandler : function() {
			// 提交按钮设置为不可用，防止重复提交
			$("#updateSubmit").attr("disabled", true);
			$.ajax({
				url : "parkDateNumber/update.html",
				type : "post",
				data : {
					ParkDate : $("#uParkDate").val(),
					ParkNum : $("#uParkNum").val(),
					ParkResidue : $("#uParkResidue").val(),
					ParkType :$("#uParkType").val()
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
						$("#uAreaList").combotree("clear");
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
		}
	});
});
