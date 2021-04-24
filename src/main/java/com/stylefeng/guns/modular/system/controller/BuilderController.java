package com.stylefeng.guns.modular.system.controller;

import com.stylefeng.guns.common.annotion.log.BussinessLog;
import com.stylefeng.guns.common.constant.Dict;
import com.stylefeng.guns.common.controller.BaseController;
import com.stylefeng.guns.common.persistence.dao.BuildingMapper;
import com.stylefeng.guns.common.persistence.model.Building;
import com.stylefeng.guns.common.persistence.model.House;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.shiro.ShiroKit;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: wcy
 * @Date: 2020/2/1
 */
@Controller
@RequestMapping("/building")
public class BuilderController extends BaseController {
    private String PREFIX = "/system/building/";

    @Resource
    private BuildingMapper buildingMapper;

    /**
     * 楼宇信息
     * @return
     */
    @RequestMapping("")
    public String building() {
        return PREFIX + "building.html";
    }

    /**
     * 获取列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Example example = new Example(Building.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtil.isNotEmpty(condition)) {
            criteria.andLike("house_desc", "%" + condition + "%");
        }
        List<Building> list = buildingMapper.selectByExample(example);
        return list;
    }

    /**
     * 跳转到添加
     */
    @RequestMapping("/add")
    public String houseAdd(Model model) {
        model.addAttribute("house",new Building());
        LogObjectHolder.me().set(new Building());
        return PREFIX + "building_edit.html";
    }
    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    @BussinessLog(value = "删除",key = "houseId",dict = Dict.DeleteDict)
    public Object delete(@RequestParam Integer houseId) {
        buildingMapper.deleteByPrimaryKey(houseId);
        return SUCCESS_TIP;
    }
    /**
     * 跳转到修改
     */
    @RequestMapping("/update/{houseId}")
    public String houseUpdate(@PathVariable Integer houseId, Model model) {
        Building building = buildingMapper.selectByPrimaryKey(houseId);
        model.addAttribute("house",building);
        LogObjectHolder.me().set(building);
        return PREFIX + "building_edit.html";
    }
    /**
     * 修改或新增
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    @BussinessLog(value = "修改通知",key = "title",dict = Dict.NoticeMap)
    public Object update(Building building) {
        if (building.getId()!=null){
            buildingMapper.updateByPrimaryKey(building);
        }else {
            buildingMapper.insert(building);

        }
        return SUCCESS_TIP;
    }
}
