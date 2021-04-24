/**
 * 通知管理初始化
 */
var Rent = {
    id: "RentTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Rent.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '合同编号', field: 'c_code', align: 'center', valign: 'middle', sortable: true},
        {title: '发票编号', field: 'invoice', align: 'center', valign: 'middle', sortable: true},
        {title: '业主姓名', field: 'name', align: 'center', valign: 'middle', sortable: true},
        {title: '家庭住址', field: 'address', align: 'center', valign: 'middle', sortable: true},
        {title: '手机号', field: 'phone', align: 'center', valign: 'middle', sortable: true},
        {title: '欠费金额', field: 'price', align: 'center', valign: 'middle', sortable: true},
        {title: '缴纳日期', field: 'create_date', align: 'center', valign: 'middle', sortable: true}
    ];
};

/**
 * 检查是否选中
 */
Rent.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        Rent.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加通知
 */
Rent.openAddRent = function () {
    var index = layer.open({
        type: 2,
        title: '添加通知',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/rent/rent_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看通知详情
 */
Rent.openRentDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '通知详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/rent/rent_update/' + Rent.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 缴费
 */
Rent.delete = function () {
    if (this.check()) {

        var operation = function(){
            var ajax = new $ax(Feng.ctxPath + "/rent/delete", function (data) {
                Feng.success("缴费成功!");
                Rent.table.refresh();
            }, function (data) {
                Feng.error("缴费失败!" + data.responseJSON.message + "!");
            });
            ajax.set("rentId", Rent.seItem.c_code);
            ajax.set("price", Rent.seItem.price);
            ajax.set("name", Rent.seItem.name);
            ajax.set("phone", Rent.seItem.phone);
            ajax.set("address", Rent.seItem.address);
            ajax.set("c_code", Rent.seItem.c_code);
            ajax.start();
        };

        Feng.confirm("确认为用户 " + Rent.seItem.name + "缴费"+Rent.seItem.price+"元?", operation);
    }
};

/**
 * 查询通知列表
 */
Rent.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    // alert($("#condition").val());
    Rent.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Rent.initColumn();
    var table = new BSTable(Rent.id, "/rent/owelist", defaultColunms);
    table.setPaginationType("client");
    Rent.table = table.init();
});
