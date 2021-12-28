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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
public class AddressDaoImplTest {

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

    public AddressDaoImplTest() {
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
     * Test of addAddress method, of class AddressDaoImpl.
     */
    @Test
    public void testAddAndGetAddress() {
        //add address
        Address address = new Address();
        address.setStreet("123 main st");
        address.setCity("st.paul");
        address.setState("MN");
        address.setCountry("USA");
        address.setZipcode("55113");
        address = addressDao.addAddress(address);
        Address fromDao = addressDao.getAddressById(address.getAddressId());
        assertNotNull(fromDao);
        assertEquals(fromDao, address);
    }

    /**
     * Test of getAllAddress method, of class AddressDaoImpl.
     */
    @Test
    public void testGetAllAddress() {
        //add address 1
        Address address1 = new Address();
        address1.setStreet("123 main st");
        address1.setCity("st.paul");
        address1.setState("MN");
        address1.setCountry("USA");
        address1.setZipcode("55113");
        address1 = addressDao.addAddress(address1);
        //add address 2
        Address address2 = new Address();
        address2.setStreet("123 st.paul st");
        address2.setCity("st.paul");
        address2.setState("MN");
        address2.setCountry("USA");
        address2.setZipcode("55103");
        address2 = addressDao.addAddress(address2);
        List<Address> fromDao = addressDao.getAllAddress();
        assertNotNull(fromDao);
        assertEquals(2, fromDao.size());
        assertTrue(fromDao.contains(address1));
        assertTrue(fromDao.contains(address2));
    }

    /**
     * Test of updateAddress method, of class AddressDaoImpl.
     */
    @Test
    public void testUpdateAddress() {
        Address address = new Address();
        address.setStreet("123 main st");
        address.setCity("st.paul");
        address.setState("MN");
        address.setCountry("USA");
        address.setZipcode("55113");
        address = addressDao.addAddress(address);
        Address fromDao = addressDao.getAddressById(address.getAddressId());
        assertNotNull(fromDao);
        assertEquals(address, fromDao);
        //update address
        address.setStreet("123 st.paul st");
        address.setCity("st.paul");
        address.setState("MN");
        address.setCountry("USA");
        address.setZipcode("55103");
        assertNotEquals(fromDao, address);
        addressDao.updateAddress(address);
        fromDao = addressDao.getAddressById(address.getAddressId());
        assertNotNull(fromDao);
        assertEquals(fromDao, address);
    }

    /**
     * Test of deleteAddress method, of class AddressDaoImpl.
     */
    @Test
    public void testDeleteAddress() {
        //add address
        Address address = new Address();
        address.setStreet("123 main st");
        address.setCity("st.paul");
        address.setState("MN");
        address.setCountry("USA");
        address.setZipcode("55113");
        address = addressDao.addAddress(address);
        Address address1 = new Address();
        address1.setStreet("123 st.paul st");
        address1.setCity("st.paul");
        address1.setState("MN");
        address1.setCountry("USA");
        address1.setZipcode("55103");
        address1 = addressDao.addAddress(address1);
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
        //add Organization
        Organization organization = new Organization();
        organization.setAddress(address);
        organization.setEmail("heroOrganization@gmail.com");
        organization.setOrganizationName("hero organization name");
        organization.setOrganizationDescription("hero organization description");
        organization.setPhone("6513999999");
        organization.setSuperheros(superheros);
        organization = organizationDao.addOrganization(organization);
        List<Address> fromDao = addressDao.getAllAddress();
        assertEquals(2, fromDao.size());
        assertTrue(fromDao.contains(address));
        assertTrue(fromDao.contains(address1));
        addressDao.deleteAddress(address.getAddressId());
        fromDao = addressDao.getAllAddress();
        assertEquals(1, fromDao.size());
        assertTrue(fromDao.contains(address1));
        assertFalse(fromDao.contains(address));
    }

}
