package com.zhangheng.zipkin.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import zipkin2.server.internal.EnableZipkinServer;


/**
 * @author zhangheng
 * @date 2019/8/15 16:41
 */
@SpringBootApplication
@EnableZipkinServer
@EnableEurekaClient
public class ZipkinServerApplication implements CommandLineRunner {

    private final static Logger logger= LoggerFactory.getLogger(ZipkinServerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ZipkinServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("=========hello World ELK========");
    }
}
