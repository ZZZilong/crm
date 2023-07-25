package com.whrj.service;

import com.whrj.base.BaseService;
import com.whrj.dao.UserRoleMapper;
import com.whrj.vo.UserRole;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserRoleService extends BaseService<UserRole,Integer> {

    @Resource
    private UserRoleMapper  userRoleMapper;
}
