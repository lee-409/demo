package com.lee.demo.service.impl;

import cn.hutool.core.date.DateUtil;
import com.lee.demo.dao.UserDao;
import com.lee.demo.entity.User;
import com.lee.demo.entity.dto.UserDto;
import com.lee.demo.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Log4j2
public class UserServiceImpl implements UserService {
    private static Map<String, String> loginUserMap = new HashMap<>();//存储在线用户
    private static Map<String, String> loginOutTime = new HashMap<>();//存储剔除用户时间
    @Resource
    private UserDao userDao;

    @Override
    public UserDto getUserInfo(String userCode) {
        UserDto userDto = null;
        User user = userDao.findByCode(userCode);
        if (user != null) {
            BeanUtils.copyProperties(user, userDto);
        }
        return userDto;
    }


    public String loginLimite(HttpServletRequest request, String userCode) {
        UserDto user = this.getUserInfo(userCode);
        String sessionId = request.getSession().getId();
        for (String key : loginUserMap.keySet()) {
            //用户已在另一处登录
            if (key.equals(user.getUserName()) && !loginUserMap.containsValue(sessionId)) {
                log.info("用户：" + user.getUserName() + "，于" + DateUtil.formatDateTime(new Date()) + "被剔除！");
                loginOutTime.put(user.getUserName(), DateUtil.formatDateTime(new Date()));
                loginUserMap.remove(user.getUserName());
                break;
            }
        }
        loginUserMap.put(user.getUserName(), sessionId);
        request.getSession().getServletContext().setAttribute("loginUserMap", loginUserMap);
        request.getSession().getServletContext().setAttribute("loginOutTime", loginOutTime);
        return "success";
    }

}
