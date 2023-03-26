package com.lee.demo.service.impl;

import com.lee.demo.dao.LoginDao;
import com.lee.demo.dao.UserDao;
import com.lee.demo.entity.User;
import com.lee.demo.entity.UserVo;
import com.lee.demo.entity.dto.UserDto;
import com.lee.demo.entity.request.LoginUser;
import com.lee.demo.entity.response.BaseResult;
import com.lee.demo.service.LoginService;
import com.lee.demo.utils.EncodeUtil;
import com.lee.demo.utils.IpAndAddrUtil;
import com.lee.demo.utils.TokenUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private LoginDao loginDao;
    @Resource
    private UserDao userDao;

    @Resource
    private RedisTemplate redisTemplate;


    @Override
    @Transactional
    public BaseResult login(LoginUser loginUser, HttpServletRequest request) {
        UserDto userDto = new UserDto();
        if (loginUser == null || null == loginUser.getUserCode() || null == loginUser.getPassWord()) {
            return BaseResult.fail("0001","用户姓名和密码不能为空");
        }
        userDto.setUserCode(loginUser.getUserCode());
        User user = loginDao.findUser(userDto);
        if (user == null) {
            return BaseResult.fail("0002","用户不存在");
        }
        if (!user.getPassWord().equals(EncodeUtil.encoderByMd5(loginUser.getPassWord()))){
            return BaseResult.fail("0003","用户密码不正确");
        }

        //插入登录ip
        String ip = IpAndAddrUtil.getIp(request);
        UserVo userVo = new UserVo();
        userVo.setId(user.getId());
        if (Objects.isNull(user.getLoginIp())){
            userVo.setLoginIp(ip);
        }else {
            userVo.setLoginIp(ip);
            userVo.setLastLoginIp(user.getLoginIp());
        }
        int a = userDao.updateById(userVo);
        //key--键   value--值  time--过期时间  TimeUnit--时间单位枚举
        //设置用户在线状态
        redisTemplate.opsForValue().setIfAbsent(loginUser.getUserCode(), loginUser.getUserToken() , 30, TimeUnit.MINUTES);
        return new BaseResult(true,"10000","登录成功");
    }

    @Override
    public BaseResult reset(String userCode) {
        int a = 0;
        String jwtToken = (String) redisTemplate.opsForValue().get(userCode);
        String tokenInfo = TokenUtil.getTokenInfo(jwtToken,"userCode");//token解析
        if (userCode.equals(tokenInfo)){
            redisTemplate.delete(userCode);
            a = userDao.updateStateByCode(userCode);
        }
        return a > 0 ? BaseResult.success() : BaseResult.fail("0010","清理【"+ userCode +"】用户的登录状态失败") ;
    }

}
