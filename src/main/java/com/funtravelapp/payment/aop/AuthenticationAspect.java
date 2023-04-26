package com.funtravelapp.payment.aop;

import com.funtravelapp.payment.ext.token.GetTokenAPI;
import com.funtravelapp.payment.ext.token.dto.GetTokenResponse;
import com.funtravelapp.payment.validator.string.StringValidator;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthenticationAspect {
    @Autowired
    GetTokenAPI getTokenAPI;
    @Autowired
    StringValidator stringValidator;

    @Pointcut("execution(* com.funtravelapp.payment.service.account.AccountService(..))")
    private void accountServiceAuth(){}

    @Pointcut("execution(* com.funtravelapp.payment.service.transaction.TransactionService(..))")
    private void transactionServiceAuth(){}

    @Around("accountServiceAuth() || transactionServiceAuth()")
    public Object checkToken(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();

        String authHeader = (String) args[0];
        boolean isValid = stringValidator.setStr(authHeader).validate();
        if (!isValid) {
            throw new Exception("Unauthorized");
        }

        GetTokenResponse user = getTokenAPI.getToken(authHeader);

        args[1] = user.getData();

        return pjp.proceed(args);
    }
}
