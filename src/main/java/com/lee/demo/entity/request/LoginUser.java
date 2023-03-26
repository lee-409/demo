package com.lee.demo.entity.request;

import lombok.Data;

/**
 * 用户登录入参
 */
@Data
public class LoginUser {
    private String userCode;
    private String passWord;
    private String userToken;
    private String loginIp;
}
