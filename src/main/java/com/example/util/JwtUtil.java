package com.example.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 */
@Slf4j
@Component
public class JwtUtil {

    // 用户ID的Key
    public static final String USER_ID_KEY = "userId";
    // 用户角色的Key
    public static final String USER_ROLE_KEY = "role";
    /**
     * JWT密钥
     */
    @Value("${jwt.secret}")
    private String secret;
    /**
     * JWT过期时间（毫秒）
     */
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 生成Token
     *
     * @param userId 用户ID
     * @param role   用户角色
     * @return JWT Token
     */
    public String generateToken(Long userId, Integer role) {
        Map<String, Object> claims = new HashMap<>(3);
        claims.put(USER_ID_KEY, userId);
        claims.put(USER_ROLE_KEY, role);
        claims.put("created", new Date());
        return generateToken(claims);
    }

    /**
     * 从Token中获取用户ID
     *
     * @param token JWT Token
     * @return 用户ID
     */
    public Long getUserIdFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims != null ? Long.parseLong(claims.get(USER_ID_KEY).toString()) : null;
    }

    /**
     * 从Token中获取用户角色
     *
     * @param token JWT Token
     * @return 用户角色
     */
    public Integer getUserRoleFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims != null ? Integer.parseInt(claims.get(USER_ROLE_KEY).toString()) : null;
    }

    /**
     * 判断Token是否过期
     *
     * @param token JWT Token
     * @return 是否过期
     */
    public boolean isTokenExpired(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims != null && claims.getExpiration().before(new Date());
    }

    /**
     * 验证Token
     *
     * @param token JWT Token
     * @return 是否有效
     */
    public boolean validateToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            return claims != null && !isTokenExpired(token);
        } catch (Exception e) {
            log.error("Token验证失败", e);
            return false;
        }
    }

    /**
     * 根据声明生成Token
     *
     * @param claims 声明
     * @return JWT Token
     */
    private String generateToken(Map<String, Object> claims) {
        Date expirationDate = new Date(System.currentTimeMillis() + expiration);
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 从Token中获取声明
     *
     * @param token JWT Token
     * @return 声明
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("从Token中获取声明失败", e);
            claims = null;
        }
        return claims;
    }
} 