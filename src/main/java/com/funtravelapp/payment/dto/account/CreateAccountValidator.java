package com.funtravelapp.payment.dto.account;

import com.funtravelapp.payment.validator.ValidatorInterface;
import com.funtravelapp.payment.validator.number.NumberValidator;
import com.funtravelapp.payment.validator.string.StringValidator;
import org.springframework.stereotype.Component;

@Component
public class CreateAccountValidator implements ValidatorInterface {
    private CreateAccountRequest request;
    public CreateAccountValidator(CreateAccountRequest request){
        this.request = request;
    }

    public CreateAccountRequest getRequest() {
        return request;
    }

    public CreateAccountValidator setRequest(CreateAccountRequest request) {
        this.request = request;

        return this;
    }

    @Override
    public boolean validate() {
        return numValidator(request.getUserId()) && stringValidator(request.getBank()) && stringValidator(request.getNumber()) && stringValidator(request.getName()) && stringValidator(request.getType());
    }

    private boolean numValidator(Integer userId){
        NumberValidator numVal = new NumberValidator(userId);

        return numVal.validate();
    }

    private boolean stringValidator(String userId){
        StringValidator strVal = new StringValidator(userId);

        return strVal.validate();
    }
}
