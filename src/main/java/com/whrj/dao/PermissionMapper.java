package com.whrj.dao;

import com.whrj.base.BaseMapper;
import com.whrj.vo.Permission;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission,Integer> {

    /**
     * 统计当前角色的权限
     * @param roleId
     * @return
     */
    public Integer countPermissionByRoleId(Integer roleId);


    /**
     * 删除当前角色的权限
     * @param roleId
     * @return
     */
    public Integer deletePermissionByRoleId(Integer roleId);

    /**
     * 根据角色id查询当前角色的权限
     * @param roleId
     * @return
     */
    List<Integer> queryModuleIdByRoleId(Integer roleId);

    /**
     * 根据用户id查询当前用户的权限
     * @param userId
     * @return
     */
    List<String> queryUserHasRolesHasPermissions(Integer userId);
}