/**
 * 初始化通知详情对话框
 */
var ContractInfoDlg = {
    contractInfoData: {},
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
ContractInfoDlg.clearData = function () {
    this.contractInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ContractInfoDlg.set = function (key, val) {
    this.contractInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ContractInfoDlg.get = function (key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ContractInfoDlg.close = function () {
    parent.layer.close(window.parent.Contract.layerIndex);
}

/**
 * 收集数据
 */
ContractInfoDlg.collectData = function () {
    this.contractInfoData['content'] = ContractInfoDlg.editor.txt.text();
    this.set('id').set('user_id').set('customer').set('company').set('price').set('h_id').set('create_date').set('customer').set('status');
}

/**
 * 验证数据是否为空
 */
ContractInfoDlg.validate = function () {
    $('#contractInfoForm').data("bootstrapValidator").resetForm();
    $('#contractInfoForm').bootstrapValidator('validate');
    return $("#contractInfoForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
ContractInfoDlg.addSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/contract/add", function (data) {
        Feng.success("添加成功!");
        window.parent.Contract.table.refresh();
        ContractInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.contractInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ContractInfoDlg.editSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/contract/update", function (data) {
        Feng.success("修改成功!");
        window.parent.Contract.table.refresh();
        ContractInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.contractInfoData);
    ajax.start();
}
ContractInfoDlg.showUser = function () {
    var id = document.getElementById('user_id').value;
    var ajax = new $ax(Feng.ctxPath + "/mgr/view/"+id, function(data) {
        document.getElementById('customer').value = data['name'];
    }, function(data) {
        Feng.error("加载ztree信息失败!");
    });
    ajax.start();
};
$(function () {
    Feng.initValidator("contractInfoForm", ContractInfoDlg.validateFields);

    //初始化编辑器
    var E = window.wangEditor;
    var editor = new E('#editor');
    editor.create();
    editor.txt.html($("#contentVal").val());
    ContractInfoDlg.editor = editor;


    var ajax = new $ax(Feng.ctxPath + "/mgr/list2", function(data) {
        for(var i=0; i<data.length;i++){ //循环添加多个值
            $("#user_id").append("<option value='"+data[i]['id']+"'>"+data[i]['name']+"</option>");
        }
    }, function(data) {
        Feng.error("加载ztree信息失败!");
    });
    ajax.start();
    ajax = new $ax(Feng.ctxPath + "/house/list", function(data) {
        for(var i=0; i<data.length;i++){ //循环添加多个值
            $("#h_id").append("<option value='"+data[i]['id']+"'>"+data[i]['house_desc']+"</option>");
        }
    }, function(data) {
        Feng.error("加载ztree信息失败!");
    });
    ajax.start();
});
