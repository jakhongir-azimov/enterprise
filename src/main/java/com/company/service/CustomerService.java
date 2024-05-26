package com.company.service;

import com.company.model.form.CustomerForm;
import org.springframework.http.ResponseEntity;

public interface CustomerService {
    ResponseEntity<?> addCustomer(CustomerForm customerForm);

    ResponseEntity<?> getCustomer(Integer id);

    ResponseEntity<?> listCustomer(Integer page, Integer size);

    ResponseEntity<?> updateCustomer(Integer id, CustomerForm customerForm);

    ResponseEntity<?> deleteCustomer(Integer id);
}
