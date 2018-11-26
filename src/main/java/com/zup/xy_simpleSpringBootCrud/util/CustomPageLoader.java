package com.zup.xy_simpleSpringBootCrud.util;

import com.zup.xy_simpleSpringBootCrud.model.CustomPage;
import org.springframework.data.domain.Page;

import java.util.HashMap;

import static java.lang.String.valueOf;

public class CustomPageLoader {

    private CustomPageLoader(){}

    public static CustomPage loadCustomPage(Page<?> page, String name){
        CustomPage customPage = new CustomPage();
        HashMap<String,Object> _embedded= new HashMap<>();
        HashMap<String,Integer> pageData= new HashMap<>();

        _embedded.put(name,page.getContent());
        customPage.set_embedded(_embedded);


        pageData.put("size",page.getSize());
        pageData.put("totalElements",(int)page.getTotalElements());
        pageData.put("totalPages",page.getTotalPages());
        pageData.put("number",page.getNumber());
        customPage.setPage(pageData);

        return customPage;
    }
}
