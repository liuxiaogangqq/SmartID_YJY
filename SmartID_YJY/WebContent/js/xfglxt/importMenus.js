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
			$.messager.alert('温馨提示', '请上传后缀名为xlsx 的文件!');
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
					url : "showMenusExcel.html", // 默认是form的action， 如果申明，则会覆盖
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
	
	file.value="";
}

/**
 * 充值导入操作
 * daoruLoading
 */
$("#submitBtn").click(function() {
	if (kongzhi == 0) {
		$.messager.alert('温馨提示', '要先浏览文件');
	} else {
		if (flag == 1) {
			$.messager.alert('温馨提示', '您的文件有非法数据，请处理后再导入');
		}else {
			if ($("#uploadFile").val() == '') {
				$.messager.alert('温馨提示', '请选择要导入的文件');
				return false;
			}
			$("#loading").show();
			$("#submitBtn").attr("disabled", false);
			kongzhi = 0;
			$("#daoruForm").ajaxSubmit({
				url : "importMenusExcel.html",
				type : "post",
				dataType : "json",
				// 提交前的回调函数
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
					$("#submitBtn").removeAttr("disabled");
					$("#loading").hide();
					$('.datagrid-mask-msg').remove();
					$('.datagrid-mask').remove();
					var html = "";
					// 提交按钮设置为可用
					if (data.code == 200) {
						// 成功
						//$("#xiazai").html("数据已导出：<a href='" + data.data + "'>下载</a>");
						$("#myXiazai").modal("show");
					} else {
						// 失败
						showMsg("提示信息", data.msg, true);
					}
				},
				error : function() {
					$("#submitBtn").removeAttr("disabled");
					$("#loading").hide();
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
		;
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
		field : 'MerchantName',
		title : '所属商户',
		width : 150,
		align : 'center',
	}, {
		field : 'MenuTypeId',
		title : '菜单编号',
		width : 150,
		align : 'center',
	}, {
		field : 'MenuTypeName',
		title : '菜单名称',
		width : 150,
		align : 'center',
	}, {
		field : 'MenuId',
		title : '菜品编号',
		width : 150,
		align : 'center',
	}, {
		field : 'MenuName',
		title : '菜品名称',
		width : 150,
		align : 'center',
	}, {
		field : 'MenuPrice',
		title : '价格(元)',
		width : 150,
		align : 'center',
	}, {
		field : 'EnableFlag',
		title : '启用标识',
		width : 150,
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
	onLoadSuccess : function(data) {
		kongzhi = 1;
		if (data.rows.length == 0) {
			var body = $(this).data().datagrid.dc.body2;
			body.find('table tbody').append('<tr><td width="' + body.width() + '" style="height: 35px; text-align: center;"><h2>此条件下没有数据</h2></td></tr>');
		}
	}
});
