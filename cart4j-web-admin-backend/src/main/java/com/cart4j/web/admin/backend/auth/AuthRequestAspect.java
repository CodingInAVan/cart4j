package com.cart4j.web.admin.backend.auth;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Aspect
@Component
public class AuthRequestAspect {
    @Value("${resource.id}")
    private String resourceId;

    @Value("${resource.secret}")
    private String resourceSecret;

    @Around("@annotation(com.cart4j.web.admin.backend.auth.AuthToken)")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes())
                .getRequest();
        HttpSession session = request.getSession();
        String token = (String) session.getAttribute("token");


    }
}
