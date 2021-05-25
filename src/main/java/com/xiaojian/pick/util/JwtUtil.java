package com.xiaojian.pick.util;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import net.minidev.json.JSONObject;

import java.util.Map;

/**
 * @author 小贱
 * @date 2020/11/2 - 14:46
 */
public class JwtUtil {
    /**
     * 创建一个 32 位 的秘钥
     */
    private static final byte[] SECRET = "6MNSobBRCHGIO0fS6MNSobBRCHGIO0fS".getBytes();

    /**
     * 生成一个 token
     * @param payloadMap
     * @return
     */
    public static String createToken(Map<String,Object> payloadMap){

        String token = "";
        try {
            // 创建密匙
            MACSigner macSigner = new MACSigner(SECRET);
            // 建立一个 载荷
            Payload payload = new Payload(new JSONObject(payloadMap));
            // 建立签名
            JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256),payload);
            jwsObject.sign(macSigner);

            token = jwsObject.serialize();
        } catch (JOSEException e) {
            e.printStackTrace();
        }

        return token;
    }

}
