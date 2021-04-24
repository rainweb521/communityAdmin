package com.stylefeng.guns.common.persistence.model;

import javax.persistence.Table;
import java.util.Date;

/**
 * @Author: wcy
 * @Date: 2019/12/14
 */
public class HouseDTO extends Base {

    private String  name;
    private String  email;
    private String  phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private String house_desc;
    private String house_model;
    private String house_area;
    private String house_floor;
    private String house_type;
    private Integer house_price ;
    private String house_address;
    private String house_image;
    private String community_name;
    private String house_oriented;
    private String create_date;
    private Integer u_id;
    private String house_code;
    private Integer status;

    private String buy_date;

    private String contract;

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public HouseDTO() {
    }

    public HouseDTO(House house) {
        this.id = house.getId();
        this.house_desc = house.getHouse_desc();
        this.house_model = house.getHouse_model();
        this.house_area = house.getHouse_area();
        this.house_floor = house.getHouse_floor();
        this.house_type = house.getHouse_type();
        this.house_price = house.getHouse_price() ;
        this.house_address = house.getHouse_address();
        this.house_image = house.getHouse_image();
        this.community_name = house.getCommunity_name();
        this.house_oriented = house.getHouse_oriented();
        this.create_date = house.getCreate_date();
        this.u_id = house.getU_id();
        this.house_code = house.getHouse_code();
        this.status = house.getStatus();
        this.buy_date = house.getBuy_date();
        this.contract = house.getContract();
    }


    public String getBuy_date() {
        return buy_date;
    }

    public void setBuy_date(String buy_date) {
        this.buy_date = buy_date;
    }

    public String getHouse_code() {
        return house_code;
    }

    public void setHouse_code(String house_code) {
        this.house_code = house_code;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getHouse_desc() {
        return house_desc;
    }

    public void setHouse_desc(String house_desc) {
        this.house_desc = house_desc;
    }

    public String getHouse_model() {
        return house_model;
    }

    public void setHouse_model(String house_model) {
        this.house_model = house_model;
    }

    public String getHouse_area() {
        return house_area;
    }

    public void setHouse_area(String house_area) {
        this.house_area = house_area;
    }

    public String getHouse_floor() {
        return house_floor;
    }

    public void setHouse_floor(String house_floor) {
        this.house_floor = house_floor;
    }

    public String getHouse_type() {
        return house_type;
    }

    public void setHouse_type(String house_type) {
        this.house_type = house_type;
    }

    public Integer getHouse_price() {
        return house_price;
    }

    public void setHouse_price(Integer house_price) {
        this.house_price = house_price;
    }

    public String getHouse_address() {
        return house_address;
    }

    public void setHouse_address(String house_address) {
        this.house_address = house_address;
    }

    public String getHouse_image() {
        return house_image;
    }

    public void setHouse_image(String house_image) {
        this.house_image = house_image;
    }

    public String getCommunity_name() {
        return community_name;
    }

    public void setCommunity_name(String community_name) {
        this.community_name = community_name;
    }

    public String getHouse_oriented() {
        return house_oriented;
    }

    public void setHouse_oriented(String house_oriented) {
        this.house_oriented = house_oriented;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public Integer getU_id() {
        return u_id;
    }

    public void setU_id(Integer u_id) {
        this.u_id = u_id;
    }
}
