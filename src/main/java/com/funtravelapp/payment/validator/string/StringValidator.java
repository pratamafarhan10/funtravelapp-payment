package com.funtravelapp.payment.validator.string;

import com.funtravelapp.payment.validator.ValidatorInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StringValidator implements ValidatorInterface {
    private String str;

    @Override
    public boolean isValid() {
        return this.str != null && !this.str.isEmpty() && !this.str.isBlank();
    }

    public String getStr() {
        return str;
    }

    public StringValidator setStr(String str) {
        this.str = str;
        return this;
    }
}
