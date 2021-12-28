/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ig.SuperheroSightings.entity;

import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

/**
 *
 * @author ebisa
 */
public class Sightings {

    private int sightingId;
    
    @NotNull(message = "Please provide a date.")
    @Past(message = "sighting must be in the past")
    private LocalDate date;
    
    private Location location;
    private Superhero superhero;

    public int getSightingId() {
        return sightingId;
    }

    public void setSightingId(int sightingId) {
        this.sightingId = sightingId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Superhero getSuperhero() {
        return superhero;
    }

    public void setSuperhero(Superhero superhero) {
        this.superhero = superhero;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + this.sightingId;
        hash = 83 * hash + Objects.hashCode(this.date);
        hash = 83 * hash + Objects.hashCode(this.location);
        hash = 83 * hash + Objects.hashCode(this.superhero);
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
        final Sightings other = (Sightings) obj;
        if (this.sightingId != other.sightingId) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        if (!Objects.equals(this.superhero, other.superhero)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Sightings{" + "sightingId=" + sightingId + ", date=" + date + ", location=" + location + ", superhero=" + superhero + '}';
    }

}
