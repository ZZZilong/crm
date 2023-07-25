package com.whrj.service;

import com.whrj.base.BaseService;
import com.whrj.dao.PermissionMapper;
import com.whrj.vo.Permission;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PermissionService extends BaseService<Permission,Integer> {

    @Resource
    private PermissionMapper permissionMapper;

    /**
     * 根据用户Id查询当前用户的权限
     * @param userId
     * @return
     */
    public  List<String> queryUserHasRolesHasPermissions(Integer userId) {
        return permissionMapper.queryUserHasRolesHasPermissions(userId);
    }
}
