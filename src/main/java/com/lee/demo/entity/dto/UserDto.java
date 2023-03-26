package com.lee.demo.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserDto implements Serializable {

    private static final long serialVersionUID = 1407248393024772798L;

    private Integer id;

    private String userName;

    private String userCode;

    private String passWord;

    private Date lastTime;

    private Date lastLoginTime;

    private String loginIp;

    private String lastLoginIp;

    public UserDto(String userName, String userCode, String loginIp) {
        this.userName = userName;
        this.userCode = userCode;
        this.loginIp = loginIp;
    }

    public UserDto() {
    }
}
