$shList = $('.sh-item');
var url = '';
var $item;
var picIds;
for( var i = 0; i<$shList.length; i++) {
    $item = $($shList[i]);
    picIds = ($item.data('pid') + '').split(';');
    if(picIds[0] > 0) {
        url = '/static/images/users/' + picIds[0];
    } else {
        //url = '/images/pig.jpg';
    }
    $item.attr('src', url);
}
