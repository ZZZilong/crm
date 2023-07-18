package com.whrj.controller;

import com.whrj.base.BaseController;
import com.whrj.query.SaleChanceQuery;
import com.whrj.service.SaleChanceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
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
    public Map<String, Object> list(SaleChanceQuery query) {
        return saleChanceService.querySaleChanceByParams(query);
    }

    /**
     * 进入营销机会页面
     * @return
     */
    @RequestMapping("index")
    public String index() {
        return "saleChance/sale_chance";
    }
}
