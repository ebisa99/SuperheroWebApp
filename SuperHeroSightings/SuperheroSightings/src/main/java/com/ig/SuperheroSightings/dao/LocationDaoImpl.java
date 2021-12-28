/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ig.SuperheroSightings.dao;

import com.ig.SuperheroSightings.dao.AddressDaoImpl.AddressMapper;
import com.ig.SuperheroSightings.entity.Address;
import com.ig.SuperheroSightings.entity.Location;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class LocationDaoImpl implements LocationDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Location addLocation(Location location) {
        final String INSERT_LOCATION = "INSERT INTO Location(locationName, locationDescription, longitude, "
                + "latitude, addressId)VALUES(?, ?, ?, ?, ?)";
        jdbc.update(INSERT_LOCATION,
                location.getLocationName(),
                location.getLocationDescription(),
                location.getLongitude(),
                location.getLatitude(),
                location.getAddress().getAddressId());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        location.setLocationId(newId);
        return location;
    }

    private Address getAddressForLocation(int locationId) {
        final String GET_ADDRESS_FOR_ORGANIZATION = "SELECT a.* FROM Address a "
                + "JOIN Location l ON l.addressId = a.addressId "
                + "WHERE l.locationId = ?";
        return jdbc.queryForObject(GET_ADDRESS_FOR_ORGANIZATION, new AddressMapper(), locationId);
    }

    private void associateAddressLocation(List<Location> locations) {
        for (Location location : locations) {
            location.setAddress(getAddressForLocation(location.getLocationId()));
        }
    }

    @Override
    public Location getLocationById(int locationId) {
        try {
            final String GET_LOCATION = "SELECT * FROM Location WHERE locationId = ?";
            Location location = jdbc.queryForObject(GET_LOCATION, new LocationMapper(), locationId);
            location.setAddress(getAddressForLocation(locationId));
            return location;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Location> getAllLocations() {
        final String GET_ALL_LOCATIONS = "SELECT * FROM Location";
        List<Location> locations = jdbc.query(GET_ALL_LOCATIONS, new LocationMapper());
        associateAddressLocation(locations);
        return locations;
    }

    @Override
    public void updateLocation(Location location) {
        final String UPDATE_LOCATION = "UPDATE Location SET locationName = ?, locationDescription = ?, "
                + "longitude = ?, latitude = ?, addressId = ? WHERE locationId =?";
        jdbc.update(UPDATE_LOCATION,
                location.getLocationName(),
                location.getLocationDescription(),
                location.getLongitude(),
                location.getLatitude(),
                location.getAddress().getAddressId(),
                location.getLocationId());
    }

    @Override
    @Transactional
    public void deleteLocation(int locationId) {
        final String DELETE_FROM_SIGHTING = "DELETE FROM Sighting WHERE locationId = ?";
        jdbc.update(DELETE_FROM_SIGHTING, locationId);
        final String DELETE_FROM_LOCATION = "DELETE FROM Location WHERE locationId = ?";
        jdbc.update(DELETE_FROM_LOCATION, locationId);
    }

    public static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int index) throws SQLException {
            Location location = new Location();
            location.setLocationId(rs.getInt("locationId"));
            location.setLocationName(rs.getString("locationName"));
            location.setLocationDescription(rs.getString("locationDescription"));
            location.setLongitude(rs.getDouble("longitude"));
            location.setLatitude(rs.getDouble("latitude"));
            return location;
        }
    }
}
