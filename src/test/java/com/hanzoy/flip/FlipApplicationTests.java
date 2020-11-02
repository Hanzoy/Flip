package com.hanzoy.flip;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hanzoy.flip.dao.entity.User;
import com.hanzoy.flip.utils.JWTUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.rmi.CORBA.Util;

@SpringBootTest
class FlipApplicationTests {

    @Test
    void contextLoads() {
        User user = new User();
        user.setUuid(12);
        user.setUsername("xhh");
        user.setPassword("123123");

        String token = JWTUtils.createToken( user );


        if(JWTUtils.checkToken(token)){
            System.out.println("true");
        }else {
            System.out.println("false");
        }

        User user1 = new User();

        JWTUtils.getBeanByToken(token,user1);
        System.out.println(user1);
    }

}
