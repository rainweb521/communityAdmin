package com.stylefeng.guns.modular.system.controller;

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
import javax.annotation.Resource;

/**
 * @Author: wcy
 * @Date: 2020/2/2
 */
@Controller
@RequestMapping("/guard")
public class GuardController extends BaseController {
    private String PREFIX = "/system/guard/";

    @Resource
    private ScheduingMapper scheduingMapper;
    @Resource
    private PatrolMapper patrolMapper;
    @Resource
    private SecurityMapper securityMapper;
    @Resource
    private VisitingMapper visitingMapper;
    @Resource
    private GoodsMapper goodsMapper;

    /**
     * 排班信息
     * @return
     */
    @RequestMapping("/scheduling")
    public String building() {
        return PREFIX + "scheduling.html";
    }

    /**
     * 获取排班列表
     */
    @RequestMapping(value = "/scheduling/list")
    @ResponseBody
    public Object list(String condition) {
        return scheduingMapper.selectAll();
    }

    /**
     * 跳转到添加
     */
    @RequestMapping("/scheduling/add")
    public String add(Model model) {
        Scheduing scheduing = new Scheduing();
        model.addAttribute("house",scheduing);
        LogObjectHolder.me().set(scheduing);
        return PREFIX + "scheduling_edit.html";
    }
    /**
     * 删除
     */
    @RequestMapping(value = "/scheduling/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer houseId) {
        scheduingMapper.deleteByPrimaryKey(houseId);
        return SUCCESS_TIP;
    }
    /**
     * 跳转到修改
     */
    @RequestMapping("/scheduling/update/{houseId}")
    public String houseUpdate(@PathVariable Integer houseId, Model model) {
        Scheduing scheduing  = scheduingMapper.selectByPrimaryKey(houseId);
        model.addAttribute("house",scheduing);
        LogObjectHolder.me().set(scheduing);
        return PREFIX + "scheduling_edit.html";
    }
    /**
     * 修改或新增
     */
    @RequestMapping(value = "/scheduling/update")
    @ResponseBody
    public Object update(Scheduing scheduing ) {
        if (scheduing.getId()!=null){
            scheduingMapper.updateByPrimaryKey(scheduing);
        }else {
            scheduingMapper.insert(scheduing);
        }
        return SUCCESS_TIP;
    }


    /**
     * 巡查管理信息
     * @return
     */
    @RequestMapping("/patrol")
    public String patrol() {
        return PREFIX + "patrol.html";
    }

    /**
     * 获取列表
     */
    @RequestMapping(value = "/patrol/list")
    @ResponseBody
    public Object patrollist(String condition) {
        return patrolMapper.selectAll();
    }

    /**
     * 跳转到添加
     */
    @RequestMapping("/patrol/add")
    public String patroladd(Model model) {
        Patrol patrol = new Patrol();
        model.addAttribute("house",patrol);
        LogObjectHolder.me().set(patrol);
        return PREFIX + "patrol_edit.html";
    }
    /**
     * 删除
     */
    @RequestMapping(value = "/patrol/delete")
    @ResponseBody
    public Object patroldelete(@RequestParam Integer houseId) {
        patrolMapper.deleteByPrimaryKey(houseId);
        return SUCCESS_TIP;
    }
    /**
     * 跳转到修改
     */
    @RequestMapping("/patrol/update/{houseId}")
    public String patrolUpdate(@PathVariable Integer houseId, Model model) {
        Patrol patrol  = patrolMapper.selectByPrimaryKey(houseId);
        model.addAttribute("house",patrol);
        LogObjectHolder.me().set(patrol);
        return PREFIX + "patrol_edit.html";
    }
    /**
     * 修改或新增
     */
    @RequestMapping(value = "/patrol/update")
    @ResponseBody
    public Object patrolupdate(Patrol patrol ) {
        if (patrol.getId()!=null){
            patrolMapper.updateByPrimaryKey(patrol);
        }else {
            patrolMapper.insert(patrol);
        }
        return SUCCESS_TIP;
    }



    /**
     * 治安管理信息
     * @return
     */
    @RequestMapping("/security")
    public String security() {
        return PREFIX + "security.html";
    }

    /**
     * 获取列表
     */
    @RequestMapping(value = "/security/list")
    @ResponseBody
    public Object securitylist(String condition) {
        return securityMapper.selectAll();
    }

