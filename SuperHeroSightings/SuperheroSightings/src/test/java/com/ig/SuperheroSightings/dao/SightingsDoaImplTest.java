/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ig.SuperheroSightings.dao;

import com.ig.SuperheroSightings.entity.*;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
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
public class SightingsDoaImplTest {

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

    public SightingsDoaImplTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
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

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of addSighting method, of class SightingsDoaImpl.
     */
    @Test
    public void testAddandGetSighting() {
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
        //add sightings
        Sightings sighting = new Sightings();
        sighting.setDate(LocalDate.now());
        sighting.setLocation(location);
        sighting.setSuperhero(hero);
        sighting = sightingsDao.addSighting(sighting);
        Sightings fromDao = sightingsDao.getSightingById(sighting.getSightingId());
        assertEquals(sighting, fromDao);
    }

    /**
     * Test of getAllSightingsByDate method, of class SightingsDoaImpl.
     */
    @Test
    public void testGetAllSightingsByDate() {
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
        //add sighting 1
        Sightings sighting1 = new Sightings();
        sighting1.setDate(LocalDate.now());
        sighting1.setLocation(location);
        sighting1.setSuperhero(hero);
        sighting1 = sightingsDao.addSighting(sighting1);
        //add sighting 2
        Sightings sighting2 = new Sightings();
        sighting2.setDate(LocalDate.parse("2021-11-27"));
        sighting2.setLocation(location);
        sighting2.setSuperhero(hero);
        sighting2 = sightingsDao.addSighting(sighting2);
        //add sighting 
        Sightings sighting3 = new Sightings();
        sighting3.setDate(LocalDate.parse("2021-11-27"));
        sighting3.setLocation(location);
        sighting3.setSuperhero(hero);
        sighting3 = sightingsDao.addSighting(sighting3);
        List<Sightings> fromDao = sightingsDao.getAllSightingsByDate(sighting3.getDate());
        assertEquals(2, fromDao.size());
        assertTrue(fromDao.contains(sighting2));
        assertTrue(fromDao.contains(sighting3));
    }

    /**
     * Test of getAllSightings method, of class SightingsDoaImpl.
     */
    @Test
    public void testGetAllSightings() {
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
        //add sighting 1
        Sightings sighting1 = new Sightings();
        sighting1.setDate(LocalDate.now());
        sighting1.setLocation(location);
        sighting1.setSuperhero(hero);
        sighting1 = sightingsDao.addSighting(sighting1);
        //add sighting 2
        Sightings sighting2 = new Sightings();
        sighting2.setDate(LocalDate.now());
        sighting2.setLocation(location);
        sighting2.setSuperhero(hero);
        sighting2 = sightingsDao.addSighting(sighting2);
        List<Sightings> fromDao = sightingsDao.getAllSightings();
        assertEquals(2, fromDao.size());
        assertTrue(fromDao.contains(sighting1));
        assertTrue(fromDao.contains(sighting2));
    }

    /**
     * Test of updateSighting method, of class SightingsDoaImpl.
     */
    @Test
    public void testUpdateSighting() {
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
        //add sighting 1
        Sightings sighting1 = new Sightings();
        sighting1.setDate(LocalDate.now());
        sighting1.setLocation(location);
        sighting1.setSuperhero(hero);
        sighting1 = sightingsDao.addSighting(sighting1);
        Sightings fromDao = sightingsDao.getSightingById(sighting1.getSightingId());
        assertEquals(sighting1, fromDao);
        //update sighting 1
        sighting1.setDate(LocalDate.parse("2021-11-27"));
        sighting1.setLocation(location);
        sighting1.setSuperhero(hero);
        sightingsDao.updateSighting(sighting1);
        assertNotEquals(sighting1, fromDao);
        fromDao = sightingsDao.getSightingById(sighting1.getSightingId());
        assertEquals(fromDao, sighting1);
    }

    /**
     * Test of deleteSighting method, of class SightingsDoaImpl.
     */
    @Test
    public void testDeleteSighting() {
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
        //add sightings
        Sightings sighting = new Sightings();
        sighting.setDate(LocalDate.now());
        sighting.setLocation(location);
        sighting.setSuperhero(hero);
        sighting = sightingsDao.addSighting(sighting);
        Sightings fromDao = sightingsDao.getSightingById(sighting.getSightingId());
        assertNotNull(fromDao);
        assertEquals(sighting, fromDao);
        sightingsDao.deleteSighting(sighting.getSightingId());
        fromDao = sightingsDao.getSightingById(sighting.getSightingId());
        assertNull(fromDao);
    }

