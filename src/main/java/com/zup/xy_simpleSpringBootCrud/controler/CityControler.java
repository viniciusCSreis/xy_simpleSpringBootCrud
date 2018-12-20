package com.zup.xy_simpleSpringBootCrud.controler;

import com.zup.xy_simpleSpringBootCrud.model.City;
import com.zup.xy_simpleSpringBootCrud.model.CustomPage;
import com.zup.xy_simpleSpringBootCrud.service.CityService;
import com.zup.xy_simpleSpringBootCrud.util.CustomPageLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

import javax.validation.Valid;


@RestController
public class CityControler {


    private final CityService cityService;

    @Autowired
    public CityControler(CityService cityService) {
        this.cityService = cityService;
    }


    @GetMapping(path = "cities")
    public CustomPage listAll(Pageable pageable){
        Page<City> page = cityService.findAll(pageable);
        return CustomPageLoader.loadCustomPage(page,"cities");

    }



    @GetMapping(path = "cities/{id}")
    public City searchCityById(@PathVariable Long id){
        return cityService.findOne(id);
    }

    @GetMapping(path = "cities/search/findByNameIgnoreCaseContaining")
    public CustomPage searchCityByName(Pageable pageable,@RequestParam String name){
        Page<City> page =  cityService.searchByName(pageable,name);
        return CustomPageLoader.loadCustomPage(page,"cities");
    }

    @PostMapping(path = "cities")
    @ResponseStatus(HttpStatus.CREATED)
    public City create(@Valid @RequestBody City city){
        return cityService.create(city);
    }

    @DeleteMapping(path = "cities/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id){
        cityService.delete(id);
    }

    @PutMapping(path = "cities/{id}")
    public City update(@PathVariable long id , @Valid @RequestBody City city){
        city.setId(id);
        return cityService.update(city);
    }

    @PostMapping(path = "cities/teste")
    @ResponseStatus(HttpStatus.CREATED)
    public boolean createTeste(){
        return cityService.createTeste();
    }

}
