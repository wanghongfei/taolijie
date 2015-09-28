// 图片上传demo
jQuery(function() {
    var $ = jQuery,
        $list = $('.img-list-wrapper ul'),
        $img = $('.img-list-img'),
    // 优化retina, 在retina下这个值是2
        ratio = window.devicePixelRatio || 1,

    // 缩略图大小
        thumbnailWidth = 100 * ratio,
        thumbnailHeight = 100 * ratio,

    // Web Uploader实例
        uploader;

    // 初始化Web Uploader
    uploader = WebUploader.create({

        // 自动上传。
        auto: true,

        // swf文件路径
        swf: '/js/Uploader.swf',

        // 文件接收服务端。
        //server: '/static/upload',
        server: 'http://v0.api.upyun.com/taolijie-pic/',

        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: '.img-list-btn',

        // 只允许选择文件，可选。
        accept: {
            title: 'Images',
            extensions: 'gif,jpg,jpeg,bmp,png',
            mimeTypes: 'image/*'
        },
        compress: {
            width: 600,
            height: 450,
            compressSize: 0,
            allowMagnify:true,
            crop: true,
            preserveHeaders: true,
            noCompressIfLarger: false
        },
        method: "POST",
        formData:{
        }
    });
    uploader.on('uploadBeforeSend',function(obj,data, header){
        //header['Origin'] = '*';
        //header['Access-Control-Request-Method']= "POST";
        //console.log(data);
        var url = '/api/user/sign?picType=1&expiration='+(new Date().getTime()+10*1000);
            $.ajax({
                url: url,
                async: false
            })
            .success(function(d){
                    if(d.code ==0){
                       data['policy'] = d.data.policy;
                       data['signature'] = d.data.sign;
                    }else{
                        //取消发送
                        alert('您上传图片过于频繁');
                    }
            }).error(function(d){
                });

    });
    uploader.on('error', function(err, file){
        console.log(err);
        if(err == 'F_DUPLICATE'){
            alert('上传失败,图片重复');
        }
    });

    // 当有文件添加进来的时候
    uploader.on( 'fileQueued', function( file ) {
        //var $li = $(
        //        '<div id="' + file.id + '" class="file-item thumbnail">' +
        //        '<img>' +
        //        '<div class="info">' + file.name + '</div>' +
        //        '</div>'
        //    ),
        //    $img = $li.find('img');
        //
        //$list.append( $li );

        // 创建缩略图
        /*
        uploader.makeThumb( file, function( error, src ) {
            if ( error ) {
                $img.replaceWith('<span>不能预览</span>');
                return;
            }

            $img.attr( 'src', src );
        }, thumbnailWidth, thumbnailHeight );
        */
    });

    // 文件上传过程中创建进度条实时显示。
    uploader.on( 'uploadProgress', function( file, percentage ) {
        var $li = $( '#'+file.id ),
            $percent = $li.find('.progress span');

        // 避免重复创建
        if ( !$percent.length ) {
            $percent = $('<p class="progress"><span></span></p>')
                .appendTo( $li )
                .find('span');
        }

        $percent.css( 'width', percentage * 100 + '%' );
    });

    // 文件上传成功，给item添加成功class, 用样式标记上传成功。
    uploader.on( 'uploadSuccess', function( file , data) {
        if( $('.img-list-item').length == 3) {
            $('.img-list-btn').toggle();
        }
        $('<li class="img-list-item" data-pid="'
            + data.url.substr(1)
            + '">'
            + '<img src="http://taolijie-pic.b0.upaiyun.com/'
            + data.url.substr(1)
            + '!pc200" class="img-list-img"/>'
            + '<span class="btn-img-del">x</span>'
            + '</li>').insertBefore('.img-list-btn');
        var picIds = $('input[name=picIds]').val().split(';');
        if(picIds[0] === "") {
            picIds = [];
        }
        picIds.push(data.url.substr(1));
        $('input[name=picIds]').val(picIds.join(';'));
    });

    // 文件上传失败，现实上传出错。
    uploader.on( 'uploadError', function( file ) {
        var $li = $( '#'+file.id ),
            $error = $li.find('div.error');
        // 避免重复创建
        if ( !$error.length ) {
            $error = $('<div class="error"></div>').appendTo( $li );
        }

        $error.text('上传失败');
    });

    // 完成上传完了，成功或者失败，先删除进度条。
    uploader.on( 'uploadComplete', function( file ) {
        $( '#'+file.id ).find('.progress').remove();
    });




    /*
     * del img uploaded
     *
     */
    $('.img-list-wrapper').delegate('.btn-img-del','click', function(){
        if( $('.img-list-item').length == 4) {
            $('.img-list-btn').toggle();
        }
        var $img = $(this).parent('li');
        $img.remove();
        //同时删除文件队列
        var picIds = $('input[name=picIds]').val().split(';');
        for( var i = 0;i < picIds.length; i++){
            if( $img.data('pid') == picIds[i] ) {
                picIds.splice(i,1);
                break;
            }
        }
        $('input[name=picIds]').val(picIds.join(';'));
    });

    /* upload img list init*/
    var initImgList = function() {
        var ids = $('input[name=picIds]').val().split(';');
        if(ids[0] != '') {
            ids.forEach(function(data) {
                $('<li class="img-list-item" data-pid="'
                    + data
                    + '">'
                    + '<img src="http://taolijie-pic.b0.upaiyun.com/'
                    + data
                    + '!pc200" class="img-list-img"/>'
                    + '<span class="btn-img-del">x</span>'
                    + '</li>').insertBefore('.img-list-btn');
            })
            if(ids.length > 3) {
                $('.img-list-btn').toggle();
            }
        }
    }
    initImgList();
});
