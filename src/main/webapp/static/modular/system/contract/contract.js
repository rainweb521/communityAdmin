/**
 * 通知管理初始化
 */
var Contract = {
    id: "ContractTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Contract.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '合同编号', field: 'code', align: 'center', valign: 'middle', sortable: true},
        {title: '客户姓名', field: 'customer', align: 'center', valign: 'middle', sortable: true},
        {title: '物业名称', field: 'company', align: 'center', valign: 'middle', sortable: true},
        {title: '房屋名称', field: 'house_desc', align: 'center', valign: 'middle', sortable: true},
        {title: '合同状态', field: 'status_str', align: 'center', valign: 'middle', sortable: true},
        {title: '创建时间', field: 'create_date', align: 'center', valign: 'middle', sortable: true}
    ];
};

/**
 * 检查是否选中
 */
Contract.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        Contract.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加通知
 */
Contract.openAddContract = function () {
    var index = layer.open({
        type: 2,
        title: '添加',
        area: ['800px', '620px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/contract/contract_add'
    });
    this.layerIndex = index;
};
Contract.download = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '下载',
            area: ['600px', '620px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/contract/download/'+ Contract.seItem.id
        });
        this.layerIndex = index;
    }
};
/**
 * 打开查看通知详情
 */
Contract.openContractDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/contract/contract_update/' + Contract.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除通知
 */
Contract.delete = function () {
    if (this.check()) {

        var operation = function(){
            var ajax = new $ax(Feng.ctxPath + "/contract/delete", function (data) {
                Feng.success("删除成功!");
                Contract.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("contractId", Contract.seItem.id);
            ajax.start();
        };

        Feng.confirm("是否删除合同 " + Contract.seItem.code + "?", operation);
    }
};

/**
 * 查询通知列表
 */
Contract.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    // alert($("#condition").val());
    Contract.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Contract.initColumn();
    var table = new BSTable(Contract.id, "/contract/list", defaultColunms);
    table.setPaginationType("client");
    Contract.table = table.init();
});
