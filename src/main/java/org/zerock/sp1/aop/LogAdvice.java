package org.zerock.sp1.aop;


import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Log4j2
@Component
public class LogAdvice {

    @Before("execution(* org.zerock.sp1.service.*.*(..))")
    public void printLog(JoinPoint joinPoint){

        Object[] params = joinPoint.getArgs();

        log.info("------------------------");
        log.info("------------------------");
        log.info(Arrays.toString(params));
        log.info("------------------------");
        log.info("------------------------");

    }
}
