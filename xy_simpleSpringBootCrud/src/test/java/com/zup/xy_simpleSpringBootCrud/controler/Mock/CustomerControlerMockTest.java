package com.zup.xy_simpleSpringBootCrud.controler.Mock;

import com.zup.xy_simpleSpringBootCrud.controler.CustomerControler;
import com.zup.xy_simpleSpringBootCrud.model.City;
import com.zup.xy_simpleSpringBootCrud.model.Customer;
import com.zup.xy_simpleSpringBootCrud.service.CustomerService;
import org.apache.commons.io.IOUtils;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerControler.class)
public class CustomerControlerMockTest {


    @MockBean
    CustomerService customerService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testCreate() throws Exception {
        String jsonContent = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("payload/createCity.json"));
        Customer customerCreated = new Customer("TestCustomer",new City(1,"Uberlândia"));
        customerCreated.setId(1);

        Mockito.when(customerService.create(notNull())).thenReturn(customerCreated);


        mockMvc.perform(post("/customers").contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent)
                .characterEncoding("utf-8")
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", Matchers.notNullValue()))
                .andExpect(jsonPath("$.name",Matchers.is(customerCreated.getName())))
                .andExpect(jsonPath("$.id",Matchers.is((int)customerCreated.getId())))
                .andExpect(jsonPath("$.city.id",Matchers.is((int)customerCreated.getCity().getId())))
                .andExpect(jsonPath("$.city.name",Matchers.is(customerCreated.getCity().getName())));

        verify(customerService,atLeast(1)).create(notNull());


    }

    @Test
    public void testPutName() throws Exception {

        String name = "Clemente";
        long id = 1;
        Customer customer = new Customer(name,new City(1,"Uberlândia"));
        customer.setId(id);
        String jsonContent = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("payload/createCustomer.json"));

        Mockito.when(customerService.update(notNull())).thenReturn(customer);

        mockMvc.perform(put("/customers/"+id).contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent)
                .characterEncoding("utf-8")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",Matchers.notNullValue()))
                .andExpect(jsonPath("$.name",Matchers.is(name)))
                .andExpect(jsonPath("$.id",Matchers.is((int)id)))
                .andExpect(jsonPath("$.city.id",Matchers.is((int)customer.getCity().getId())))
                .andExpect(jsonPath("$.city.name",Matchers.is(customer.getCity().getName())));
        verify(customerService,atLeast(1)).update(notNull());


    }

    @Test
    public void testDelete() throws Exception {

        long id = 1;

        mockMvc.perform(delete("/customers/"+id))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").doesNotExist());



    }

    @Test
    public void testList() throws Exception {


        List<Customer> customers = new ArrayList<>();

        customers.add(new Customer("Vinicius",new City(1,"Uberlândia")));
        customers.add(new Customer("Gabriel",new City(1,"Uberlândia")));
        customers.add(new Customer("Bruno",new City(1,"Uberaba")));
        customers.add(new Customer("Murilo",new City(1,"Uberlândia")));

        Page<Customer> page = new PageImpl<>(customers);

        when(customerService.findAll(notNull())).thenReturn(page);


        mockMvc.perform(get("/customers"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content",Matchers.hasSize(customers.size())))
                .andExpect(jsonPath("$.content[0].id",Matchers.is((int)customers.get(0).getId())))
                .andExpect(jsonPath("$.content[0].name",Matchers.is(customers.get(0).getName())))
                .andExpect(jsonPath("$.content[1].id",Matchers.is((int)customers.get(1).getId())))
                .andExpect(jsonPath("$.content[1].name",Matchers.is(customers.get(1).getName())))
                .andExpect(jsonPath("$.content[2].id",Matchers.is((int)customers.get(2).getId())))
                .andExpect(jsonPath("$.content[2].name",Matchers.is(customers.get(2).getName())))
                .andExpect(jsonPath("$.content[3].id",Matchers.is((int)customers.get(3).getId())))
                .andExpect(jsonPath("$.content[3].name",Matchers.is(customers.get(3).getName())))
                .andExpect(jsonPath("$.numberOfElements",Matchers.is(customers.size())));

        verify(customerService,atLeast(1)).findAll(notNull());


    }


    @Test
    public void testSearchById() throws Exception {

        Customer customer = new Customer("TestCustomer",new City(1,"Uberlândia"));
        customer.setId(1);

        Mockito.when(customerService.findOne(anyLong())).thenReturn(customer);


        mockMvc.perform(get("/customers/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.notNullValue()))
                .andExpect(jsonPath("$.name",Matchers.is(customer.getName())))
                .andExpect(jsonPath("$.id",Matchers.is((int)customer.getId())))
                .andExpect(jsonPath("$.city.id",Matchers.is((int)customer.getCity().getId())))
                .andExpect(jsonPath("$.city.name",Matchers.is(customer.getCity().getName())));

        verify(customerService,atLeast(1)).findOne(anyLong());

    }

    @Test
    public void testSearchByCity() throws Exception {
        List<Customer> customers = new ArrayList<>();

        City city = new City(1,"Uberlândia");
        customers.add(new Customer("Vinicius",city));
        customers.add(new Customer("Gabriel",city));

        Page<Customer> page = new PageImpl<>(customers);

        String paginationArgs = "page=0&size=2&sort=name,desc";

        when(customerService.searchByCityId(notNull(),anyLong())).thenReturn(page);


        mockMvc.perform(get("/customers/search/city/1?"+paginationArgs))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content",Matchers.hasSize(customers.size())))
                .andExpect(jsonPath("$.content[0].id",Matchers.is((int)customers.get(0).getId())))
                .andExpect(jsonPath("$.content[0].name",Matchers.is(customers.get(0).getName())))
                .andExpect(jsonPath("$.content[1].id",Matchers.is((int)customers.get(1).getId())))
                .andExpect(jsonPath("$.content[1].name",Matchers.is(customers.get(1).getName())))
                .andExpect(jsonPath("$.numberOfElements",Matchers.is(customers.size())));

        verify(customerService,atLeast(1)).searchByCityId(notNull(),anyLong());

    }

    @Test
    public void testSearchByName() throws Exception {

        List<Customer> customers = new ArrayList<>();

        City city = new City(1,"Uberlândia");
        customers.add(new Customer("Vinicius",city));
        customers.add(new Customer("Vitor",city));

        Page<Customer> page = new PageImpl<>(customers);

        String paginationArgs = "page=0&size=2&sort=name,desc";

        when(customerService.searchByName(notNull(),anyString())).thenReturn(page);


        mockMvc.perform(get("/customers/search/findByNameIgnoreCaseContaining?name=vi&"+paginationArgs))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content",Matchers.hasSize(customers.size())))
                .andExpect(jsonPath("$.content[0].id",Matchers.is((int)customers.get(0).getId())))
                .andExpect(jsonPath("$.content[0].name",Matchers.is(customers.get(0).getName())))
                .andExpect(jsonPath("$.content[1].id",Matchers.is((int)customers.get(1).getId())))
                .andExpect(jsonPath("$.content[1].name",Matchers.is(customers.get(1).getName())))
                .andExpect(jsonPath("$.numberOfElements",Matchers.is(customers.size())));

        verify(customerService,atLeast(1)).searchByName(notNull(),anyString());

    }



}
