package com.hdu.lease.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hdu.lease.pojo.dto.TokenDTO;

/**
 * @author Jackson
 * @date 2022/4/30 20:42
 * @description: Jwt util
 */
public class JwtUtils {

    private static final String SING = "Authorization";

    private JwtUtils(){
    }

    /**
     * create token.
     * @param tokenDTO
     * @return
     */
    public static String createToken(TokenDTO tokenDTO) {
        JWTCreator.Builder builder = JWT.create();
        builder.withClaim("account", tokenDTO.getAccount());
        builder.withClaim("role", tokenDTO.getRole());
        return builder.sign(Algorithm.HMAC256(SING));
    }


    /**
     * verify the token.
     * @param token
     */
    public static boolean verifyToken(String token) {
        try {
            JWT.require(Algorithm.HMAC256(SING)).build().verify(token);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    /**
     * get the message from token.
     * @param token
     * @return
     */
    public static DecodedJWT getTokenInfo(String token) {
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SING)).build().verify(token);
        return verify;
    }
}
