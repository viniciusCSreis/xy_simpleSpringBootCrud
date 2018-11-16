package com.zup.xy_simpleSpringBootCrud.service;


import com.zup.xy_simpleSpringBootCrud.model.City;
import com.zup.xy_simpleSpringBootCrud.model.Customer;
import com.zup.xy_simpleSpringBootCrud.repository.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;


import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CustomerServiceTest {

    @Mock
    CustomerRepository customerRepository;

    @Mock
    CityService cityService;

    @InjectMocks
    CustomerServiceImp customerServiceImp;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateCustomer(){

        Customer customer = new Customer("Vinicius",new City(1,"Uberlândia"));
        Customer customerCreated = new Customer("Vinicius",new City(1,"Uberlândia"));
        customerCreated.setId(1);
        when(customerRepository.saveAndFlush(customer)).thenReturn(customerCreated);
        when(cityService.findOne(customer.getCity().getId())).thenReturn(customer.getCity());

        Customer result = customerServiceImp.create(customer);

        assertThat(result,notNullValue());
        assertThat(result.getId(), is(customerCreated.getId()));
        assertThat(result.getName(), is(customerCreated.getName()));
        assertThat(result.getCity(),is(customerCreated.getCity()));
        verify(customerRepository,atLeast(1)).saveAndFlush(customer);



    }

    @Test
    public void testFindAll(){

        List<Customer> customers = new ArrayList<>();

        customers.add(new Customer("Vinicius",new City(1,"Uberlândia")));
        customers.add(new Customer("Gabriel",new City(1,"Uberlândia")));
        customers.add(new Customer("Bruno",new City(1,"Uberaba")));
        customers.add(new Customer("Murilo",new City(1,"Uberlândia")));

        PageRequest pageRequest = new PageRequest(0, 20);

        Page<Customer> page = new PageImpl<>(customers);
        when(customerRepository.findAll(pageRequest)).thenReturn(page);

        Page<Customer> pageResult = customerServiceImp.findAll(pageRequest);

        List<Customer> result = pageResult.getContent();

        assertThat(result.size(),is(4));
        assertThat(result.get(0),is(customers.get(0)));
        assertThat(result.get(1),is(customers.get(1)));
        assertThat(result.get(2),is(customers.get(2)));
        assertThat(result.get(3),is(customers.get(3)));

    }
    @Test
    public void testSearchByName(){

        String name = "vi";

        List<Customer> customers = new ArrayList<>();

        customers.add(new Customer("Vinicius",new City(1,"Uberlândia")));
        customers.add(new Customer("Vitor",new City(1,"Uberlândia")));
        PageRequest pageRequest = new PageRequest(0, 20);

        Page<Customer> page = new PageImpl<>(customers);
        when(customerRepository.findByNameIgnoreCaseContaining(pageRequest,name)).thenReturn(page);

        Page<Customer> pageResult = customerServiceImp.searchByName(pageRequest,name);

        List<Customer> result = pageResult.getContent();

        assertThat(result.size(),is(2));
        assertThat(result.get(0),is(customers.get(0)));
        assertThat(result.get(1),is(customers.get(1)));

        verify(customerRepository,atLeast(1)).findByNameIgnoreCaseContaining(pageRequest,name);

    }
    @Test
    public void testSearchByCityId(){

        long cityId = 1L;

        City city = new City(cityId,"Uberlândia");

        List<Customer> customers = new ArrayList<>();

        customers.add(new Customer("Vinicius",city));
        customers.add(new Customer("Vitor",city));
        PageRequest pageRequest = new PageRequest(0, 20);

        Page<Customer> page = new PageImpl<>(customers);
        when(customerRepository.findByCity(pageRequest,city)).thenReturn(page);
        when(cityService.findOne(cityId)).thenReturn(city);

        Page<Customer> pageResult = customerServiceImp.searchByCityId(pageRequest,cityId);

        List<Customer> result = pageResult.getContent();

        assertThat(result.size(),is(2));
        assertThat(result.get(0),is(customers.get(0)));
        assertThat(result.get(1),is(customers.get(1)));

        verify(customerRepository,atLeast(1)).findByCity(pageRequest,city);
        verify(cityService,atLeast(1)).findOne(cityId);


    }
    @Test
    public void testFindOne(){
        Optional<Customer> customer = Optional.of(new Customer("Vinicius",new City(1,"Uberlândia")));
        when(customerRepository.findById(1L)).thenReturn(customer);

        Customer result = customerServiceImp.findOne(1L);

        verify(customerRepository,atLeast(1)).findById(1L);
        assertThat(result,notNullValue());
        assertThat(result.getId(), is(customer.get().getId()));
        assertThat(result.getName(), is(customer.get().getName()));
        assertThat(result.getCity(),is(customer.get().getCity()));

    }
    @Test
    public void testUpdate(){

        Customer customer = new Customer("Vinicius",new City(1,"Uberlândia"));
        customer.setId(1);
        Optional<Customer> resultCity = Optional.of(customer);
        when(customerRepository.saveAndFlush(customer)).thenReturn(customer);
        when(customerRepository.findById(customer.getId())).thenReturn(resultCity);
        when(cityService.findOne(customer.getCity().getId())).thenReturn(customer.getCity());
        Customer result = customerServiceImp.update(customer);

        assertThat(result,notNullValue());
        assertThat(result.getId(), is(customer.getId()));
        assertThat(result.getName(), is(customer.getName()));
        assertThat(result.getCity(),is(customer.getCity()));
        verify(customerRepository,atLeast(1)).saveAndFlush(customer);
    }

    @Test
    public void testDelete(){

        long id = 1;
        customerServiceImp.delete(id);
        verify(customerRepository,atLeast(1)).deleteById(id);
    }


    @Test
    public void testFindOneNotExist()
    {

        Optional<Customer> city = Optional.empty();
        when(customerRepository.findById(1L)).thenReturn(city);

        Customer result = customerServiceImp.findOne(1L);
        verify(customerRepository,atLeast(1)).findById(1L);
        assertThat(result,is(nullValue()));

    }



}
