package com.stylefeng.guns.common.persistence.model;


/**
 * @Author: wcy
 * @Date: 2019/12/28
 */
public class ContractDTO extends Contract{

    private String house_desc;
    private String status_str;

    public ContractDTO(Contract line) {
        super.setId(line.getId());
        super.setCode(line.getCode());
        super.setCompany(line.getCompany());
        super.setContent(line.getContent());
        super.setCreate_date(line.getCreate_date());
        super.setCustomer(line.getCustomer());
        super.setPrice(line.getPrice());

    }


    public String getHouse_desc() {
        return house_desc;
    }

    public void setHouse_desc(String house_desc) {
        this.house_desc = house_desc;
    }

    public String getStatus_str() {
        return status_str;
    }

    public void setStatus_str(String status_str) {
        this.status_str = status_str;
    }
}
