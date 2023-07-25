package com.whrj.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.whrj.base.BaseService;
import com.whrj.dao.UserMapper;
import com.whrj.dao.UserRoleMapper;
import com.whrj.model.UserModel;
import com.whrj.query.SaleChanceQuery;
import com.whrj.query.UserQuery;
import com.whrj.utils.AssertUtil;
import com.whrj.utils.Md5Util;
import com.whrj.utils.UserIDBase64;
import com.whrj.vo.SaleChance;
import com.whrj.vo.User;
import com.whrj.vo.UserRole;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;


@Service
public class UserService extends BaseService<User, Integer> {
    @Resource
    private UserMapper userMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    /**
     * 用户登录
     *
     * @param userName
     */
    public UserModel userLogin(String userName, String userPwd) {
        checkLoginParams(userName, userPwd);
        User user = userMapper.queryByUserName(userName);
        AssertUtil.isTrue(null == user, "用户不存在或已注销");
        checkLoginPwd(userPwd, user.getUserPwd());
        return buildUserInfo(user);
    }

    /**
     * 修改密码
     *
     * @param id
     * @param oldPassword
     * @param newPassword
     * @param newPassword1
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updatePassword(Integer id, String oldPassword, String newPassword, String newPassword1) {
        User user = userMapper.selectByPrimaryKey(id);
        AssertUtil.isTrue(null == user, "待更新记录不存在");
        checkPwdParams(oldPassword, newPassword, newPassword1);
        AssertUtil.isTrue(oldPassword.equals(newPassword), "输入密码和旧密码相同");
        AssertUtil.isTrue(!newPassword.equals(newPassword1), "两次设置密码不一致");
        user.setUserPwd(Md5Util.encode(newPassword));
        AssertUtil.isTrue(userMapper.updateByPrimaryKeySelective(user) < 1, "密码修改失败");
    }

    /**
     * 验证密码非空
     *
     * @param oldPassword
     * @param newPassword
     * @param newPassword1
     */
    private void checkPwdParams(String oldPassword, String newPassword, String newPassword1) {
        AssertUtil.isTrue(oldPassword == null, "密码不能为空");
        AssertUtil.isTrue(oldPassword == null, "新密码不能为空");
        AssertUtil.isTrue(oldPassword == null, "确认密码不能为空");

    }


    /**
     * 构建回显模型
     *
     * @param user
     * @return
     */
    private UserModel buildUserInfo(User user) {
        UserModel userModel = new UserModel();
        userModel.setUserIdStr(UserIDBase64.encoderUserID(user.getId()));
        userModel.setUserName(user.getUserName());
        userModel.setTrueName(user.getTrueName());
        return userModel;
    }

    /**
     * 验证密码
     *
     * @param userPwd
     * @param userPwd1
     */
    private void checkLoginPwd(String userPwd, String userPwd1) {
        userPwd = Md5Util.encode(userPwd);
        AssertUtil.isTrue(!userPwd.equals(userPwd1), "密码不正确");
    }

    /**
     * 验证参数是否为空
     *
     * @param userName
     * @param userPwd
     */
    private void checkLoginParams(String userName, String userPwd) {
        AssertUtil.isTrue(StringUtil.isEmpty(userName), "用户名不能为空!");
        AssertUtil.isTrue(StringUtil.isEmpty(userPwd), "密码不能为空！");

    }

    /**
     * 根据id去查用户
     *
     * @param id
     * @return
     */
    public User selectById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询所有销售人员
     *
     * @return
     */
    public List<Map<String, Object>> selectSaleMan() {
        return userMapper.selectSaleman();
    }

    /**
     * 多条件查询用户
     *
     * @param query
     * @return
     */
    public Map<String, Object> queryUserByParams(UserQuery query) {
        Map<String, Object> map = new HashMap<>();
        PageHelper.startPage(query.getPage(), query.getLimit());
        PageInfo<User> pageInfo = new PageInfo<>(userMapper.selectByParams(query));
        map.put("code", 0);
        map.put("msg", "suceess");
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }

    /**
     * 添加用户
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addUser(User user) {
        checkaddParams(user.getUserName(), user.getTrueName(), user.getEmail(), user.getPhone());
        User user1 = userMapper.queryByUserName(user.getUserName());
        AssertUtil.isTrue(user1 != null, "该用户已经存在");
        user.setCreateDate(new Date());
        user.setUpdateDate(new Date());
        AssertUtil.isTrue(userMapper.insertSelective(user) < 1, "用户添加失败");


        /**
         * 用户和角色进行绑定
         */

        relationUserRole(user.getId(), user.getRoleIds());
    }


    /**
     * 用户和角色进行绑定
     *
     * @param id
     * @param roleIds
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void relationUserRole(Integer id, String roleIds) {
        Integer count = userRoleMapper.countUserRoleByUserId(id);
        if (count > 0) {
            AssertUtil.isTrue(userRoleMapper.deleteUserRoleByUserId(id) != count, "用户角色删除失败");
        }
        if (StringUtils.isNotBlank(roleIds)) {
            List<UserRole> userRoleList = new ArrayList<>();
            String[] roleIdArray = roleIds.split(",");
            for (String roleId : roleIdArray) {
                UserRole userRole = new UserRole();
                userRole.setUserId(id);
                userRole.setRoleId(Integer.parseInt(roleId));
                userRole.setCreateDate(new Date());
                userRole.setUpdateDate(new Date());
                userRoleList.add(userRole);
            }
            AssertUtil.isTrue(userRoleMapper.insertBatch(userRoleList) != roleIdArray.length, "用户分配角色失败");
        }
    }

    /**
     * 用户修改
     *
     * @param user
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUser(User user) {
        checkaddParams(user.getUserName(), user.getTrueName(), user.getEmail(), user.getPhone());
        User temp = userMapper.queryByUserName(user.getUserName());
        AssertUtil.isTrue(temp != null && !(temp.getId().equals(user.getId())), "该用户已经存在");
        AssertUtil.isTrue(userMapper.updateByPrimaryKeySelective(user) < 1, "用户修改失败");
        relationUserRole(user.getId(), user.getRoleIds());
    }

    /**
     * 删除用户
     *
     * @param ids
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteUser(Integer[] ids) {
        AssertUtil.isTrue(ids == null || ids.length == 0, "暂无可删除数据");
        deleteUserRole(ids);
        AssertUtil.isTrue(userMapper.deleteBatch(ids) < ids.length, "用户删除失败");

    }

    /**
     * 删除用户角色
     *
     * @param ids
     */
    private void deleteUserRole(Integer[] ids) {
        if (ids.length != 0) {
            AssertUtil.isTrue(userRoleMapper.deleteUserRoleByUserIds(ids) < 1, "用户角色删除失败");
        }
    }

    /**
     * 判断添加字符是否为空
     *
     * @param userName
     * @param trueName
     * @param email
     * @param phone
     */
    private void checkaddParams(String userName, String trueName, String email, String phone) {
        AssertUtil.isTrue(userName == null, "用户名不能为空");
        AssertUtil.isTrue(trueName == null, "真实不能为空");
        AssertUtil.isTrue(email == null, "邮箱不能为空");
        AssertUtil.isTrue(phone == null, "联系电话不能为空");

    }
}
