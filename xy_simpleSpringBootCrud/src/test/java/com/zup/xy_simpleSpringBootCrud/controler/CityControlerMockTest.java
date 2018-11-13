package com.zup.xy_simpleSpringBootCrud.controler;

import com.zup.xy_simpleSpringBootCrud.model.City;
import com.zup.xy_simpleSpringBootCrud.service.CityService;
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

import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@WebMvcTest(CityControler.class)
public class CityControlerMockTest {


    @MockBean
    CityService cityService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testCreate() throws Exception {
        String jsonContent = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("payload/createCity.json"));
        City cityCreated = new City(1,"Uberlândia");

        Mockito.when(cityService.create(notNull())).thenReturn(cityCreated);


            mockMvc.perform(post("/cities").contentType(MediaType.APPLICATION_JSON)
                    .content(jsonContent)
                    .characterEncoding("utf-8")
            )
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$",Matchers.notNullValue()))
                    .andExpect(jsonPath("$.name",Matchers.is(cityCreated.getName())))
                    .andExpect(jsonPath("$.id",Matchers.is((int)cityCreated.getId())));
            verify(cityService,atLeast(1)).create(notNull());


    }

    @Test
    public void testPut() throws Exception {

        String nameCity = "Uberlândia";
        long id = 1;
        City cityTobeUpdated = new City(id,nameCity);
        String jsonContent = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("payload/createCity.json"));

        Mockito.when(cityService.update(notNull())).thenReturn(cityTobeUpdated);

            mockMvc.perform(put("/cities/"+id).contentType(MediaType.APPLICATION_JSON)
                    .content(jsonContent)
                    .characterEncoding("utf-8")
            )
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$",Matchers.notNullValue()))
                    .andExpect(jsonPath("$.name",Matchers.is(nameCity)))
                    .andExpect(jsonPath("$.id",Matchers.is((int)id)));
            verify(cityService,atLeast(1)).update(notNull());


    }

    @Test
    public void testDelete() throws Exception {

        long id = 1;

            mockMvc.perform(delete("/cities/"+id))
                    .andDo(print())
                    .andExpect(status().isNoContent())
                    .andExpect(jsonPath("$").doesNotExist());



    }

    @Test
    public void testList() throws Exception {


        List<City> cities = new ArrayList<>();

        cities.add(new City(1,"Uberlândia"));
        cities.add(new City(2,"Uberaba"));
        cities.add(new City(3,"São Paulo"));
        cities.add(new City(4,"Belo Horizonte"));

        Page<City> page = new PageImpl<>(cities);

        when(cityService.findAll(notNull())).thenReturn(page);


            mockMvc.perform(get("/cities"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content",Matchers.hasSize(cities.size())))
                    .andExpect(jsonPath("$.content[0].id",Matchers.is((int)cities.get(0).getId())))
                    .andExpect(jsonPath("$.content[0].name",Matchers.is(cities.get(0).getName())))
                    .andExpect(jsonPath("$.content[1].id",Matchers.is((int)cities.get(1).getId())))
                    .andExpect(jsonPath("$.content[1].name",Matchers.is(cities.get(1).getName())))
                    .andExpect(jsonPath("$.content[2].id",Matchers.is((int)cities.get(2).getId())))
                    .andExpect(jsonPath("$.content[2].name",Matchers.is(cities.get(2).getName())))
                    .andExpect(jsonPath("$.content[3].id",Matchers.is((int)cities.get(3).getId())))
                    .andExpect(jsonPath("$.content[3].name",Matchers.is(cities.get(3).getName())))
                    .andExpect(jsonPath("$.numberOfElements",Matchers.is(cities.size())));

            verify(cityService,atLeast(1)).findAll(notNull());


    }







}
