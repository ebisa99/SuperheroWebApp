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
public class SuperPower {

    private int superPowerId;
    
    @NotBlank(message = "SuperPower Name must not be empty.")
    @Size(max = 45, message="SuperPower Name must be less than 45 characters.")
    private String superPowerName;
    
    @NotBlank(message = "SuperPower Description must not be empty.")
    @Size(max = 200, message="SuperPower Description must be less than 200 characters.")
    private String superPowerDescription;

    public int getSuperPowerId() {
        return superPowerId;
    }

    public void setSuperPowerId(int superPowerId) {
        this.superPowerId = superPowerId;
    }

    public String getSuperPowerName() {
        return superPowerName;
    }

    public void setSuperPowerName(String superPowerName) {
        this.superPowerName = superPowerName;
    }

    public String getSuperPowerDescription() {
        return superPowerDescription;
    }

    public void setSuperPowerDescription(String superPowerDescription) {
        this.superPowerDescription = superPowerDescription;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + this.superPowerId;
        hash = 79 * hash + Objects.hashCode(this.superPowerName);
        hash = 79 * hash + Objects.hashCode(this.superPowerDescription);
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
        final SuperPower other = (SuperPower) obj;
        if (this.superPowerId != other.superPowerId) {
            return false;
        }
        if (!Objects.equals(this.superPowerName, other.superPowerName)) {
            return false;
        }
        if (!Objects.equals(this.superPowerDescription, other.superPowerDescription)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SuperPower{" + "superPowerId=" + superPowerId + ", superPowerName=" + superPowerName + ", superPowerDescription=" + superPowerDescription + '}';
    }

}
