package com.funtravelapp.payment.dto.account;

import com.funtravelapp.payment.validator.ValidatorInterface;
import com.funtravelapp.payment.validator.number.NumberValidator;
import com.funtravelapp.payment.validator.string.StringValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateAccountValidator implements ValidatorInterface {
    @Autowired
    StringValidator stringValidator;
    @Autowired
    NumberValidator numberValidator;
    private CreateAccountRequest request;

    public CreateAccountRequest getRequest() {
        return request;
    }

    @Autowired(required = false)
    public CreateAccountValidator setRequest(CreateAccountRequest request) {
        this.request = request;

        return this;
    }

    @Override
    public boolean isValid() {
        return numValidator(request.getUserId()) && stringValidator(request.getBank()) && stringValidator(request.getNumber()) && stringValidator(request.getName()) && stringValidator(request.getType());
    }

    private boolean numValidator(Integer userId){
        return numberValidator.setNum(userId).isValid();
    }

    private boolean stringValidator(String userId){
        return stringValidator.setStr(userId).isValid();
    }
}
