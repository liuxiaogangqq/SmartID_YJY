var TableSize = 10;
// 加载时载入表格
window.onload = function() {
	table();
};

// 点击修改时操作
$("#update").click(function() {
	var Table = $("#table").datagrid("getSelected");
	if (Table) {
		$("#ConfigureValueDiv").empty();
		if (Table.ConfigureId == 'CardNumber') {
			var ConfigureValue = "<select class='form-control' id='ConfigureValue' name='ConfigureValue'><option value='1'>1</option><option value='2'>2</option><option value='3'>3</option><option value='4'>4</option><option value='5'>5</option><option value='10'>10</option><option value='100'>100</option></select>";
			$("#ConfigureValueDiv").append(ConfigureValue);
		} else if (Table.ConfigureId == 'CardType') {
			var ConfigureValue = "<select class='form-control' id='ConfigureValue' name='ConfigureValue'><option value='M1'>M1</option><option value='CPU'>CPU</option></select>";
			$("#ConfigureValueDiv").append(ConfigureValue);
		} else if (Table.ConfigureId == 'SessionTimeout'||Table.ConfigureId == 'ConTerminalTimeout') {
			var ConfigureValue = "<select class='form-control' id='ConfigureValue' name='ConfigureValue'><option value='2'>2分钟</option><option value='5'>5分钟</option><option value='10'>10分钟</option><option value='15'>15分钟</option><option value='20'>20分钟</option><option value='30'>30分钟</option><option value='60'>60分钟</option></select>";
			$("#ConfigureValueDiv").append(ConfigureValue);
		} else {
			var ConfigureValue = "<input type='text' class='form-control inp' id='ConfigureValue' name='ConfigureValue' />";
			$("#ConfigureValueDiv").append(ConfigureValue);
		}
		$("#ConfigureId").val(Table.ConfigureId);
		$("#ConfigureName").val(Table.ConfigureName);
		$("#ConfigureValue").val(Table.ConfigureValue);
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
		url : "systemConfigure/select.html", // 发送的url地址
		height : TableSize * 35 + 68,
		method : 'post',
		fitColumns : true,
		rownumbers : true,
		singleSelect : true,
		columns : [ [ {
			field : 'ConfigureId',
			title : '参数编号',
			width : 200
		}, {
			field : 'ConfigureName',
			title : '参数名称',
			width : 200
		}, {
			field : 'ConfigureValue',
			title : '参数值',
			width : 200
		} ] ],
		onLoadError : function() {
			showMsg("提示信息", "服务发生异常,请检查是否断开服务", true);
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
			ConfigureValue : {
				required : true,
				maxlength : 4000,
				isNewContainsSpecialChar : true,
			}
		},
		messages : {
			ConfigureValue : {
				required : "参数值不能为空",
				maxlength : "最大不超过4000个字符",
			}
		},
		submitHandler : function() {
			if($("#ConfigureId").val() == "EveryConMoney" || $("#ConfigureId").val() == "DayMoney" ){
				var exp = /^[1-9][\d]{0,5}$/;
				if (!exp.test($("#ConfigureValue").val())) {
					showMsg("信息校验", "请输入正整数的金额,最大6位",true);
					return false;
				}
			}
			// 提交按钮设置为不可用，防止重复提交
			$("#updateSubmit").attr("disabled", true);
			$.ajax({
				url : "systemConfigure/update.html",
				type : "post",
				data : {
					ConfigureId : $("#ConfigureId").val(),
					ConfigureName : $("#ConfigureName").val(),
					ConfigureValue : $("#ConfigureValue").val().replace(/\s+/g,""),
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
