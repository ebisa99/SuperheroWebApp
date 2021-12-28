/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ig.SuperheroSightings.entity;

import java.awt.Image;
import java.sql.Blob;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 *
 * @author ebisa
 */
public class Superhero {

    private int heroId;
    
    @NotBlank(message = "Hero Name must not be empty.")
    @Size(max = 45, message="Hero Name must be less than 45 characters.")
    private String heroName;
    
    @NotBlank(message = "Hero Description must not be empty.")
    @Size(max = 45, message="Hero Description must be less than 45 characters.")
    private String heroDescription;
    
    private boolean isHero;
    private String imageFile;
    private SuperPower superPower;

    public int getHeroId() {
        return heroId;
    }

    public void setHeroId(int heroId) {
        this.heroId = heroId;
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    public String getHeroDescription() {
        return heroDescription;
    }

    public void setHeroDescription(String heroDescription) {
        this.heroDescription = heroDescription;
    }

    public boolean isIsHero() {
        return isHero;
    }

    public void setIsHero(boolean isHero) {
        this.isHero = isHero;
    }

    public SuperPower getSuperPower() {
        return superPower;
    }

    public void setSuperPower(SuperPower superPower) {
        this.superPower = superPower;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.heroId;
        hash = 17 * hash + Objects.hashCode(this.heroName);
        hash = 17 * hash + Objects.hashCode(this.heroDescription);
        hash = 17 * hash + (this.isHero ? 1 : 0);
        hash = 17 * hash + Objects.hashCode(this.superPower);
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
        final Superhero other = (Superhero) obj;
        if (this.heroId != other.heroId) {
            return false;
        }
        if (this.isHero != other.isHero) {
            return false;
        }
        if (!Objects.equals(this.heroName, other.heroName)) {
            return false;
        }
        if (!Objects.equals(this.heroDescription, other.heroDescription)) {
            return false;
        }
        if (!Objects.equals(this.superPower, other.superPower)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Superhero{" + "heroId=" + heroId + ", heroName=" + heroName + ", heroDescription=" + heroDescription + ", isHero=" + isHero + ", superPower=" + superPower + '}';
    }

}
