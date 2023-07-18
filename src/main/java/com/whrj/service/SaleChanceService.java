package com.whrj.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.whrj.dao.SaleChanceMapper;
import com.whrj.query.SaleChanceQuery;
import com.whrj.vo.SaleChance;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class SaleChanceService  {

    @Resource
    private SaleChanceMapper saleChanceMapper;

    public Map<String, Object> querySaleChanceByParams(SaleChanceQuery query) {
        Map<String, Object> map = new HashMap<>();
        PageHelper.startPage(query.getPage(), query.getLimit());
        PageInfo<SaleChance> pageInfo = new PageInfo<>(saleChanceMapper.selectByParams(query));
        map.put("code", 0);
        map.put("msg", "suceess");
        map.put("count",pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }
}