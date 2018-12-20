package com.zup.xy_simpleSpringBootCrud.service;

import com.zup.xy_simpleSpringBootCrud.model.City;
import com.zup.xy_simpleSpringBootCrud.modelLocal.Teste;
import com.zup.xy_simpleSpringBootCrud.repository.CityRepository;
import com.zup.xy_simpleSpringBootCrud.repositoryLocal.testeLocalRepository;
import com.zup.xy_simpleSpringBootCrud.util.FieldException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
public class CityServiceImp implements CityService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private testeLocalRepository testeLocalRepository;


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
        return city.orElse(null);

    }

    @Override
    public City create(City city) {
        city.setId(0);
        return cityRepository.saveAndFlush(city);
    }

    @Override
    public boolean createTeste() {
        List<City> cityList = cityRepository.findAll();
        cityList.forEach(city -> {
            Teste teste = new Teste();
            teste.setName(city.getId()+":"+city.getName());
            testeLocalRepository.save(teste);
        });
        return true;

    }

    @Override
    public City update(City city) {

        Optional<City> cityById = cityRepository.findById(city.getId());
        if(!cityById.isPresent()){
            throw new FieldException("City Id not found");
        }

        return cityRepository.saveAndFlush(city);
    }

    @Override
    public void delete(long id) {
        cityRepository.deleteById(id);
    }
}
