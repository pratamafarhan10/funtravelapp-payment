package com.funtravelapp.payment.service.role;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RoleService {
    private final Map<String, Boolean> customerAndSeller = generateCustAndSeller();
    private final Map<String, Boolean> customer = generateCust();
    private final Map<String, Boolean> seller = generateSeller();

    private Map<String, Boolean> generateCustAndSeller(){
        Map<String, Boolean> users = new HashMap<>();
        users.put("customer", true);
        users.put("seller", true);
        return users;
    }

    private Map<String, Boolean> generateCust(){
        Map<String, Boolean> users = new HashMap<>();
        users.put("customer", true);
        return users;
    }

    private Map<String, Boolean> generateSeller(){
        Map<String, Boolean> users = new HashMap<>();
        users.put("seller", true);
        return users;
    }

    public Map<String, Boolean> getCustomerAndSeller() {
        return customerAndSeller;
    }

    public Map<String, Boolean> getCustomer() {
        return customer;
    }

    public Map<String, Boolean> getSeller() {
        return seller;
    }
}
