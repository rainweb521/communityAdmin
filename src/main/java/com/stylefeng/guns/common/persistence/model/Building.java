package com.stylefeng.guns.common.persistence.model;

import javax.persistence.Table;

/**
 * @Author: wcy
 * @Date: 2020/2/1
 */
@Table(name = "sys_building")
public class Building extends Base{
    private String code;
    private String num;
    private String area;
    private String unit;
    private String house;
    private String location;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
