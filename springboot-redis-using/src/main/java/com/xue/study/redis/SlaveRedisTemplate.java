package com.xue.study.redis;

import com.xue.study.config.RedisConfig;
import com.xue.study.config.RedisConfigReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@EnableCaching
public class SlaveRedisTemplate extends RedisConfigReader {

    @Bean(name = "slaveRedisTemp")
    @Override
    public RedisTemplate getRedisTemplate(@Qualifier("slaveRedisConnFactory") RedisConnectionFactory redisConnectionFactory) {
        return super.getRedisTemplate(redisConnectionFactory);
    }

    @Bean(name = "slaveRedisConnFactory")
    @Override
    public JedisConnectionFactory getJedisConnFactory(@Qualifier("slaveRedisProperties") RedisConfig redisConfig) {
        return super.getJedisConnFactory(redisConfig);
    }

    @Bean(name = "slaveRedisProperties")
    @ConfigurationProperties(prefix = "spring.redis.lbs")
    public RedisConfig getBaseDBProperties(){
        return new RedisConfig();
    }
}
