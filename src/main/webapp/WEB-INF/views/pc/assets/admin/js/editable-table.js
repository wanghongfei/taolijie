var EditableTable = function () {

    return {

        //main function to initiate the module
        init: function (data) {
            var $id = '#'+data.id;

            var $addButton = $id+' .add-button';
            var $table = $id+' .show-table';

            var $delete = $table+' a.'+'delete';
            var $cancel = $table+' a.cancel';
            var $edit = $table+' a.edit';



            function restoreRow(oTable, nRow) {
                var aData = oTable.fnGetData(nRow);
                var jqTds = $('>td', nRow);

                var i = 0;
                for(var p in aData){
                    oTable.fnUpdate(aData[p], nRow, i, false);
                    i++;
                }


                oTable.fnDraw();
            }

            function editRow(oTable, nRow) {
                var aData = oTable.fnGetData(nRow);
                var jqTds = $('>td', nRow);
                var i = 0;
                for(var p in aData){
                    jqTds[i].innerHTML = '<input type="text" class="form-control small" value="' +aData[p] + '">';
                    i++;
                }

                jqTds[i].innerHTML = '<a class="edit " >保存</a>';
                jqTds[i+1].innerHTML = '<a class="cancel" >取消</a>';
            }

            function saveRow(oTable, nRow) {
                var jqInputs = $('input', nRow);
                var aData = oTable.fnGetData(nRow);
                var jqTds = $('>td', nRow);
                var i = 0;
                for(var p in aData){
                    oTable.fnUpdate(aData[p],nRow,i,false);
                    i++;
                }
                oTable.fnUpdate('<a class="edit" href="">编辑</a>', nRow, i, false);
                oTable.fnUpdate('<a class="delete" href="">删除</a>', nRow, i+1, false);
                oTable.fnDraw();
            }

            function cancelEditRow(oTable, nRow) {
                console.log("cancelEdit");
                var jqInputs = $('input', nRow);
                var jqTds = $('>td', nRow);
                for (var i = 0, iLen = jqTds.length-2; i < iLen; i++) {
                    oTable.fnUpdate(jqInputs[i].value, nRow, i, false);
                }

                oTable.fnUpdate('<a class="edit" href="">保存</a>', nRow, i, false);
                oTable.fnDraw();
            }

            var oTable = $($table).dataTable({
                "aLengthMenu": [
                    [5, 15, 20, -1],
                    [5, 15, 20, "All"] // change per page values here
                ],
                // set the initial value
                "iDisplayLength": 5,
                "sAjaxSource": data.url,
                "aoColumns": data.columns,
                "sAjaxDataProp": "",
                "sDom": "<'row'<'col-lg-6'l><'col-lg-6'f>r>t<'row'<'col-lg-6'i><'col-lg-6'p>>",
                "sPaginationType": "bootstrap",
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
                },
                "aoColumnDefs": [{
                    'bSortable': false,
                    'aTargets': [0]
                }
                ]
            });

            jQuery('#editable-sample_wrapper .dataTables_filter input').addClass("form-control medium"); // modify table search input
            jQuery('#editable-sample_wrapper .dataTables_length select').addClass("form-control xsmall"); // modify table per page dropdown

            var nEditing = null;

            $($addButton).click(function (e) {
                e.preventDefault();
                var aiNew = oTable.fnAddData(['','', '', '', '',
                    '<a class="edit" href="">编辑</a>', '<a class="cancel" data-mode="new" href="">删除</a>'
                ]);
                var nRow = oTable.fnGetNodes(aiNew[0]);
                editRow(oTable, nRow);
                nEditing = nRow;
            });

            $($delete).live('click', function (e) {
                e.preventDefault();

                if (confirm("是否删除 ?") == false) {
                    return;
                }

                var nRow = $(this).parents('tr')[0];
                oTable.fnDeleteRow(nRow);
                alert("Deleted! Do not forget to do some ajax to sync with backend :)");
            });

            $($cancel).live('click', function (e) {
                e.preventDefault();
                if ($(this).attr("data-mode") == "new") {
                    var nRow = $(this).parents('tr')[0];
                    oTable.fnDeleteRow(nRow);
                } else {
                    restoreRow(oTable, nEditing);
                    nEditing = null;
                }
            });

            $($edit).live('click', function (e) {
                e.preventDefault();

                /* Get the row as a parent of the link that was clicked on */
                var nRow = $(this).parents('tr')[0];

                if (nEditing !== null && nEditing != nRow) {
                    /* Currently editing - but not this row - restore the old before continuing to edit mode */
                    restoreRow(oTable, nEditing);
                    editRow(oTable, nRow);
                    nEditing = nRow;
                } else if (nEditing == nRow && this.innerHTML == "保存") {
                    /* Editing this row and want to save it */
                    saveRow(oTable, nEditing);
                    nEditing = null;
                    alert("Updated! Do not forget to do some ajax to sync with backend :)");
                } else {
                    /* No edit in progress - let's start one */
                    editRow(oTable, nRow);
                    nEditing = nRow;
                }
            });
        }

    };

}();