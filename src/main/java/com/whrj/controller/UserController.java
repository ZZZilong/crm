package com.whrj.controller;

import com.whrj.base.BaseController;
import com.whrj.base.ResultInfo;
import com.whrj.exceptions.ParamsException;
import com.whrj.model.UserModel;
import com.whrj.query.UserQuery;
import com.whrj.service.UserService;
import com.whrj.utils.LoginUserUtil;
import com.whrj.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("user")
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
    @PostMapping("login")
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
     *
     * @param request
     * @param oldPassword
     * @param newPassword
     * @param repeatPassword
     * @return
     */
    @PostMapping("updatePwd")
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
     *
     * @return
     */
    @RequestMapping("toPasswordPage")
    public String toPasswordPage() {
        System.out.println("hhhh");
        return "user/password";
    }

    /**
     * 查询销售用户
     *
     * @return
     */
    @RequestMapping("queryAllSales")
    @ResponseBody
    public List<Map<String, Object>> querySaleman() {
        return userService.selectSaleMan();
    }

    /**
     * 进入用户管理主界面
     *
     * @return
     */
    @RequestMapping("index")
    public String index() {
        return "user/user";
    }


    /**
     * 多条件查询用户
     *
     * @param userQuery
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String, Object> queryUserByParam(UserQuery userQuery) {
        return userService.queryUserByParams(userQuery);
    }


    /**
     * 进入添加/修改页面
     */
    @RequestMapping("toAddOrUpdateUserPage")
    public String toAddOrUpdateUserPage(Integer id, HttpServletRequest request) {
        if (id != null) {
            request.setAttribute("updateUser", userService.selectById(id));
        }
        return "user/add_update";
    }

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    @RequestMapping("add")
    @ResponseBody
    public ResultInfo addUser(User user) {
        userService.addUser(user);
        return success("添加用户成功");
    }


    /**
     * 修改用户信息
     *
     * @param user
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateUser(User user) {
        userService.updateUser(user);
        return success("用户修改成功");
    }

    /**
     * 删除用户
     * @param ids
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteUser(Integer[] ids) {
        userService.deleteUser(ids);
        return success("删除成功");
    }
}