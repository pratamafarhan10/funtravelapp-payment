package com.funtravelapp.payment.validator.number;

import com.funtravelapp.payment.validator.ValidatorInterface;
import org.springframework.stereotype.Component;

public class NumberValidator implements ValidatorInterface {
    private final Integer num;

    public NumberValidator(Integer num){
        this.num = num;
    }
    @Override
    public boolean validate() {
        return num != null && num.equals(0);
    }
}
