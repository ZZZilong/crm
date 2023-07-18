package com.whrj.controller;

import com.whrj.base.BaseController;
import com.whrj.base.ResultInfo;
import com.whrj.exceptions.ParamsException;
import com.whrj.model.UserModel;
import com.whrj.service.UserService;
import com.whrj.utils.LoginUserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController extends BaseController {
    @Resource
    private UserService userService;

    /**
     * 用户登录
     *
     * @param userName
     * @param userPwd
     * @return
     */
    @PostMapping("user/login")
    @ResponseBody
    public ResultInfo userLogin(String userName, String userPwd) {
        ResultInfo resultInfo = new ResultInfo();
        try {
            UserModel userModel = userService.userLogin(userName, userPwd);
            System.out.println(userModel);
            resultInfo.setResult(userModel);
        } catch (ParamsException e) {
            e.printStackTrace();
            resultInfo.setCode(e.getCode());
            resultInfo.setMsg(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setMsg("操作失败");
            resultInfo.setCode(500);
        }
        return resultInfo;
    }

    /**
     * 修改用户密码
     * @param request
     * @param oldPassword
     * @param newPassword
     * @param repeatPassword
     * @return
     */
    @PostMapping("user/updatePwd")
    @ResponseBody
    public ResultInfo updateUserPwd(HttpServletRequest request, String oldPassword, String newPassword, String repeatPassword) {
        ResultInfo resultInfo = new ResultInfo();
        Integer id = LoginUserUtil.releaseUserIdFromCookie(request);
        try {
            userService.updatePassword(id, oldPassword, newPassword, repeatPassword);
        } catch (ParamsException e) {
            resultInfo.setCode(e.getCode());
            resultInfo.setMsg(e.getMsg());

        } catch (Exception e) {
            resultInfo.setMsg("修改密码失败");
            resultInfo.setCode(500);
        }

        return resultInfo;
    }

    /**
     * 跳转到修改页面
     * @return
     */
    @RequestMapping("/user/toPasswordPage")
    public String toPasswordPage(){
        System.out.println("hhhh");
        return "user/password";
    }

}
