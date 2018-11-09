package com.zup.xy_simpleSpringBootCrud.service;

import com.zup.xy_simpleSpringBootCrud.model.City;
import com.zup.xy_simpleSpringBootCrud.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CityServiceImp implements CityService {

    @Autowired
    private CityRepository cityRepository;


    @Override
    public List<City> findAll() {
        return cityRepository.findAll();
    }

    @Override
    public City findOne(long id) {
        return null;
    }

    @Override
    public City create(City city) {
        return null;
    }

    @Override
    public City update(City city) {
        return null;
    }

    @Override
    public City delete(long id) {
        return null;
    }
}
