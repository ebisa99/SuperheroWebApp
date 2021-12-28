/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ig.SuperheroSightings.dao;

import com.ig.SuperheroSightings.entity.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
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
public class SuperheroDaoImplTest {

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

    public SuperheroDaoImplTest() {
    }

//    @BeforeClass
//    public static void setUpClass() {
//    }
//
//    @AfterClass
//    public static void tearDownClass() {
//    }

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

//    @After
//    public void tearDown() {
//    }

    /**
     * Test of addSuperhero method, of class SuperheroDaoImpl.
     */
    @Test
    public void testAddAndGetSuperhero() {
        //add super power
        SuperPower superPower = new SuperPower();
        superPower.setSuperPowerName("test super power");
        superPower.setSuperPowerDescription("description for test super power");
        superPower = superPowerDao.addSuperPower(superPower);
        //add super hero
        Superhero hero = new Superhero();
        hero.setHeroName("test hero name");
        hero.setHeroDescription("test hero description");
        hero.setIsHero(true);
        hero.setSuperPower(superPower);
        hero = superheroDao.addSuperhero(hero);
        Superhero fromDao = superheroDao.getSuperheroById(hero.getHeroId());
        assertNotNull(fromDao);
        assertEquals(fromDao, hero);
    }

    /**
     * Test of getAllSuperhero method, of class SuperheroDaoImpl.
     */
    @Test
    public void testGetAllSuperhero() {
        //add super power
        SuperPower superPower = new SuperPower();
        superPower.setSuperPowerName("test super power");
        superPower.setSuperPowerDescription("description for test super power");
        superPower = superPowerDao.addSuperPower(superPower);
        //add super hero 1
        Superhero hero1 = new Superhero();
        hero1.setHeroName("test hero name");
        hero1.setHeroDescription("test hero description");
        hero1.setIsHero(true);
        hero1.setSuperPower(superPower);
        hero1 = superheroDao.addSuperhero(hero1);
        //add super hero 2
        Superhero hero2 = new Superhero();
        hero2.setHeroName("test hero name2");
        hero2.setHeroDescription("test hero description2");
        hero2.setIsHero(false);
        hero2.setSuperPower(superPower);
        hero2 = superheroDao.addSuperhero(hero2);
        List<Superhero> fromDao = superheroDao.getAllSuperhero();
        assertEquals(2, fromDao.size());
        assertTrue(fromDao.contains(hero2));
        assertTrue(fromDao.contains(hero1));
    }

    /**
     * Test of updateSuperhero method, of class SuperheroDaoImpl.
     */
    @Test
    public void testUpdateSuperhero() {
        //add super power
        SuperPower superPower = new SuperPower();
        superPower.setSuperPowerName("test super power");
        superPower.setSuperPowerDescription("description for test super power");
        superPower = superPowerDao.addSuperPower(superPower);
        //add super hero
        Superhero hero = new Superhero();
        hero.setHeroName("test hero name");
        hero.setHeroDescription("test hero description");
        hero.setIsHero(true);
        hero.setSuperPower(superPower);
        hero = superheroDao.addSuperhero(hero);
        Superhero fromDao = superheroDao.getSuperheroById(hero.getHeroId());
        assertNotNull(fromDao);
        assertEquals(fromDao, hero);
        //update the super hero
        hero.setHeroName("test hero name2");
        hero.setHeroDescription("new test hero description");
        hero.setIsHero(false);
        hero.setSuperPower(superPower);
        superheroDao.updateSuperhero(hero);
        assertNotEquals(fromDao, hero);
        fromDao = superheroDao.getSuperheroById(hero.getHeroId());
        assertEquals(fromDao, hero);
    }

    /**
     * Test of deleteSuperhero method, of class SuperheroDaoImpl.
     */
    @Test
    public void testDeleteSuperhero() {
        //add super power
        SuperPower superPower = new SuperPower();
        superPower.setSuperPowerName("test super power");
        superPower.setSuperPowerDescription("description for test super power");
        superPower = superPowerDao.addSuperPower(superPower);
        //add super hero 1
        Superhero hero1 = new Superhero();
        hero1.setHeroName("test hero name1");
        hero1.setHeroDescription("test hero description1");
        hero1.setIsHero(true);
        hero1.setSuperPower(superPower);
        hero1 = superheroDao.addSuperhero(hero1);
        //add super hero 2
        Superhero hero2 = new Superhero();
        hero2.setHeroName("test hero name2");
        hero2.setHeroDescription("test hero description2");
        hero2.setIsHero(false);
        hero2.setSuperPower(superPower);
        hero2 = superheroDao.addSuperhero(hero2);
        List<Superhero> superheros = new ArrayList<>();
        superheros.add(hero1);
         superheros.add(hero2);
        //add address
        Address address = new Address();
        address.setStreet("123 main st");
        address.setCity("st.paul");
        address.setState("MN");
        address.setCountry("USA");
        address.setZipcode("55113");
        address = addressDao.addAddress(address);
        //add location
        Location location = new Location();
        location.setLocationName("test location name");
        location.setLocationDescription("test location description");
        location.setLongitude(123.456789);
        location.setLatitude(23.345678);
        location.setAddress(address);
        location = locationDao.addLocation(location);
        //add Sighting
        Sightings sighting = new Sightings();
        sighting.setDate(LocalDate.now());
        sighting.setLocation(location);
        sighting.setSuperhero(hero2);
        sighting = sightingsDao.addSighting(sighting);
        Organization organization = new Organization();
        organization.setAddress(address);
        organization.setEmail("heroOrganization@gmail.com");
        organization.setOrganizationName("hero organization name");
        organization.setOrganizationDescription("hero organization description");
        organization.setPhone("6513999999");
        organization.setSuperheros(superheros);
        organization = organizationDao.addOrganization(organization);
        List<Superhero> fromDao = superheroDao.getAllSuperhero();
        assertEquals(2, fromDao.size());
        assertTrue(fromDao.contains(hero2));
        assertTrue(fromDao.contains(hero1));
        superheroDao.deleteSuperhero(hero2.getHeroId());
        fromDao = superheroDao.getAllSuperhero();
        assertEquals(1, fromDao.size());
        assertTrue(fromDao.contains(hero1));
        assertFalse(fromDao.contains(hero2));
    }
}
