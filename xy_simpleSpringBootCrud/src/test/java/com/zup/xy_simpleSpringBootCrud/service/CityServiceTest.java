package com.zup.xy_simpleSpringBootCrud.service;

import com.zup.xy_simpleSpringBootCrud.model.City;
import com.zup.xy_simpleSpringBootCrud.repository.CityRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.BaseMatcher.*;
import static org.mockito.ArgumentMatchers.isNotNull;
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

        cities.add(new City(1,"Vinicius"));
        cities.add(new City(2,"Gabriel"));
        cities.add(new City(3,"Bruno"));
        cities.add(new City(4,"Murilo"));

        when(cityRepository.findAll()).thenReturn(cities);

        List<City> result = cityService.findAll();

        assertThat(result,is(notNullValue()));
        assertThat(result.size(), is(4));
        assertThat(result.get(0),is(cities.get(0)));
        assertThat(result.get(1),is(cities.get(1)));
        assertThat(result.get(2),is(cities.get(2)));
        assertThat(result.get(3),is(cities.get(3)));

    }

}
