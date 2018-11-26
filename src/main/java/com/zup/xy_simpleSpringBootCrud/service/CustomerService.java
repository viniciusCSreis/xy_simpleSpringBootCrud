package com.zup.xy_simpleSpringBootCrud.service;

import com.zup.xy_simpleSpringBootCrud.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {

    Page<Customer> findAll(Pageable pageable);
    Page<Customer> searchByName(Pageable pageable,String name);
    Page<Customer> searchByCityId(Pageable pageable,Long cityId);

    Customer findOne(long id);
    Customer create(Customer customer);
    Customer update(Customer customer);
    void delete(long id);
}
