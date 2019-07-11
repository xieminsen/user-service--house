package com.xms.house.utils;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.collect.Maps;

/**
 * 使用jwt鉴权类
 * @author xie
 *
 */
public class JwtHelper {
  
  private static final String  SECRET = "session_secret";
  
  private static final String  ISSUER = "xms_user";
  
  /**
   * 生成token
   * @param claims
   * @return
   */
  public static String genToken(Map<String, String> claims){
    try {
      //声明我们要使用生成token的算法
      Algorithm algorithm = Algorithm.HMAC256(SECRET);
      //jwt生成发布者为 xms_user  设置过期时间为1天
      JWTCreator.Builder builder = JWT.create().withIssuer(ISSUER).withExpiresAt(DateUtils.addDays(new Date(), 1));
      //将我们的claims设置进我们刚刚生成的builder里边
      claims.forEach((k,v) -> builder.withClaim(k, v));
      //使用jwt的签名算法加工我们刚刚的builder并且返回token
      return builder.sign(algorithm).toString();
    } catch (IllegalArgumentException | UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }
  
  /**
   * 校验token操作
   * @param token
   * @return
   */
  public static Map<String, String> verifyToken(String token)  {
    Algorithm algorithm = null;
    try {
      //算法的声明
      algorithm = Algorithm.HMAC256(SECRET);
    } catch (IllegalArgumentException | UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
    //校验token 使用require 并且校验 发布者
    JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISSUER).build();
    DecodedJWT jwt =  verifier.verify(token);
    Map<String, Claim> map = jwt.getClaims();
    Map<String, String> resultMap = Maps.newHashMap();
    map.forEach((k,v) -> resultMap.put(k, v.asString()));
    return resultMap;
  }

}
