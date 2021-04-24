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
        {title: '租房描述', field: 'house_desc', align: 'center', valign: 'middle', sortable: true},
        {title: '房屋类型', field: 'house_model', align: 'center', valign: 'middle', sortable: true},
        {title: '房屋面积', field: 'house_area', align: 'center', valign: 'middle', sortable: true},
        {title: '房屋楼层', field: 'house_floor', align: 'center', valign: 'middle', sortable: true},
        {title: '租售方式', field: 'house_type', align: 'center', valign: 'middle', sortable: true},
        {title: '价格', field: 'house_price', align: 'center', valign: 'middle', sortable: true},
        {title: '地址', field: 'house_address', align: 'center', valign: 'middle', sortable: true},
        {title: '小区名字', field: 'community_name', align: 'center', valign: 'middle', sortable: true},
        {title: '房屋朝向', field: 'house_oriented', align: 'center', valign: 'middle', sortable: true},
        {title: '发布时间', field: 'create_date', align: 'center', valign: 'middle', sortable: true}
    ];
};



$(function () {
    var defaultColunms = House.initColumn();
    var table = new BSTable(House.id, "/house/liststatus?status=20", defaultColunms);
    table.setPaginationType("client");
    House.table = table.init();
});
