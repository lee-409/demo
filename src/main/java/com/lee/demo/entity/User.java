package com.lee.demo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {

    private static final long serialVersionUID = 5387044893055967314L;

    @Id
    private Integer id;

    private String userName;

    private String userCode;

    private String passWord;
    /**
     * 用户登录状态 0退出，1登录
     */
    private Integer state;

    private Date createTime;

    private Date updateTime;

    private Date loginTime;

    private Date lastLoginTime;

    private String loginIp;

    private String lastLoginIp;
}
