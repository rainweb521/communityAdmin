package com.stylefeng.guns.modular.system.controller;

import com.stylefeng.guns.common.annotion.log.BussinessLog;
import com.stylefeng.guns.common.constant.Dict;
import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.controller.BaseController;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.common.exception.BussinessException;
import com.stylefeng.guns.common.persistence.dao.CommunityMapper;
import com.stylefeng.guns.common.persistence.dao.HouseMapper;
import com.stylefeng.guns.common.persistence.dao.UserMapper;
import com.stylefeng.guns.common.persistence.model.*;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.system.warpper.NoticeWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wcy
 * @Date: 2019/12/14
 */
@Controller
@RequestMapping("/house")
public class HouseController extends BaseController {
    private String PREFIX = "/system/house/";

    @Resource
    private HouseMapper houseMapper;

    @Resource
    private CommunityMapper communityMapper;

    @Resource
    private UserMapper userMapper;

    /**
     * 跳转到通知列表首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "house.html";
    }
    @RequestMapping("/price")
    public String price() {
        return PREFIX + "houseprice.html";
    }
    @RequestMapping("/houseempty")
    public String houseempty() {
        return PREFIX + "houseempty.html";
    }
    @RequestMapping("/housenotempty")
    public String housenotempty() {
        return PREFIX + "housenotempty.html";
    }

    /**
     * 小区信息
     * @return
     */
    @RequestMapping("/community")
    public String community( Model model) {
        Community community = communityMapper.selectByPrimaryKey(Integer.valueOf(1));
        model.addAttribute("house",community);
        LogObjectHolder.me().set(community);
        return PREFIX + "community.html";
    }
    /**
     * 修改小区信息
     * @return
     */
    @RequestMapping("/updatecommunity")
    @ResponseBody
    public Object updatecommunity( Community community) {
        communityMapper.updateByPrimaryKey(community);
        return SUCCESS_TIP;
    }



    /**
     * 跳转到添加
     */
    @RequestMapping("/house_add")
    public String houseAdd() {
        return PREFIX + "house_add.html";
    }

