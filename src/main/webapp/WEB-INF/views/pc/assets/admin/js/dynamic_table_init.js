/*声明*/
var oTable;

function InitDetailsColumn() {
    /*
     * Insert a 'details' column to the table
     */
    var nCloneTh = document.createElement('th');
    var nCloneTd = document.createElement('td');
    nCloneTd.innerHTML = '<img src="/admin/images/details_open.png">';
    nCloneTd.className = "center";

    $('#hidden-table-info thead tr').each(function () {
        this.insertBefore(nCloneTh, this.childNodes[0]);
    });

    $('#hidden-table-info tbody tr').each(function () {
        this.insertBefore(nCloneTd.cloneNode(true), this.childNodes[0]);
    });

    return nCloneTd;
}


function toDelete(t,id, url,param) {
    url = (id == undefined) ? url : url+id;
    url = (param == undefined) ? url : url +'?'+ param

    var nTr = $(t).parents('tr')[0];

    console.log(url);
    var isDelete = confirm("确定要删除?");
    if (isDelete) {

        $.ajax({
            url: url,
            type: 'POST',
            success: function (data) {
                console.log(data);
                if(data.result == false){
                    alert("操作失败!  "+data.message);
                }else{
                    oTable.fnDeleteRow(nTr);
                }
            }
        })
    }
}



/**
 * 初始化dataTable
 * @param url
 * @param detailUrl 详细信息的url
 * @param columns 表格中的列
 * @param forrmatFunc 用来构造详情页的函数
 *
 * */


function initDataTable(url, detailUrl, columns, formatFunc,sortMethod) {
    /*
     * Initialse DataTables, with no sorting on the 'details' column
     */
    console.log(sortMethod);
     oTable = $('#hidden-table-info').dataTable({
        
        "sAjaxSource": url,
        "sAjaxDataProp": "",
        "aaSorting": (sortMethod == undefined)?[[1, 'desc']]:sortMethod,
        "aoColumns": columns,
        "bProcessing": true,
        "oLanguage": {
            "sProcessing": "正在加载中......",
            "sLengthMenu": "每页显示 _MENU_ 条记录",
            "sZeroRecords": "对不起，查询不到相关数据！",
            "sEmptyTable": "表中无数据存在！",
            "sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
            "sInfoFiltered": "数据表中共为 _MAX_ 条记录",
            "sSearch": "搜索",
            "oPaginate": {
                "sFirst": "首页",
                "sPrevious": "上一页",
                "sNext": "下一页",
                "sLast": "末页"
            }
        }
    });

    /* Add event listener for opening and closing details
     * Note that the indicator for showing which row is open is not controlled by DataTables,
     * rather it is done here
     */


    $(document).on('click', '#hidden-table-info tbody td img', function () {
        var nTr = $(this).parents('tr')[0];
        if (oTable.fnIsOpen(nTr)) {
            /* This row is already open - close it */
            this.src = "/admin/images/details_open.png";
            oTable.fnClose(nTr);
        }
        else {
            /* Open this row */
            this.src = "/admin/images/details_close.png";

            var aData = oTable.fnGetData(nTr);
            var id = aData.id;
            var url = detailUrl + id;
            $.ajax({
                type: "GET",
                url: url,
                success: function (data) {
                    console.log(data);
                    oTable.fnOpen(nTr, formatFunc(data), 'details');
                }
            });


        }
    });

}



