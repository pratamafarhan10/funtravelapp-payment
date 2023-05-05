package com.funtravelapp.payment.aop;

import com.funtravelapp.payment.ext.token.GetTokenAPI;
import com.funtravelapp.payment.ext.token.dto.GetTokenResponse;
import com.funtravelapp.payment.user.User;
import com.funtravelapp.payment.validator.string.StringValidator;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Aspect
@Component
public class AuthenticationAspect {
    @Autowired
    GetTokenAPI getTokenAPI;
    @Autowired
    StringValidator stringValidator;

    @Pointcut("execution(* com.funtravelapp.payment.account.service.AccountService.*(..))")
    private void accountServiceAuth(){}

    @Pointcut("execution(* com.funtravelapp.payment.transaction.service.TransactionService.*(..))")
    private void transactionServiceAuth(){}

    @Around("accountServiceAuth() || transactionServiceAuth()")
    public Object checkToken(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();

        if (args.length < 3){
            return pjp.proceed();
        }

        String authHeader = (String) args[0];
        boolean isValid = stringValidator.setStr(authHeader).isValid();
        System.out.println(authHeader);
        if (!isValid) {
            throw new Exception("Unauthorized, invalid token");
        }

        GetTokenResponse user = getTokenAPI.getToken(authHeader);
        Map<String, Boolean> allowedUser = (Map<String, Boolean>) args[1];

        if (!allowedUser.get(user.getData().getRole().toLowerCase())){
            throw new Exception("Unauthorized, user not allowed!");
        }

        args[2] = user.getData();

        return pjp.proceed(args);
    }
}
