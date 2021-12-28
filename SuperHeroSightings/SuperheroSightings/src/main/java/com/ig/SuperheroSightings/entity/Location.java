/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ig.SuperheroSightings.entity;

import java.util.Objects;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author ebisa
 */
public class Location {

    private int locationId;
    @NotBlank(message = "Location Name must not be empty.")
    @Size(max = 45, message = "Location Name must be less than 45 characters.")
    private String locationName;
    
    @NotBlank(message = "Location Description must not be empty.")
    @Size(max = 200, message = "Location Description must be less than 200 characters.")
    private String locationDescription;
    
    @NotNull(message = "please provide a longitude.")
    @DecimalMax(value = "180", message = "longitude must be less than or equal to 180") 
    @DecimalMin(value = "-180", message = "longitude must be greater or equal to -180")
    private Double longitude;
    
    @NotNull(message = "please provide a latitude.")
    @DecimalMax(value = "90", message = "latitude must be less than or equal to 90") 
    @DecimalMin(value = "-90", message = "latitude must be greater than or equal to -90")
    private Double latitude;
    
    private Address address;

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationDescription() {
        return locationDescription;
    }

    public void setLocationDescription(String locationDescription) {
        this.locationDescription = locationDescription;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + this.locationId;
        hash = 83 * hash + Objects.hashCode(this.locationName);
        hash = 83 * hash + Objects.hashCode(this.locationDescription);
        hash = 83 * hash + (int) (Double.doubleToLongBits(this.longitude) ^ (Double.doubleToLongBits(this.longitude) >>> 32));
        hash = 83 * hash + (int) (Double.doubleToLongBits(this.latitude) ^ (Double.doubleToLongBits(this.latitude) >>> 32));
        hash = 83 * hash + Objects.hashCode(this.address);
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
        final Location other = (Location) obj;
        if (this.locationId != other.locationId) {
            return false;
        }
        if (Double.doubleToLongBits(this.longitude) != Double.doubleToLongBits(other.longitude)) {
            return false;
        }
        if (Double.doubleToLongBits(this.latitude) != Double.doubleToLongBits(other.latitude)) {
            return false;
        }
        if (!Objects.equals(this.locationName, other.locationName)) {
            return false;
        }
        if (!Objects.equals(this.locationDescription, other.locationDescription)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Location{" + "locationId=" + locationId + ", locationName=" + locationName + ", locationDescription=" + locationDescription + ", longitude=" + longitude + ", latitude=" + latitude + ", address=" + address + '}';
    }

}
