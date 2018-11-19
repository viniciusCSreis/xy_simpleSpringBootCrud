package com.zup.xy_simpleSpringBootCrud.model;

import java.util.HashMap;

public class CustomPage {

    private HashMap<String,Object> _embedded;
    private HashMap<String,String> page;

    public void set_embedded(HashMap<String, Object> _embedded) {
        this._embedded = _embedded;
    }

    public void setPage(HashMap<String, String> page) {
        this.page = page;
    }

    public HashMap<String, Object> get_embedded() {
        return _embedded;
    }

    public HashMap<String, String> getPage() {
        return page;
    }
}
