package com.funtravelapp.payment.account.validator;

import com.funtravelapp.payment.account.dto.CreateAccountRequest;
import com.funtravelapp.payment.validator.ValidatorInterface;
import com.funtravelapp.payment.validator.number.NumberValidator;
import com.funtravelapp.payment.validator.string.StringValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateAccountValidator implements ValidatorInterface {
    @Autowired
    StringValidator stringValidator;
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
        return stringValidator(request.getBank()) && stringValidator(request.getNumber()) && stringValidator(request.getName()) && stringValidator(request.getType());
    }

    private boolean stringValidator(String userId){
        return stringValidator.setStr(userId).isValid();
    }
}
