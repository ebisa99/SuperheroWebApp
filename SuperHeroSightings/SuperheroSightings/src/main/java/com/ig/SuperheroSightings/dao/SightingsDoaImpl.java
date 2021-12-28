/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ig.SuperheroSightings.dao;

import com.ig.SuperheroSightings.dao.LocationDaoImpl.LocationMapper;
import com.ig.SuperheroSightings.dao.SuperPowerDaoImpl.SuperPowerMapper;
import com.ig.SuperheroSightings.dao.SuperheroDaoImpl.SuperheroMapper;
import com.ig.SuperheroSightings.entity.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ebisa
 */
@Repository
public class SightingsDoaImpl implements SightingsDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Sightings addSighting(Sightings sightings) {
        final String INSERT_SIGHTING = "INSERT INTO Sighting(sightingDate, locationId, heroId) "
                + "VALUES(?,?,?)";
        jdbc.update(INSERT_SIGHTING,
                sightings.getDate(),
                sightings.getLocation().getLocationId(),
                sightings.getSuperhero().getHeroId());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sightings.setSightingId(newId);
        return sightings;
    }

    @Override
    public Sightings getSightingById(int sightingId) {
        try {
            final String SELECT_SIGHTING_BY_ID = "SELECT * FROM Sighting WHERE sightingId = ?";
            Sightings sighting = jdbc.queryForObject(SELECT_SIGHTING_BY_ID, new SightingsMapper(), sightingId);
            sighting.setLocation(getLocationForSighting(sightingId));
            sighting.setSuperhero(getSuperheroForSighting(sightingId));
            return sighting;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Sightings> getAllSightingsByDate(LocalDate date) {
        //which implementation is better, if we are to allow to search for sightings based on the date,
        //how would the user be required to enter date.
        /*
        final String GET_ALL_SIGHTINGSBYDATE = "SELECT * FROM Sighting WHERE sightingDate = ?";
        List<Sightings> sightingsData = jdbc.query(GET_ALL_SIGHTINGSBYDATE, new SightingsMapper(), date);
        associateHeroAndLocation(sightingsData);
        return sightingsData;
         */
        List<Sightings> sightings = getAllSightings();
        List<Sightings> sightingsByDate = new ArrayList<>();
        for (Sightings sighting : sightings) {
            if (sighting.getDate().compareTo(date) == 0) {
                sightingsByDate.add(sighting);
            }
        }
        return sightingsByDate;
    }

    private Location getLocationForSighting(int id) {
        final String SELECT_LOCATION_FOR_SIGHTING = "SELECT l.* FROM Location l "
                + "JOIN Sighting s ON s.locationId = l.locationId WHERE s.sightingId = ?";
        Location location = jdbc.queryForObject(SELECT_LOCATION_FOR_SIGHTING, new LocationMapper(), id);
        location.setAddress(getAddressForLocation(location.getLocationId()));
        return location;
    }

    private Superhero getSuperheroForSighting(int id) {
        final String SELECT_SUPERHERO_FOR_SIGHTING = "SELECT h.* FROM Superhero h "
                + "JOIN Sighting s ON s.heroId = h.heroId WHERE s.sightingId = ?";
        Superhero hero = jdbc.queryForObject(SELECT_SUPERHERO_FOR_SIGHTING, new SuperheroMapper(), id);
        hero.setSuperPower(getSuperPowerForSuperhero(hero.getHeroId()));
        return hero;
    }

    private void associateHeroAndLocation(List<Sightings> sightings) {
        for (Sightings sighting : sightings) {
            sighting.setSuperhero(getSuperheroForSighting(sighting.getSightingId()));
            sighting.setLocation(getLocationForSighting(sighting.getSightingId()));
        }
    }

    private SuperPower getSuperPowerForSuperhero(int id) {
        final String SELECT_SUPERPOWER_FOR_SUPERHERO = "SELECT sp.* FROM SuperPower sp "
                + "JOIN Superhero sh ON sh.superPowerId = sp.superPowerId WHERE sh.heroId = ?";
        return jdbc.queryForObject(SELECT_SUPERPOWER_FOR_SUPERHERO, new SuperPowerMapper(), id);
    }

    private Address getAddressForLocation(int locationId) {
        final String GET_ADDRESS_FOR_ORGANIZATION = "SELECT a.* FROM Address a "
                + "JOIN Location l ON l.addressId = a.addressId "
                + "WHERE l.locationId = ?";
        return jdbc.queryForObject(GET_ADDRESS_FOR_ORGANIZATION, new AddressDaoImpl.AddressMapper(), locationId);
    }

    @Override
    public List<Sightings> getAllSightings() {
        final String SELECT_ALL_SIGHTINGS = "SELECT * FROM Sighting";
        List<Sightings> sightings = jdbc.query(SELECT_ALL_SIGHTINGS, new SightingsMapper());
        //getting hero and Location for sightings.
        associateHeroAndLocation(sightings);
        return sightings;
    }

    @Override
    public void updateSighting(Sightings sightings) {
        final String UPDATE_SIGHTING = "UPDATE Sighting SET sightingDate = ?, locationId = ?, "
                + "heroId = ? WHERE sightingId = ?";
        jdbc.update(UPDATE_SIGHTING,
                sightings.getDate(),
                sightings.getLocation().getLocationId(),
                sightings.getSuperhero().getHeroId(),
                sightings.getSightingId()
        );
    }

    @Override
    public void deleteSighting(int sightingId) {
        final String DELETE_SIGHTING = "DELETE FROM Sighting WHERE sightingId = ?";
        jdbc.update(DELETE_SIGHTING, sightingId);
    }

    @Override
    public List<Sightings> getAllSightingsByHero(int heroId) {
        final String GET_ALL_SIGHTINGSBYHERO = "SELECT * FROM Sighting WHERE heroId = ?";
        List<Sightings> sightings = jdbc.query(GET_ALL_SIGHTINGSBYHERO, new SightingsMapper(), heroId);
        associateHeroAndLocation(sightings);
        return sightings;
    }

    @Override
    public List<Sightings> getAllSightingsByLocation(int locationId) {
        final String GET_ALL_SIGHTINGSBYHERO = "SELECT * FROM Sighting WHERE locationId = ?";
        List<Sightings> sightings = jdbc.query(GET_ALL_SIGHTINGSBYHERO, new SightingsMapper(), locationId);
        associateHeroAndLocation(sightings);
        return sightings;
    }

    public List<Sightings> getTheLatest10Sightings() {
        final String GET_THE_LATTEST10SIGHTINGS = "SELECT * FROM Sighting order by sightingDate Desc LIMIT 10";
        List<Sightings> sightings = jdbc.query(GET_THE_LATTEST10SIGHTINGS, new SightingsMapper());
        associateHeroAndLocation(sightings);
        return sightings;
    }

    public static final class SightingsMapper implements RowMapper<Sightings> {

        @Override
        public Sightings mapRow(ResultSet rs, int index) throws SQLException {
            Sightings sightings = new Sightings();
            sightings.setSightingId(rs.getInt("sightingId"));
            sightings.setDate(LocalDate.parse(rs.getDate("sightingDate").toString()));
            return sightings;
        }
    }

}
