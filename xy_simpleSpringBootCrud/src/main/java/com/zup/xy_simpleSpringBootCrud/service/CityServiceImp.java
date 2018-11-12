package com.zup.xy_simpleSpringBootCrud.service;

import com.zup.xy_simpleSpringBootCrud.model.City;
import com.zup.xy_simpleSpringBootCrud.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.Optional;

@Service
public class CityServiceImp implements CityService {

    @Autowired
    private CityRepository cityRepository;


    @Override
    public Page<City> findAll(Pageable pageable) {
        return cityRepository.findAll(pageable);
    }

    @Override
    public Page<City> searchByName(Pageable pageable,String name) {
        return cityRepository.findByNameIgnoreCaseContaining(pageable,name);
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
