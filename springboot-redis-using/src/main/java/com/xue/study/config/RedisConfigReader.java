package com.xue.study.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

public class RedisConfigReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisConfigReader.class);

    public JedisConnectionFactory getJedisConnFactory(RedisConfig redisConfig){
        LOGGER.info("redis config is :"+redisConfig);
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setDatabase(redisConfig.getDatabase());
        jedisConnectionFactory.setHostName(redisConfig.getHost());
        jedisConnectionFactory.setPort(redisConfig.getPort());
        jedisConnectionFactory.setPassword(redisConfig.getPassword());
        jedisConnectionFactory.setTimeout(redisConfig.getTimeout());

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(redisConfig.getPool().getMaxIdle());
        jedisPoolConfig.setMinIdle(redisConfig.getPool().getMinIdle());
        jedisPoolConfig.setMaxTotal(redisConfig.getPool().getMaxActive());
        jedisPoolConfig.setMaxWaitMillis(redisConfig.getPool().getMaxWait());
        jedisPoolConfig.setTestOnBorrow(true);

        jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
        return jedisConnectionFactory;
    }

    public RedisTemplate getRedisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisSerializer redisSerializer = new StringRedisSerializer();
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        redisTemplate.setKeySerializer(redisSerializer);
        redisTemplate.setValueSerializer(redisSerializer);
        redisTemplate.setHashKeySerializer(redisSerializer);
        redisTemplate.setHashValueSerializer(redisSerializer);

        return redisTemplate;
    }
}
