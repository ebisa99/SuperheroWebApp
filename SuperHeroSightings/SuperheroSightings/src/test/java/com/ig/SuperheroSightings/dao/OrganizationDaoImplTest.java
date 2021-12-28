/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ig.SuperheroSightings.dao;

import com.ig.SuperheroSightings.entity.*;
import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
//import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author ebisa
 */
//@RunWith(SpringRunner.class)
@SpringBootTest
public class OrganizationDaoImplTest {

    @Autowired
    AddressDao addressDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    SightingsDao sightingsDao;

    @Autowired
    SuperPowerDao superPowerDao;

    @Autowired
    SuperheroDao superheroDao;

    public OrganizationDaoImplTest() {
    }

    @BeforeEach
    public void setUp() {
        List<Sightings> sightings = sightingsDao.getAllSightings();
        for (Sightings sighting : sightings) {
            sightingsDao.deleteSighting(sighting.getSightingId());
        }

        List<Address> addresses = addressDao.getAllAddress();
        for (Address address : addresses) {
            addressDao.deleteAddress(address.getAddressId());
        }

        List<Location> locations = locationDao.getAllLocations();
        for (Location location : locations) {
            locationDao.deleteLocation(location.getLocationId());
        }
        List<Organization> organizations = organizationDao.getAllOrganizations();
        for (Organization organization : organizations) {
            organizationDao.deleteOrganization(organization.getOrganizationId());
        }
        List<Superhero> superheros = superheroDao.getAllSuperhero();
        for (Superhero superhero : superheros) {
            superheroDao.deleteSuperhero(superhero.getHeroId());
        }
        List<SuperPower> superPowers = superPowerDao.getAllSuperPowers();
        for (SuperPower superPower : superPowers) {
            superPowerDao.deleteSuperPower(superPower.getSuperPowerId());
        }
    }

    /**
     * Test of addOrganization method, of class OrganizationDaoImpl.
     */
    @Test
    public void testAddAndGetOrganization() {
        //add Address
        Address address = new Address();
        address.setStreet("123 main st");
        address.setCity("st.paul");
        address.setState("MN");
        address.setCountry("USA");
        address.setZipcode("55113");
        address = addressDao.addAddress(address);
        //add super power
        SuperPower superPower = new SuperPower();
        superPower.setSuperPowerName("test super power");
        superPower.setSuperPowerDescription("description for test super power");
        superPower = superPowerDao.addSuperPower(superPower);
        //add super hero1
        Superhero hero1 = new Superhero();
        hero1.setHeroName("test hero name");
        hero1.setHeroDescription("test hero description");
        hero1.setIsHero(true);
        hero1.setSuperPower(superPower);
        hero1 = superheroDao.addSuperhero(hero1);
        //add super hero2
        Superhero hero2 = new Superhero();
        hero2.setHeroName("test hero name");
        hero2.setHeroDescription("test hero description");
        hero2.setIsHero(true);
        hero2.setSuperPower(superPower);
        hero2 = superheroDao.addSuperhero(hero2);
        List<Superhero> superheros = new ArrayList<>();
        superheros.add(hero1);
        superheros.add(hero2);
        //add organization
        Organization organization = new Organization();
        organization.setAddress(address);
        organization.setEmail("heroOrganization@gmail.com");
        organization.setOrganizationName("hero organization name");
        organization.setOrganizationDescription("hero organization description");
        organization.setPhone("6513999999");
        organization.setSuperheros(superheros);
        organization = organizationDao.addOrganization(organization);
        Organization fromDao = organizationDao.getOrganizationById(organization.getOrganizationId());
        assertNotNull(fromDao);
        assertEquals(fromDao, organization);
    }

