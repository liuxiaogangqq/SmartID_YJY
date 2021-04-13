// 加载时载入表格
window.onload = function() {
	moren();
	
    /*$('input').click(function () {
        switch (this.value) {
            case '获取日历当前选中值': alert($('#div1').fullYearPicker('getSelected')); break;
            case '更新日历值': $('#div1').fullYearPicker('acceptChange'); alert('更新值成功，切换年份查看效果'); break;
            case '设置为指定年份': var year = prompt('请输入4位年份数字'); if (/^\d{4}$/.test(year)) $('#div1').fullYearPicker('setYear', parseInt(year)); else alert('年份为4位数字！'); break;
            case '设置为指定星期不可选': var day = prompt('请输入0~6的数字，不输入则设置所有星期可选'); if (/^[0-6]*$/.test(day)) $('#div1').fullYearPicker('setDisabledDay', day); else alert('请输入0~6的数字！');
        }
    });*/
	$("#select").click(function() {
		var year = $("#sHolidayName").val();
		if($("#sHolidayName").val() != ""){
			if(year%1 ===0){
				if(year.length != 4){
					showMsg("提示信息", "请检查年份是否正确", true);
					return false;
				}else{
					$('#div1').fullYearPicker('setYear', $("#sHolidayName").val());
					moren();
				}
			}else{
				showMsg("提示信息", "请检查年份是否正确", true);
				return false;
			}
		}
	})
	// 点击添加时弹出框
	$("#insert").click(function() {
		if(confirm("是否确定设置选择时间为节假日？")){
			$.ajax({
				url : "attendanceHoliday/insert.html",
				type : "post",
				data : {
					StartDate : $("#a").text(),
				},
				dataType : "json",
				success : function(data) {
					// 提交按钮设置为可用
					if (data.code == 200) {
						// 成功
						showMsg("提示信息", data.msg);
						// 取消表格选中
						// 重载表格数据
						moren();
					} else {
						// 失败
						showMsg("提示信息", data.msg, true);
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					// 提交按钮设置为可用
					$("#delete").removeAttr("disabled");
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
	 
}
function moren(){
	var year = $("#sHolidayName").val();
	if($("#sHolidayName").val() != ""){
		if(year%1 ===0){
			if(year.length != 4){
				showMsg("提示信息", "请检查年份是否正确", true);
				return false;
			}
		}else{
			showMsg("提示信息", "请检查年份是否正确", true);
			return false;
		}
	}
	$.ajax({
		url : "attendanceHoliday/select.html",
		type : "post",
		data : {
			year : $("#sHolidayName").val(),
		},
		dataType : "json",
		success : function(data) {
			// 提交按钮设置为可用
			var selectList = data.toString().split(",");
			var array=new Array(); 
			year="2019";
			for(var i = 0;i<selectList.length;i++){
				array[i] = selectList[i];
			}
			if($("#sHolidayName").val() == ""){
				year = new Date().getFullYear();
			}else{
				year = $("#sHolidayName").val();
			}
			$('#div1').fullYearPicker({
		        disable:false,//只读
		        year: year,//指定年份
		        initDate: array,//初始化选中日期
		        yearScale: { min: 1949, max: 2100 },//初始化日历范围
		        format:"YYYY-MM-DD",//日期格式化  YYYY-MM-DD  YYYY-M-D
		        cellClick: function (dateStr, isDisabled) {//当前选中日期回调函数
		        },
		        choose:function (a) {//实时获取所有选中的日期的回调函数
		           $("#a").text(JSON.stringify(a));
		        }
		    });
		}
	});
}
