package com.lee.demo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class UserVo implements Serializable {
    private static final long serialVersionUID = -6825369097978037202L;
    private Integer id;

    private String userName;

    private String userCode;

    private String passWord;

    private Date createTime;

    private Date updateTime;

    private Date lastTime;

    private Date lastLoginTime;

    private String loginIp;

    private String lastLoginIp;
}