    /**
     * Test of getAllSightingsByLocation method, of class SightingsDoaImpl.
     */
    @Test
    public void testgetAllSightingsByLocation() {
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
        //add address
        Address address = new Address();
        address.setStreet("123 main st");
        address.setCity("st.paul");
        address.setState("MN");
        address.setCountry("USA");
        address.setZipcode("55113");
        address = addressDao.addAddress(address);
        //add location 1
        Location location = new Location();
        location.setLocationName("test location name");
        location.setLocationDescription("test location description");
        location.setLongitude(123.456789);
        location.setLatitude(23.345678);
        location.setAddress(address);
        location = locationDao.addLocation(location);
        //add location 2
        Location location2 = new Location();
        location2.setLocationName("test location name");
        location2.setLocationDescription("test location description");
        location2.setLongitude(123.456789);
        location2.setLatitude(23.345678);
        location2.setAddress(address);
        location2 = locationDao.addLocation(location2);
        //add sightings 1
        Sightings sighting = new Sightings();
        sighting.setDate(LocalDate.now());
        sighting.setLocation(location);
        sighting.setSuperhero(hero);
        sighting = sightingsDao.addSighting(sighting);
        //add sightings 2
        Sightings sighting2 = new Sightings();
        sighting2.setDate(LocalDate.now());
        sighting2.setLocation(location2);
        sighting2.setSuperhero(hero);
        sighting2 = sightingsDao.addSighting(sighting2);
        //add sightings 3
        Sightings sighting3 = new Sightings();
        sighting3.setDate(LocalDate.now());
        sighting3.setLocation(location2);
        sighting3.setSuperhero(hero);
        sighting3 = sightingsDao.addSighting(sighting3);
        List<Sightings> fromDao = sightingsDao.getAllSightingsByLocation(location2.getLocationId());
        assertEquals(2, fromDao.size());
        assertTrue(fromDao.contains(sighting2));
        assertTrue(fromDao.contains(sighting3));
        assertFalse(fromDao.contains(sighting));
    }

    /**
     * Test of getAllSightingsByHero method, of class SightingsDoaImpl.
     */
    @Test
    public void testgetAllSightingsByHero() {
        //add super power
        SuperPower superPower = new SuperPower();
        superPower.setSuperPowerName("test super power");
        superPower.setSuperPowerDescription("description for test super power");
        superPower = superPowerDao.addSuperPower(superPower);
        //add super hero 1
        Superhero hero = new Superhero();
        hero.setHeroName("test hero name");
        hero.setHeroDescription("test hero description");
        hero.setIsHero(true);
        hero.setSuperPower(superPower);
        hero = superheroDao.addSuperhero(hero);
        //add super hero 1
        Superhero hero2 = new Superhero();
        hero2.setHeroName("test hero name");
        hero2.setHeroDescription("test hero description");
        hero2.setIsHero(true);
        hero2.setSuperPower(superPower);
        hero2 = superheroDao.addSuperhero(hero2);
        //add address
        Address address = new Address();
        address.setStreet("123 main st");
        address.setCity("st.paul");
        address.setState("MN");
        address.setCountry("USA");
        address.setZipcode("55113");
        address = addressDao.addAddress(address);
        //add location 1
        Location location = new Location();
        location.setLocationName("test location name");
        location.setLocationDescription("test location description");
        location.setLongitude(123.456789);
        location.setLatitude(23.345678);
        location.setAddress(address);
        location = locationDao.addLocation(location);
        //add sightings 1
        Sightings sighting = new Sightings();
        sighting.setDate(LocalDate.now());
        sighting.setLocation(location);
        sighting.setSuperhero(hero);
        sighting = sightingsDao.addSighting(sighting);
        //add sightings 2
        Sightings sighting2 = new Sightings();
        sighting2.setDate(LocalDate.now());
        sighting2.setLocation(location);
        sighting2.setSuperhero(hero);
        sighting2 = sightingsDao.addSighting(sighting2);
        //add sightings 3
        Sightings sighting3 = new Sightings();
        sighting3.setDate(LocalDate.now());
        sighting3.setLocation(location);
        sighting3.setSuperhero(hero2);
        sighting3 = sightingsDao.addSighting(sighting3);
        List<Sightings> fromDao = sightingsDao.getAllSightingsByHero(hero.getHeroId());
        assertEquals(2, fromDao.size());
        assertTrue(fromDao.contains(sighting));
        assertTrue(fromDao.contains(sighting2));
        assertFalse(fromDao.contains(sighting3));
    }
}
