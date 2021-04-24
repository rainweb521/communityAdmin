package com.stylefeng.guns.modular.system.controller;

import com.stylefeng.guns.common.annotion.log.BussinessLog;
import com.stylefeng.guns.common.constant.Dict;
import com.stylefeng.guns.common.constant.state.ManagerStatus;
import com.stylefeng.guns.common.controller.BaseController;
import com.stylefeng.guns.common.persistence.dao.*;
import com.stylefeng.guns.common.persistence.model.*;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.modular.system.factory.UserFactory;
import com.stylefeng.guns.modular.system.transfer.UserDto;
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
 * @Date: 2020/2/1
 */
@Controller
@RequestMapping("/customer")
public class CustomerController extends BaseController {
    private String PREFIX = "/system/customer/";

    @Resource
    private CustomerMapper customerMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private ComplaintMapper complaintMapper;
    @Resource
    private RepairMapper repairMapper;

    /**
     * 业主信息
     * @return
     */
    @RequestMapping("info")
    public String building() {
        return PREFIX + "customer.html";
    }

    /**
     * 个人信息
     * @return
     */
    @RequestMapping("self")
    public String self(Model model) {
        Customer customer = new Customer();
        customer.setUser_id(ShiroKit.getUser().getId());
        customer = customerMapper.selectOne(customer);
        model.addAttribute("house",customer);
        LogObjectHolder.me().set(customer);
        return PREFIX + "myself.html";
    }
    /**
     * 个人信息修改
     */
    @RequestMapping(value = "/self/update")
    @ResponseBody
    public Object visitingupdate(Customer patrol ) {
        customerMapper.updateByPrimaryKey(patrol);
        return SUCCESS_TIP;
    }

