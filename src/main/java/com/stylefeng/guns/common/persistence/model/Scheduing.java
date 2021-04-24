package com.stylefeng.guns.common.persistence.model;

import javax.persistence.Table;

/**
 * @Author: wcy
 * @Date: 2020/2/2
 */
@Table(name = "sys_scheduing")
public class Scheduing extends Base{
    private String old_name;
    private String new_name;
    private String classstr;
    private String create_date;
    private String remark;
    private String area;

    public String getOld_name() {
        return old_name;
    }

    public void setOld_name(String old_name) {
        this.old_name = old_name;
    }

    public String getNew_name() {
        return new_name;
    }

    public void setNew_name(String new_name) {
        this.new_name = new_name;
    }

    public String getClassstr() {
        return classstr;
    }

    public void setClassstr(String classstr) {
        this.classstr = classstr;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
