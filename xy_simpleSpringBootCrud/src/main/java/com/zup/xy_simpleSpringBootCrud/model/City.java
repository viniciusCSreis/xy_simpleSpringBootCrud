package com.zup.xy_simpleSpringBootCrud.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
public class City {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank
    private String name;


    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    List<Customer> customerList;

    public City(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public City(@NotBlank String name) {
        this.name = name;
    }

    public City() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
