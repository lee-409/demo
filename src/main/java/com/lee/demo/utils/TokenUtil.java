package com.lee.demo.utils;

import com.lee.demo.entity.dto.UserDto;
import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;

public class TokenUtil {




    public static void main(String[] agars) throws Exception {
        UserDto userToken=new UserDto("超级管理员","admin","127.0.0.1");
        String token = createToken(userToken);
        System.out.println(token);
        String infoFromToken = getTokenInfo(token,"userCode");
        System.out.println(infoFromToken);

    }

    //设置过期时间
    private static final long EXPIRE_DATE = 1000 * 10 * 60 * 24; // 一天
    //token秘钥
    private static final String TOKEN_SECRET = "CSYZWECHAT";

    /**
     * 生成token
     * @param userDto 用户
     * @return token
     */
    public static String createToken(UserDto userDto) {

        // 过期时间
        Date date = new Date(System.currentTimeMillis() + EXPIRE_DATE);
        // 获得JWT构造器
        JwtBuilder builder = Jwts.builder();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("userCode", userDto.getUserCode());
        hashMap.put("userName", userDto.getUserName());
        hashMap.put("loginIp", userDto.getLoginIp());


        return builder.setSubject("hello")
                .setClaims(hashMap)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS256, TOKEN_SECRET)
                .compact();
    }

    /**
     * 解析token
     * @param token token
     * @param key   key
     * @return 结果
     */
    public static String getTokenInfo(String token, String key) {
        if (StringUtils.isEmpty(token)) return null;
        try {
            Jws<Claims> claimsJws
                    = Jwts.parser() // 构造器
                    .setSigningKey(TOKEN_SECRET) // 加盐
                    .parseClaimsJws(token);
            // 获取token里的信息
            Claims claims = claimsJws.getBody();
            return (String) claims.get(key);
        } catch (Exception e) {
            return null;
        }
    }


}
