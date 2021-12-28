/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ig.SuperheroSightings.dao;

import com.ig.SuperheroSightings.entity.SuperPower;
import com.ig.SuperheroSightings.entity.Superhero;
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
public class SuperPowerDaoImpl implements SuperPowerDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public SuperPower addSuperPower(SuperPower superPower) {
        final String INSERT_SUPERPOWER = "INSERT INTO SuperPower(superPowerName, superPowerDescription) "
                + "VALUES(?, ?)";
        jdbc.update(INSERT_SUPERPOWER,
                superPower.getSuperPowerName(),
                superPower.getSuperPowerDescription());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        superPower.setSuperPowerId(newId);
        return superPower;
    }

    @Override
    public SuperPower getSuperPowerById(int superPowerId) {
        try {
            final String SELECT_FROM_SUPERPOWER = "SELECT * FROM SuperPower WHERE superPowerId = ?";
            return jdbc.queryForObject(SELECT_FROM_SUPERPOWER, new SuperPowerMapper(), superPowerId);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<SuperPower> getAllSuperPowers() {
        final String SELECT_ALL_SUPERPOWERS = "SELECT * FROM SuperPower";
        return jdbc.query(SELECT_ALL_SUPERPOWERS, new SuperPowerMapper());
    }

    @Override
    public void updateSuperPower(SuperPower superPower) {
        final String UPDATE_SUPERPOWER = "UPDATE SuperPower SET superPowerName = ?, superPowerDescription = ? "
                + "WHERE superPowerId = ?";
        jdbc.update(UPDATE_SUPERPOWER,
                superPower.getSuperPowerName(),
                superPower.getSuperPowerDescription(),
                superPower.getSuperPowerId());
    }

    @Override
    @Transactional
    public void deleteSuperPower(int superPowerId) {
        final String DELETE_FROM_SIGHTING = "DELETE s FROM Sighting s "
                + "JOIN Superhero sh ON sh.heroId = s.heroId "
                + "WHERE sh.superPowerId = ?";
        jdbc.update(DELETE_FROM_SIGHTING, superPowerId);
        final String DELETE_FROM_HEROORGANIZATION = "DELETE ho FROM HeroOrganization ho "
                + "JOIN Superhero sh ON sh.heroId = ho.heroId WHERE sh.superPowerId = ?";
        jdbc.update(DELETE_FROM_HEROORGANIZATION, superPowerId);
        final String DELETE_FROM_SUPERHERO = "DELETE FROM Superhero WHERE superPowerId = ?";
        jdbc.update(DELETE_FROM_SUPERHERO, superPowerId);
        final String DELETE_FROM_SUPERPOWER = "DELETE FROM SuperPower WHERE superPowerId = ?";
        jdbc.update(DELETE_FROM_SUPERPOWER, superPowerId);
    }

    public static final class SuperPowerMapper implements RowMapper<SuperPower> {

        @Override
        public SuperPower mapRow(ResultSet rs, int index) throws SQLException {
            SuperPower superPower = new SuperPower();
            superPower.setSuperPowerId(rs.getInt("superPowerId"));
            superPower.setSuperPowerName(rs.getString("superPowerName"));
            superPower.setSuperPowerDescription(rs.getString("superPowerDescription"));
            return superPower;
        }
    }
}
