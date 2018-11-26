package com.zup.xy_simpleSpringBootCrud.controler;

import com.zup.xy_simpleSpringBootCrud.model.CustomPage;
import com.zup.xy_simpleSpringBootCrud.model.Customer;
import com.zup.xy_simpleSpringBootCrud.service.CustomerService;
import com.zup.xy_simpleSpringBootCrud.util.CustomPageLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class CustomerControler {



    private final CustomerService customerService;

    private String jsonName="customers";

    @Autowired
    public CustomerControler(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping(path = "customers")
    @ResponseStatus(HttpStatus.CREATED)
    public Customer create(@Valid @RequestBody Customer customer){
        return customerService.create(customer);
    }

    @GetMapping(path = "customers")
    public CustomPage list(Pageable pageable){

        Page<Customer> page = customerService.findAll(pageable);
        return CustomPageLoader.loadCustomPage(page,jsonName);
    }

    @DeleteMapping(path = "customers/{id}" )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id){
        customerService.delete(id);
    }

    @PutMapping(path = "customers/{id}")
    public Customer edit(@PathVariable long id, @Valid @RequestBody Customer customer){
        customer.setId(id);
        return customerService.update(customer);
    }

    @GetMapping(path = "customers/{id}")
    public Customer searchById(@PathVariable long id){
        return customerService.findOne(id);
    }

    @GetMapping(path = "customers/search/city/{id}")
    public CustomPage searchByCity(Pageable pageable,@PathVariable long id){
        Page<Customer> page =  customerService.searchByCityId(pageable,id);
        return CustomPageLoader.loadCustomPage(page,jsonName);
    }


    @GetMapping(path = "customers/search/findByNameIgnoreCaseContaining")
    public CustomPage searchByName(Pageable pageable, @RequestParam String name){
        Page<Customer> page = customerService.searchByName(pageable,name);
        return CustomPageLoader.loadCustomPage(page,jsonName);
    }




}
