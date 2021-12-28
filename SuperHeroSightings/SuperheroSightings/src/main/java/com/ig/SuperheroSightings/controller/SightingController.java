/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ig.SuperheroSightings.controller;

import com.ig.SuperheroSightings.dao.*;
import com.ig.SuperheroSightings.entity.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
public class SightingController {

    Set<ConstraintViolation<Sightings>> violations = new HashSet<>();

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

    @GetMapping("sightings")
    public String displaySightings(Model model) {
        List<Sightings> sightings = sightingsDao.getAllSightings();
        List<Superhero> superheroes = superheroDao.getAllSuperhero();
        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("sightings", sightings);
        model.addAttribute("superheroes", superheroes);
        model.addAttribute("locations", locations);
        model.addAttribute("errors", violations);
        violations = new HashSet<>();
        return "sightings";
    }

    @GetMapping
    public String displayTheLatest10Sightings(Model model) {
        List<Sightings> sightings = sightingsDao.getTheLatest10Sightings();
        List<Superhero> superheroes = superheroDao.getAllSuperhero();
        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("sightings", sightings);
        model.addAttribute("superheroes", superheroes);
        model.addAttribute("locations", locations);
        return "Index";
    }

    @PostMapping("addSighting")
    public String addSighting(Sightings sighting, HttpServletRequest request, Model model) {
        String date = request.getParameter("dates");
        String locationId = request.getParameter("locationId");
        String superheroId = request.getParameter("heroId");
        if (date.equals("")) {
            sighting.setDate(null);
        } else {
            sighting.setDate(LocalDate.parse(date));
        }
        sighting.setSuperhero(superheroDao.getSuperheroById(Integer.parseInt(superheroId)));
        sighting.setLocation(locationDao.getLocationById(Integer.parseInt(locationId)));

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(sighting);
        if (violations.isEmpty()) {
            sightingsDao.addSighting(sighting);
        }
        return "redirect:/sightings";
    }

    @GetMapping("sightingDetail")
    public String sightingDetail(Integer id, Model model) {
        Sightings sighting = sightingsDao.getSightingById(id);
        model.addAttribute("sighting", sighting);
        return "sightingDetail";
    }

    @GetMapping("deleteSighting")
    public String deleteSighting(Integer id) {
        sightingsDao.deleteSighting(id);
        return "redirect:/sightings";
    }

    @GetMapping("editSighting")
    public String editSighting(Integer id, Model model) {
        Sightings sighting = sightingsDao.getSightingById(id);
        List<Superhero> superheroes = superheroDao.getAllSuperhero();
        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("sighting", sighting);
        model.addAttribute("superheroes", superheroes);
        model.addAttribute("locations", locations);
        model.addAttribute("errors", violations);
        return "editSighting";
    }

    @PostMapping("editSighting")
    public String performEditSighting(Sightings sighting, HttpServletRequest request, Model model) {
        String date = request.getParameter("dates");
        String locationId = request.getParameter("locationId");
        String superheroId = request.getParameter("heroId");
        sighting.setDate(LocalDate.parse(date));
        sighting.setSuperhero(superheroDao.getSuperheroById(Integer.parseInt(superheroId)));
        sighting.setLocation(locationDao.getLocationById(Integer.parseInt(locationId)));
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(sighting);
        if (!violations.isEmpty()) {
            model.addAttribute("errors", violations);
            model.addAttribute("locations", locationDao.getAllLocations());
            model.addAttribute("superheroes", superheroDao.getAllSuperhero());
            model.addAttribute("sighting", sighting);
            violations = new HashSet<>();
            return "editSighting";
        }
        sightingsDao.updateSighting(sighting);

        return "redirect:/sightings";
    }
}
