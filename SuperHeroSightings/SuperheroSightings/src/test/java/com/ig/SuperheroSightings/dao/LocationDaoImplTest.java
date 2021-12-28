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
public class LocationDaoImplTest {

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

    public LocationDaoImplTest() {
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
     * Test of addLocation method, of class LocationDaoImpl.
     */
    @Test
    public void testAddAndGetLocation() {
        //add Address
        Address address = new Address();
        address.setStreet("123 main st");
        address.setCity("st.paul");
        address.setState("MN");
        address.setCountry("USA");
        address.setZipcode("55113");
        address = addressDao.addAddress(address);
        //add Location
        Location location = new Location();
        location.setLocationName("test location name");
        location.setLocationDescription("test location description");
        location.setLongitude(123.456789);
        location.setLatitude(23.345678);
        location.setAddress(address);
        location = locationDao.addLocation(location);
        Location fromDao = locationDao.getLocationById(location.getLocationId());
        assertNotNull(fromDao);
        assertEquals(fromDao, location);
    }

    /**
     * Test of getAllLocations method, of class LocationDaoImpl.
     */
    @Test
    public void testGetAllLocations() {
        //add Address
        Address address = new Address();
        address.setStreet("123 main st");
        address.setCity("st.paul");
        address.setState("MN");
        address.setCountry("USA");
        address.setZipcode("55113");
        address = addressDao.addAddress(address);
        //add Location 1
        Location location1 = new Location();
        location1.setLocationName("test location name");
        location1.setLocationDescription("test location description");
        location1.setLongitude(123.456789);
        location1.setLatitude(23.345678);
        location1.setAddress(address);
        location1 = locationDao.addLocation(location1);
        //add Location 2
        Location location2 = new Location();
        location2.setLocationName("test location name2");
        location2.setLocationDescription("test location description2");
        location2.setLongitude(123.456789);
        location2.setLatitude(23.345678);
        location2.setAddress(address);
        location2 = locationDao.addLocation(location2);
        List<Location> fromDao = locationDao.getAllLocations();
        assertEquals(2, fromDao.size());
        assertTrue(fromDao.contains(location1));
        assertTrue(fromDao.contains(location2));
    }

    /**
     * Test of updateLocation method, of class LocationDaoImpl.
     */
    @Test
    public void testUpdateLocation() {
        //add Address
        Address address = new Address();
        address.setStreet("123 main st");
        address.setCity("st.paul");
        address.setState("MN");
        address.setCountry("USA");
        address.setZipcode("55113");
        address = addressDao.addAddress(address);
        //add Location 1
        Location location = new Location();
        location.setLocationName("test location name");
        location.setLocationDescription("test location description");
        location.setLongitude(123.456789);
        location.setLatitude(23.345678);
        location.setAddress(address);
        location = locationDao.addLocation(location);
        Location fromDao = locationDao.getLocationById(location.getLocationId());
        assertNotNull(fromDao);
        assertEquals(fromDao, location);
        //update location
        location.setLocationName("test updated location name");
        location.setLocationDescription("test updated location description");
        location.setLongitude(130.456789);
        location.setLatitude(30.345678);
        location.setAddress(address);
        locationDao.updateLocation(location);
        assertNotEquals(fromDao, location);
        fromDao = locationDao.getLocationById(location.getLocationId());
        assertNotNull(fromDao);
        assertEquals(fromDao, location);
    }

    /**
     * Test of deleteLocation method, of class LocationDaoImpl.
     */
    @Test
    public void testDeleteLocation() {
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
        //add super hero
        Superhero hero = new Superhero();
        hero.setHeroName("test hero name");
        hero.setHeroDescription("test hero description");
        hero.setIsHero(true);
        hero.setSuperPower(superPower);
        hero = superheroDao.addSuperhero(hero);
        Superhero hero1 = new Superhero();
        hero1.setHeroName("test hero name1");
        hero1.setHeroDescription("test hero description1");
        hero1.setIsHero(false);
        hero1.setSuperPower(superPower);
        hero1 = superheroDao.addSuperhero(hero1);
        List<Superhero> superheros = new ArrayList<>();
        superheros.add(hero);
        superheros.add(hero1);
        //add Location 1
        Location location = new Location();
        location.setLocationName("test location name1");
        location.setLocationDescription("test location description1");
        location.setLongitude(113.456789);
        location.setLatitude(23.345678);
        location.setAddress(address);
        location = locationDao.addLocation(location);
        //add Location 2
        Location location1 = new Location();
        location1.setLocationName("test location name");
        location1.setLocationDescription("test location description");
        location1.setLongitude(123.456789);
        location1.setLatitude(23.345678);
        location1.setAddress(address);
        location1 = locationDao.addLocation(location1);
        //add Sighting
        Sightings sighting = new Sightings();
        sighting.setDate(LocalDate.now());
        sighting.setLocation(location);
        sighting.setSuperhero(hero);
        sighting = sightingsDao.addSighting(sighting);
        List<Location> fromDao = locationDao.getAllLocations();
        assertEquals(2, fromDao.size());
        assertTrue(fromDao.contains(location));
        assertTrue(fromDao.contains(location1));
        locationDao.deleteLocation(location.getLocationId());
        fromDao = locationDao.getAllLocations();
        assertEquals(1, fromDao.size());
        assertTrue(fromDao.contains(location1));
        assertFalse(fromDao.contains(location));
    }

    /**
     * Test of getAllLocationsByHeroId method, of class LocationDaoImpl.
     */
    /*
    @Test
    public void testGetAllLocationsByHeroId() {
        //add Address
        Address address = new Address();
        address.setStreet("123 main st");
        address.setCity("st.paul");
        address.setState("MN");
        address.setCountry("USA");
        address.setZipcode("55113");
        address = addressDao.addAddress(address);
        //add Location 1
        Location location1 = new Location();
        location1.setLocationName("test location name");
        location1.setLocationDescription("test location description");
        location1.setLongitude(23.456789);
        location1.setLatitude(3.345678);
        location1.setAddress(address);
        location1 = locationDao.addLocation(location1);
        //add Location 2
        Location location2 = new Location();
        location2.setLocationName("test location name2");
        location2.setLocationDescription("test location description2");
        location2.setLongitude(123.456789);
        location2.setLatitude(23.345678);
        location2.setAddress(address);
        location2 = locationDao.addLocation(location2);
        //add Location 3
        Location location3 = new Location();
        location3.setLocationName("test location name2");
        location3.setLocationDescription("test location description2");
        location3.setLongitude(123.456789);
        location3.setLatitude(23.345678);
        location3.setAddress(address);
        location3 = locationDao.addLocation(location3);
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
        //add sightings1
        Sightings sighting1 = new Sightings();
        sighting1.setDate(LocalDate.now());
        sighting1.setLocation(location1);
        sighting1.setSuperhero(hero1);
        sighting1 = sightingsDao.addSighting(sighting1);
        //add sightings2
        Sightings sighting2 = new Sightings();
        sighting2.setDate(LocalDate.parse("2021-11-27"));
        sighting2.setLocation(location2);
        sighting2.setSuperhero(hero2);
        sighting2 = sightingsDao.addSighting(sighting2);
        //add sightings3
        Sightings sighting3 = new Sightings();
        sighting3.setDate(LocalDate.parse("2021-11-27"));
        sighting3.setLocation(location3);
        sighting3.setSuperhero(hero2);
        sighting3 = sightingsDao.addSighting(sighting3);
        List<Sightings> fromDao = sightingsDao.getAllSightings();
        assertEquals(3, fromDao.size());
        List<Location> locationFromDao = locationDao.getAllLocationsByHeroId(hero2.getHeroId());
        assertEquals(2, locationFromDao.size());
        assertTrue(locationFromDao.contains(location2));
        assertTrue(locationFromDao.contains(location3));

    }
*/
}
