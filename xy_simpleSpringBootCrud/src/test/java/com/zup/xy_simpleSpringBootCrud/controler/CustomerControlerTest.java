package com.zup.xy_simpleSpringBootCrud.controler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zup.xy_simpleSpringBootCrud.AbstractTest;
import com.zup.xy_simpleSpringBootCrud.model.City;
import com.zup.xy_simpleSpringBootCrud.model.Customer;
import net.minidev.json.JSONObject;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.valueOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControlerTest extends AbstractTest {

    private final String PATH = "/customers";
    private City city1;
    private City city2;

    @Before
    public void createCity(){
        this.city1 = new City("Uberlândia");
        this.city1 = cityRepository.saveAndFlush(city1);
        this.city2 = new City("Uberaba");
        this.city2 =cityRepository.saveAndFlush(city2);
    }

    @Test
    public void testFindAllCustomers() throws Exception {

        saveAll();

        String paginationArgs = "page=0&size=2&sort=name,desc";
        this.mockMvc.perform(get(PATH+"?"+paginationArgs))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.customers",Matchers.hasSize(2)))
                .andExpect(jsonPath("$.page.totalElements",Matchers.greaterThanOrEqualTo(5)));


    }

    @Test
    public void testSearchCustomerByName() throws Exception {

        saveAll();
        String paginationArgs = "page=0&size=1&sort=name,desc";
        this.mockMvc.perform(get(PATH+"/search/findByNameIgnoreCaseContaining?name=vi&"+paginationArgs))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.customers",Matchers.hasSize(1)))
                .andExpect(jsonPath("$.page.totalElements",Matchers.is(2)));


    }

    @Test
    public void testSearchCustomerById() throws Exception {

        Customer customer = saveOneCustomer();

        this.mockMvc.perform(get(PATH+"/"+customer.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", Matchers.is((int)customer.getId())))
                .andExpect(jsonPath("name",Matchers.is(customer.getName())));


    }

    @Test
    public void testSearchCustomerByCity() throws Exception {

        Customer customer = saveOneCustomer();
        saveAll();

        String paginationArgs = "page=0&size=3&sort=name,desc";
        this.mockMvc.perform(get(PATH+"/search/city/"+customer.getCity().getId()+"?"+paginationArgs))
                .andDo(print())
                .andExpect(jsonPath("$._embedded.customers",Matchers.hasSize(3)))
                .andExpect(jsonPath("$.page.totalElements",Matchers.greaterThanOrEqualTo(5)));

    }

    @Test
    public void testCreateCustomer() throws Exception {


        String customerNameJson = "TestCustomer";

        Map<String, Object> data = new HashMap<>();
        Map<String, String> dataCity = new HashMap<>();
        dataCity.put("id",valueOf(city1.getId()));
        data.put("name",customerNameJson);
        data.put("city",dataCity);

        String jsonContent = JSONObject.toJSONString(data);
        this.mockMvc.perform(post(PATH).content(jsonContent).characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id", Matchers.notNullValue()))
                .andExpect(jsonPath("name",Matchers.is(customerNameJson)))
                .andExpect(jsonPath("city.id",Matchers.is((int)city1.getId())))
                .andExpect(jsonPath("city.name",Matchers.is(city1.getName())));


    }

    @Test
    public void testCreateCustomerWrongCity() throws Exception {


        String customerNameJson = "TestCustomer";

        Map<String, Object> data = new HashMap<>();
        Map<String, String> dataCity = new HashMap<>();
        dataCity.put("id",valueOf(city1.getId()*1000));
        data.put("name",customerNameJson);
        data.put("city",dataCity);

        String jsonContent = JSONObject.toJSONString(data);
        this.mockMvc.perform(post(PATH).content(jsonContent).characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    public void testCreateCustomerEmptyName() throws Exception {


        String customerNameJson = "";

        Map<String, Object> data = new HashMap<>();
        Map<String, String> dataCity = new HashMap<>();
        dataCity.put("id",valueOf(city1.getId()));
        data.put("name",customerNameJson);
        data.put("city",dataCity);

        String jsonContent = JSONObject.toJSONString(data);

        this.mockMvc.perform(post(PATH).content(jsonContent).characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());


    }

    @Test
    public void testCreateCustomerEmptyCity() throws Exception {


        String customerNameJson = "TestCustomer";

        Map<String, Object> data = new HashMap<>();
        data.put("name",customerNameJson);

        String jsonContent = JSONObject.toJSONString(data);

        this.mockMvc.perform(post(PATH).content(jsonContent).characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());


    }
    @Test
    public void testCreateCustomerEmptyCityAndName() throws Exception {


        String customerNameJson = "";

        Map<String, Object> data = new HashMap<>();
        data.put("name",customerNameJson);

        String jsonContent = JSONObject.toJSONString(data);

        this.mockMvc.perform(post(PATH).content(jsonContent).characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());


    }

    @Test
    public void testUpdateCustomerCityAndName() throws Exception {

        Customer customer = saveOneCustomer();

        String customerNameJson = "TestCustomer";

        Map<String, Object> data = new HashMap<>();
        Map<String, String> dataCity = new HashMap<>();
        dataCity.put("id",valueOf(city2.getId()));
        data.put("name",customerNameJson);
        data.put("city",dataCity);

        String jsonContent = JSONObject.toJSONString(data);

        this.mockMvc.perform(put(PATH+"/"+customer.getId()).content(jsonContent).characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", Matchers.is((int)customer.getId())))
                .andExpect(jsonPath("name",Matchers.is(customerNameJson)))
                .andExpect(jsonPath("city.id",Matchers.is((int)city2.getId())))
                .andExpect(jsonPath("city.name",Matchers.is(city2.getName())));


    }

    @Test
    public void testUpdateCustomerWrongCity() throws Exception {

        Customer customer = saveOneCustomer();
        String customerNameJson = "TestCustomer";

        Map<String, Object> data = new HashMap<>();
        Map<String, String> dataCity = new HashMap<>();

        dataCity.put("id",valueOf(city2.getId()*1000));

        data.put("name",customerNameJson);
        data.put("city",dataCity);

        String jsonContent = JSONObject.toJSONString(data);

        this.mockMvc.perform(put(PATH+"/"+customer.getId()).content(jsonContent).characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

    }


    @Test
    public void testUpdateCustomerEmptyName() throws Exception {

        Customer customer = saveOneCustomer();
        String customerNameJson = "";

        Map<String, Object> data = new HashMap<>();
        Map<String, String> dataCity = new HashMap<>();

        dataCity.put("id",valueOf(city2.getId()*1000));

        data.put("name",customerNameJson);
        data.put("city",dataCity);

        String jsonContent = JSONObject.toJSONString(data);

        this.mockMvc.perform(put(PATH+"/"+customer.getId()).content(jsonContent).characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());


    }

    @Test
    public void testUpdateCustomerWrongId() throws Exception {

        Customer customer = saveOneCustomer();

        String customerNameJson = "TestCustomer";

        Map<String, Object> data = new HashMap<>();
        Map<String, String> dataCity = new HashMap<>();

        dataCity.put("id",valueOf(city2.getId()));

        data.put("name",customerNameJson);
        data.put("city",dataCity);

        String jsonContent = JSONObject.toJSONString(data);

        this.mockMvc.perform(put(PATH+"/"+customer.getId()+1000).content(jsonContent).characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());


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
