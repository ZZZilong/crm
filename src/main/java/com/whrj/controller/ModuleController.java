package com.whrj.controller;

import com.whrj.base.BaseController;
import com.whrj.base.ResultInfo;
import com.whrj.model.TreeModule;
import com.whrj.service.ModuleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/module")
public class ModuleController extends BaseController {

    @Resource
    private ModuleService moduleService;

    /**
     * 查询所有权限列表
     *
     * @return
     */
    @RequestMapping("/queryAllModules")
    @ResponseBody
    public List<TreeModule> queryAllModules(Integer roleId) {
        List<TreeModule> treeModuleList = moduleService.queryAllModules(roleId);
        return treeModuleList;
    }


    /**
     * 打开授权界面
     *
     * @return
     */
    @RequestMapping("toAddGrantPage")
    public String grant(Integer roleId, HttpServletRequest request) {
        request.setAttribute("roleId", roleId);
        return "role/grant";
    }

    /**
     * 打开权限页面
     *
     * @return
     */
    @RequestMapping("index")
    public String index() {
        return "module/module";
    }
}
