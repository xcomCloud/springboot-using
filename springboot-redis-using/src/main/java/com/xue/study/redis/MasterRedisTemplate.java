package com.xue.study.redis;

import com.xue.study.config.RedisConfig;
import com.xue.study.config.RedisConfigReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@EnableCaching
public class MasterRedisTemplate extends RedisConfigReader {

    @Bean("masterRedisTemp")
    @Override
    public RedisTemplate getRedisTemplate(@Qualifier("masterJedisConnFactory") RedisConnectionFactory redisConnectionFactory) {
        return super.getRedisTemplate(redisConnectionFactory);
    }

    @Primary
    @Bean(name = "masterJedisConnFactory")
    @Override
    public JedisConnectionFactory getJedisConnFactory(@Qualifier("masterRedisProperties") RedisConfig redisConfig) {
        return super.getJedisConnFactory(redisConfig);
    }

    @Bean(name = "masterRedisProperties")
    @ConfigurationProperties(prefix = "spring.redis.master")
    public RedisConfig getBaseDBProperties(){
        return new RedisConfig();
    }
}
