package com.lee.demo.service;


import com.lee.demo.entity.request.LoginUser;
import com.lee.demo.entity.response.BaseResult;

import javax.servlet.http.HttpServletRequest;

public interface LoginService {

    BaseResult login(LoginUser loginUser, HttpServletRequest request);

    BaseResult reset(String userCode);
}
