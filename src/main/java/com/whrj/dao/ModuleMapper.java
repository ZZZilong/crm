package com.whrj.dao;


import com.whrj.base.BaseMapper;
import com.whrj.model.TreeModule;
import com.whrj.vo.Module;

import java.util.List;

public interface ModuleMapper extends BaseMapper<Module, Integer> {

    /**
     * 查询所有的权限
     * @return
     */
    public List<TreeModule> selectAllByModules();


    public Module selectModuleById(Integer id);
}