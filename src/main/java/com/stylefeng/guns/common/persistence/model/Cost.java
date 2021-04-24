package com.stylefeng.guns.common.persistence.model;

import javax.persistence.Table;

/**
 * @Author: wcy
 * @Date: 2020/2/2
 */
@Table(name = "sys_cost")
public class Cost extends Base {
    private String name;
    private String useinfo;
    private String content;
    private Double cost;
    private String create_date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUseinfo() {
        return useinfo;
    }

    public void setUseinfo(String useinfo) {
        this.useinfo = useinfo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }
}
