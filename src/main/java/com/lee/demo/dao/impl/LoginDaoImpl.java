package com.lee.demo.dao.impl;

import com.lee.demo.dao.LoginDao;
import com.lee.demo.entity.User;
import com.lee.demo.entity.UserVo;
import com.lee.demo.entity.dto.UserDto;
import com.lee.demo.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class LoginDaoImpl implements LoginDao {

    @Resource
    private UserMapper userMapper;


    @Override
    public User findUser(UserDto userDto) {
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userDto, userVo);
        return userMapper.selectOne(userVo);
    }
}
