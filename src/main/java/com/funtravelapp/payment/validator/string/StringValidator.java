package com.funtravelapp.payment.validator.string;

import com.funtravelapp.payment.validator.ValidatorInterface;
import org.springframework.stereotype.Component;

@Component
public class StringValidator implements ValidatorInterface {

    private String str;

    public StringValidator(String str){
        this.str = str;
    }

    @Override
    public boolean validate() {
        return this.str != null && this.str.isEmpty() && this.str.isBlank();
    }

    public String getStr() {
        return str;
    }

    public StringValidator setStr(String str) {
        this.str = str;
        return this;
    }
}
