package com.company.repository;

import com.company.model.entity.CustomerEntity;
import com.company.model.entity.EmployeeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer> {

    @Transactional(readOnly = true)
    @Query("SELECT c FROM CustomerEntity c WHERE c.id = ?1 AND c.state = true ")
    CustomerEntity findCustomerById(Integer id);

    @Transactional
    @Query("SELECT c FROM CustomerEntity c WHERE c.state = true ")
    Page<CustomerEntity> findAllCustomerWithStateTrue(PageRequest pageable);

    @Transactional
    @Modifying
    @Query("UPDATE CustomerEntity SET firstName = ?1, lastName = ?2, passportSeries = ?3, passportNumber = ?4," +
            "jshshir = ?5, address = ?6, employee = ?7 WHERE id = ?8 AND state = true ")
    void updateCustomer(String firstName, String lastName, String passportSeries, String passportNumber,
                        String jshshir, String address, EmployeeEntity employee, Integer id);

    @Transactional
    @Modifying
    @Query("UPDATE CustomerEntity c SET c.state = false, c.passportNumber = ?1 WHERE c.id = ?2 ")
    void deleteCustomerById(String newPassportNumber, Integer id);
}
