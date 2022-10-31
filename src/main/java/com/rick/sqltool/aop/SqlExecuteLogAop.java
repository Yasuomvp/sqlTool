package com.rick.sqltool.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class SqlExecuteLogAop {


//    @Pointcut("execution(* com.rick.sqltool.service.SqlService.executeSql())")
//    public void sqlPointCut(){}
//
//    @Before("sqlPointCut()")
//    public void beforeSqlPointCut(JoinPoint joinPoint){
//        joinPoint.getArgs();
//    }
}
