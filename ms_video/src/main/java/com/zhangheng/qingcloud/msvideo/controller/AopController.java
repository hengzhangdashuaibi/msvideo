package com.zhangheng.qingcloud.msvideo.controller;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author zhangheng
 * @date 2019/7/16 16:59
 * 切面
 */
@Aspect//在类上使用 @Aspect 注解 使之成为切面类
@Component//在类上使用 @Component 注解 把切面类加入到IOC容器中
public class AopController {

    private final Logger logger = LoggerFactory.getLogger(AopController.class);

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    /**
     * 定义一个切点
     */
    @Pointcut(value = "execution(public String hello (..))")
    public void cutOffPoint(){

    }

    //前置通知
    //在切入点开始处切入
    @Before("cutOffPoint()")
    public void beforeTest(){
        logger.info("在HelloController中的hello方法前调用....");
    }

    //后置通知
    //在切入点后开始执行
    @After("cutOffPoint()")
    public void afterTest(){
        logger.info("在HelloController中的hello方法后调用....");
    }

    //环绕通知  ---适合做全局异常处理和日志处理
    @Around("cutOffPoint()")
    public Object doAround(ProceedingJoinPoint pjp){
        startTime.set(System.currentTimeMillis());
        logger.info("我是环绕通知执行");
        Object obj;
        try{
            obj = pjp.proceed();
            logger.info("执行返回值 : " + obj);
            logger.info(pjp.getSignature().getName()+"方法执行耗时: " + (System.currentTimeMillis() - startTime.get()));
        } catch (Throwable throwable) {
            obj=throwable.toString();
        }
        return obj;
    }

    /**
     * 执行完请求可以做的
     * @param result
     * @throws Throwable
     * 切入点返回结果之后执行
     */
    @AfterReturning(returning = "result", pointcut = "cutOffPoint()")
    public void doAfterReturning(Object result) throws Throwable {
        logger.info("大家好，我是@AfterReturning，他们都秀完了，该我上场了");
    }

    /**
     * 在切入点报错异常的时候执行
     * @param e
     */
    // 声明错误e时指定的抛错类型法必会抛出指定类型的异常
    // 此处将e的类型声明为Throwable，对抛出的异常不加限制
    @AfterThrowing(throwing = "e",pointcut = "cutOffPoint()")
    public void doAfterReturning(Throwable e) {
        logger.info("大家好，我是@AfterThrowing，他们犯的错误，我来背锅");
        logger.info("错误信息"+e.getMessage());
    }
}