    /**
     * 跳转到修改
     */
    @RequestMapping("/house_update/{houseId}")
    public String houseUpdate(@PathVariable Integer houseId, Model model) {
        House house = houseMapper.selectByPrimaryKey(houseId);
        model.addAttribute("house",house);
        LogObjectHolder.me().set(house);
        return PREFIX + "house_edit.html";
    }
    /**
     * 获取列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Example example = new Example(House.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtil.isNotEmpty(condition)) {
            criteria.andLike("house_desc", "%" + condition + "%");
        }
        List<House> list = houseMapper.selectByExample(example);
        return list;
    }
    /**
     * 获取空置房屋列表，已租房屋列表
     */
    @RequestMapping(value = "/liststatus")
    @ResponseBody
    public Object liststatus(String status) {
        House house = new House();
        List<House> list;
        //查询空置
        if("10".equals(status)){
            house.setStatus(1);
            list = houseMapper.select(house);
            house.setStatus(2);
            list.addAll(houseMapper.select(house));
        }else if("20".equals(status)){
            //查询非空置
            house.setStatus(3);
            list = houseMapper.select(house);
            house.setStatus(4);
            list.addAll(houseMapper.select(house));
        }else {
            house.setStatus(Integer.valueOf(status));
            list = houseMapper.select(house);
        }
        return list;
    }
    /**
     * 获取房价列表
     */
    @RequestMapping(value = "/listprice")
    @ResponseBody
    public Object list(Integer max,Integer min) {
        Example example = new Example(House.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andGreaterThan("house_price", min);//大于
        criteria.andLessThan("house_price", max);//小于
        List<House> list = houseMapper.selectByExample(example);
        return list;
    }
    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    @BussinessLog(value = "删除",key = "houseId",dict = Dict.DeleteDict)
    public Object delete(@RequestParam Integer houseId) {
        houseMapper.deleteByPrimaryKey(houseId);
        return SUCCESS_TIP;
    }

    /**
     * 新增
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    @BussinessLog(value = "新增",key = "title",dict = Dict.NoticeMap)
    public Object add(House house) {
        if (1==house.getStatus()){
            house.setHouse_type("待租");
        }else{
            house.setHouse_type("待售");
        }
        house.setU_id(ShiroKit.getUser().getId());
        houseMapper.insert(house);
        return SUCCESS_TIP;
    }
    /**
     * 查看详情
     */
    @RequestMapping("/view/{Id}")
    @ResponseBody
    public House view(@PathVariable Integer Id) {
        return houseMapper.selectByPrimaryKey(Id);
    }
    /**
     * 修改通知
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    @BussinessLog(value = "修改通知",key = "title",dict = Dict.NoticeMap)
    public Object update(House house) {
        if (1==house.getStatus()){
            house.setHouse_type("待租");
        }else{
            house.setHouse_type("待售");
        }
        house.setU_id(ShiroKit.getUser().getId());
        houseMapper.updateByPrimaryKey(house);
        return SUCCESS_TIP;
    }

    @RequestMapping("/buylist")
    public String buylist() {
        return PREFIX + "buy_list.html";
    }
    @RequestMapping(value = "/buyadd",method = RequestMethod.GET)
    public String buyadd() {
        return PREFIX + "buy_add.html";
    }
    @RequestMapping("/rentlist")
    public String rentlist() {
        return PREFIX + "rent_list.html";
    }
    @RequestMapping(value = "/rentadd",method = RequestMethod.GET)
    public String rentadd() {
        return PREFIX + "rent_add.html";
    }
    /**
     * 获取购房列表
     */
    @RequestMapping(value = "/buylist2/{status}")
    @ResponseBody
    public Object buylist2(@PathVariable Integer status) {
        Example example = new Example(House.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("status", "%" + status + "%");
        List<House> list = houseMapper.selectByExample(example);
        List<HouseDTO> houseDTOS = new ArrayList<>();
        list.forEach(item->{
            HouseDTO houseDTO = new HouseDTO(item);
            User user = userMapper.selectByPrimaryKey(item.getU_id());
            houseDTO.setName(user.getName());
            houseDTO.setEmail(user.getEmail());
            houseDTO.setPhone(user.getPhone());
            houseDTOS.add(houseDTO);
        });

        return houseDTOS;
    }
    /**
     * 新增购买
     */
    @RequestMapping(value = "/buyadd",method = RequestMethod.POST)
    @ResponseBody
    public Object buyadd(HouseDTO house) {
        House houses = houseMapper.selectByPrimaryKey(house.getId());
        houses.setU_id(house.getU_id());
        houses.setStatus(4);
        houses.setHouse_type("已售");
        //添加合同字段
        houses.setContract(String.valueOf(System.currentTimeMillis()));
        houseMapper.updateByPrimaryKey(houses);
        return SUCCESS_TIP;
    }
    /**
     * 新增租赁
     */
    @RequestMapping(value = "/rentadd",method = RequestMethod.POST)
    @ResponseBody
    public Object rentadd(HouseDTO house) {
        House houses = houseMapper.selectByPrimaryKey(house.getId());
        houses.setU_id(house.getU_id());
        houses.setStatus(3);
        houses.setHouse_type("已租");
        //添加合同字段
        houses.setContract(String.valueOf(System.currentTimeMillis()));
        houseMapper.updateByPrimaryKey(houses);
        return SUCCESS_TIP;
    }
    /**
     * 删除购买记录
     */
    @RequestMapping(value = "/buydelete")
    @ResponseBody
    public Object buydelete(@RequestParam Integer houseId) {
        House houses = houseMapper.selectByPrimaryKey(houseId);
        houses.setU_id(0);
        houses.setStatus(2);
        houses.setHouse_type("待售");
        houses.setContract("");
        houseMapper.updateByPrimaryKey(houses);
        return SUCCESS_TIP;
    }
    /**
     * 删除租房记录
     */
    @RequestMapping(value = "/rentdelete")
    @ResponseBody
    public Object rentdelete(@RequestParam Integer houseId) {
        House houses = houseMapper.selectByPrimaryKey(houseId);
        houses.setU_id(0);
        houses.setStatus(1);
        houses.setHouse_type("待租");
        houses.setContract("");
        houseMapper.updateByPrimaryKey(houses);
        return SUCCESS_TIP;
    }
}
