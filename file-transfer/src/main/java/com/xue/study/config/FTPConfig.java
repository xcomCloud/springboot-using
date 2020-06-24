package com.xue.study.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ftp")
@PropertySource("classpath:config/ftp.properties")
public class FTPConfig {
    private String host;
    private Integer port;
    private String username;
    private String password;
//    private String workdir;
    private String encoding;
    private Integer maxtotal;
    private Integer minidle;
    private Integer maxidle;
    private Integer maxwaitmillis;
    private String root;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public String getWorkdir() {
//        return workdir;
//    }
//
//    public void setWorkdir(String workdir) {
//        this.workdir = workdir;
//    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public Integer getMaxtotal() {
        return maxtotal;
    }

    public void setMaxtotal(Integer maxtotal) {
        this.maxtotal = maxtotal;
    }

    public Integer getMinidle() {
        return minidle;
    }

    public void setMinidle(Integer minidle) {
        this.minidle = minidle;
    }

    public Integer getMaxidle() {
        return maxidle;
    }

    public void setMaxidle(Integer maxidle) {
        this.maxidle = maxidle;
    }

    public Integer getMaxwaitmillis() {
        return maxwaitmillis;
    }

    public void setMaxwaitmillis(Integer maxwaitmillis) {
        this.maxwaitmillis = maxwaitmillis;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }
}
