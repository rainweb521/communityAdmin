package com.stylefeng.guns.modular.system.controller;

import com.stylefeng.guns.common.annotion.log.BussinessLog;
import com.stylefeng.guns.common.constant.Dict;
import com.stylefeng.guns.common.controller.BaseController;
import com.stylefeng.guns.common.persistence.dao.BuildingMapper;
import com.stylefeng.guns.common.persistence.dao.CostMapper;
import com.stylefeng.guns.common.persistence.model.Building;
import com.stylefeng.guns.common.persistence.model.Cost;
import com.stylefeng.guns.common.persistence.model.House;
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
import java.util.List;

/**
 * @Author: wcy
 * @Date: 2020/2/1
 */
@Controller
@RequestMapping("/cost")
public class CostController extends BaseController {
    private String PREFIX = "/system/cost/";

    @Resource
    private CostMapper costMapper;

    /**
     * 费用信息
     * @return
     */
    @RequestMapping("")
    public String building() {
        return PREFIX + "cost.html";
    }

    /**
     * 获取列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Example example = new Example(Cost.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtil.isNotEmpty(condition)) {
            criteria.andLike("house_desc", "%" + condition + "%");
        }
        List<Cost> list = costMapper.selectByExample(example);
        return list;
    }

    /**
     * 跳转到添加
     */
    @RequestMapping("/add")
    public String add(Model model) {
        model.addAttribute("house",new Cost());
        LogObjectHolder.me().set(new Cost());
        return PREFIX + "cost_edit.html";
    }
    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    @BussinessLog(value = "删除",key = "houseId",dict = Dict.DeleteDict)
    public Object delete(@RequestParam Integer houseId) {
        costMapper.deleteByPrimaryKey(houseId);
        return SUCCESS_TIP;
    }
    /**
     * 跳转到修改
     */
    @RequestMapping("/update/{houseId}")
    public String houseUpdate(@PathVariable Integer houseId, Model model) {
        Cost cost  = costMapper.selectByPrimaryKey(houseId);
        model.addAttribute("house",cost);
        LogObjectHolder.me().set(cost);
        return PREFIX + "cost_edit.html";
    }
    /**
     * 修改或新增
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Cost cost ) {
        if (cost.getId()!=null){
            costMapper.updateByPrimaryKey(cost);
        }else {
            costMapper.insert(cost);
        }
        return SUCCESS_TIP;
    }
}
