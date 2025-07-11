package com.hwadee.springboot.utils;



import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.hwadee.springboot.entity.UserEntity;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TokenUtil {
    private static final long EXPIRE_TIME= 2*1000;
    private static final String TOKEN_SECRET="test";  //密钥盐
    public static Map<String, String> tokenMap = new ConcurrentHashMap<>();

    /**
     * 签名生成
     * @param user
     * @return
     */
    public static String sign(UserEntity user){
        String token = null;
        try {
            Date expiresAt = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            token = JWT.create()
                    .withIssuer("auth0")
                    .withClaim("userAccount", user.getAccount())
                    .withExpiresAt(expiresAt)
                    // 使用了HMAC256加密算法。
                    .sign(Algorithm.HMAC256(TOKEN_SECRET));
        } catch (Exception e){
            e.printStackTrace();
        }
        tokenMap.put(token,user.getAccount());
        return token;
    }
    /**
     * 签名验证
     * @param token
     * @return
     */
    public static boolean verify(String token){
        try {
            if(tokenMap.containsKey(token)){
//                JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("auth0").build();
//                DecodedJWT jwt = verifier.verify(token);
//                System.out.println("认证通过：");
//                System.out.println("userAccount: " + jwt.getClaim("userAccount").asString());
//                System.out.println("过期时间：      " + jwt.getExpiresAt());
                return true;
            }
            return false;
        } catch (Exception e){
            return false;
        }
    }
}
