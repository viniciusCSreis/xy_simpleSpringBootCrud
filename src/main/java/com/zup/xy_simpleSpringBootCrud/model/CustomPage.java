package com.zup.xy_simpleSpringBootCrud.model;

import java.util.HashMap;
import java.util.Map;

public class CustomPage {

    private HashMap<String,Object> _embedded;
    private HashMap<String,Integer> page;

    public Map<String, Object> get_embedded() {
        return _embedded;
    }

    public void set_embedded(Map<String, Object> _embedded) {
        this._embedded = (HashMap<String, Object>) _embedded;
    }

    public Map<String, Integer> getPage() {
        return page;
    }

    public void setPage(Map<String, Integer> page) {
        this.page =(HashMap<String, Integer>) page;
    }
}
