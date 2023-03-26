package com.lee.demo.dao;

import com.lee.demo.entity.User;
import com.lee.demo.entity.dto.UserDto;

public interface LoginDao {
    public User findUser(UserDto userDto);
}
