package com.zup.xy_simpleSpringBootCrud.controler;

import com.zup.xy_simpleSpringBootCrud.model.Customer;
import com.zup.xy_simpleSpringBootCrud.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.util.List;

@RestController
public class CustomerControler {


    @Autowired
    CustomerRepository customerRepository;

    @PostMapping(path = "customers")
    public Customer teste(@RequestBody Customer customer){
        return customerRepository.save(customer);
    }

    @GetMapping(path = "customers")
    public List<Customer> testeList(){
        return customerRepository.findAll();
    }


}
