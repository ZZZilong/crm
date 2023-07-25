package com.whrj.controller;

import com.whrj.base.BaseController;
import com.whrj.base.ResultInfo;
import com.whrj.query.RoleQuery;
import com.whrj.service.RoleService;
import com.whrj.vo.Role;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("role")
public class RoleController extends BaseController {

    @Resource
    private RoleService roleService;


    /**
     * 进入角色管理页面
     * @return
     */
    @RequestMapping("index")
    public String index() {
        return "role/role";
    }



    /**
     * 查询所有角色
     *
     * @param query
     * @return
     */

    @RequestMapping("list")
    @ResponseBody
    public Map<String, Object> queryRoleList(RoleQuery query) {
        return roleService.queryRoleByParams(query);
    }



    /**
     * 进入添加页面/修改页面
     */

    @RequestMapping("toAddOrUpdateRolePage")
    public String toAddOrUpdateRolePage(Integer roleId, HttpServletRequest request){
        if (roleId!=null){
            request.setAttribute("role",roleService.queryByPrimaryKey(roleId));
        }
        return "role/add_update";
    }


    /**
     * 添加角色
     * @return
     */

    @RequestMapping("add")
    @ResponseBody
    public ResultInfo addRole(Role role){
        roleService.addRole(role);
        return success("添加角色成功");
    }


    /**
     * 修改角色
     * @param role
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateRole(Role role){
        roleService.updateRole(role);
        return  success("角色修改成功");
    }


    /**
     * 删除角色
     * @param roleId
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteRole(Integer roleId){
        roleService.deleteRoleByRoleId(roleId);
        return success("角色删除成功");
    }


    /**
     * 角色权限添加
     * @param roleId
     * @param mids
     * @return
     */

    @RequestMapping("addGrant")
    @ResponseBody
    public ResultInfo addGrant(Integer roleId , Integer[] mIds){
        roleService.addGrant(roleId,mIds);
        return success("角色权限添加成功");
    }


    /**
     * 查询角色列表
     * @return
     */
    @RequestMapping("queryAllRoles")
    @ResponseBody
    public List<Map<String,Object>> queryAllRoles(Integer userId){
        return roleService.queryAllRoles(userId);
    }

}
