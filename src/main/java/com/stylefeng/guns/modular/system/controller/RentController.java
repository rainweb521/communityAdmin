package com.stylefeng.guns.modular.system.controller;

import cn.hutool.core.date.DateUtil;
import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.controller.BaseController;
import com.stylefeng.guns.common.persistence.dao.*;
import com.stylefeng.guns.common.persistence.model.*;
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
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: wcy
 * @Date: 2019/12/28
 */
@Controller
@RequestMapping("/rent")
public class RentController extends BaseController {
    private String PREFIX = "/system/rent/";

    @Resource
    private HouseMapper houseMapper;
    @Resource
    private RentMapper rentMapper;
    @Resource
    private ContractMapper contractMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private CustomerMapper customerMapper;
    @Resource
    private CostMapper costMapper;

    /**
     * 收费管理
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "rent.html";
    }
    /**
     * 欠收费管理
     */
    @RequestMapping("/owerent")
    public String owerent() {
        return PREFIX + "owerent.html";
    }

    /**
     * 物业费用收取情况汇总
     */
    @RequestMapping("/all")
    public String all() {
        return PREFIX + "all.html";
    }
    /**
     * 物业费用收取情况汇总
     */
    @RequestMapping(value = "/alllist")
    @ResponseBody
    public Object alllist() {
        RentAllDTO rentall = new RentAllDTO();
        //总合同数=总户数
        int contractCount = contractMapper.selectCount(new Contract());
        int rentCount = rentMapper.selectCount(new Rent());
        rentall.setBehouse(rentCount);
        rentall.setOwehouse(contractCount-rentCount);
        rentall.setAllmoney(rentMapper.selectAll().stream().mapToDouble(Rent::getPrice).sum());
        List<RentAllDTO> rentAllDTOS = new ArrayList<>();
        rentAllDTOS.add(rentall);
        return rentAllDTOS;
    }
    /**
     * 到期合同统计
     */
    @RequestMapping("/pie")
    public String pie( Model model) {
        Contract contract = new Contract();
        contract.setStatus(0);
        int allnum = contractMapper.selectCount(new Contract());
        int use = contractMapper.selectCount(contract);
        Map<String ,Integer> map = new HashMap<>();
        map.put("use",use);
        map.put("due",allnum - use);
        model.addAttribute("map",map);
        return PREFIX + "pie.html";
    }
    /**
     * 到期房屋统计
     */
    @RequestMapping("/pie2")
    public String pie2(Model model) {
        House house = new House();
        house.setStatus(1);
        int allnum = houseMapper.selectCount(new House());
        int use = houseMapper.selectCount(house);
        Map<String ,Integer> map = new HashMap<>();
        map.put("use",use);
        map.put("due",allnum - use);
        model.addAttribute("map",map);
        return PREFIX + "pie2.html";
    }
    /**
     * 本月租金统计
     */
    @RequestMapping("/bar")
    public String bar(Model model) {
        Date dNow = new Date();   //当前时间
        Date dBefore = new Date();
        Date dBefore2 = new Date();
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dNow);//把当前时间赋给日历
        calendar.add(Calendar.MONTH, -1);  //设置为前1月
        dBefore = calendar.getTime();   //得到前1月的时间
        Calendar calendar2 = Calendar.getInstance(); //得到日历
        calendar2.setTime(dNow);//把当前时间赋给日历
        calendar2.add(Calendar.MONTH, -2);  //设置为前1月
        dBefore2 = calendar2.getTime();   //得到前1月的时间
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM"); //设置时间格式
        String month1 = sdf.format(dBefore);    //格式化前2月的时间
        String month2 = sdf.format(dBefore2);    //格式化前1月的时间
        String defaultEndDate = sdf.format(dNow); //格式化当前时间

        Map<String,Object> monthmap = new HashMap<>();
        monthmap.put("month1",month1);
        monthmap.put("month2",month2);
        monthmap.put("month3",defaultEndDate);
//        Map<String,Double> datamap = new HashMap<>();
        Contract contract = new Contract();

