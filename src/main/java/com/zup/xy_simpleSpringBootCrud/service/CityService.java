package com.zup.xy_simpleSpringBootCrud.service;

import com.zup.xy_simpleSpringBootCrud.model.City;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

public interface CityService {

    Page<City> findAll(Pageable pageable);
    Page<City> searchByName(Pageable pageable,String name);

    City findOne(long id);
    City create(City city);
    boolean createTeste();
    City update(City city);
    void delete(long id);


}
