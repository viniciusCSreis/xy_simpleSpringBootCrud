package com.zup.xy_simpleSpringBootCrud.controler;

import com.zup.xy_simpleSpringBootCrud.model.City;
import com.zup.xy_simpleSpringBootCrud.model.CustomPage;
import com.zup.xy_simpleSpringBootCrud.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

import javax.validation.Valid;
import java.util.HashMap;

import static java.lang.String.valueOf;

@RestController
public class CityControler {


    @Autowired
    private CityService cityService;


    @GetMapping(path = "cities")
    public CustomPage listAll(Pageable pageable){
        Page<City> page = cityService.findAll(pageable);

        CustomPage customPage = new CustomPage();
        HashMap<String,Object> _embedded= new HashMap<>();
        HashMap<String,String> pageData= new HashMap<>();

        _embedded.put("cities",page.getContent());
        customPage.set_embedded(_embedded);


        pageData.put("size",valueOf(page.getSize()));
        pageData.put("totalElements",valueOf(page.getTotalElements()));
        pageData.put("totalPages",valueOf(page.getTotalPages()));
        pageData.put("number",valueOf(page.getNumber()));
        customPage.setPage(pageData);

        return customPage;

    }

    @GetMapping(path = "cities/{id}")
    public City searchCityById(@PathVariable Long id){
        return cityService.findOne(id);
    }

    @GetMapping(path = "cities/search/findByNameIgnoreCaseContaining")
    public Page<City> searchCityByName(Pageable pageable,@RequestParam String name){
        return cityService.searchByName(pageable,name);
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



}
