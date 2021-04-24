/**
 * 单据管理初始化
 */
var Invoice = {
    id: "InvoiceTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Invoice.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '物品名称', field: 'name', align: 'center', valign: 'middle', sortable: true},
        {title: '类型', field: 'type', align: 'center', valign: 'middle', sortable: true},
        {title: '日期', field: 'create_date', align: 'center', valign: 'middle', sortable: true},
        {title: '出发地', field: 'origin', align: 'center', valign: 'middle', sortable: true},
        {title: '目的地', field: 'destination', align: 'center', valign: 'middle', sortable: true},
        {title: '物品价格', field: 'price', align: 'center', valign: 'middle', sortable: true},
        {title: '运输费', field: 'transportation', align: 'center', valign: 'middle', sortable: true},
        {title: '状态', field: 'status', align: 'center', valign: 'middle', sortable: true}

    ];
};

/**
 * 检查是否选中
 */
Invoice.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        Invoice.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加单据
 */
Invoice.openAdd = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '添加单据',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/invoice/invoice_add?d_id='+Invoice.seItem.id

        });
        // $("#d_id").val(Invoice.seItem.id);
        this.layerIndex = index;
    }
};

/**
 * 打开查看单据详情
 */
Invoice.openDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '单据详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/invoice/invoice_update/' + Invoice.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除单据
 */
Invoice.delete = function () {
    if (this.check()) {

        var operation = function(){
            var ajax = new $ax(Feng.ctxPath + "/invoice/delete", function (data) {
                Feng.success("删除成功!");
                Invoice.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id", Invoice.seItem.id);
            ajax.start();
        };

        Feng.confirm("是否删除单据 " + Invoice.seItem.name + "?", operation);
    }
};

/**
 * 查询单据列表
 */
Invoice.search = function () {

    var queryData = {};
    queryData['condition'] = $("#condition").val();
    // alert($("#condition").val());
    Invoice.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Invoice.initColumn();
    var table = new BSTable(Invoice.id, "/invoice/list", defaultColunms);
    table.setPaginationType("client");
    Invoice.table = table.init();
});
