/**
 * 初始化通知详情对话框
 */
var RentInfoDlg = {
    rentInfoData: {},
    editor: null,
    validateFields: {
        title: {
            validators: {
                notEmpty: {
                    message: '标题不能为空'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
RentInfoDlg.clearData = function () {
    this.rentInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
RentInfoDlg.set = function (key, val) {
    this.rentInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
RentInfoDlg.get = function (key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
RentInfoDlg.close = function () {
    parent.layer.close(window.parent.Rent.layerIndex);
}

/**
 * 收集数据
 */
RentInfoDlg.collectData = function () {
    this.set('id').set('c_code').set('invoice').set('yajin').set('dinjin').set('price').set('num').set('status').set('create_date');
}

/**
 * 验证数据是否为空
 */
RentInfoDlg.validate = function () {
    $('#rentInfoForm').data("bootstrapValidator").resetForm();
    $('#rentInfoForm').bootstrapValidator('validate');
    return $("#rentInfoForm").data('bootstrapValidator').isValid();
};



/**
 * 提交修改
 */
RentInfoDlg.editSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/rent/update", function (data) {
        Feng.success("修改成功!");
        window.parent.Rent.table.refresh();
        RentInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.rentInfoData);
    ajax.start();
}

$(function () {
    Feng.initValidator("rentInfoForm", RentInfoDlg.validateFields);


});
