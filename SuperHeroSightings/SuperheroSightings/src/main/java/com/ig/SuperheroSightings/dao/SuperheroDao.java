/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ig.SuperheroSightings.dao;

import com.ig.SuperheroSightings.entity.*;
import java.util.List;

/**
 *
 * @author ebisa
 */
public interface SuperheroDao {

    Superhero addSuperhero(Superhero hero);

    List<Superhero> getAllSuperhero();

    Superhero getSuperheroById(int heroId);

    void updateSuperhero(Superhero hero);

    void deleteSuperhero(int heroId);

    //List<Superhero> getAllSuperherosByLocation(int locationId);
}
