package com.smarthire.smarthire.security;


import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;
import java.util.Date;

// I am creating this class to manage my token
public class JwtUtil {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expirationMs}")
    private long jwtExpirationInMs;

    private Key getSingingKey(){
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }
    /*It converts your plain-text jwtSecret string
     (which is just text) into a secure, cryptographic Key object.
     The JWT library needs this special Key object to perform the digital signature,
     it can't just use the string.*/

    public String generateToken(String userName){
        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(getSingingKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token , String userName){
        try {
            String tokenUser = getUserNameFromToken(token);
            return (tokenUser.equals(userName) && !isTokenExpired(token));
        }catch (JwtException | IllegalArgumentException ex){
            return false;
        }
    }

    private String getUserNameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSingingKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    private boolean isTokenExpired(String token){
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(getSingingKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();

        return expiration.before(new Date());
    }


}