        Example example = new Example(Contract.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("create_date", "%" + defaultEndDate + "%");
        monthmap.put("data3",rentMapper.selectByExample(example).stream().mapToDouble(Rent::getPrice).sum());

        example = new Example(Contract.class);
        criteria = example.createCriteria();
        criteria.andLike("create_date", "%" + month1 + "%");
        monthmap.put("data1",rentMapper.selectByExample(example).stream().mapToDouble(Rent::getPrice).sum());

        example = new Example(Contract.class);
        criteria = example.createCriteria();
        criteria.andLike("create_date", "%" + month2 + "%");
        monthmap.put("data2",rentMapper.selectByExample(example).stream().mapToDouble(Rent::getPrice).sum());

        model.addAttribute("map",monthmap);
//        model.addAttribute("monthmap",monthmap);

        return PREFIX + "bar.html";
    }

    /**
     * 跳转到修改
     */
    @RequestMapping("/rent_update/{rentId}")
    public String houseUpdate(@PathVariable Integer rentId, Model model) {
        Rent rent = rentMapper.selectByPrimaryKey(rentId);
        model.addAttribute("rent",rent);
        LogObjectHolder.me().set(rent);
        return PREFIX + "rent_edit.html";
    }
    /**
     * 获取列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Example example = new Example(Rent.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtil.isNotEmpty(condition)) {
            criteria.andLike("c_code", "%" + condition + "%");
            criteria.andLike("invoice", "%" + condition + "%");
        }
        List<Rent> list = rentMapper.selectByExample(example);
        return list;
    }
    /**
     * 获取未缴费列表
     * 先去合同表里查找，用合同编号进行比对，找不到数据的，查找user表，再查找业主customer表，
     * 在查找对应house表，获取房屋面积，计算cost表所有费用
     */
    @RequestMapping(value = "/owelist")
    @ResponseBody
    public Object owelist(String condition) {
        Example example = new Example(Contract.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtil.isNotEmpty(condition)) {
            criteria.andLike("code", "%" + condition + "%");
        }
        List<Contract> contractList = contractMapper.selectByExample(example);
//        List<Contract> contractList = contractMapper.selectAll();
        List<String> rentList = rentMapper.selectAll().stream().map(Rent::getC_code).collect(Collectors.toList());
        List<Contract> reduce = contractList.stream().filter(item -> !rentList.contains(item.getCode())).collect(Collectors.toList());
        List<Rent> rents = new ArrayList<>();
        reduce.forEach(item->{
            Rent rent = new Rent();
            User user = userMapper.selectByPrimaryKey(item.getUser_id());
            Customer customer = new Customer();
            customer.setUser_id(user.getId());
            customer = customerMapper.selectOne(customer);
//            House house = new House();
//            house.setU_id(user.getId());
            House houseall = houseMapper.selectByPrimaryKey(item.getH_id());
            double price = costMapper.selectAll().stream().mapToDouble(item2 -> (item2.getCost() * Double.valueOf(houseall.getHouse_area()))).sum();
            rent.setPrice(price);
            rent.setC_code(item.getCode());
            rent.setStatus_str("未缴费");
            rent.setName(user.getName());
            rent.setPhone(user.getPhone());
            rent.setAddress(customer.getLive_address());
            rents.add(rent);

        });

        return rents;
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Rent rent) {
        Rent old_rent = rentMapper.selectByPrimaryKey(rent.getId());
        rent.setC_code(old_rent.getC_code());
        rent.setInvoice(old_rent.getInvoice());
        rentMapper.updateByPrimaryKey(rent);
        return SUCCESS_TIP;
    }
    /**
     * 缴费操作
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(String c_code,Double price,String name,String phone,String address) {
        Rent rent = new Rent();
        rent.setC_code(c_code);
        rent.setCreate_date(DateUtil.format(new Date(),"yyyy-MM-dd"));
        rent.setDinjin(0.0);
        rent.setNum(0);
        rent.setPrice(price);
        rent.setInvoice(System.currentTimeMillis()+"");
        rent.setStatus(0);
        rent.setStatus_str("已交费");
        rent.setYajin(0.0);
        rent.setAddress(address);
        rent.setPhone(phone);
        rent.setName(name);

        rentMapper.insert(rent);

        return SUCCESS_TIP;
    }
}
