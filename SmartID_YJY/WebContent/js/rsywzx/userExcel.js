var flag = 0;
var kongzhi = 0;
$(document).ready(function() {

});
/**
 * 验证是否是Excel表
 * @param obj
 * @returns {Boolean}
 */
function isValidateFile(obj) {
	// showProcess(true, '温馨提示', '<img src="./images/lodding.gif"/>');
	if(document.getElementById("uploadFile").value != ""){
		$("#loading").show();
		var extend = obj.value.substring(obj.value.lastIndexOf(".") + 1);
		if (extend == "") {
			$("#loading").hide();
			$.messager.alert('温馨提示', '请选择要上传的文件及正确的文本内容格式');
			return false;
		} else if (!(extend == "xlsx")) {
			$("#loading").hide();
			$.messager.alert('温馨提示', '请上传后缀名为xlsx 的文件');
			obj.value = "";
			var nf = obj.cloneNode(true);
			nf.value = '';
			obj.parentNode.replaceChild(nf, obj);
			return false;
		} else if (obj.files[0].size / 1024 > 2048) {
			$("#loading").hide();
			$.messager.alert('温馨提示', '您上传的文件大小超出了2M限制');
			obj.value = "";
			return false;
		} else {
			if (obj.files && obj.files[0]) {
				var reader = new FileReader();
				reader.onload = function(e) {
				};
				$("#daoruForm").ajaxSubmit({
					url : "showUserExcel.html", // 默认是form的action， 如果申明，则会覆盖
					type : "post", // 默认是form的method（get or post），如果申明，则会覆盖
					dataType : "json", // html(默认), xml, script,
					success : function(data) {
						$("#loading").hide();
						$("#excelInfo").datagrid('loadData', data);
					},
				});
				reader.readAsDataURL(obj.files[0]);
			}
		}
		return true;
	}
}

function clearFile(){
	var file = document.getElementById("uploadFile");
	file.value="";
}

/**
 * 导入操作 daoruLoading
 */
$("#submitBtn").click(function() {
	if (kongzhi == 0) {
		$.messager.alert('温馨提示', '要先游览文件');
	} else {
		if (flag == 1) {
			$.messager.alert('温馨提示', '您的文件有非法数据，请处理后再导入');
			return false;
		}
		if ($("#uploadFile").val() == '') {
			$.messager.alert('温馨提示', '请选择要导入的文件');
			return false;
		}
		$("#loading").show();
		$("#submitBtn").attr("disabled", false);
		kongzhi = 0;
		$("#daoruForm").ajaxSubmit({
			url : "importUserExcel.html",
			type : "post",
			dataType : "json",
			// 提交前的回调函数
			success : function(data) {
				$("#submitBtn").removeAttr("disabled");
				$("#loading").hide();
					showMsg("提示信息", data.msg, true);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				$("#submitBtn").removeAttr("disabled");
				$("#loading").hide();
				// 出错
				if (XMLHttpRequest.readyState == 0) {
					// 常见错误，网络连不上或服务没有起来
					showMsg("提示信息", "找不到服务器，请检查网络连接", true);
				} else if (XMLHttpRequest.readyState == 4) {
					if (XMLHttpRequest.status == 404) {
						showMsg("提示信息", "找不到服务！", true);
					} else {
						showMsg("提示信息", "程序出现异常，请重试！", true);
					}
				} else {
					showMsg("提示信息", "程序出现异常，请重试！", true);
				}
			}
		});
	}
});

function initData(obj) {
	// Excel格式验证
	isValidateFile(obj);
	flag = 0;
}
/**
 * 初始化Grid 导入内容 显示数据
 */

$("#excelInfo").datagrid({
	loadMsg : "正在加载数据，请稍等.....", // 在加载页面时显示数据
	fitColumns : true,
	// 获取分页对象
	rownumbers : true,
	singleSelect : true,
	height : TableSize * 35 + 68,
	columns : [ [ {
		field : 'UserId',
		title : '人员编号',
		width : 200,
		align : 'center',
	}, {
		field : 'UserName',
		title : '人员姓名',
		width : 200,
		align : 'center',
	}, {
		field : 'UserTypeName',
		title : '人员类型',
		width : 200,
		align : 'center',
	}, {
		field : 'DepartmentId',
		title : '部门编号',
		width : 200,
		align : 'center',
	}, {
		field : 'Mobile',
		title : '手机号',
		width : 200,
		align : 'center',
	} ] ],
	rowStyler : function(index, row) {
		if (row.Validate == 1) {
			// 非法数据
			flag = 1;
			return 'color:red;';
		} else if (row.Validate == 2) {
			// 警告数据
			if (flag == 0) {
				flag = 2;
			}
			return 'color: yellow;';
		}
	},
	onLoadError : function() {
		showMsg("提示信息", "服务发生异常，请检查服务", true);
	},
	onLoadSuccess : function(data) {
		kongzhi = 1;
		if (data.rows.length == 0) {
			var body = $(this).data().datagrid.dc.body2;
			body.find('table tbody').append('<tr><td width="' + body.width() + '" style="height: 35px; text-align: center;"><h2>此条件下没有数据</h2></td></tr>');
		}
	}
});
