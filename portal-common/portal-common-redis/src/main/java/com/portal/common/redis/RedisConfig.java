package com.portal.common.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Redis配置
 *
 * @author dcp
 */
@Configuration
public class RedisConfig {

    /**
     * Redis repository redis repository.
     *
     * @param redisTemplate the redis template
     * @return the redis repository
     */
    @Bean
    public RedisRepository redisRepository(RedisTemplate<String, String> redisTemplate) {
        return new RedisRepository(redisTemplate);
    }

}