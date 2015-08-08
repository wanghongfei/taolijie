/**
 * Created by wyn on 15-8-8.
 */

/**
 * 字符串过长省略过滤器
 * @value 字符串
 * @len 指定长度
 */
tlj.filter('omit', function () {
    return function (value, len) {
        if (value.length > len) {
            return value.substring(0, len) + "...";
        } else {
            return value;
        }
    }
});


/**
 * 时间过滤器
 *
 */
tlj.filter('dateShow', function () {
    return function (date) {
        //如果是当天 显示几分钟前
        var nowTime = new Date().getTime();
        var post = new Date(date);
        var nowDay = new Date().getDay();
        var time = nowTime- post.getTime() ;
        if (nowDay === post.getDay()) {
            if (time < 60 * 60 * 1000) {
                return Math.ceil(time / (60 * 1000)) + "分钟前";
            } else {
                return Math.ceil(time / (60 * 60 * 1000))+ "小时前";
            }

        }else{
            return (post.getMonth()+1)+"月"+post.getDay()+"日";
        }


        return time;
    }
});