    /**
     * Test of getAllOrganizations method, of class OrganizationDaoImpl.
     */
    @Test
    public void testGetAllOrganizations() {
        //add Address
        Address address = new Address();
        address.setStreet("123 main st");
        address.setCity("st.paul");
        address.setState("MN");
        address.setCountry("USA");
        address.setZipcode("55113");
        address = addressDao.addAddress(address);
        //add super power
        SuperPower superPower = new SuperPower();
        superPower.setSuperPowerName("test super power");
        superPower.setSuperPowerDescription("description for test super power");
        superPower = superPowerDao.addSuperPower(superPower);
        //add super hero1
        Superhero hero1 = new Superhero();
        hero1.setHeroName("test hero name");
        hero1.setHeroDescription("test hero description");
        hero1.setIsHero(true);
        hero1.setSuperPower(superPower);
        hero1 = superheroDao.addSuperhero(hero1);
        //add super hero2
        Superhero hero2 = new Superhero();
        hero2.setHeroName("test hero name");
        hero2.setHeroDescription("test hero description");
        hero2.setIsHero(true);
        hero2.setSuperPower(superPower);
        hero2 = superheroDao.addSuperhero(hero2);
        List<Superhero> superheros = new ArrayList<>();
        superheros.add(hero1);
        superheros.add(hero2);
        //add organization 1
        Organization organization = new Organization();
        organization.setAddress(address);
        organization.setEmail("heroOrganization@gmail.com");
        organization.setOrganizationName("hero organization name");
        organization.setOrganizationDescription("hero organization description");
        organization.setPhone("6513999999");
        organization.setSuperheros(superheros);
        organization = organizationDao.addOrganization(organization);
        //add organization 2
        Organization organization1 = new Organization();
        organization1.setAddress(address);
        organization1.setEmail("heroOrganization@gmail.com");
        organization1.setOrganizationName("hero organization name2");
        organization1.setOrganizationDescription("hero organization description2");
        organization1.setPhone("6513333333");
        organization1.setSuperheros(superheros);
        organization1 = organizationDao.addOrganization(organization1);
        List<Organization> fromDao = organizationDao.getAllOrganizations();
        assertEquals(2, fromDao.size());
        assertTrue(fromDao.contains(organization1));
        assertTrue(fromDao.contains(organization));
    }

    /**
     * Test of updateOrganization method, of class OrganizationDaoImpl.
     */
    @Test
    public void testUpdateOrganization() {
        //add Address
        Address address = new Address();
        address.setStreet("123 main st");
        address.setCity("st.paul");
        address.setState("MN");
        address.setCountry("USA");
        address.setZipcode("55113");
        address = addressDao.addAddress(address);
        //add super power
        SuperPower superPower = new SuperPower();
        superPower.setSuperPowerName("test super power");
        superPower.setSuperPowerDescription("description for test super power");
        superPower = superPowerDao.addSuperPower(superPower);
        //add super hero1
        Superhero hero1 = new Superhero();
        hero1.setHeroName("test hero name");
        hero1.setHeroDescription("test hero description");
        hero1.setIsHero(true);
        hero1.setSuperPower(superPower);
        hero1 = superheroDao.addSuperhero(hero1);
        //add super hero2
        Superhero hero2 = new Superhero();
        hero2.setHeroName("test hero name2");
        hero2.setHeroDescription("test hero description2");
        hero2.setIsHero(false);
        hero2.setSuperPower(superPower);
        hero2 = superheroDao.addSuperhero(hero2);
        List<Superhero> superheros = new ArrayList<>();
        superheros.add(hero1);
        superheros.add(hero2);
        //add organization 1
        Organization organization = new Organization();
        organization.setAddress(address);
        organization.setEmail("heroOrganization@gmail.com");
        organization.setOrganizationName("hero organization name");
        organization.setOrganizationDescription("hero organization description");
        organization.setPhone("6513999999");
        organization.setSuperheros(superheros);
        organization = organizationDao.addOrganization(organization);
        Organization fromDao = organizationDao.getOrganizationById(organization.getOrganizationId());
        assertNotNull(fromDao);
        assertEquals(fromDao, organization);
        //update the Organization
        organization.setAddress(address);
        organization.setEmail("heroOrganization@gmail.com");
        organization.setOrganizationName("new hero organization name");
        organization.setOrganizationDescription("new hero organization description");
        organization.setPhone("6513777777");
        organization.setSuperheros(superheros);
        organizationDao.updateOrganization(organization);
        assertNotEquals(fromDao, organization);
        fromDao = organizationDao.getOrganizationById(organization.getOrganizationId());
        assertNotNull(fromDao);
        assertEquals(fromDao, organization);
    }

