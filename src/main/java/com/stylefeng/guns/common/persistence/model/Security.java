package com.stylefeng.guns.common.persistence.model;

import javax.persistence.Table;

/**
 * @Author: wcy
 * @Date: 2020/2/2
 */
@Table(name = "sys_security")
public class Security extends Base{
    private String name;
    private String problem;
    private String important;
    private String create_date;
    private String remark;
    private String area;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getImportant() {
        return important;
    }

    public void setImportant(String important) {
        this.important = important;
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
