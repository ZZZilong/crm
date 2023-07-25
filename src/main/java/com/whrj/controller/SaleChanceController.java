package com.whrj.controller;

import com.whrj.annotation.RequirePermission;
import com.whrj.base.BaseController;
import com.whrj.base.ResultInfo;
import com.whrj.query.SaleChanceQuery;
import com.whrj.service.SaleChanceService;
import com.whrj.utils.CookieUtil;
import com.whrj.utils.LoginUserUtil;
import com.whrj.vo.SaleChance;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("sale_chance")
public class SaleChanceController extends BaseController {
    @Resource
    private SaleChanceService saleChanceService;

    /**
     * 多条件查询
     *
     * @param query
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    @RequirePermission(code = "101001")
    public Map<String, Object> list(SaleChanceQuery query,Integer flag,HttpServletRequest request) {
        if (flag != null && flag == 1) {
            Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
            query.setAssignMan(Integer.toString(userId));
        }
        return saleChanceService.querySaleChanceByParams(query);
    }

    /**
     * 进入营销机会页面
     *
     * @return
     */
    @RequestMapping("index")
    public String index() {
        return "saleChance/sale_chance";
    }



    /**
     * 添加营销机会
     * @param saleChance
     * @param request
     * @return
     */
    @PostMapping ("add")
    @ResponseBody
    @RequirePermission(code = "101002")
    public ResultInfo addSaleChance(SaleChance saleChance, HttpServletRequest request) {
        String userName = CookieUtil.getCookieValue(request, "userName");
        saleChance.setCreateMan(userName);
        saleChanceService.addSaleChance(saleChance);
        return success("营销机会添加成功添加成功");
    }

    /**
     * 进入添加页面
     * @return saleChance/add_update 页面
     */
    @RequestMapping("toSaleChancePage")
    public String toSaleChancePage(Integer saleChanceId,HttpServletRequest request){
        //判断Id id存在查询所有用户 在修改页面展示
        if (saleChanceId != null) {
            SaleChance saleChance = saleChanceService.selectByPrimaryKey(saleChanceId);
            request.setAttribute("saleChance", saleChance);
        }
        return "saleChance/add_update";
    }

    /**
     * 修改营销机会
     * @param saleChance
     * @param request
     * @return success("修改成功");
     */
    @RequestMapping("update")
    @ResponseBody
    public ResultInfo  updateSaleChance(SaleChance saleChance, HttpServletRequest request){
        saleChanceService.updateSaleChance(saleChance);
        return success("修改成功");
    }

    /**
     * 删除销售
     * @param ids
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteSaleChance(Integer[] ids){
        saleChanceService.deleteBith(ids);
        return success("删除成功");
    }
}
