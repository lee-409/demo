package com.lee.demo.dao;

import com.lee.demo.entity.User;
import com.lee.demo.entity.UserVo;

public interface UserDao {
    int updateById(UserVo userVo);

    User findByCode(String userCode);

    int updateStateByCode(String userCode);
}
