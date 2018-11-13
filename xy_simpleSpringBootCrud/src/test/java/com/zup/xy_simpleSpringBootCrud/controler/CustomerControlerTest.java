package com.zup.xy_simpleSpringBootCrud.controler;

import com.zup.xy_simpleSpringBootCrud.AbstractTest;
import com.zup.xy_simpleSpringBootCrud.model.City;
import com.zup.xy_simpleSpringBootCrud.model.Customer;
import org.apache.commons.io.IOUtils;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControlerTest extends AbstractTest {

    private final String PATH = "/customers";
    private City city1;
    private String customerNameJson = "TestCustomer";

    @Before
    public void createCity(){
        this.city1 = new City("Uberlândia");
        cityRepository.saveAndFlush(city1);
    }

    @Test
    public void testFindAllCustomers() throws Exception {

        saveAll();

        String paginationArgs = "page=0&size=2&sort=name,desc";
        this.mockMvc.perform(get(PATH+"?"+paginationArgs))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content",Matchers.hasSize(2)))
                .andExpect(jsonPath("$.totalElements",Matchers.is(5)));


    }

    @Test
    public void testSearchByName() throws Exception {

        saveAll();
        String paginationArgs = "page=0&size=1&sort=name,desc";
        this.mockMvc.perform(get(PATH+"/search/findByNameIgnoreCaseContaining?name=vi&"+paginationArgs))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content",Matchers.hasSize(1)))
                .andExpect(jsonPath("$.totalElements",Matchers.is(2)));


    }

    @Test
    public void testSearchById() throws Exception {

        Customer customer = saveOneCustomer();

        this.mockMvc.perform(get(PATH+"/"+customer.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", Matchers.is((int)customer.getId())))
                .andExpect(jsonPath("name",Matchers.is(customer.getName())));


    }

    @Test
    public void testCreateCustomer() throws Exception {

        String jsonContent = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("payload/createCustomer.json"));

        this.mockMvc.perform(post(PATH).content(jsonContent).characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id", Matchers.notNullValue()))
                .andExpect(jsonPath("name",Matchers.is(customerNameJson)));


    }

    @Test
    public void testCreateCustomerEmptyName() throws Exception {

        String jsonContent = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("payload/createCustomerEmptyName.json"));


        this.mockMvc.perform(post(PATH).content(jsonContent).characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());


    }

    @Test
    public void testUpdateCustomerName() throws Exception {

        Customer customer = saveOneCustomer();

        String jsonContent = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("payload/createCustomer.json"));

        this.mockMvc.perform(put(PATH+"/"+customer.getId()).content(jsonContent).characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", Matchers.is((int)customer.getId())))
                .andExpect(jsonPath("name",Matchers.is(customerNameJson)));


    }

    @Test
    public void testUpdateCityEmptyName() throws Exception {

        Customer customer = saveOneCustomer();


        String jsonContent = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("payload/createCustomerEmptyName.json"));

        this.mockMvc.perform(put(PATH+"/"+customer.getId()).content(jsonContent).characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());


    }

    @Test
    public void testDeleteCustomer() throws Exception {

        Customer customer = saveOneCustomer();

        this.mockMvc.perform(delete(PATH+"/"+customer.getId()))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").doesNotExist());


    }



    private Customer saveOneCustomer(){
        Customer customer = new Customer("Vinicius",city1);
        return customerRepository.saveAndFlush(customer);
    }


    private void saveAll(){

        Customer customer = new Customer("Vinicius",city1);
        customerRepository.saveAndFlush(customer);

        Customer customer1 = new Customer("Vitor",city1);
        customerRepository.saveAndFlush(customer1);

        Customer customer2 = new Customer("Gabriel",city1);
        customerRepository.saveAndFlush(customer2);

        Customer customer3 = new Customer("Bruno",city1);
        customerRepository.saveAndFlush(customer3);

        Customer customer4 = new Customer("Murilo",city1);
        customerRepository.saveAndFlush(customer4);

    }
}
