package com.company.service.impl;

import com.company.mapper.CustomerMapper;
import com.company.model.dto.CustomerDto;
import com.company.model.dto.ResponseDto;
import com.company.model.entity.CustomerEntity;
import com.company.model.entity.EmployeeEntity;
import com.company.model.form.CustomerForm;
import com.company.repository.CustomerRepository;
import com.company.repository.EmployeeRepository;
import com.company.service.CustomerService;
import com.company.service.base.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final BaseService baseService;
    private final EmployeeRepository employeeRepository;


    @Override
    public ResponseEntity<?> addCustomer(CustomerForm customerForm) {

        ResponseDto<?> response;
        CustomerEntity customer;
        try {

            customer = customerMapper.CUSTOMER_ENTITY(customerForm);

            EmployeeEntity employee = employeeRepository.findEmployee(customerForm.getEmployeeId())
                    .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

            customer.setEmployee(employee);
            customer = customerRepository.save(customer);
            log.info("Customer created successfully");

            CustomerDto dto = customerMapper.CUSTOMER_DTO(customer);
            response = baseService.convertResponseDto(dto, "success", true, 200);
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Customer creation failed, Item not found");
            response = baseService.convertResponseDto(null, "Item not found", false, 500);
            return ResponseEntity.status(500).body(response);
        }
    }

    @Override
    public ResponseEntity<?> getCustomer(Integer id) {

        ResponseDto<?> response;

        response = baseService.convertResponseDto(customerMapper.CUSTOMER_DTO(
                customerRepository.findCustomerById(id)), "success", true, 200
        );

        return ResponseEntity.status(200).body(response);
    }

    @Override
    public ResponseEntity<?> listCustomer(Integer page, Integer size) {

        ResponseDto<?> response;
        Page<CustomerEntity> page1;

        try {
            PageRequest pageable = PageRequest.of(page, size);
            page1 = customerRepository.findAllCustomerWithStateTrue(pageable);

            List<CustomerDto> customDtos = page1.getContent().stream()
                    .map(customerMapper::CUSTOMER_DTO)
                    .collect(Collectors.toList());

            log.info("Retrieved page {} of size {} from all customer.", page, size);
            response = baseService.convertResponseDto(customDtos, "success", true, 200);
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Error retrieving all customers");
            response = baseService.convertResponseDto(null, "Interval Server Error", false, 500);
            return ResponseEntity.status(500).body(response);
        }
    }

    @Override
    public ResponseEntity<?> updateCustomer(Integer id, CustomerForm customForm) {

        ResponseDto<?> response;

        try {
            EmployeeEntity employee = employeeRepository.findEmployee(customForm.getEmployeeId())
                    .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

            customerRepository.updateCustomer(customForm.getFirstName(), customForm.getLastName(), customForm.getPassportSeries(),
                    customForm.getPassportNumber(), customForm.getJshshir(), customForm.getAddress(), employee, id);

            log.info("Customer updated successfully");
            response = baseService.convertResponseDto(null, "Customer updated successfully", true, 200);
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Customer updated failed, Item not found");
            response = baseService.convertResponseDto(null, "Item not found", false, 500);
            return ResponseEntity.status(500).body(response);
        }
    }

    @Override
    public ResponseEntity<?> deleteCustomer(Integer id) {

        ResponseDto<?> response;

        try {
            String newPassportNumber = baseService.updateState(customerRepository.findCustomerById(id).getPassportNumber());

            customerRepository.deleteCustomerById(newPassportNumber, id);
            response = baseService.convertResponseDto(null, "Customer deleted successfully", true, 200);
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Something went wrong while deleting customer {}", id);
            response = baseService.convertResponseDto(null, "Something went wrong while deleting customer", false, 500);
            return ResponseEntity.status(500).body(response);
        }
    }
}
