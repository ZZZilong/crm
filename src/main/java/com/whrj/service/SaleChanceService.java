package com.whrj.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.whrj.base.BaseService;
import com.whrj.dao.SaleChanceMapper;
import com.whrj.enums.DevResult;
import com.whrj.enums.StateStatus;
import com.whrj.query.SaleChanceQuery;
import com.whrj.utils.AssertUtil;
import com.whrj.utils.PhoneUtil;
import com.whrj.vo.SaleChance;
import com.whrj.vo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;

import java.util.Map;

@Service
public class SaleChanceService extends BaseService<SaleChance,Integer> {

    @Resource
    private SaleChanceMapper saleChanceMapper;

    /**
     * 营销机会多条件查询
     *
     * @param query
     * @return
     */
    public Map<String, Object> querySaleChanceByParams(SaleChanceQuery query) {
        Map<String, Object> map = new HashMap<>();
        PageHelper.startPage(query.getPage(), query.getLimit());
        PageInfo<SaleChance> pageInfo = new PageInfo<>(saleChanceMapper.selectByParams(query));
        map.put("code", 0);
        map.put("msg", "suceess");
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }

    /**
     * 营销机会添加
     *
     * @param saleChance
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addSaleChance(SaleChance saleChance) {
        //判断联系人 用户名 联系手机是否非空
        checkSaleChanceParams(saleChance.getCustomerName(), saleChance.getLinkMan(), saleChance.getLinkPhone());

        //设置默认参数
        saleChance.setState(StateStatus.UNSTATE.getType());
        saleChance.setDevResult(DevResult.UNDEV.getStatus());
        if (StringUtils.isNotBlank(saleChance.getAssignMan())) {
            saleChance.setState(StateStatus.STATED.getType());
            saleChance.setDevResult(DevResult.DEVING.getStatus());
            saleChance.setAssignTime(new Date());
        }
        saleChance.setIsValid(1);
        saleChance.setCreateDate(new Date());
        saleChance.setUpdateDate(new Date());
        AssertUtil.isTrue(saleChanceMapper.insertSelective(saleChance) < 1, "添加失败");
    }

    /**
     * 营销机会修改操作
     *
     * @param saleChance
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateSaleChance(SaleChance saleChance) {
        //校验数据
        SaleChance temp = saleChanceMapper.selectByPrimaryKey(saleChance.getId());
        AssertUtil.isTrue(temp == null, "对象不存在");
        AssertUtil.isTrue(StringUtils.isBlank(saleChance.getCustomerName()), "客户名不能为空");
        //判断客户名 手机号 联系人
        checkSaleChanceParams(saleChance.getCustomerName(), saleChance.getLinkMan(), saleChance.getLinkPhone());
        //设置相关参数
        saleChance.setUpdateDate(new Date());
        //判断 assignMan是否存在 根据assignMan的设置前后的值，修改开发状态和分配状态
        if (temp.getAssignMan() == null) {
            if (saleChance.getAssignMan() != null) {
                saleChance.setAssignTime(new Date());
                saleChance.setState(StateStatus.STATED.getType());
                saleChance.setDevResult(DevResult.DEVING.getStatus());
            }
        } else {
            if (saleChance.getAssignMan() == null) {
                saleChance.setAssignTime(null);
                saleChance.setState(StateStatus.UNSTATE.getType());
                saleChance.setDevResult(DevResult.UNDEV.getStatus());
            } else {
                if (!temp.getAssignMan().equals(saleChance.getAssignMan())) {
                    saleChance.setAssignTime(new Date());
                }
            }

        }
        AssertUtil.isTrue(saleChanceMapper.updateByPrimaryKeySelective(saleChance) < 1, "修改失败");
    }

    /**
     * 判断是否非空
     *
     * @param customerName
     * @param linkMan
     * @param linkPhone
     */
    private void checkSaleChanceParams(String customerName, String linkMan, String linkPhone) {
        AssertUtil.isTrue(StringUtils.isBlank(customerName), "请输入客户名");
        AssertUtil.isTrue(StringUtils.isBlank(linkMan), "请输入联系人！");
        AssertUtil.isTrue(StringUtils.isBlank(linkPhone), "请输入手机号");
        AssertUtil.isTrue(!PhoneUtil.isMobile(linkPhone), "手机格式不正确");
    }

    /**
     * 根据id查询所有信息
     *
     * @param id
     * @return
     */
    public SaleChance selectByPrimaryKey(Integer id) {
        return saleChanceMapper.selectByPrimaryKey(id);
    }

    /**
     * 删除销售用户
     * @param ids
     */
    public void deleteBith(Integer[] ids) {
        saleChanceMapper.deleteBatch(ids);
    }
}