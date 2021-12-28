/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ig.SuperheroSightings.dao;

import com.ig.SuperheroSightings.dao.SuperPowerDaoImpl.SuperPowerMapper;
import com.ig.SuperheroSightings.entity.Organization;
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
public class SuperheroDaoImpl implements SuperheroDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Superhero addSuperhero(Superhero hero) {
        final String INSERT_SUPERHERO = "INSERT INTO Superhero(heroName, heroDescription, isHero, imageFileName, superPowerId) "
                + "VALUES(?, ?, ?, ?, ?)";
        jdbc.update(INSERT_SUPERHERO,
                hero.getHeroName(),
                hero.getHeroDescription(),
                hero.isIsHero(),
                hero.getImageFile(),
                hero.getSuperPower().getSuperPowerId());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        hero.setHeroId(newId);
        return hero;
    }

    private SuperPower getSuperPowerForSuperhero(int id) {
        final String SELECT_SUPERPOWER_FOR_SUPERHERO = "SELECT sp.* FROM SuperPower sp "
                + "JOIN Superhero sh ON sh.superPowerId = sp.superPowerId WHERE sh.heroId = ?";
        return jdbc.queryForObject(SELECT_SUPERPOWER_FOR_SUPERHERO, new SuperPowerMapper(), id);
    }

    private void associateSuperPower(List<Superhero> superhero) {
        for (Superhero hero : superhero) {
            hero.setSuperPower(getSuperPowerForSuperhero(hero.getHeroId()));
        }
    }

    @Override
    public List<Superhero> getAllSuperhero() {
        final String SELECT_ALL_SUPERHEROS = "SELECT * FROM Superhero";
        List<Superhero> superheros = jdbc.query(SELECT_ALL_SUPERHEROS, new SuperheroMapper());
        associateSuperPower(superheros);
        return superheros;
    }

    @Override
    public Superhero getSuperheroById(int heroId) {
        try {
            final String SELECT_SUPERHERO_BYID = "SELECT * FROM Superhero WHERE heroId = ?";
            Superhero superhero = jdbc.queryForObject(SELECT_SUPERHERO_BYID, new SuperheroMapper(), heroId);
            superhero.setSuperPower(getSuperPowerForSuperhero(heroId));
            return superhero;
        } catch (DataAccessException ex) {
            return null;
        }

    }

    @Override
    public void updateSuperhero(Superhero hero) {
        final String UPDATE_SUPERHERO = "UPDATE Superhero SET heroName = ?, heroDescription = ?, "
                + "isHero = ?, imageFileName = ?, superPowerId = ? WHERE heroId = ?";
        jdbc.update(UPDATE_SUPERHERO,
                hero.getHeroName(),
                hero.getHeroDescription(),
                hero.isIsHero(),
                hero.getImageFile(),
                hero.getSuperPower().getSuperPowerId(),
                hero.getHeroId());
    }

    @Override
    @Transactional
    public void deleteSuperhero(int heroId) {
        final String DELETE_FROM_HERO_ORGANIZATION = "DELETE FROM HeroOrganization WHERE heroId = ?";
        jdbc.update(DELETE_FROM_HERO_ORGANIZATION, heroId);
        final String DELETE_FROM_SIGHTING = "DELETE FROM Sighting WHERE heroId = ?";
        jdbc.update(DELETE_FROM_SIGHTING, heroId);
        final String DELETE_FROM_SUPERHERO = "DELETE FROM Superhero WHERE heroId = ?";
        jdbc.update(DELETE_FROM_SUPERHERO, heroId);
    }

    public static final class SuperheroMapper implements RowMapper<Superhero> {

        @Override
        public Superhero mapRow(ResultSet rs, int index) throws SQLException {
            Superhero superhero = new Superhero();
            superhero.setHeroId(rs.getInt("heroId"));
            superhero.setHeroName(rs.getString("heroName"));
            superhero.setHeroDescription(rs.getString("heroDescription"));
            superhero.setIsHero(rs.getBoolean("isHero"));
            superhero.setImageFile(rs.getString("imageFileName"));
            return superhero;
        }
    }
}
