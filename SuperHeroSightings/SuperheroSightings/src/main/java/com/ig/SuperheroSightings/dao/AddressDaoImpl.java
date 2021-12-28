/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ig.SuperheroSightings.dao;

import com.ig.SuperheroSightings.entity.Address;
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
public class AddressDaoImpl implements AddressDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Address addAddress(Address address) {
        final String INSERT_ADDRESS = "INSERT INTO Address(street, city, state, country, "
                + "zipcode)VALUES(?,?,?,?,?)";
        jdbc.update(INSERT_ADDRESS,
                address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getCountry(),
                address.getZipcode());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        address.setAddressId(newId);
        return address;
    }

    @Override
    public Address getAddressById(int addressId) {
        try {
            final String GET_ADDRESS = "SELECT * FROM Address WHERE addressId = ?";
            return jdbc.queryForObject(GET_ADDRESS, new AddressMapper(), addressId);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Address> getAllAddress() {
        final String GET_ALL_ADDRESS = "SELECT * FROM Address";
        return jdbc.query(GET_ALL_ADDRESS, new AddressMapper());
    }

    @Override
    public void updateAddress(Address address) {
        final String UPDATE_ADDRESS = "UPDATE Address SET street = ?, city = ?, state = ?, "
                + "country = ?, zipcode = ? WHERE addressId =?";
        jdbc.update(UPDATE_ADDRESS,
                address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getCountry(),
                address.getZipcode(),
                address.getAddressId());
    }

    @Override
    @Transactional
    public void deleteAddress(int addressId) {
        final String DELETE_FROM_HEROORGANIZATION = "DELETE ho FROM HeroOrganization ho "
                + "JOIN organization o ON o.organizationId = ho.organizationId"
                + " WHERE o.addressId =  ?";
        jdbc.update(DELETE_FROM_HEROORGANIZATION, addressId);
        final String DELETE_FROM_ORGANIZATION = "DELETE FROM Organization WHERE addressId = ?";
        jdbc.update(DELETE_FROM_ORGANIZATION, addressId);
        final String DELETE_FROM_SIGHTING = "DELETE s FROM Sighting s "
                + "JOIN Location l ON l.locationId = s.locationId"
                + " WHERE l.addressId =  ?";
        jdbc.update(DELETE_FROM_SIGHTING, addressId);
        final String DELETE_FROM_LOCATION = "DELETE FROM Location WHERE addressId = ?";
        jdbc.update(DELETE_FROM_LOCATION, addressId);
        final String DELETE_FROM_ADDRESS = "DELETE FROM Address WHERE addressId =  ?";
        jdbc.update(DELETE_FROM_ADDRESS, addressId);
    }

    public static final class AddressMapper implements RowMapper<Address> {

        @Override
        public Address mapRow(ResultSet rs, int index) throws SQLException {
            Address address = new Address();
            address.setAddressId(rs.getInt("addressId"));
            address.setStreet(rs.getString("street"));
            address.setCity(rs.getString("city"));
            address.setState(rs.getString("state"));
            address.setCountry(rs.getString("country"));
            address.setZipcode(rs.getString("zipcode"));
            return address;
        }
    }
}
