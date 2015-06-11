/**
 *
 * Created by wynfrith on 15-5-31.
 */

jQuery(function(){
    var $avatar = $("#userImg");
    var uploader = WebUploader.create({
        swf: 'http://127.0.0.1:8888/webuploader/dist/Uploader.swf',
        server: 'http://localhost:8080/static/upload',
        pick: $avatar,
        resize: false,
        accept:{
            title: 'Images',
            extensions: 'gif,jpg,jpeg,bmp,png',
            mimeTypes: 'image/*'
        }

    });

    uploader.on('fileQueued',function(file){
        //获得缩率图,并覆盖之前 头像
        $img = $avatar.find('img');
        var width = 150;
        var height  = 150;

        uploader.makeThumb(file, function(error,src){
            if(error){
                alert("无法预览");
                return ;
            }
            $img.attr('src',src);

        },width,height);

        //上传
        //uploader.upload();
    }.bind('#avatar'));


    //初始化上传
// var uploader = WebUploader.create({
// 	swf: 'http://127.0.0.1:8888/webuploader/dist/Uploader.swf',
// 	server: 'http://localhost:8080/static/upload',
// 	pick: '#avatar',
// 	resize:false,
// 	accept: {
// 	        title: 'Images',
// 	        extensions: 'gif,jpg,jpeg,bmp,png',
// 	        mimeTypes: 'image/*'
// 	    }
// });




// //有文件添加到队列时
// uploader.on('fileQueued',function(file){
// 	var $li = $('<div id="'+file.id+'" class="thumbnail">'+
// 			'<img />'+ '<div class="info">' + file.name + '</div>' +
// 		'</div>');
// 	$list.append($li);

// 	//生成缩略图
// 	var $img = $li.find('img');
// 	var width = 100;
// 	var height =100;

// 	uploader.makeThumb(file,function(error, src){
// 		if(error){
// 			$img.replaceWith('<span>无法预览</span>');
// 			return;
// 		}
// 		$img.attr('src',src);
// 	},width,height);

// });


// 文件上传过程中创建进度条实时显示。
    uploader.on('uploadProgress', function( file, percentage ) {
        console.log("上传中...");
    });

//点击上传
// $btn.on('click',function(){
// 	uploader.upload();
// });

    uploader.on( 'uploadSuccess', function( file ) {
        console.log("上传成功");
    });

    uploader.on( 'uploadError', function( file ) {
        alert("uploadSuccess");
    });

    uploader.on( 'uploadComplete', function( file ) {
        console.log("上传完成")

    });



});
