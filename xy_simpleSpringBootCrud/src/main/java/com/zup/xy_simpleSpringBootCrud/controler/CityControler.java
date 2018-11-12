package com.zup.xy_simpleSpringBootCrud.controler;

import com.zup.xy_simpleSpringBootCrud.model.City;
import com.zup.xy_simpleSpringBootCrud.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CityControler {


    @Autowired
    private CityService cityService;


    @GetMapping(path = "cities")
    public List<City> listAll(){
        return cityService.findAll();
    }

    @PostMapping(path = "cities")
    public City create(@RequestBody City city){

        return cityService.create(city);

    }

    @DeleteMapping(path = "cities/{id}")
    public void delete(@PathVariable long id){
        cityService.delete(id);
    }

    @PutMapping(path = "cities/{id}")
    public City update(@PathVariable long id , @RequestBody City city){
        city.setId(id);
        return cityService.update(city);
    }

}
