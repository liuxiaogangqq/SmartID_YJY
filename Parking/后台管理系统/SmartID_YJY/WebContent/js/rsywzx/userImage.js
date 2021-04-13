var feng_pic = {
                    uploadTotal : 0,
                    uploadLimit : 25,
                    Max : 25,
                    swf:'',
                    uploader:'',
                    uploadify : function () {
                        //文件上传测试
                        $('#file').uploadify({
                            swf : feng_pic.swf,
                            uploader : feng_pic.uploader,
                            uploadLimit : feng_pic.uploadLimit,
                            width : 120,
                            height : 35,
                            fileTypeDesc : '图片类型',
                            buttonCursor : 'pointer',
                            buttonText : '上传图片',
                            fileTypeExts : '*.jpeg; *.jpg; *.png; *.gif',
                            fileSizeLimit : '10MB',
                            removeCompleted : true,
                            removeTimeout   : 1,
                            overrideEvents : ['onSelectError','onSelect','onDialogClose','onUploadError'],
                            //上传失败返回的错误
                            onSelectError : function (file, errorCode, errorMsg) {
                                console.log(file, errorCode, errorMsg);
                                switch (errorCode) {
                                    case -100:
                                        bootbox.alert("限制为"+feng_pic.Max+"张...", function(){});
                                        break;
                                    case -110 :
                                        bootbox.alert("文件 [" + file.name + "]超过10MB...", function(){});
                                        break;
                                    case -120:
                                        bootbox.alert("文件 [" + file.name + "] 大小异常", function(){});
                                        break;
                                    case -130:
                                        bootbox.alert("文件 [" + file.name + "] 类型不正确", function(){});
                                        break;
                                }
                            },
                            onUploadStart : function () {
                                if (feng_pic.uploadTotal == feng_pic.Max) {
                                    $('#file').uploadify('stop');
                                    $('#file').uploadify('cancel');
                                    bootbox.alert("限制为"+feng_pic.Max+"张...", function(){});

                                }

                            },
                            //检测FLASH失败调用
                            onFallback: function () {
                                alert("您未安装FLASH控件，无法上传，请安装FLASH控件后再试。");
                            },
                            //图片上传成功回调
                            onUploadSuccess : function (file, data, response) {
                            //	alert(data);
                               // var allData = eval('(' + data + ')');
                               // alert(allData);
                                //自定义写的图片上传显示html
                                $('.pic_list').append('<div class="pic_content"><span class="remove"></span><span class="text">删除</span><input type="hidden" name="contractImage[]" value="'+ data +'"><img src="touxiang/' + data + '" class="pic_img"></div>');
                                setTimeout(function () {
                                    feng_pic.hover();
                                    feng_pic.remove();
                                }, 10);
                                feng_pic.uploadTotal++;
                                feng_pic.uploadLimit--;
                                $('.pic_total').text(feng_pic.uploadTotal);
                                $('.pic_limit').text(feng_pic.uploadLimit);


                               
                                }
                            


                        });
                    },
                    hover : function () {
                        var content = $('.pic_content');
                        var len = content.length;
                        $(content[len - 1]).hover(function () {
                            $(this).find('.remove').show();
                            $(this).find('.text').show();
                        }, function () {
                            $(this).find('.remove').hide();
                            $(this).find('.text').hide();
                        });
                    },
                    allHover:function(){
                        $('.pic_content').each(function(){
                                $(this).hover(function () {
                                $(this).find('.remove').show();
                                $(this).find('.text').show();
                            }, function () {
                                $(this).find('.remove').hide();
                                $(this).find('.text').hide();
                            });
                        });
                    },
                    //删除已上传文件
                    remove : function () {
                        var remove = $('.pic_content .text');
                        var len = remove.length;
                        $(remove[len - 1]).on('click', function () {
                            $(this).parent().remove();
                            feng_pic.uploadTotal--;
                            feng_pic.uploadLimit++;
                            $('.pic_total').text(feng_pic.uploadTotal);
                            $('.pic_limit').text(feng_pic.uploadLimit);
                            var swfu = $('#file').data('uploadify');
                            var stats = swfu.getStats();
                            stats.successful_uploads--;
                            swfu.setStats(stats);
                        });
                    },
                    allRemove:function(){
                        $('.pic_content .text').each(function(){
                            $(this).on('click', function () {
                                $(this).parent().remove();
                                feng_pic.uploadTotal--;
                                feng_pic.uploadLimit++;
                                $('.pic_total').text(feng_pic.uploadTotal);
                                $('.pic_limit').text(feng_pic.uploadLimit);
                                var swfu = $('#file').data('uploadify');
                                var stats = swfu.getStats();
                                stats.successful_uploads--;
                                swfu.setStats(stats);
                            });
                        });
                    },
                    judgeUploadTotal:function(){
                        var content = $('.pic_content');
                        var len = content.length;
                        feng_pic.uploadTotal=len;
                        feng_pic.uploadLimit=feng_pic.uploadLimit-len;
                        $('.pic_total').text(feng_pic.uploadTotal);
                        $('.pic_limit').text(feng_pic.uploadLimit);
                    },
                    clearAll:function(){
                    	$("#clearAll").click(function(){
                    		$('.pic_total').text(0);
                    	    $('.pic_limit').text(25);
                    	    $('.pic_list').html("");
                    	});
                    },
                    //初始化参数
                    init : function (swf,uploader,uploadLimit) {
                            feng_pic.swf=swf;
                            feng_pic.uploader=uploader;
                            feng_pic.uploadLimit=uploadLimit;
                            feng_pic.Max=uploadLimit;
                            feng_pic.allHover();
                            feng_pic.allRemove();
                            feng_pic.clearAll();
                            feng_pic.judgeUploadTotal();
                            feng_pic.uploadify();

                    }
};



