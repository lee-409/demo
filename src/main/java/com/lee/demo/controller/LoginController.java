package com.lee.demo.controller;

import com.lee.demo.entity.dto.UserDto;
import com.lee.demo.entity.request.LoginUser;
import com.lee.demo.entity.response.BaseResult;
import com.lee.demo.service.LoginService;
import com.lee.demo.service.UserService;
import com.lee.demo.utils.TokenUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
public class LoginController {

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;
    private final String ADMIN = "admin";

    @Resource
    private LoginService loginService;

    @Resource
    private UserService userService;

    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response){
        this.request = request;
        this.response = response;
        this.session = request.getSession();
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public BaseResult login(LoginUser loginUser){
        if (loginUser == null || null == loginUser.getUserCode() || null == loginUser.getPassWord()) {
            return BaseResult.fail("0001","用户姓名和密码不能为空");
        }
        //创建token及验证
        String jwtToken = TokenUtil.createToken(userService.getUserInfo(loginUser.getUserCode()));//生成token
        System.out.println(jwtToken);
        loginUser.setUserToken(jwtToken);
        return loginService.login(loginUser, request);
    }

    @RequestMapping("info")
    public BaseResult info(String userCode){
        UserDto userDto = userService.getUserInfo(userCode);
        return userDto != null ? BaseResult.success(userDto) : BaseResult.fail("00000","获取用户信息失败");
    }

    @RequestMapping("reset")
    public BaseResult reset(String userCode){
        UserDto userDto = userService.getUserInfo(userCode);
        if (!ADMIN.equals(userCode)){
            BaseResult.fail("0009","非管理员用户无权清理指定用户的登录状态");
        }
        session.invalidate();
        return loginService.reset(userCode);
    }



}
