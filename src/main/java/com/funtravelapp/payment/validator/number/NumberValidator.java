package com.funtravelapp.payment.validator.number;

import com.funtravelapp.payment.validator.ValidatorInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NumberValidator implements ValidatorInterface {
    private Integer num;

    @Override
    public boolean isValid() {
        return num != null && !num.equals(0);
    }

    public Integer getNum() {
        return num;
    }

    @Autowired(required = false)
    public NumberValidator setNum(Integer num) {
        this.num = num;
        return this;
    }
}
