package com.whrj.dao;


import com.whrj.base.BaseMapper;
import com.whrj.vo.UserRole;

public interface UserRoleMapper extends BaseMapper<UserRole, Integer> {

    Integer countUserRoleByUserId(Integer id);

    Integer deleteUserRoleByUserId(Integer id);

    int  deleteUserRoleByUserIds(Integer[] ids);
}