    /**
     * 获取列表
     * 业主信息表和用户表关联
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Example example = new Example(Customer.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtil.isNotEmpty(condition)) {
            criteria.andLike("house_desc", "%" + condition + "%");
        }
        List<Customer> customerlist = customerMapper.selectByExample(example);
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        customerlist.forEach(item->{
            User user = userMapper.selectByPrimaryKey(item.getUser_id());
            CustomerDTO customerdto = new CustomerDTO();
            customerdto.setAnimal(item.getAnimal());
            customerdto.setCar(item.getCar());
            customerdto.setFamily(item.getFamily());
            customerdto.setLive_address(item.getLive_address());
            customerdto.setLive_date(item.getLive_date());
            customerdto.setUser_id(item.getUser_id());
            customerdto.setRemark(item.getRemark());
            customerdto.setWork(item.getWork());
            customerdto.setWork_address(item.getWork_address());
            customerdto.setC_id(item.getId());

            customerdto.setEmail(user.getEmail());
            customerdto.setPhone(user.getPhone());
            customerdto.setName(user.getName());
            customerdto.setSex(user.getSex());
            customerdto.setSexstr(user.getSex()==1?"男":"女");
            customerDTOList.add(customerdto);
        });
        return customerDTOList;
    }

    /**
     * 跳转到添加
     */
    @RequestMapping("/add")
    public String houseAdd(Model model) {
        model.addAttribute("house",new CustomerDTO());
        LogObjectHolder.me().set(new CustomerDTO());
        return PREFIX + "customer_edit.html";
    }
    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer houseId) {
        Customer customer = customerMapper.selectByPrimaryKey(houseId);
        //将两张表的信息都删除
        customerMapper.deleteByPrimaryKey(houseId);
        userMapper.deleteByPrimaryKey(customer.getUser_id());
        return SUCCESS_TIP;
    }
    /**
     * 跳转到修改
     */
    @RequestMapping("/update/{houseId}")
    public String houseUpdate(@PathVariable Integer houseId, Model model) {
        Customer item = customerMapper.selectByPrimaryKey(houseId);
        User user = userMapper.selectByPrimaryKey(item.getUser_id());
        CustomerDTO customerdto = new CustomerDTO();
        customerdto.setAnimal(item.getAnimal());
        customerdto.setCar(item.getCar());
        customerdto.setFamily(item.getFamily());
        customerdto.setLive_address(item.getLive_address());
        customerdto.setLive_date(item.getLive_date());
        customerdto.setUser_id(item.getUser_id());
        customerdto.setRemark(item.getRemark());
        customerdto.setWork(item.getWork());
        customerdto.setWork_address(item.getWork_address());
        customerdto.setC_id(item.getId());

        customerdto.setEmail(user.getEmail());
        customerdto.setPhone(user.getPhone());
        customerdto.setName(user.getName());
        customerdto.setSex(user.getSex());
        model.addAttribute("house",customerdto);
        LogObjectHolder.me().set(customerdto);
        return PREFIX + "customer_edit.html";
    }
    /**
     * 修改或新增
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(CustomerDTO customerdto ) {
        Customer customer = new Customer();
        customer.setAnimal(customerdto.getAnimal());
        customer.setCar(customerdto.getCar());
        customer.setFamily(customerdto.getFamily());
        customer.setLive_address(customerdto.getLive_address());
        customer.setLive_date(customerdto.getLive_date());
        customer.setUser_id(customerdto.getUser_id());
        customer.setRemark(customerdto.getRemark());
        customer.setWork(customerdto.getWork());
        customer.setWork_address(customerdto.getWork_address());
        customer.setPhone(customerdto.getPhone());
        if (customerdto.getC_id()!=null){
            customer.setId(customerdto.getC_id());
            customerMapper.updateByPrimaryKey(customer);
            User user = userMapper.selectByPrimaryKey(customerdto.getUser_id());
            user.setEmail(customerdto.getEmail());
            user.setPhone(customerdto.getPhone());
            user.setName(customerdto.getName());
            user.setSex(customerdto.getSex());
            userMapper.updateByPrimaryKey(user);
        }else {

            UserDto user = new UserDto();
            user.setEmail(customerdto.getEmail());
            user.setPhone(customerdto.getPhone());
            user.setName(customerdto.getName());
            user.setSex(customerdto.getSex());
            // 完善账号信息
            user.setSalt(ShiroKit.getRandomSalt(5));
            user.setPassword(ShiroKit.md5("123456", user.getSalt()));
            user.setStatus(ManagerStatus.OK.getCode());
            user.setCreatetime(new Date());
            //后加
            user.setBirthday(new Date());
            user.setAccount(user.getEmail());
            //暂时写死为8-业主
            user.setRoleid("8");
            //暂时写为客户部-29
            user.setDeptid(29);
            //还有头像，部门，保留字段，三个值不设置
            userMapper.insert(UserFactory.createUser(user));
            User users = new User();
            //这里查找已经插入用户的id
            users.setRoleid("8");
            users.setEmail(customerdto.getEmail());
            users.setPhone(customerdto.getPhone());
            users.setName(customerdto.getName());
            users.setSex(customerdto.getSex());
            // 完善账号信息
            users.setPassword(user.getPassword());
            User select = userMapper.selectOne(users);
            customer.setUser_id(select.getId());
            customerMapper.insert(customer);
        }
        return SUCCESS_TIP;
    }

    /**
     * 投诉管理信息
     * @return
     */
    @RequestMapping("/complaint")
    public String complaint() {
        return PREFIX + "complaint.html";
    }
    /**
     * 我的投诉管理信息
     * @return
     */
    @RequestMapping("/mycomplaint")
    public String mycomplaint() {
        return PREFIX + "mycomplaint.html";
    }

    /**
     * 获取列表
     */
    @RequestMapping(value = "/complaint/list")
    @ResponseBody
    public Object complaintlist(String condition) {
        return complaintMapper.selectAll();
    }
    /**
     * 获取我的列表
     */
    @RequestMapping(value = "/mycomplaint/list")
    @ResponseBody
    public Object mycomplaintlist(String condition) {
        Complaint complaint = new Complaint();
        complaint.setUser_id(ShiroKit.getUser().getId());
        return complaintMapper.select(complaint);
    }
    /**
     * 跳转到我的添加
     */
    @RequestMapping("/mycomplaint/add")
    public String mycomplaintadd(Model model) {
        Complaint patrol = new Complaint();
        model.addAttribute("house",patrol);
        LogObjectHolder.me().set(patrol);
        return PREFIX + "mycomplaint_edit.html";
    }
    /**
     * 我的删除
     */
    @RequestMapping(value = "/mycomplaint/delete")
    @ResponseBody
    public Object mycomplaintdelete(@RequestParam Integer houseId) {
        complaintMapper.deleteByPrimaryKey(houseId);
        return SUCCESS_TIP;
    }
    /**
     * 跳转到我的修改
     */
    @RequestMapping("/mycomplaint/update/{houseId}")
    public String mycomplaintUpdate(@PathVariable Integer houseId, Model model) {
        Complaint patrol  = complaintMapper.selectByPrimaryKey(houseId);
        model.addAttribute("house",patrol);
        LogObjectHolder.me().set(patrol);
        return PREFIX + "mycomplaint_edit.html";
    }
    /**
     * 我的修改或新增
     */
    @RequestMapping(value = "/mycomplaint/update")
    @ResponseBody
    public Object mycomplaintupdate(Complaint patrol ) {
        patrol.setStatus(1);
        patrol.setStatus_str("待处理");
        patrol.setUser_id(ShiroKit.getUser().getId());
        if (patrol.getId()!=null){
            complaintMapper.updateByPrimaryKey(patrol);
        }else {
            complaintMapper.insert(patrol);
        }
        return SUCCESS_TIP;
    }
    /**
     * 处理
     */
    @RequestMapping(value = "/complaint/delete")
    @ResponseBody
    public Object complaintdelete(@RequestParam Integer houseId) {
        Complaint complaint = complaintMapper.selectByPrimaryKey(houseId);
        complaint.setStatus_str("已处理");
        complaint.setStatus(2);
        complaintMapper.updateByPrimaryKey(complaint);
        return SUCCESS_TIP;
    }





    /**
     * 报修管理信息
     * @return
     */
    @RequestMapping("/repair")
    public String repair() {
        return PREFIX + "repair.html";
    }
    /**
     * 我的报修管理信息
     * @return
     */
    @RequestMapping("/myrepair")
    public String myrepair() {
        return PREFIX + "myrepair.html";
    }

    /**
     * 获取我的列表
     */
    @RequestMapping(value = "/myrepair/list")
    @ResponseBody
    public Object myrepairlist(String condition) {
        Repair complaint = new Repair();
        complaint.setUser_id(ShiroKit.getUser().getId());
        return repairMapper.select(complaint);
    }
    /**
     * 跳转到我的添加
     */
    @RequestMapping("/myrepair/add")
    public String myrepairadd(Model model) {
        Repair patrol = new Repair();
        model.addAttribute("house",patrol);
        LogObjectHolder.me().set(patrol);
        return PREFIX + "myrepair_edit.html";
    }
    /**
     * 我的删除
     */
    @RequestMapping(value = "/myrepair/delete")
    @ResponseBody
    public Object myrepairdelete(@RequestParam Integer houseId) {
        repairMapper.deleteByPrimaryKey(houseId);
        return SUCCESS_TIP;
    }
    /**
     * 跳转到我的修改
     */
    @RequestMapping("/myrepair/update/{houseId}")
    public String myrepairUpdate(@PathVariable Integer houseId, Model model) {
        Repair patrol  = repairMapper.selectByPrimaryKey(houseId);
        model.addAttribute("house",patrol);
        LogObjectHolder.me().set(patrol);
        return PREFIX + "myrepair_edit.html";
    }
    /**
     * 我的修改或新增
     */
    @RequestMapping(value = "/myrepair/update")
    @ResponseBody
    public Object myrepairupdate(Repair patrol ) {
        patrol.setStatus(1);
        patrol.setStatus_str("待处理");
        patrol.setUser_id(ShiroKit.getUser().getId());
        if (patrol.getId()!=null){
            repairMapper.updateByPrimaryKey(patrol);
        }else {
            repairMapper.insert(patrol);
        }
        return SUCCESS_TIP;
    }
    /**
     * 获取列表
     */
    @RequestMapping(value = "/repair/list")
    @ResponseBody
    public Object repairlist(String condition) {
        return repairMapper.selectAll();
    }
    /**
     * 处理
     */
    @RequestMapping(value = "/repair/delete")
    @ResponseBody
    public Object repairdelete(@RequestParam Integer houseId) {
        Repair repair = repairMapper.selectByPrimaryKey(houseId);
        repair.setStatus_str("已处理");
        repair.setStatus(2);
        repairMapper.updateByPrimaryKey(repair);
        return SUCCESS_TIP;
    }
}
