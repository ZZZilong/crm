package com.whrj.controller;

import com.whrj.base.BaseController;
import com.whrj.service.UserRoleService;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;


@Controller
public class UserRoleController extends BaseController {

    @Resource
    private UserRoleService userRoleService;
}
