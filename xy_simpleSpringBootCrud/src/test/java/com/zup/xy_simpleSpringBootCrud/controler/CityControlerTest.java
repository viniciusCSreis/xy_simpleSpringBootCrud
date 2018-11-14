package com.zup.xy_simpleSpringBootCrud.controler;

import com.zup.xy_simpleSpringBootCrud.AbstractTest;
import com.zup.xy_simpleSpringBootCrud.model.City;
import net.minidev.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CityControlerTest extends AbstractTest {

    private final String PATH = "/cities";

    @Test
    public void testFindAllCity() throws Exception {

        saveAll();

        String paginationArgs = "page=0&size=2&sort=name,desc";
        this.mockMvc.perform(get(PATH+"?"+paginationArgs))
                .andDo(print())
                .andExpect(status().isOk());


    }

    @Test
    public void testSearchByName() throws Exception {

        saveAll();
        String paginationArgs = "page=0&size=2&sort=name,desc";
        this.mockMvc.perform(get(PATH+"/search/findByNameIgnoreCaseContaining?name=ube&"+paginationArgs))
                .andDo(print())
                .andExpect(status().isOk());


    }

    @Test
    public void testSearchById() throws Exception {

        City city = saveOneCity();

        this.mockMvc.perform(get(PATH+"/"+city.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id",Matchers.is((int)city.getId())))
                .andExpect(jsonPath("name",Matchers.is(city.getName())));


    }

    @Test
    public void testCreateCity() throws Exception {

        String jsonContent = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("payload/createCity.json"));

        this.mockMvc.perform(post(PATH).content(jsonContent).characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id", Matchers.notNullValue()))
                .andExpect(jsonPath("name",Matchers.is("Uberlândia")));


    }

    @Test
    public void testCreateCityEmptyName() throws Exception {

        Map<String, String> data = new HashMap<>();
        data.put("name", "");

        String jsonContent = JSONObject.toJSONString(data);


        this.mockMvc.perform(post(PATH).content(jsonContent).characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());


    }

    @Test
    public void testUpdateCity() throws Exception {

        City city = saveOneCity();

        String jsonContent = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("payload/createCity.json"));

        this.mockMvc.perform(put(PATH+"/"+city.getId()).content(jsonContent).characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", Matchers.is((int)city.getId())))
                .andExpect(jsonPath("name",Matchers.is("Uberlândia")));


    }

    @Test
    public void testUpdateCityEmptyName() throws Exception {

        City city = saveOneCity();

        Map<String, String> data = new HashMap<>();
        data.put("name", "");

        String jsonContent = JSONObject.toJSONString(data);

        this.mockMvc.perform(put(PATH+"/"+city.getId()).content(jsonContent).characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());


    }

    @Test
    public void testUpdateCityWrongId() throws Exception {

        City city = saveOneCity();


        String jsonContent = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("payload/createCity.json"));

        this.mockMvc.perform(put(PATH+"/"+city.getId()+1000).content(jsonContent).characterEncoding("utf-8").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());


    }

    @Test
    public void testDeleteCity() throws Exception {

        City city = saveOneCity();

        this.mockMvc.perform(delete(PATH+"/"+city.getId()))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").doesNotExist());


    }



    private City saveOneCity(){
        City city = new City("Uberaba");
        return cityRepository.saveAndFlush(city);
    }


    private void saveAll(){
        City city1 = new City("Uberlândia");
        cityRepository.saveAndFlush(city1);

        City city2 = new City("Uberaba");
        cityRepository.saveAndFlush(city2);

        City city3 = new City("Araguari");
        cityRepository.saveAndFlush(city3);
    }


}
