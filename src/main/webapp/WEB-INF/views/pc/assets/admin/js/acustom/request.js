/**
 * Created by wynfrith on 15-6-18.
 */

$(document).ready(function(){

    /**
     * 删除
     */
    $(document).on('click','.delete-btn',function(){
        //获取删除的id
        var id = this.getAttribute("data-id");
        var type = this.getAttribute("data-type");
        var confirmMsg = this.getAttribute("data-confirm-msg")!=null?this.getAttribute("data-confirm-msg"):'确定要删除吗?';
        var okMsg = this.getAttribute("data-ok-msg")!=null?this.getAttribute("data-ok-msg"):'删除成功';
        //获取删除的url
        var url = "/manage/"+type+"/del/"+id;

        if(confirm(confirmMsg)){
            $.ajax({
                url:url,
                type:'post',
                success:function(data){
                    if(data.result){
                        alert(okMsg);
                        location.reload();
                    }else{
                        alert(data.message);
                    }
                }
            });
        }

    });

    //表单提交的在validation-init中



});