    /**
     * 跳转到添加
     */
    @RequestMapping("/security/add")
    public String securityadd(Model model) {
        Security patrol = new Security();
        model.addAttribute("house",patrol);
        LogObjectHolder.me().set(patrol);
        return PREFIX + "security_edit.html";
    }
    /**
     * 删除
     */
    @RequestMapping(value = "/security/delete")
    @ResponseBody
    public Object securitydelete(@RequestParam Integer houseId) {
        securityMapper.deleteByPrimaryKey(houseId);
        return SUCCESS_TIP;
    }
    /**
     * 跳转到修改
     */
    @RequestMapping("/security/update/{houseId}")
    public String securityUpdate(@PathVariable Integer houseId, Model model) {
        Security patrol  = securityMapper.selectByPrimaryKey(houseId);
        model.addAttribute("house",patrol);
        LogObjectHolder.me().set(patrol);
        return PREFIX + "security_edit.html";
    }
    /**
     * 修改或新增
     */
    @RequestMapping(value = "/security/update")
    @ResponseBody
    public Object securityupdate(Security patrol ) {
        if (patrol.getId()!=null){
            securityMapper.updateByPrimaryKey(patrol);
        }else {
            securityMapper.insert(patrol);
        }
        return SUCCESS_TIP;
    }



    /**
     * 来访管理信息
     * @return
     */
    @RequestMapping("/visiting")
    public String visiting() {
        return PREFIX + "visiting.html";
    }

    /**
     * 获取列表
     */
    @RequestMapping(value = "/visiting/list")
    @ResponseBody
    public Object visitinglist(String condition) {
        return visitingMapper.selectAll();
    }

    /**
     * 跳转到添加
     */
    @RequestMapping("/visiting/add")
    public String visitingadd(Model model) {
        Visiting patrol = new Visiting();
        model.addAttribute("house",patrol);
        LogObjectHolder.me().set(patrol);
        return PREFIX + "visiting_edit.html";
    }
    /**
     * 删除
     */
    @RequestMapping(value = "/visiting/delete")
    @ResponseBody
    public Object visitingdelete(@RequestParam Integer houseId) {
        visitingMapper.deleteByPrimaryKey(houseId);
        return SUCCESS_TIP;
    }
    /**
     * 跳转到修改
     */
    @RequestMapping("/visiting/update/{houseId}")
    public String visitingUpdate(@PathVariable Integer houseId, Model model) {
        Visiting patrol  = visitingMapper.selectByPrimaryKey(houseId);
        model.addAttribute("house",patrol);
        LogObjectHolder.me().set(patrol);
        return PREFIX + "visiting_edit.html";
    }
    /**
     * 修改或新增
     */
    @RequestMapping(value = "/visiting/update")
    @ResponseBody
    public Object visitingupdate(Visiting patrol ) {
        if (patrol.getId()!=null){
            visitingMapper.updateByPrimaryKey(patrol);
        }else {
            visitingMapper.insert(patrol);
        }
        return SUCCESS_TIP;
    }



    /**
     * 物品管理信息
     * @return
     */
    @RequestMapping("/goods")
    public String goods() {
        return PREFIX + "goods.html";
    }

    /**
     * 获取列表
     */
    @RequestMapping(value = "/goods/list")
    @ResponseBody
    public Object goodslist(String condition) {
        return goodsMapper.selectAll();
    }

    /**
     * 跳转到添加
     */
    @RequestMapping("/goods/add")
    public String goodsadd(Model model) {
        Goods patrol = new Goods();
        model.addAttribute("house",patrol);
        LogObjectHolder.me().set(patrol);
        return PREFIX + "goods_edit.html";
    }
    /**
     * 删除
     */
    @RequestMapping(value = "/goods/delete")
    @ResponseBody
    public Object goodsdelete(@RequestParam Integer houseId) {
        goodsMapper.deleteByPrimaryKey(houseId);
        return SUCCESS_TIP;
    }
    /**
     * 跳转到修改
     */
    @RequestMapping("/goods/update/{houseId}")
    public String goodsUpdate(@PathVariable Integer houseId, Model model) {
        Goods patrol  = goodsMapper.selectByPrimaryKey(houseId);
        model.addAttribute("house",patrol);
        LogObjectHolder.me().set(patrol);
        return PREFIX + "goods_edit.html";
    }
    /**
     * 修改或新增
     */
    @RequestMapping(value = "/goods/update")
    @ResponseBody
    public Object visitingupdate(Goods patrol ) {
        if (patrol.getId()!=null){
            goodsMapper.updateByPrimaryKey(patrol);
        }else {
            goodsMapper.insert(patrol);
        }
        return SUCCESS_TIP;
    }
}
