package com.zup.xy_simpleSpringBootCrud.util;

import com.zup.xy_simpleSpringBootCrud.model.City;
import com.zup.xy_simpleSpringBootCrud.model.CustomPage;
import com.zup.xy_simpleSpringBootCrud.model.Customer;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CustomPageLoaderTest {


    @Test
    public void customPageLoaderTest(){



        City city = new City(1,"Uberlândia");

        List<Customer> customers = new ArrayList<>();

        customers.add(new Customer("Vinicius",city));
        customers.add(new Customer("Vitor",city));

        Page<Customer> page = new PageImpl<>(customers);

        CustomPage customPage = CustomPageLoader.loadCustomPage(page,"customers");

        assertThat(customPage.get_embedded().isEmpty(), is(false));
        assertThat(customPage.get_embedded().size(), is(1));
        assertThat(customPage.get_embedded().get("customers"),is(page.getContent()));

        assertThat(customPage.getPage().isEmpty(), is(false));
        assertThat(customPage.getPage().get("number"),is(page.getNumber()));
        assertThat(customPage.getPage().get("size"),is(page.getSize()));
        assertThat(customPage.getPage().get("totalPages"),is(page.getTotalPages()));
        assertThat(customPage.getPage().get("totalElements"),is((int)page.getTotalElements()));




    }

}
