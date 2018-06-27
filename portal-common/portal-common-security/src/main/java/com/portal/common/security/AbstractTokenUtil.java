package com.portal.common.security;

import com.portal.common.redis.RedisRepository;
import com.portal.common.utils.StringHelper;
import com.google.gson.Gson;
import com.xiaoleilu.hutool.cache.CacheUtil;
import com.xiaoleilu.hutool.cache.impl.TimedCache;
import com.xiaoleilu.hutool.date.DateUnit;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

/**
 * Token 工具类
 *
 * @author dcp
 */
public abstract class AbstractTokenUtil {

    /**
     * Logger
     */
    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractTokenUtil.class);

    /**
     * Token 类型
     */
    public static final String TOKEN_TYPE_BEARER = "Bearer";
    /**
     * 权限缓存前缀
     */
    private static final String REDIS_PREFIX_AUTH = "auth:";
    /**
     * 用户信息缓存前缀
     */
    private static final String REDIS_PREFIX_USER = "user-details:";

    /**
     * redis repository
     */
    @Autowired
    private RedisRepository redisRepository;

    /**
     * secret
     */
    private String secret;
    /**
     * 过期时间
     */
    private Long expiration;

    private TimedCache<String, String> timedCache = (TimedCache) CacheUtil.newTimedCache(DateUnit.SECOND.getMillis() * 50000);

    /**
     * 获取用户名
     *
     * @param token Token
     * @return String
     */
    public String getUsernameFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims != null ? claims.getSubject() : null;
    }

    /**
     * 获取过期时间
     *
     * @param token Token
     * @return Date
     */
    public Date getExpiredFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims != null ? claims.getExpiration() : null;
    }

    /**
     * 获得 Claims
     *
     * @param token Token
     * @return Claims
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        } catch (Exception e) {
            LOGGER.warn("getClaimsFromToken exception", e);
            claims = null;
        }
        return claims;
    }

    /**
     * 计算过期时间
     *
     * @return Date
     */
    private Date generateExpired() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * 判断 Token 是否过期
     *
     * @param token Token
     * @return Boolean
     */
    private Boolean isTokenExpired(String token) {
        Date expirationDate = getExpiredFromToken(token);
        return expirationDate.before(new Date());
    }

    /**
     * 生成 Token
     *
     * @param userDetails 用户信息
     * @return String
     */
    public String generateToken(UserDetails userDetails, String ip) {
        String token = Jwts.builder()
            .setSubject(userDetails.getUsername())
            .setExpiration(generateExpired())
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact();

        String key = REDIS_PREFIX_AUTH + userDetails.getUsername() + ":" + ip;
        timedCache.put(key, token, expiration);

//        redisRepository.setExpire(key, token, expiration);
        putUserDetails(userDetails, ip);
        return token;
    }

    /**
     * 验证 Token
     *
     * @param token Token
     * @return Boolean
     */
    public Boolean validateToken(String token, String ip) {
        final String username = getUsernameFromToken(token);
        String key = REDIS_PREFIX_AUTH + username + ":" + ip;
//        String redisToken = redisRepository.get(key);
        String redisToken = timedCache.get(key);
        return StringHelper.isNotEmpty(token) && !isTokenExpired(token) && token.equals(redisToken);
    }

    /**
     * 移除 Token
     *
     * @param token Token
     */
    public void removeToken(String token, String ip) {
        final String username = getUsernameFromToken(token);
        String key = REDIS_PREFIX_AUTH + username + ":" + ip;
//        redisRepository.del(key);
        timedCache.remove(key);
        delUserDetails(username, ip);
    }

    /**
     * 获得用户信息 Json 字符串
     *
     * @param token Token
     * @return String
     */
    protected String getUserDetailsString(String token, String ip) {
        final String username = getUsernameFromToken(token);
        String key = REDIS_PREFIX_USER + username + ":" + ip;
//        return redisRepository.get(key);
        return timedCache.get(key);
    }

    /**
     * 获得用户信息
     *
     * @param token Token
     * @return UserDetails
     */
    public abstract UserDetails getUserDetails(String token, String ip);

    /**
     * 存储用户信息
     *
     * @param userDetails 用户信息
     */
    private void putUserDetails(UserDetails userDetails, String ip) {
        String key = REDIS_PREFIX_USER + userDetails.getUsername() + ":" + ip;
//        redisRepository.setExpire(key, new Gson().toJson(userDetails), expiration);
        timedCache.put(key, new Gson().toJson(userDetails), expiration);
    }

    /**
     * 删除用户信息
     *
     * @param username 用户名
     */
    private void delUserDetails(String username, String ip) {
        String key = REDIS_PREFIX_USER + username + ":" + ip;
//        redisRepository.del(key);
        timedCache.remove(key);
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration * DateUnit.SECOND.getMillis();
    }
}