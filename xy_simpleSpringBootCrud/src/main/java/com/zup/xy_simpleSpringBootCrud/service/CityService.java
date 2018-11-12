package com.zup.xy_simpleSpringBootCrud.service;

import com.zup.xy_simpleSpringBootCrud.model.City;

import java.util.List;

public interface CityService {

    List<City> findAll();
    City findOne(long id);
    City create(City city);
    City update(City city);
    void delete(long id);

}
