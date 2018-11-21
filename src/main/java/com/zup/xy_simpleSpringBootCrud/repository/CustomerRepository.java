package com.zup.xy_simpleSpringBootCrud.repository;

import com.zup.xy_simpleSpringBootCrud.model.City;
import com.zup.xy_simpleSpringBootCrud.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Page<Customer> findByNameIgnoreCaseContaining(Pageable pageable, String name);
    Page<Customer> findByCity(Pageable pageable, City city);
}
