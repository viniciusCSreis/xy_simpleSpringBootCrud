package com.zup.xy_simpleSpringBootCrud.controler;

import com.zup.xy_simpleSpringBootCrud.model.Customer;
import com.zup.xy_simpleSpringBootCrud.repository.CustomerRepository;
import com.zup.xy_simpleSpringBootCrud.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;

@RestController
public class CustomerControler {


    @Autowired

    CustomerService customerService;

    @PostMapping(path = "customers")
    @ResponseStatus(HttpStatus.CREATED)
    public Customer teste(@RequestBody Customer customer){
        return customerService.create(customer);
    }

    @GetMapping(path = "customers")
    public Page<Customer> testeList(Pageable pageable){
        return customerService.findAll(pageable);
    }

    @DeleteMapping(path = "customers/{id}" )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id){
        customerService.delete(id);
    }

    @PutMapping(path = "customers/{id}")
    public Customer edit(@PathVariable long id, @RequestBody Customer customer){
        return customerService.update(customer);
    }

    @GetMapping(path = "customers/{id}")
    public Customer searchById(@PathVariable long id){
        return customerService.findOne(id);
    }

    @GetMapping(path = "customers/search/findByNameIgnoreCaseContaining")
    public Page<Customer> searchByName(Pageable pageable,@RequestParam String name){
        return customerService.searchByName(pageable,name);
    }

}