    /**
     * Test of deleteOrganization method, of class OrganizationDaoImpl.
     */
    @Test
    public void testDeleteOrganization() {
        //add Address
        Address address = new Address();
        address.setStreet("123 main st");
        address.setCity("st.paul");
        address.setState("MN");
        address.setCountry("USA");
        address.setZipcode("55113");
        address = addressDao.addAddress(address);
        //add super power
        SuperPower superPower = new SuperPower();
        superPower.setSuperPowerName("test super power");
        superPower.setSuperPowerDescription("description for test super power");
        superPower = superPowerDao.addSuperPower(superPower);
        //add super hero1
        Superhero hero1 = new Superhero();
        hero1.setHeroName("test hero name");
        hero1.setHeroDescription("test hero description");
        hero1.setIsHero(true);
        hero1.setSuperPower(superPower);
        hero1 = superheroDao.addSuperhero(hero1);
        //add super hero2
        Superhero hero2 = new Superhero();
        hero2.setHeroName("test hero name2");
        hero2.setHeroDescription("test hero description2");
        hero2.setIsHero(false);
        hero2.setSuperPower(superPower);
        hero2 = superheroDao.addSuperhero(hero2);
        List<Superhero> superheros = new ArrayList<>();
        superheros.add(hero1);
        superheros.add(hero2);
        //add organization 1
        Organization organization = new Organization();
        organization.setAddress(address);
        organization.setEmail("heroOrganization@gmail.com");
        organization.setOrganizationName("hero organization name");
        organization.setOrganizationDescription("hero organization description");
        organization.setPhone("6513999999");
        organization.setSuperheros(superheros);
        organization = organizationDao.addOrganization(organization);
        //add organization 2
        Organization organization1 = new Organization();
        organization1.setAddress(address);
        organization1.setEmail("heroOrganization@gmail.com");
        organization1.setOrganizationName("hero organization name2");
        organization1.setOrganizationDescription("hero organization description2");
        organization1.setPhone("6513333333");
        organization1.setSuperheros(superheros);
        organization1 = organizationDao.addOrganization(organization1);
        List<Organization> fromDao = organizationDao.getAllOrganizations();
        assertEquals(2, fromDao.size());
        assertTrue(fromDao.contains(organization1));
        assertTrue(fromDao.contains(organization));
        organizationDao.deleteOrganization(organization1.getOrganizationId());
        fromDao = organizationDao.getAllOrganizations();
        assertEquals(1, fromDao.size());
        assertTrue(fromDao.contains(organization));
        assertFalse(fromDao.contains(organization1));
    }

    /**
     * Test of getAllOrganizationsByHeroId method, of class OrganizationDaoImpl.
     */
    @Test
    public void testGetAllOrganizationsByHeroId() {
        //add Address
        Address address = new Address();
        address.setStreet("123 main st");
        address.setCity("st.paul");
        address.setState("MN");
        address.setCountry("USA");
        address.setZipcode("55113");
        address = addressDao.addAddress(address);
        //add super power
        SuperPower superPower = new SuperPower();
        superPower.setSuperPowerName("test super power");
        superPower.setSuperPowerDescription("description for test super power");
        superPower = superPowerDao.addSuperPower(superPower);
        //add super hero1
        Superhero hero1 = new Superhero();
        hero1.setHeroName("test hero name");
        hero1.setHeroDescription("test hero description");
        hero1.setIsHero(true);
        hero1.setSuperPower(superPower);
        hero1 = superheroDao.addSuperhero(hero1);
        //add super hero2
        Superhero hero2 = new Superhero();
        hero2.setHeroName("test hero name2");
        hero2.setHeroDescription("test hero description2");
        hero2.setIsHero(false);
        hero2.setSuperPower(superPower);
        hero2 = superheroDao.addSuperhero(hero2);
        List<Superhero> superheros = new ArrayList<>();
        superheros.add(hero1);
        superheros.add(hero2);
        //add super hero3
        Superhero hero3 = new Superhero();
        hero3.setHeroName("test hero name2");
        hero3.setHeroDescription("test hero description2");
        hero3.setIsHero(false);
        hero3.setSuperPower(superPower);
        hero3 = superheroDao.addSuperhero(hero3);
        List<Superhero> superheros2 = new ArrayList<>();
        superheros2.add(hero3);
        //add organization 1
        Organization organization = new Organization();
        organization.setAddress(address);
        organization.setEmail("heroOrganization@gmail.com");
        organization.setOrganizationName("hero organization name");
        organization.setOrganizationDescription("hero organization description");
        organization.setPhone("6513999999");
        organization.setSuperheros(superheros);
        organization = organizationDao.addOrganization(organization);
        //add organization 2
        Organization organization1 = new Organization();
        organization1.setAddress(address);
        organization1.setEmail("heroOrganization@gmail.com");
        organization1.setOrganizationName("hero organization name2");
        organization1.setOrganizationDescription("hero organization description2");
        organization1.setPhone("6513333333");
        organization1.setSuperheros(superheros2);
        organization1 = organizationDao.addOrganization(organization1);
        //add organization 3
        Organization organization2 = new Organization();
        organization2.setAddress(address);
        organization2.setEmail("heroOrganization@gmail.com");
        organization2.setOrganizationName("hero organization name2");
        organization2.setOrganizationDescription("hero organization description2");
        organization2.setPhone("6513333333");
        organization2.setSuperheros(superheros2);
        organization2 = organizationDao.addOrganization(organization1);
        List<Organization> fromDao = organizationDao.getAllOrganizationsByHeroId(hero3.getHeroId());
        assertEquals(2, fromDao.size());
        assertTrue(fromDao.contains(organization1));
        assertTrue(fromDao.contains(organization2));
    }

}
