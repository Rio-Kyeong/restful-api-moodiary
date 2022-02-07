package com.rest.moodiary.logger;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    @Pointcut("execution(* com.rest.moodiary.controller..*(..))")
    public void allController(){}

    @Pointcut("execution(* com.rest.moodiary.service..*(..))")
    public void allService(){}

    @Pointcut("execution(* com.rest.moodiary.repository..*(..))")
    public void allRepository(){}

    @Pointcut("allController() || allService() || allRepository()")
    public void all(){}
}
