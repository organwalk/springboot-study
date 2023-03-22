package com.example.springbootstudy.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;

public class JwtUtils {
     //7天过期
     private static long expire = 604800;
    private static String secret = "abcdfghiabcdfghiabcdfghiabcdfghi";

    public static String generateToken(String username) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 1000 * expire);

        String header = "{\"alg\":\"HS512\",\"typ\":\"JWT\"}";
        String encodedHeader = Base64.getEncoder().encodeToString(header.getBytes());

        String payload = "{\"sub\":\"" + username + "\",\"iat\":" + now.getTime() / 1000 + ",\"exp\":" + expiration.getTime() / 1000 + "}";
        String encodedPayload = Base64.getEncoder().encodeToString(payload.getBytes());

        String signature = encodedHeader + "." + encodedPayload;
        String encodedSignature = Base64.getEncoder().encodeToString(signature.getBytes());

        return encodedHeader + "." + encodedPayload + "." + encodedSignature;
    }

    public static Claims getClaimsByToken(String token) {
        String[] parts = token.split("\\.");
        String encodedHeader = parts[0];
        String encodedPayload = parts[1];
        String encodedSignature = parts[2];

        String header = new String(Base64.getDecoder().decode(encodedHeader));
        String payload = new String(Base64.getDecoder().decode(encodedPayload));

        if (!verifySignature(encodedHeader + "." + encodedPayload, encodedSignature)) {
            throw new RuntimeException("Invalid signature");
        }

        return Jwts.parser()
                .setSigningKey(secret.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }

    private static boolean verifySignature(String content, String encodedSignature) {
        byte[] signature = Base64.getDecoder().decode(encodedSignature);
        try {
            Mac mac = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), "HmacSHA512");
            mac.init(secretKeySpec);
            byte[] contentBytes = content.getBytes();
            byte[] signatureBytes = mac.doFinal(contentBytes);
            return Arrays.equals(signature, signatureBytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            return false;
        }
    }
}