package com.whrj.service;

import com.whrj.base.BaseService;
import com.whrj.dao.ModuleMapper;
import com.whrj.dao.PermissionMapper;
import com.whrj.model.TreeModule;
import com.whrj.vo.Module;
import com.whrj.vo.Permission;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ModuleService extends BaseService<Module, Integer> {

    @Resource
    private ModuleMapper moduleMapper;

    @Resource
    private PermissionMapper permissionMapper;

    /**
     * 查询所有权限列表
     *
     * @return
     */
    public List<TreeModule> queryAllModules(Integer roleId) {
        List<TreeModule> treeModuleList = moduleMapper.selectAllByModules();
        List<Integer> permissionListId = permissionMapper.queryModuleIdByRoleId(roleId);
        if (roleId != null) {
            if (permissionListId.size() > 0 && permissionListId != null) {
                treeModuleList.forEach(treeModule -> {
                    if (permissionListId.contains(treeModule.getId())) {
                        treeModule.setChecked(true);
                    }
                });
            }
        }
        return treeModuleList;
    }
}
