package com.example.exercicio.entities;

import jakarta.persistence.*;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private StreetInfo streetInfo;

    private String city;
    private String state;

    // Outros campos e métodos getters/setters


    public Address() {
    }

    public Address(Long id, StreetInfo streetInfo, String city, String state) {
        this.id = id;
        this.streetInfo = streetInfo;
        this.city = city;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StreetInfo getStreetInfo() {
        return streetInfo;
    }

    public void setStreetInfo(StreetInfo streetInfo) {
        this.streetInfo = streetInfo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
