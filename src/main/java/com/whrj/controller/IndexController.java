package com.whrj.controller;


import com.whrj.base.BaseController;
import com.whrj.service.PermissionService;
import com.whrj.service.UserService;
import com.whrj.utils.LoginUserUtil;
import com.whrj.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class IndexController extends BaseController {

    @Autowired
    private UserService userService;

    @Resource
    private PermissionService permissionServicel;


    /**
     * 系统登录页面
     */
    @RequestMapping("index")
    public String index() {
        return "index";
    }

    // 系统界⾯欢迎⻚
    @RequestMapping("welcome")
    public String welcome() {
        return "welcome";
    }

    /**
     * 后端管理主页面
     *
     * @return
     */
    @RequestMapping("main")
    public String main(HttpServletRequest request) {
        Integer id = LoginUserUtil.releaseUserIdFromCookie(request);
        User user = userService.selectById(id);
        request.getSession().setAttribute("user", user);
        Integer userId = id;
        List<String> permissions=permissionServicel.queryUserHasRolesHasPermissions(userId);
        request.getSession().setAttribute("permissions",permissions);
        return "main";
    }

}
