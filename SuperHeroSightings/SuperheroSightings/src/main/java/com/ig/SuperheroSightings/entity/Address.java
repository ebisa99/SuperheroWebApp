/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ig.SuperheroSightings.entity;

import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 *
 * @author ebisa
 */
public class Address {

    private int addressId;
    @NotBlank(message = "Street must not be empty.")
    @Size(max = 55, message="Street must be less than 55 characters.")
    private String street;
    
    @NotBlank(message = "City must not be empty.")
    @Size(max = 30, message="City must be less than 30 characters.")
    private String city;
    
    @NotBlank(message = "State must not be empty.")
    @Size(min = 2, max = 2, message="State must be 2 characters.")
    private String state;
    
    @NotBlank(message = "Zip Code must not be empty.")
    @Size(max = 5, min = 5, message="Zip Code must be 5 characters.")
    private String zipcode;
    
    @NotBlank(message = "Country must not be empty.")
    @Size(max = 30, message="Country must be less than 30 characters.")
    private String country;

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
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

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.addressId;
        hash = 37 * hash + Objects.hashCode(this.street);
        hash = 37 * hash + Objects.hashCode(this.city);
        hash = 37 * hash + Objects.hashCode(this.state);
        hash = 37 * hash + Objects.hashCode(this.zipcode);
        hash = 37 * hash + Objects.hashCode(this.country);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Address other = (Address) obj;
        if (this.addressId != other.addressId) {
            return false;
        }
        if (!Objects.equals(this.street, other.street)) {
            return false;
        }
        if (!Objects.equals(this.city, other.city)) {
            return false;
        }
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        if (!Objects.equals(this.zipcode, other.zipcode)) {
            return false;
        }
        if (!Objects.equals(this.country, other.country)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Address{" + "addressId=" + addressId + ", street=" + street + ", city=" + city + ", state=" + state + ", zipcode=" + zipcode + ", country=" + country + '}';
    }
}
