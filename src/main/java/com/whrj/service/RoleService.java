package com.whrj.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.whrj.base.BaseService;
import com.whrj.base.ResultInfo;
import com.whrj.dao.ModuleMapper;
import com.whrj.dao.PermissionMapper;
import com.whrj.dao.RoleMapper;
import com.whrj.query.RoleQuery;
import com.whrj.query.UserQuery;
import com.whrj.utils.AssertUtil;
import com.whrj.vo.Module;
import com.whrj.vo.Permission;
import com.whrj.vo.Role;
import com.whrj.vo.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.security.Permissions;
import java.util.*;


@Service
public class RoleService extends BaseService<Role, Integer> {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private PermissionMapper permissionMapper;


    @Resource
    private ModuleMapper moduleMapper;

    /**
     * 查询角色列表
     * @return
     */
    public List<Map<String, Object>> queryAllRoles(Integer userId){
        return roleMapper.selectRoleList(userId);
    }


    /**
     * 多条件查询
     *
     * @param query
     * @return
     */
    public Map<String, Object> queryRoleByParams(RoleQuery query) {
        Map<String, Object> map = new HashMap<>();
        PageHelper.startPage(query.getPage(), query.getLimit());
        PageInfo<Role> pageInfo = new PageInfo<>(roleMapper.selectByParams(query));
        map.put("code", 0);
        map.put("msg", "suceess");
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }


    /**
     * 根据id查询角色
     *
     * @param roleId
     * @return
     */
    public Role queryByPrimaryKey(Integer roleId) {
        return roleMapper.selectByPrimaryKey(roleId);
    }


    /**
     * 添加角色
     *
     * @param role
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addRole(Role role) {
        checkaddParams(role.getRoleName(), role.getRoleRemark());
        AssertUtil.isTrue(roleMapper.selectByRoleName(role.getRoleName()) != null, "该角色名已存在，请重新输入");
        role.setCreateDate(new Date());
        role.setUpdateDate(new Date());
        role.setIsValid(1);
        AssertUtil.isTrue(roleMapper.insertSelective(role) < 1, "添加失败");
    }


    /**
     * 修改角色
     *
     * @param role
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateRole(Role role) {
        checkaddParams(role.getRoleName(), role.getRoleRemark());
        Role temp = roleMapper.selectByRoleName(role.getRoleName());
        if (temp != null) {
            AssertUtil.isTrue(role.getId() != temp.getId() && temp.getRoleName().equals(role.getRoleName()), "该角色名已存在，请重新输入");
        }
        role.setUpdateDate(new Date());
        AssertUtil.isTrue(updateByPrimaryKeySelective(role) < 1, "修改失败");
    }


    /**
     * 删除角色
     *
     * @param roleId
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteRoleByRoleId(Integer roleId) {
        AssertUtil.isTrue(roleMapper.deleteByPrimaryKey(roleId) < 1, "删除角色失败");
    }


    /**
     * 添加角色的权限
     */
     public void addGrant(Integer roleId, Integer[] mids) {
        Role role = roleMapper.selectByPrimaryKey(roleId);
        AssertUtil.isTrue(roleId == null || role == null, "请选中角色进行添加");
        int count = permissionMapper.countPermissionByRoleId(roleId);
        if (count > 0) {
            //删除当前角色的所有权限
            permissionMapper.deletePermissionByRoleId(roleId);
        }
        if (mids.length > 0 && mids != null) {
            List<Permission> permissionList = new ArrayList<Permission>();
            for (Integer mid : mids) {
                Permission permission = new Permission();
                permission.setRoleId(roleId);
                permission.setModuleId(mid);
                permission.setCreateDate(new Date());
                permission.setUpdateDate(new Date());
                Module module = moduleMapper.selectModuleById(mid);
                permission.setAclValue(module.getOptValue());
                permissionList.add(permission);
            }
            AssertUtil.isTrue(permissionMapper.insertBatch(permissionList) != mids.length, "添加用户权限失败");
        }

    }


    /**
     * 判断添加字符串是否为空
     *
     * @param roleName
     * @param roleRemark
     */
    public void checkaddParams(String roleName, String roleRemark) {
        AssertUtil.isTrue(roleRemark == null, "角色备注不能为空");
        AssertUtil.isTrue(roleName == null, "角色名不能为空");
    }

}
