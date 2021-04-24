/**
 * 通知管理初始化
 */
var House = {
    id: "HouseTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
House.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '房屋描述', field: 'house_desc', align: 'center', valign: 'middle', sortable: true},
        {title: '房屋类型', field: 'house_model', align: 'center', valign: 'middle', sortable: true},
        {title: '房屋面积', field: 'house_area', align: 'center', valign: 'middle', sortable: true},
        {title: '房屋楼层', field: 'house_floor', align: 'center', valign: 'middle', sortable: true},
        {title: '房号', field: 'house_code', align: 'center', valign: 'middle', sortable: true},
        {title: '状态', field: 'house_type', align: 'center', valign: 'middle', sortable: true},
        {title: '价格', field: 'house_price', align: 'center', valign: 'middle', sortable: true},
        // {title: '出租地址', field: 'house_address', align: 'center', valign: 'middle', sortable: true},
        {title: '楼号', field: 'community_name', align: 'center', valign: 'middle', sortable: true},
        {title: '房屋朝向', field: 'house_oriented', align: 'center', valign: 'middle', sortable: true},
        {title: '发布时间', field: 'create_date', align: 'center', valign: 'middle', sortable: true}
    ];
};

/**
 * 检查是否选中
 */
House.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        House.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加通知
 */
House.openAddHouse = function () {
    var index = layer.open({
        type: 2,
        title: '添加通知',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/house/house_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看通知详情
 */
House.openHouseDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '通知详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/house/house_update/' + House.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除通知
 */
House.delete = function () {
    if (this.check()) {

        var operation = function(){
            var ajax = new $ax(Feng.ctxPath + "/house/delete", function (data) {
                Feng.success("删除成功!");
                House.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("houseId", House.seItem.id);
            ajax.start();
        };

        Feng.confirm("是否删除通知 " + House.seItem.title + "?", operation);
    }
};

/**
 * 查询通知列表
 */
House.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    // alert($("#condition").val());
    House.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = House.initColumn();
    var table = new BSTable(House.id, "/house/list", defaultColunms);
    table.setPaginationType("client");
    House.table = table.init();
});
