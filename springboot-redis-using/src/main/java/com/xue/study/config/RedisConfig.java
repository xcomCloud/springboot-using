package com.xue.study.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedisConfig {
    private Integer database;
    private String host;
    private Integer port;
    private String password;
    private Integer timeout;
    private Pool pool;

    @Data
    public static class Pool{
        private Integer maxActive;
        private Integer minIdle;
        private Integer maxIdle;
        private Integer maxWait;
    }
}
