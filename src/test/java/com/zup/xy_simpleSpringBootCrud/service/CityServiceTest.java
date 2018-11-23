package com.zup.xy_simpleSpringBootCrud.service;

import com.zup.xy_simpleSpringBootCrud.model.City;
import com.zup.xy_simpleSpringBootCrud.repository.CityRepository;
import com.zup.xy_simpleSpringBootCrud.util.FieldException;
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

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class CityServiceTest {

    @InjectMocks
    private CityServiceImp cityService;

    @Mock
    private CityRepository cityRepository;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAll()
    {
        List<City> cities = new ArrayList<>();

        cities.add(new City(1,"Uberlândia"));
        cities.add(new City(2,"Uberaba"));
        cities.add(new City(3,"São Paulo"));
        cities.add(new City(4,"Belo Horizonte"));

        PageRequest pageRequest = PageRequest.of(0, 20);

        Page<City> page = new PageImpl<>(cities);
        when(cityRepository.findAll(pageRequest)).thenReturn(page);

        Page<City> pageResult = cityService.findAll(pageRequest);

        List<City> result = pageResult.getContent();

        assertThat(result.size(),is(4));
        assertThat(result.get(0),is(cities.get(0)));
        assertThat(result.get(1),is(cities.get(1)));
        assertThat(result.get(2),is(cities.get(2)));
        assertThat(result.get(3),is(cities.get(3)));

        verify(cityRepository,atLeast(1)).findAll(pageRequest);


    }

    @Test
    public void testFindOneExist()
    {

        Optional<City> city = Optional.of(new City(1,"Uberlândia"));
        when(cityRepository.findById(1L)).thenReturn(city);

        City result = cityService.findOne(1L);

        verify(cityRepository,atLeast(1)).findById(1L);
        assertThat(result,is(notNullValue()));
        assertThat(result.getId(), is(city.get().getId()));
        assertThat(result.getName(),is(city.get().getName()));

    }
    @Test
    public void testFindOneNotExist()
    {

        Optional<City> city = Optional.empty();
        when(cityRepository.findById(1L)).thenReturn(city);

        City result = cityService.findOne(1L);
        verify(cityRepository,atLeast(1)).findById(1L);
        assertThat(result,is(nullValue()));

    }

    @Test
    public void testCreate()
    {
        City city = new City(1,"Uberlândia");
        when(cityRepository.saveAndFlush(city)).thenReturn(city);

        City result = cityService.create(city);

        verify(cityRepository,atLeast(1)).saveAndFlush(city);
        assertThat(result,is(notNullValue()));
        assertThat(result.getId(),is(city.getId()));
        assertThat(result.getName(),is(city.getName()));




    }

    @Test
    public void testCreateEmptyname()
    {
        City city = new City(1,"");
        when(cityRepository.saveAndFlush(city)).thenReturn(city);

        City result = cityService.create(city);

        verify(cityRepository,atLeast(0)).saveAndFlush(city);
        assertThat(result,is(notNullValue()));
        assertThat(result.getId(),is(city.getId()));
        assertThat(result.getName(),is(city.getName()));




    }

    @Test
    public void testDelete()
    {
        long id = 1;

        cityService.delete(id);

        verify(cityRepository,atLeast(1)).deleteById(id);


    }

    @Test(expected = FieldException.class)
    public void testUpdateCityIdNotFound()
    {
        City city = new City(1,"Uberlândia");
        Optional<City> cityResult = Optional.empty();
        when(cityRepository.findById(city.getId())).thenReturn(cityResult);

        cityService.update(city);

        verify(cityRepository,atLeast(0)).saveAndFlush(city);
        verify(cityRepository,atLeast(1)).findById(city.getId());

    }

    @Test
    public void testUpdate()
    {
        City city = new City(1,"Uberlândia");

        Optional<City> resultCity = Optional.of(city);
        when(cityRepository.saveAndFlush(city)).thenReturn(city);
        when(cityRepository.findById(city.getId())).thenReturn(resultCity);

        City result = cityService.update(city);

        verify(cityRepository,atLeast(1)).saveAndFlush(city);
        assertThat(result,is(notNullValue()));
        assertThat(result.getId(),is(city.getId()));
        assertThat(result.getName(),is(city.getName()));

    }

    @Test
    public void testSearchByName()
    {

        String name = "ube";

        List<City> cities = new ArrayList<>();

        cities.add(new City(1,"Uberlândia"));
        cities.add(new City(2,"Uberaba"));

        PageRequest pageRequest = PageRequest.of(0, 20);

        Page<City> page = new PageImpl<>(cities);
        when(cityRepository.findByNameIgnoreCaseContaining(pageRequest,name)).thenReturn(page);

        Page<City> pageResult = cityService.searchByName(pageRequest,name);

        List<City> result = pageResult.getContent();

        assertThat(result.size(),is(2));
        assertThat(result.get(0),is(cities.get(0)));
        assertThat(result.get(1),is(cities.get(1)));

        verify(cityRepository,atLeast(1)).findByNameIgnoreCaseContaining(pageRequest,name);

    }


}
