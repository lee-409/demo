package com.lee.demo.dao.impl;

import com.lee.demo.dao.UserDao;
import com.lee.demo.entity.User;
import com.lee.demo.entity.UserVo;
import com.lee.demo.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @Resource
    private UserMapper userMapper;

    @Override
    public int updateById(UserVo userVo) {
        User user = new User();
        BeanUtils.copyProperties(userVo, user);
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public User findByCode(String userCode) {
        UserVo userVo = new UserVo();
        userVo.setUserCode(userCode);
        List<User> userList = userMapper.selectByCondition(userVo);
        if (!CollectionUtils.isEmpty(userList)) {
            return userList.get(0);
        }
        return null;
    }

    @Override
    public int updateStateByCode(String userCode) {
        User user = new User();
        user.setUserCode(userCode);
        user.setState(0);
        return userMapper.updateByCode(user);
    }
}
