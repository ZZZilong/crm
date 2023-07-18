package com.whrj.service;

import com.github.pagehelper.util.StringUtil;
import com.whrj.dao.UserMapper;
import com.whrj.model.UserModel;
import com.whrj.utils.AssertUtil;
import com.whrj.utils.Md5Util;
import com.whrj.utils.UserIDBase64;
import com.whrj.vo.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

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
        AssertUtil.isTrue(oldPassword.equals(newPassword),"输入密码和旧密码相同");
        AssertUtil.isTrue(!newPassword.equals(newPassword1),"两次设置密码不一致");
        user.setUserPwd(Md5Util.encode(newPassword));
        AssertUtil.isTrue(userMapper.updateByPrimaryKeySelective(user) < 1, "密码修改失败");
    }

    /**
     * 验证密码非空
     * @param oldPassword
     * @param newPassword
     * @param newPassword1
     */
    private void checkPwdParams(String oldPassword, String newPassword, String newPassword1) {
        AssertUtil.isTrue(oldPassword==null,"密码不能为空");
        AssertUtil.isTrue(oldPassword==null,"新密码不能为空");
        AssertUtil.isTrue(oldPassword==null,"确认密码不能为空");

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
     * @param id
     * @return
     */
    public User selectById(Integer id){
        return  userMapper.selectByPrimaryKey(id);
    }



}
