package com.hanzoy.flip.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.lang.reflect.Field;
import java.util.Calendar;

public class JWTUtils {
    /**
     * 签名
     */
    private static final String SING = "Hanzoy@SIPC@PRIVATE";

    /**
     * token有效期
     */
    private static final int EXPIRES_DAY = 14;

    /**
     * 根据传入的对象中具有@Token注解的字段生成token
     * @param t 传入对象
     * @param <T> 传入对象的类型
     * @return 对应token
     */
    public static <T> String createToken(T t) {
        JWTCreator.Builder builder = JWT.create();
        Class<?> aClass = t.getClass();
        Field[] fields = aClass.getDeclaredFields();
        for(Field field : fields){
            if(field.isAnnotationPresent(Token.class)){
                field.setAccessible(true);
                try {
                    builder.withClaim(field.getName(), field.get(t).toString());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE,EXPIRES_DAY);
        return builder.withExpiresAt(instance.getTime()).sign(Algorithm.HMAC256(SING));
    }

    /**
     * 验证token
     * @param token 需要验证的token
     * @return 验证结果
     */
    public static boolean checkToken(String token){
        try{
            JWT.require(Algorithm.HMAC256(SING)).build().verify(token);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    /**
     * 通过token获取相应的值并传入实体对象
     * @param token 需要解析的token
     * @param t 接受token解析结果的对象
     * @param <T> 接受的对象类型
     */
    public static<T> void getBeanByToken(String token, T t){
        DecodedJWT verify;
        try{
            verify = JWT.require(Algorithm.HMAC256(SING)).build().verify(token);
        }catch (Exception e){
            return;
        }
        Class<?> aClass = t.getClass();
        Field[] fields = aClass.getDeclaredFields();
        for(Field field : fields){
            if(field.isAnnotationPresent(Token.class)){
                field.setAccessible(true);
                try {
                    field.set(t, verify.getClaim(field.getName()).as(field.getType()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }
}
