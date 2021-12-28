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
public class SuperPowerDaoImplTest {
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
    public SuperPowerDaoImplTest() {
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
     * Test of addSuperPower method, of class SuperPowerDaoImpl.
     */
    @Test
    public void testAddAndGetSuperPower() {
        //add super power
        SuperPower superPower = new SuperPower();
        superPower.setSuperPowerName("test super power");
        superPower.setSuperPowerDescription("description for test super power");
        superPower = superPowerDao.addSuperPower(superPower);
        SuperPower fromDao = superPowerDao.getSuperPowerById(superPower.getSuperPowerId());
        assertNotNull(fromDao);
        assertEquals(fromDao, superPower);
    }
    /**
     * Test of getAllSuperPowers method, of class SuperPowerDaoImpl.
     */
    @Test
    public void testGetAllSuperPowers() {
        //add super power 1
        SuperPower superPower1 = new SuperPower();
        superPower1.setSuperPowerName("test super power1");
        superPower1.setSuperPowerDescription("description for test super power1");
        superPower1 = superPowerDao.addSuperPower(superPower1);
        //add super power 2
        SuperPower superPower2 = new SuperPower();
        superPower2.setSuperPowerName("test super power2");
        superPower2.setSuperPowerDescription("description for test super power2");
        superPower2 = superPowerDao.addSuperPower(superPower2);
        List<SuperPower> fromDao = superPowerDao.getAllSuperPowers();
        assertEquals(2, fromDao.size());
        assertTrue(fromDao.contains(superPower1));
        assertTrue(fromDao.contains(superPower2));
    }

    /**
     * Test of updateSuperPower method, of class SuperPowerDaoImpl.
     */
    @Test
    public void testUpdateSuperPower() {
        SuperPower superPower = new SuperPower();
        superPower.setSuperPowerName("test super power");
        superPower.setSuperPowerDescription("description for test super power");
        superPower = superPowerDao.addSuperPower(superPower);
        SuperPower fromDao = superPowerDao.getSuperPowerById(superPower.getSuperPowerId());
        assertNotNull(fromDao);
        assertEquals(fromDao, superPower);
        //update super power
        superPower.setSuperPowerName("new test super power");
        superPower.setSuperPowerDescription("description for test super power");
        superPowerDao.updateSuperPower(superPower);
        assertNotEquals(fromDao, superPower);
        fromDao = superPowerDao.getSuperPowerById(superPower.getSuperPowerId());
        assertEquals(fromDao, superPower);
        
    }

    /**
     * Test of deleteSuperPower method, of class SuperPowerDaoImpl.
     */
    @Test
    public void testDeleteSuperPower() {
        //add super power
        SuperPower superPower = new SuperPower();
        superPower.setSuperPowerName("test super power");
        superPower.setSuperPowerDescription("description for test super power");
        superPower = superPowerDao.addSuperPower(superPower);
        //add super power 2
        SuperPower superPower2 = new SuperPower();
        superPower2.setSuperPowerName("test super power2");
        superPower2.setSuperPowerDescription("description for test super power2");
        superPower2 = superPowerDao.addSuperPower(superPower2);
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
        hero1.setSuperPower(superPower2);
        hero1 = superheroDao.addSuperhero(hero1);
        List<Superhero> superheros = new ArrayList<>();
        superheros.add(hero);
        superheros.add(hero1);
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
        sighting.setSuperhero(hero);
        sighting = sightingsDao.addSighting(sighting);
        Organization organization = new Organization();
        organization.setAddress(address);
        organization.setEmail("heroOrganization@gmail.com");
        organization.setOrganizationName("hero organization name");
        organization.setOrganizationDescription("hero organization description");
        organization.setPhone("6513999999");
        organization.setSuperheros(superheros);
        organization = organizationDao.addOrganization(organization);
        List<SuperPower> fromDao = superPowerDao.getAllSuperPowers();
        assertEquals(2, fromDao.size());
        assertTrue(fromDao.contains(superPower));
        assertTrue(fromDao.contains(superPower2));
        superPowerDao.deleteSuperPower(superPower.getSuperPowerId());
        fromDao = superPowerDao.getAllSuperPowers();
         assertEquals(1, fromDao.size());
         assertTrue(fromDao.contains(superPower2));
        assertFalse(fromDao.contains(superPower));
    }
    
}
