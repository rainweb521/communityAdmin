package com.stylefeng.guns.common.persistence.model;

/**
 * @Author: wcy
 * @Date: 2020/2/2
 */
public class CustomerDTO extends User{
    private Integer c_id;
    private String work;
    private String work_address;
    private String live_address;
    private String live_date;
    private String family;
    private String car;
    private String animal;
    private String remark;
    private Integer user_id;

    private String sexstr;

    public Integer getC_id() {
        return c_id;
    }

    public void setC_id(Integer c_id) {
        this.c_id = c_id;
    }

    public String getSexstr() {
        return sexstr;
    }

    public void setSexstr(String sexstr) {
        this.sexstr = sexstr;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getWork_address() {
        return work_address;
    }

    public void setWork_address(String work_address) {
        this.work_address = work_address;
    }

    public String getLive_address() {
        return live_address;
    }

    public void setLive_address(String live_address) {
        this.live_address = live_address;
    }

    public String getLive_date() {
        return live_date;
    }

    public void setLive_date(String live_date) {
        this.live_date = live_date;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
}
