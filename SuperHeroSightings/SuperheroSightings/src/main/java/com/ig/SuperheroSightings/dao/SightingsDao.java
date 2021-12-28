/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ig.SuperheroSightings.dao;

import com.ig.SuperheroSightings.entity.*;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author ebisa
 */
public interface SightingsDao {

    Sightings addSighting(Sightings sightings);

    Sightings getSightingById(int sightingId);

    List<Sightings> getAllSightings();

    void updateSighting(Sightings sightings);

    void deleteSighting(int sightingId);

    List<Sightings> getAllSightingsByDate(LocalDate date);

    List<Sightings> getAllSightingsByHero(int heroId);

    List<Sightings> getAllSightingsByLocation(int locationId);

    public List<Sightings> getTheLatest10Sightings();
}
