package com.zup.xy_simpleSpringBootCrud.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Customer {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank
    private String name;

    @ManyToOne
    private City city;

    public Customer() {
    }

    public Customer(@NotBlank String name, City city) {
        this.name = name;
        this.city = city;
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

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
