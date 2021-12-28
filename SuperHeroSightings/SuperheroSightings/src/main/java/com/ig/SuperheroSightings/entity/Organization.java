/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ig.SuperheroSightings.entity;

import java.util.List;
import java.util.Objects;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 *
 * @author ebisa
 */
public class Organization {

    private int organizationId;
    @NotBlank(message = "Organization Name must not be empty.")
    @Size(max = 45, message="Organization Name must be less than 45 characters.")
    private String organizationName;
    @NotBlank(message = "Organization Description must not be empty.")
    @Size(max = 200, message="Organization Description must be less than 200 characters.")
    private String organizationDescription;
    private Address address;
    @Size(max = 10, message="phone number must be 10 digits.")
    @Size(min = 10, message="phone number must be 10 digits")
    private String phone;
    @Email
    private String email;
    private List<Superhero> superheros;

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationDescription() {
        return organizationDescription;
    }

    public void setOrganizationDescription(String organizationDescription) {
        this.organizationDescription = organizationDescription;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Superhero> getSuperheros() {
        return superheros;
    }

    public void setSuperheros(List<Superhero> superheros) {
        this.superheros = superheros;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.organizationId;
        hash = 89 * hash + Objects.hashCode(this.organizationName);
        hash = 89 * hash + Objects.hashCode(this.organizationDescription);
        hash = 89 * hash + Objects.hashCode(this.address);
        hash = 89 * hash + Objects.hashCode(this.phone);
        hash = 89 * hash + Objects.hashCode(this.email);
        hash = 89 * hash + Objects.hashCode(this.superheros);
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
        final Organization other = (Organization) obj;
        if (this.organizationId != other.organizationId) {
            return false;
        }
        if (!Objects.equals(this.organizationName, other.organizationName)) {
            return false;
        }
        if (!Objects.equals(this.organizationDescription, other.organizationDescription)) {
            return false;
        }
        if (!Objects.equals(this.phone, other.phone)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (!Objects.equals(this.superheros, other.superheros)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Organization{" + "organizationId=" + organizationId + ", organizationName=" + organizationName + ", organizationDescription=" + organizationDescription + ", address=" + address + ", phone=" + phone + ", email=" + email + ", superheros=" + superheros + '}';
    }

}
