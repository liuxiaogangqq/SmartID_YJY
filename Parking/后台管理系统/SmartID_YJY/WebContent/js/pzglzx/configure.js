var TableSize = 10;
// 加载时载入表格
window.onload = function() {
	table();
};

// 更改下拉框时更新表格
$("#sPage").bind("change", function() {
	table();
});



// 点击修改时操作
$("#update").click(function() {
	var Table = $("#table").datagrid("getSelected");
	if (Table) {
		$("#uPage").val(Table.Page);
		$("#uProperty").val(Table.Property);
		$("#uEnabled").val(Table.Enabled);
		$("#uInitialName").val(Table.InitialName);
		$("#uName").val(Table.Name);
		$("#uLength").val(Table.Length);
		if(Table.EditName==1){
			$("#uName").attr("readonly", true);
		}else{
			$("#uName").removeAttr("readonly");
		}
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
		url : "configure/select.html", // 发送的url地址
		queryParams :
		// 参数列表
		{
			Page : $('#sPage').val()
		},
		height : TableSize * 35 + 68,
		method : 'post',
		fitColumns : true,
		rownumbers : true,
		singleSelect : true,
		columns : [ [ {
			field : 'Page',
			hidden : true
		},{
			field : 'Property',
			hidden : true
		},{
			field : 'Enabled',
			hidden : true
		},{
			field : 'EditName',
			hidden : true
		}, {
			field : 'InitialName',
			title : '初始名称',
			width : 150
		}, {
			field : 'Name',
			title : '名称',
			width : 150
		}, {
			field : 'IsEnabled',
			title : '是否启用',
			width : 150
		}, {
			field : 'Length',
			title : '显示宽度',
			width : 150
		}] ],
		onLoadError : function() {
			showMsg("提示信息", "服务发生异常，请重试" , true);
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
			Name : {
				required : true,
				maxlength : 4,
				isContainsSpecialChar : true,
			},
			Length : {
				required : true,
				isTitelWidth : true,
				isInteger:true
			}
		},
		messages : {
			Name : {
				required : "请输入名称",
				maxlength : "最大不超过4个字符",
				isContainsSpecialChar:"含有中英文特殊字符",
			},
			Length : {
				required : "请输入长度",
				isTitelWidth : "请填写30到800之间的数字",
				isInteger:"必须填写整数"
			}
		},
		submitHandler : function() {
			// 提交按钮设置为不可用，防止重复提交
			$("#updateSubmit").attr("disabled", true);
			$.ajax({
				url : "configure/update.html",
				type : "post",
				data : {
					InitialName : $("#uInitialName").val(),
					Page : $("#uPage").val(),
					Enabled : $("#uEnabled").val(),
					Name : $("#uName").val().replace(/\s+/g,""),
					Length : $("#uLength").val(),
					Property : $("#uProperty").val()
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
