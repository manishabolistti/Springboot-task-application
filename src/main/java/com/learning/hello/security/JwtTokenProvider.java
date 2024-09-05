package com.learning.hello.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.learning.hello.exception.ApiException;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
    // private final String SECRET_KEY = "JWTSecretKey";
    private final SecretKey secretKey;

    public JwtTokenProvider() {
        // Generate a secure key for HS512
        secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }
    public String genarateToken(Authentication authentication){
        
        String email=authentication.getName();
        Date currentDate= new Date();
        Date expireDate = new Date(currentDate.getTime()+3600000);

        String token= Jwts.builder()
                    .setSubject(email)
                    .setIssuedAt(currentDate)
                    .setExpiration(expireDate)
                    .signWith(SignatureAlgorithm.HS512, secretKey)
                    .compact();

        return token;
    }
    

    public String getEmailFromToken(String token){
       JwtParser parser= Jwts.parser()
       .setSigningKey(secretKey)
       .build();
       Claims claims=parser.parseClaimsJws(token).getBody();
       return claims.getSubject();
    }

    public Boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        }
        catch(Exception e){
            throw new ApiException("Token issue :" + e.getMessage());
        }
     }
}
