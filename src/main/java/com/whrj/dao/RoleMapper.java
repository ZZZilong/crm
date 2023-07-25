package com.whrj.dao;

import com.whrj.base.BaseMapper;
import com.whrj.vo.Role;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

public interface RoleMapper extends BaseMapper<Role, Integer> {

    /**
     * 查询所有角色
     *
     * @param id
     * @param id
     * @return
     */

    @MapKey("id")
    public List<Map<String, Object>> selectRoleList(Integer id);

    public Role selectByRoleName(String roleName);
}