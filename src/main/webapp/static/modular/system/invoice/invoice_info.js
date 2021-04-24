/**
 * 初始化字典详情对话框
 */
var InvoiceInfoDlg = {
    InvoiceInfoData: {}
};
/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
InvoiceInfoDlg.set = function (key, val) {
    this.InvoiceInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
InvoiceInfoDlg.get = function (key) {
    return $("#" + key).val();
}
/**
 * item获取新的id
 */
InvoiceInfoDlg.newId = function () {
    if(this.count == undefined){
        this.count = 0;
    }
    this.count = this.count + 1;
    return "dictItem" + this.count;
};

/**
 * 关闭此对话框
 */
InvoiceInfoDlg.close = function () {
    parent.layer.close(window.parent.Invoice.layerIndex);
};

/**
 * 添加条目
 */
InvoiceInfoDlg.addItem = function () {
    $("#itemsArea").append(this.itemTemplate);
    $("#dictItem").attr("id", this.newId());
};

/**
 * 删除item
 */
InvoiceInfoDlg.deleteItem = function (event) {
    var obj = Feng.eventParseObject(event);
    obj.parent().parent().remove();
};


/**
 * 收集添加的数据
 */
InvoiceInfoDlg.collectData = function () {
    this.set('d_id').set('customer').set("address").set("end_date").set("create_date").set("date_length").set("price").set('id').set('u_id');
};
/**
 * 清除数据
 */
InvoiceInfoDlg.clearData = function () {
    this.InvoiceInfoDlg = {};
}

/**
 * 提交添加字典
 */
InvoiceInfoDlg.addSubmit = function () {

    this.clearData();
    this.collectData();


    //提交信息
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/invoice/add", function (data) {
        Feng.success("添加成功!");
        window.parent.Invoice.table.refresh();
        InvoiceInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    // ajax.set("name");
    ajax.set(this.InvoiceInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
InvoiceInfoDlg.editSubmit = function () {
    this.clearData();
    this.collectData();
    var ajax = new $ax(Feng.ctxPath + "/invoice/update", function (data) {
        Feng.success("修改成功!");
        window.parent.Invoice.table.refresh();
        InvoiceInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    // ajax.set('dictId',$("#dictId").val());
    ajax.set(this.InvoiceInfoData);
    ajax.start();
};
