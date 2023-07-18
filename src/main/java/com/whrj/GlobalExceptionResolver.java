package com.whrj;


import com.whrj.exceptions.NoLoginException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常统一处理
 * <p>
 * 乐字节：专注线上IT培训
 * 答疑老师微信：lezijie
 */
@Component
public class GlobalExceptionResolver implements HandlerExceptionResolver {
    /**
     * 异常处理方法
     * 方法的返回值：
     * 1. 返回视图
     * 2. 返回数据（JSON数据）
     * <p>
     * 如何判断方法的返回值？
     * 通过方法上是否声明@ResponseBody注解
     * 如果未声明，则表示返回视图
     * 如果声明了，则表示返回数据
     * <p>
     * <p>
     * 乐字节：专注线上IT培训
     * 答疑老师微信：lezijie
     *
     * @param request  request请求对象
     * @param response response响应对象
     * @param handler  方法对象
     * @param ex       异常对象
     * @return org.springframework.web.servlet.ModelAndView
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        /**
         * 非法请求拦截
         *  判断是否抛出未登录异常
         *      如果抛出该异常，则要求用户登录，重定向跳转到登录页面
         */
        if (ex instanceof NoLoginException) {
            // 重定向到登录页面
            ModelAndView mv = new ModelAndView("redirect:/index");
            return mv;
        }
        ModelAndView mv = new ModelAndView();
        return mv;
    }

}