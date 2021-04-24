package com.stylefeng.guns.common.persistence.model;

/**
 * @Author: wcy
 * @Date: 2020/2/2
 */
public class RentAllDTO {
    private Integer behouse;
    private Integer owehouse;
    private Double allmoney;

    public Integer getBehouse() {
        return behouse;
    }

    public void setBehouse(Integer behouse) {
        this.behouse = behouse;
    }

    public Integer getOwehouse() {
        return owehouse;
    }

    public void setOwehouse(Integer owehouse) {
        this.owehouse = owehouse;
    }

    public Double getAllmoney() {
        return allmoney;
    }

    public void setAllmoney(Double allmoney) {
        this.allmoney = allmoney;
    }
}
