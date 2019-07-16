package com.zhangheng.qingcloud.msvideo.service.Impl;

import com.zhangheng.qingcloud.msvideo.service.HelloService;
import com.zhangheng.qingcloud.msvideo.util.ExecutorConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author zhangheng
 * @date 2019/7/16 15:22
 */
@Service
public class HelloServiceImpl implements HelloService {


    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);

    @Override
    @Async("asyncServiceExecutor")
    public void testExcutor() {

        logger.info("测试线程");

        try{
            logger.info("........");
            //休眠一秒
            Thread.sleep(1000);
        }catch (Exception e){
            e.getMessage();
        }
    }
}
