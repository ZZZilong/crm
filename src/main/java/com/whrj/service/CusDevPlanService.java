package com.whrj.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.whrj.base.BaseService;
import com.whrj.dao.CusDevPlanMapper;
import com.whrj.dao.SaleChanceMapper;
import com.whrj.query.CusDevPlanQuery;
import com.whrj.utils.AssertUtil;
import com.whrj.vo.CusDevPlan;
import com.whrj.vo.SaleChance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
public class CusDevPlanService extends BaseService<CusDevPlan, Integer> {
    @Resource
    private CusDevPlanMapper cusDevPlanMapper;

    @Resource
    public SaleChanceMapper saleChanceMapper;

    /**
     * 多条件查询客户信息
     * @param query
     * @return
     */
    public Map<String, Object> queryCusDevByParams(CusDevPlanQuery query) {
        Map<String, Object> map = new HashMap<>();
        PageHelper.startPage(query.getPage(), query.getLimit());
        PageInfo<CusDevPlan> pageInfo = new PageInfo<>(cusDevPlanMapper.selectByParams(query));
        map.put("code", 0);
        map.put("msg", "suceess");
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }

    /**
     * 根据id查用户计划
     * @param id
     * @return
     */
    public CusDevPlan queryCusDevPlanById(Integer id) {
        return cusDevPlanMapper.selectByPrimaryKey(id);
    }




    /**
     * 添加用户计划
     * @param cusDevPlan
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addCusDevPlan(CusDevPlan cusDevPlan) {
        checkParams(cusDevPlan.getPlanItem(),cusDevPlan.getExeAffect(),cusDevPlan.getPlanDate(),cusDevPlan.getSaleChanceId());
        cusDevPlanMapper.insertSelective(cusDevPlan);
        cusDevPlan.setIsValid(1);
        cusDevPlan.setCreateDate(new Date());
        cusDevPlan.setUpdateDate(new Date());
        AssertUtil.isTrue(cusDevPlanMapper.insertSelective(cusDevPlan) < 1, "添加客户定制计划失败");
    }



    /**
     * 修改用户计划
     * @param cusDevPlan
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateCusDevPlan(CusDevPlan cusDevPlan) {
        checkParams(cusDevPlan.getPlanItem(),cusDevPlan.getExeAffect(),cusDevPlan.getPlanDate(),cusDevPlan.getSaleChanceId());
        AssertUtil.isTrue(null == cusDevPlan.getId() || null == selectByPrimaryKey(cusDevPlan.getId()),"更新记录不存在!");
        CusDevPlan temp = cusDevPlanMapper.selectByPrimaryKey(cusDevPlan.getId());
        cusDevPlan.setUpdateDate(new Date());
        AssertUtil.isTrue(updateByPrimaryKeySelective(cusDevPlan)<1,"计划更新失败！");
    }

    /**
     * 根据id删除用户计划
     * @param id
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteCusDevPlan(Integer id) {
        cusDevPlanMapper.deleteByPrimaryKey(id);
    }


    /**
     * 用户状态修改
     * @param id
     * @param devResult
     */
    public  void updateCusDevPlanStatus(Integer id,Integer devResult){
        AssertUtil.isTrue(id==null,"开发对象不存在");
        AssertUtil.isTrue(devResult==null,"数据异常,请重试");
        SaleChance temp = saleChanceMapper.selectByPrimaryKey(id);
        AssertUtil.isTrue(temp.getId()==id,"不可重复修改");
        AssertUtil.isTrue(saleChanceMapper.updateDevResult(id,devResult)<1,"客户计划状态修改失败");
    }


    /**
     * 判断字符是否为空
     * @param planItem
     * @param exeAffect
     * @param planDate
     */
    public void checkParams(String planItem, String exeAffect, Date planDate,Integer  saleChanceId) {
        AssertUtil.isTrue(saleChanceId==null, "计划项不能数为空");
        AssertUtil.isTrue(StringUtils.isBlank(planItem), "计划项不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(exeAffect), "执行效率不能为空");
        AssertUtil.isTrue(planDate == null, "计划时间不能数为空");
    }



}
