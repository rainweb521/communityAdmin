package com.stylefeng.guns.modular.system.controller;

import cn.hutool.core.date.DateUtil;
import com.stylefeng.guns.common.controller.BaseController;
import com.stylefeng.guns.common.persistence.dao.ContractMapper;
import com.stylefeng.guns.common.persistence.dao.HouseMapper;
import com.stylefeng.guns.common.persistence.dao.RentMapper;
import com.stylefeng.guns.common.persistence.model.Contract;
import com.stylefeng.guns.common.persistence.model.ContractDTO;
import com.stylefeng.guns.common.persistence.model.House;
import com.stylefeng.guns.common.persistence.model.Rent;
import com.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: wcy
 * @Date: 2019/12/28
 */
@Controller
@RequestMapping("/contract")
public class ContractController extends BaseController {
    private String PREFIX = "/system/contract/";

    @Resource
    private HouseMapper houseMapper;
    @Resource
    private ContractMapper contractMapper;
    @Resource
    private RentMapper rentMapper;

    /**
     * 跳转到通知列表首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "contract.html";
    }


    /**
     * 跳转到添加
     */
    @RequestMapping("/contract_add")
    public String houseAdd() {
        return PREFIX + "contract_add.html";
    }

    /**
     * 跳转到修改
     */
    @RequestMapping("/contract_update/{contractId}")
    public String houseUpdate(@PathVariable Integer contractId, Model model) {
        Contract contract = contractMapper.selectByPrimaryKey(contractId);
        model.addAttribute("contract",contract);
        LogObjectHolder.me().set(contract);
        return PREFIX + "contract_edit.html";
    }
    /**
     * 获取列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Example example = new Example(Contract.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtil.isNotEmpty(condition)) {
            criteria.andLike("code", "%" + condition + "%");
        }
        List<Contract> list = contractMapper.selectByExample(example);
        List<Contract> showlist = new ArrayList<>();
        list.forEach(
                line->{
                    ContractDTO contractDTO = new ContractDTO(line);
                    if (line.getStatus()==0){
                        contractDTO.setStatus_str("履行");
                    }else {
                        contractDTO.setStatus_str("过期");
                    }
                    House house = houseMapper.selectByPrimaryKey(line.getH_id());
                    contractDTO.setHouse_desc(house.getHouse_desc());
                    showlist.add(contractDTO);
                }
        );
        return showlist;
    }
    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer contractId) {
        Contract oldContract = contractMapper.selectByPrimaryKey(contractId);
//        House house = houseMapper.selectByPrimaryKey(oldContract.getH_id());
        //修改房屋的状态
//        house.setStatus(0);
//        houseMapper.updateByPrimaryKey(house);
        contractMapper.deleteByPrimaryKey(contractId);
        return SUCCESS_TIP;
    }

    /**
     * 新增
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Contract contract) {
        contract.setCode(System.currentTimeMillis()+"");
        contract.setStatus(0);
        contractMapper.insert(contract);
//        Rent rent = new Rent();
//        rent.setC_code(contract.getCode());
//        rent.setCreate_date(DateUtil.format(new Date(),"yyyy-MM-dd"));
//        rent.setDinjin(0.0);
//        rent.setNum(0);
//        rent.setPrice(contract.getPrice());
//        rent.setInvoice(System.currentTimeMillis()+"");
//        rent.setStatus(contract.getStatus());
//        rent.setYajin(0.0);
//        rentMapper.insert(rent);
        //修改房屋的状态
//        House house = houseMapper.selectByPrimaryKey(contract.getH_id());
//        house.setStatus(1);
//        houseMapper.updateByPrimaryKey(house);
        return SUCCESS_TIP;
    }
    /**
     * 修改通知
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Contract contract) {
        Contract oldContract = contractMapper.selectByPrimaryKey(contract.getId());
        oldContract.setCompany(contract.getCompany());
        oldContract.setCustomer(contract.getCustomer());
        oldContract.setCreate_date(contract.getCreate_date());
        oldContract.setContent(contract.getContent());
        oldContract.setStatus(contract.getStatus());
//        Rent rent = new Rent();
//        rent.setC_code(oldContract.getCode());
//        Rent rentnew = rentMapper.selectOne(rent);
//        rentnew.setStatus(contract.getStatus());
//        rentMapper.updateByPrimaryKey(rentnew);
        contractMapper.updateByPrimaryKey(oldContract);
//        House house = houseMapper.selectByPrimaryKey(oldContract.getH_id());
//        //修改房屋的状态
//        if (contract.getStatus()==1){
//            //过期合同
//            house.setStatus(0);
//        }else {
//            house.setStatus(1);
//        }
//        houseMapper.updateByPrimaryKey(house);
        return SUCCESS_TIP;
    }

}
