package com.example.demo.model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

/**
 * Instead of <code>@Entity</code>, this component POJO is marked with <code>@Embeddable</code>. It
 * has no identifier property.
 * 
 * No tiene ID porque no está representado en la base de datos, son propiedades que se suman a las
 * que posea la clase a la cual este POJO se "une" o "embeba". En nuestro caso la clase User
 */
@Embeddable
public class Address {

    @NotNull // Ignored for DDL generation!
    @Column(nullable = false) // Used for DDL generation!
    private String street;

    @NotNull
    @AttributeOverride(
            name = "name",  //nombre de la propiedad de la clase City, la cambiamos a CITY
            column = @Column(name = "CITY", nullable = false)
    )
    private City city;

    /**
     * Hibernate will call this no-argument constructor to create an instance, and then
     * populate the fields directly.
     */
    public Address() {
    }

    /**
     * You can have additional (public) constructors for convenience.
     */
    public Address(String street, City city) {
        this.street = street;
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}