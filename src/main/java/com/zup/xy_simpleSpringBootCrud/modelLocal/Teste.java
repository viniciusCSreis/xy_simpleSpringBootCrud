package com.zup.xy_simpleSpringBootCrud.modelLocal;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class Teste {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank
    private String name;


    public Teste(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Teste(@NotBlank String name) {
        this.name = name;
    }

    public Teste() {
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
