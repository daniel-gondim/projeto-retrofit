package com.ads.atividadehttpretrofit2.model;

import java.io.Serializable;

public class Fabricante implements Serializable {
    private Long id;
    private String name;

    public Fabricante(String name) {
        this.name = name;
    }

    public Fabricante() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return id + " - " + name ;
    }
}
