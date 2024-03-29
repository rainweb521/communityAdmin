/**
 * 初始化通知详情对话框
 */
var HouseInfoDlg = {
    houseInfoData: {},
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
HouseInfoDlg.clearData = function () {
    this.houseInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
HouseInfoDlg.set = function (key, val) {
    this.houseInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
HouseInfoDlg.get = function (key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
HouseInfoDlg.close = function () {
    parent.layer.close(window.parent.House.layerIndex);
}

/**
 * 收集数据
 */
HouseInfoDlg.collectData = function () {
    this.set('id').set('status').set('house_desc').set('house_code').set('house_model').set('house_area').set('house_floor').set('house_type').set('house_price').set('house_address').set('community_name').set('house_oriented').set('create_date');
}

/**
 * 验证数据是否为空
 */
HouseInfoDlg.validate = function () {
    $('#houseInfoForm').data("bootstrapValidator").resetForm();
    $('#houseInfoForm').bootstrapValidator('validate');
    return $("#houseInfoForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
HouseInfoDlg.addSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/house/add", function (data) {
        Feng.success("添加成功!");
        window.parent.House.table.refresh();
        HouseInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.houseInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
HouseInfoDlg.editSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/house/update", function (data) {
        Feng.success("修改成功!");
        window.parent.House.table.refresh();
        HouseInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.houseInfoData);
    ajax.start();
}

$(function () {
    Feng.initValidator("houseInfoForm", HouseInfoDlg.validateFields);


});
