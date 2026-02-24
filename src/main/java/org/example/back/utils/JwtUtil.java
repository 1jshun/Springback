package org.example.back.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtil {
    static String key = "sdfkhgsdkglnhoiurjdfoihgh397478thgwr390289gyrfhp90823uoevbdo823uvh4tf";
    static SecretKey encodedKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));

    public static String createToken(Long idx, String email) {
        String jwt = Jwts.builder()
                .claim("idx", idx)
                .claim("email", email)
                .issuedAt(new Date()).expiration(new Date(System.currentTimeMillis() + 300000)).signWith(encodedKey).compact();

        return jwt;
    }

    public static Long getUserIdx(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(encodedKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.get("idx", Long.class);
    }

    public static String getUsername(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(encodedKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.get("email", String.class);
    }
}
