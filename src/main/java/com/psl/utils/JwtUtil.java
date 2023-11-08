package com.psl.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtil {

    private static final String KEY = "pslcom";
    private static final Long ValidTime = 10800000L; //过期时间

    //接收业务数据,生成token并返回
    public static String genToken(Map<String, Object> claims) {
        return JWT.create()
                .withClaim("claims", claims) //添加载荷
                .withExpiresAt(new Date(System.currentTimeMillis() + ValidTime)) //设置过期时间
                .sign(Algorithm.HMAC256(KEY)); //设置加密算法配置密钥
    }

    //接收token,验证token,并返回业务数据
    public static Map<String, Object> parseToken(String token) {
        return JWT.require(Algorithm.HMAC256(KEY))
                .build()
                .verify(token)
                .getClaim("claims")
                .asMap();
    }

}
