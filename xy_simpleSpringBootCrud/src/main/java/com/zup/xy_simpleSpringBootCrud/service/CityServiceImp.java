package com.zup.xy_simpleSpringBootCrud.service;

import com.zup.xy_simpleSpringBootCrud.model.City;
import com.zup.xy_simpleSpringBootCrud.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityServiceImp implements CityService {

    @Autowired
    private CityRepository cityRepository;


    @Override
    public List<City> findAll() {
        return cityRepository.findAll();
    }

    @Override
    public City findOne(long id) {

        Optional<City> city = cityRepository.findById(id);
        if(city.isPresent())
            return city.get();
        return null;

    }

    @Override
    public City create(City city) {
        city.setId(0);
        return cityRepository.saveAndFlush(city);
    }

    @Override
    public City update(City city) {
        return cityRepository.saveAndFlush(city);
    }

    @Override
    public void delete(long id) {
        cityRepository.deleteById(id);
    }
}
