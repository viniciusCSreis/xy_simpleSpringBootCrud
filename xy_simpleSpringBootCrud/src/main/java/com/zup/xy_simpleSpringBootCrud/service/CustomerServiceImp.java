package com.zup.xy_simpleSpringBootCrud.service;

import com.zup.xy_simpleSpringBootCrud.model.City;
import com.zup.xy_simpleSpringBootCrud.model.Customer;
import com.zup.xy_simpleSpringBootCrud.repository.CustomerRepository;
import com.zup.xy_simpleSpringBootCrud.util.FieldException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImp implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CityService cityService;

    @Override
    public Page<Customer> findAll(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    @Override
    public Page<Customer> searchByName(Pageable pageable, String name) {
        return customerRepository.findByNameIgnoreCaseContaining(pageable,name);
    }

    @Override
    public Page<Customer> searchByCityId(Pageable pageable, Long cityId) {

        City city = cityService.findOne(cityId);
        return customerRepository.findByCity(pageable,city);
    }

    @Override
    public Customer findOne(long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.orElse(null);

    }

    @Override
    public Customer create(Customer customer) {
        customer.setId(0);
        customer = loadCityInCustomer(customer);
        return customerRepository.saveAndFlush(customer);
    }

    @Override
    public Customer update(Customer customer) {


        Optional<Customer> customerById = customerRepository.findById(customer.getId());
        if(!customerById.isPresent()){
            throw new FieldException("Customer Id not fould");
        }
        customer = loadCityInCustomer(customer);
        return customerRepository.saveAndFlush(customer);
    }

    private Customer loadCityInCustomer(Customer customer){
        City city = cityService.findOne(customer.getCity().getId());
        if(city == null)throw new FieldException("Customer city not fould");
        customer.setCity(city);
        return customer;
    }


    @Override
    public void delete(long id) {
        customerRepository.deleteById(id);
    }
}
