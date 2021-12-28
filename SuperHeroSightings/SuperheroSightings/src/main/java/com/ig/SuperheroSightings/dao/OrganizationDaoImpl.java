/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ig.SuperheroSightings.dao;

import com.ig.SuperheroSightings.dao.AddressDaoImpl.AddressMapper;
import com.ig.SuperheroSightings.dao.SuperheroDaoImpl.SuperheroMapper;
import com.ig.SuperheroSightings.entity.Address;
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
public class OrganizationDaoImpl implements OrganizationDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Organization addOrganization(Organization organization) {
        final String INSERT_ORGANIZATION = "INSERT INTO Organization(organizationName, organizationDescription,"
                + "phone, email, addressId)VALUES(?, ?, ?, ?, ?)";
        jdbc.update(INSERT_ORGANIZATION,
                organization.getOrganizationName(),
                organization.getOrganizationDescription(),
                organization.getPhone(),
                organization.getEmail(),
                organization.getAddress().getAddressId());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        organization.setOrganizationId(newId);
        insertOrganizationSuperhero(organization);
        return organization;
    }

    private Address getAddressForOrganization(int organizationId) {
        final String GET_ADDRESS_FOR_ORGANIZATION = "SELECT a.* FROM Address a "
                + "JOIN Organization o ON o.addressId = a.addressId "
                + "WHERE o.organizationId = ?";
        return jdbc.queryForObject(GET_ADDRESS_FOR_ORGANIZATION, new AddressMapper(), organizationId);
    }

    private void associateAddressAndSuperheros(List<Organization> organizations) {
        for (Organization organization : organizations) {
            organization.setAddress(getAddressForOrganization(organization.getOrganizationId()));
            organization.setSuperheros(getSuperherosForOrganization(organization.getOrganizationId()));
        }
    }

    private void insertOrganizationSuperhero(Organization organization) {
        final String INSERT_SUPERHERO_ORGANIZATION = "INSERT INTO HeroOrganization(heroId, organizationId)"
                + "VALUES(?, ?)";
        for (Superhero superhero : organization.getSuperheros()) {
            jdbc.update(INSERT_SUPERHERO_ORGANIZATION,
                    superhero.getHeroId(),
                    organization.getOrganizationId());
        }
    }

    @Override
    public Organization getOrganizationById(int organizationId) {
        try {
            final String GET_ORGANIZATION = "SELECT * FROM Organization WHERE organizationId = ?";
            Organization organization = jdbc.queryForObject(GET_ORGANIZATION, new OrganizationMapper(), organizationId);
            organization.setAddress(getAddressForOrganization(organizationId));
            organization.setSuperheros(getSuperherosForOrganization(organizationId));
            return organization;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Organization> getAllOrganizations() {
        final String SELECT_ALL_ORGANIZATIONS = "SELECT * FROM Organization";
        List<Organization> organizations = jdbc.query(SELECT_ALL_ORGANIZATIONS, new OrganizationMapper());
        associateAddressAndSuperheros(organizations);
        return organizations;
    }

    @Override
    @Transactional
    public void updateOrganization(Organization organization) {
        //when organization is updated, rows in heroOrganization with organizationId that is updated should be deleted.
        //and reinserted.
        final String UPDDATE_ORGANIZATION = "UPDATE Organization SET organizationName = ?, "
                + "organizationDescription = ?, phone = ?, email = ?, addressId = ? WHERE organizationId =?";
        jdbc.update(UPDDATE_ORGANIZATION,
                organization.getOrganizationName(),
                organization.getOrganizationDescription(),
                organization.getPhone(),
                organization.getEmail(),
                organization.getAddress().getAddressId(),
                organization.getOrganizationId());
        final String DELETE_FROM_HEROORGANIZATION = "DELETE FROM HeroOrganization WHERE organizationId = ?";
        jdbc.update(DELETE_FROM_HEROORGANIZATION, organization.getOrganizationId());
        //insert the updated organization into HeroOrganization
        insertOrganizationSuperhero(organization);
    }

    @Override
    @Transactional
    public void deleteOrganization(int organizationId) {
        final String DELETE_FROM_HERO_ORGANIZATION = "DELETE FROM HeroOrganization WHERE organizationId = ?";
        jdbc.update(DELETE_FROM_HERO_ORGANIZATION, organizationId);
        final String DELETE_FROM_ORGANIZATION = "DELETE FROM Organization WHERE organizationId = ?";
        jdbc.update(DELETE_FROM_ORGANIZATION, organizationId);
    }

    private List<Superhero> getSuperherosForOrganization(int organizationId) {
        final String GET_ALL_MEMBERS_OF_PARTICULAR_ORGANIZATION = "SELECT h.* FROM Superhero h "
                + "JOIN HeroOrganization o ON o.heroId = h.heroId WHERE o.organizationId = ?";
        List<Superhero>superheros =  jdbc.query(GET_ALL_MEMBERS_OF_PARTICULAR_ORGANIZATION, new SuperheroMapper(), organizationId);
        for(Superhero superhero: superheros){
            superhero.setSuperPower(getSuperPowerForSuperhero(superhero.getHeroId()));
        }
        return superheros;
    }

    private SuperPower getSuperPowerForSuperhero(int id) {
        final String SELECT_SUPERPOWER_FOR_SUPERHERO = "SELECT sp.* FROM SuperPower sp "
                + "JOIN Superhero sh ON sh.superPowerId = sp.superPowerId WHERE sh.heroId = ?";
        return jdbc.queryForObject(SELECT_SUPERPOWER_FOR_SUPERHERO, new SuperPowerDaoImpl.SuperPowerMapper(), id);
    }

    @Override
    public List<Organization> getAllOrganizationsByHeroId(int heroId) {
        final String GET_ALL_ORGANIZATIONS_BYHEROID = "SELECT o.* FROM Organization o "
                + "JOIN HeroOrganization ho ON ho.organizationId = o.organizationId WHERE ho.heroId = ?";
        List<Organization> organizations = jdbc.query(GET_ALL_ORGANIZATIONS_BYHEROID, new OrganizationMapper(), heroId);
        associateAddressAndSuperheros(organizations);
        return organizations;
    }

    public static final class OrganizationMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int index) throws SQLException {
            Organization organization = new Organization();
            organization.setOrganizationId(rs.getInt("organizationId"));
            organization.setOrganizationName(rs.getString("organizationName"));
            organization.setOrganizationDescription(rs.getString("organizationDescription"));
            organization.setPhone(rs.getString("phone"));
            organization.setEmail(rs.getString("email"));
            return organization;
        }
    }
}
