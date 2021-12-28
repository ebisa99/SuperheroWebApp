/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ig.SuperheroSightings.controller;

import com.ig.SuperheroSightings.dao.*;
import com.ig.SuperheroSightings.entity.Address;
import com.ig.SuperheroSightings.entity.Location;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author ebisa
 */
@Controller
public class LocationController {

    Set<ConstraintViolation<Location>> violations = new HashSet<>();

    @Autowired
    AddressDao addressDao;

    @Autowired
    SuperPowerDao superPowerDao;

    @Autowired
    SuperheroDao superheroDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    SightingsDao sightingsDao;

    @GetMapping("locations")
    public String displaylocations(Model model) {
        List<Location> locations = locationDao.getAllLocations();
        List<Address> addresses = addressDao.getAllAddress();
        model.addAttribute("locations", locations);
        model.addAttribute("addresses", addresses);
        model.addAttribute("errors", violations);
        violations = new HashSet<>();
        return "locations";
    }

    @PostMapping("addLocation")
    public String addLocation(Location location, HttpServletRequest request) {
        String addressId = request.getParameter("addressId");
        location.setAddress(addressDao.getAddressById(Integer.parseInt(addressId)));

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(location);
        if (violations.isEmpty()) {
            locationDao.addLocation(location);
        }
        return "redirect:/locations";
    }

    @GetMapping("locationDetail")
    public String locationDetail(Integer id, Model model) {
        Location location = locationDao.getLocationById(id);
        model.addAttribute("location", location);
        return "locationDetail";
    }

    @GetMapping("deleteLocation")
    public String deleteLocation(Integer id) {
        locationDao.deleteLocation(id);
        return "redirect:/locations";
    }

    @GetMapping("editLocation")
    public String editLocation(Integer id, Model model) {
        Location location = locationDao.getLocationById(id);
        List<Address> addresses = addressDao.getAllAddress();
        model.addAttribute("location", location);
        model.addAttribute("addresses", addresses);
        return "editLocation";
    }

    @PostMapping("editLocation")
    public String performEditLocation(@Valid Location location, BindingResult result,
            HttpServletRequest request, Model model) {
        String addressId = request.getParameter("addressId");
 
        location.setAddress(addressDao.getAddressById(Integer.parseInt(addressId)));
        if (result.hasErrors()) {
            model.addAttribute("addresses", addressDao.getAllAddress());
            model.addAttribute("location", location);
            model.addAttribute("errors", violations);
            violations = new HashSet<>();
            return "editLocation";
        }
        locationDao.updateLocation(location);
        return "redirect:/locations";
    }
}
