package com.whrj.controller;

import com.whrj.base.BaseController;
import com.whrj.base.ResultInfo;
import com.whrj.query.CusDevPlanQuery;
import com.whrj.service.CusDevPlanService;
import com.whrj.service.SaleChanceService;
import com.whrj.vo.CusDevPlan;
import com.whrj.vo.SaleChance;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("customer")
public class CusDevPlanController extends BaseController {

    @Resource
    private SaleChanceService saleChanceService;

    @Resource
    private CusDevPlanService cusDevPlanService;
    /**
     * 打开客户信息页面
     *
     * @return
     */
    @RequestMapping("index")
    public String index() {
        return "cusDevPlan/cus_dev_plan";
    }

    /**
     * 进入详情或者开发页面
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("toCusDevPlanPage")
    public String toCusDevPlanPage(Integer id, HttpServletRequest request) {
        SaleChance saleChance = saleChanceService.selectByPrimaryKey(id);
        request.setAttribute("saleChance",saleChance);
        return "cusDevPlan/cus_dev_plan_data";
    }

    /**
     * 查询客户计划项列表
     * @param query
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String, Object> list(CusDevPlanQuery query) {
        return cusDevPlanService.queryCusDevByParams(query);
    }

    /**
     * 计划管理-添加或修改页面
     * @return
     */
    @RequestMapping("toAddOrUpdateCusDevPlanPage")
    public String toAddOrUpdateCusDevPlanPage(Integer sid,HttpServletRequest request,Integer id){
        request.setAttribute("sid",sid);
        if (id!=null){
            CusDevPlan cusDevPlan = cusDevPlanService.selectByPrimaryKey(id);
            request.setAttribute("cusDevPlan",cusDevPlan);
        }
        return "cusDevPlan/add_update";
    }

    /**
     * 添加管理计划
     * @param cusDevPlan
     * @return
     */
   @RequestMapping("add")
   @ResponseBody
    public ResultInfo addCusDevPlan(CusDevPlan cusDevPlan) {
        cusDevPlanService.addCusDevPlan(cusDevPlan);
        return success("计划添加成功");
    }

    /**
     * 更新计划
     * @param cusDevPlan
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateCusDevPlan(CusDevPlan cusDevPlan){
        cusDevPlanService.updateCusDevPlan(cusDevPlan);
        return success("更新计划!");
    }

    /**
     * 删除用户计划
     * @param id
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo  deleteCusDevPlan(Integer id){
        cusDevPlanService.deleteCusDevPlan(id);
        return success("删除成功！");
    }


    /**
     * 修改用户状态
     * @param id
     * @param devResult
     * @return
     */
    @RequestMapping("updateSaleChanceDevResult")
    @ResponseBody
    public ResultInfo updateCusDevPlanStatus(Integer id,Integer devResult){
        cusDevPlanService.updateCusDevPlanStatus(id,devResult);
        return success("状态修改成功！");
    }
}
