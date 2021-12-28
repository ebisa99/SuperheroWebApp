/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ig.SuperheroSightings.controller;

import com.ig.SuperheroSightings.dao.*;
import com.ig.SuperheroSightings.entity.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ebisa
 */
@Controller
public class SuperheroController {

    @Autowired
    ImageDao imageDao;

    private final String SUPERHERO_UPLOAD_DIR = "Superheroes";

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
    Set<ConstraintViolation<Superhero>> violations = new HashSet<>();

    @GetMapping("superheroes")
    public String displaySuperheroes(Model model) {
        List<Superhero> superheroes = superheroDao.getAllSuperhero();
        List<SuperPower> superPowers = superPowerDao.getAllSuperPowers();
        model.addAttribute("superheroes", superheroes);
        model.addAttribute("superPowers", superPowers);
        model.addAttribute("errors", violations);
        violations = new HashSet<>();
        return "superheroes";
    }

    @PostMapping("addSuperhero")
    public String addSuperhero(Superhero superhero, HttpServletRequest request,
            @RequestParam("file") MultipartFile file) {
        String fileLocation = imageDao.saveImage(file, Long.toString(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)), SUPERHERO_UPLOAD_DIR);
        boolean hero = false;
        String superPowerId = request.getParameter("superPowerId");
        String selection = request.getParameter("herovillain");
        if (selection.equals("hero")) {
            hero = true;
        }
        superhero.setIsHero(hero);
        superhero.setSuperPower(superPowerDao.getSuperPowerById(Integer.parseInt(superPowerId)));
        superhero.setImageFile(fileLocation);
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(superhero);
        if (violations.isEmpty()) {
            superheroDao.addSuperhero(superhero);
        }

        return "redirect:/superheroes";
    }

    @GetMapping("superheroDetail")
    public String superheroDetail(Integer id, Model model) {
        Superhero superhero = superheroDao.getSuperheroById(id);
        model.addAttribute("superhero", superhero);
        return "superheroDetail";
    }

    @GetMapping("deleteSuperhero")
    public String deleteSuperhero(Integer id) {
        Superhero superhero = superheroDao.getSuperheroById(id);
        superheroDao.deleteSuperhero(id);
        imageDao.deleteImage(superhero.getImageFile());
        return "redirect:/superheroes";
    }

    @GetMapping("editSuperhero")
    public String editSuperhero(Integer id, Model model) {
        Superhero superhero = superheroDao.getSuperheroById(id);
        List<SuperPower> superPowers = superPowerDao.getAllSuperPowers();
        model.addAttribute("superhero", superhero);
        model.addAttribute("superPowers", superPowers);
        model.addAttribute("errors", violations);
        return "editSuperhero";
    }

    @PostMapping("editSuperhero")
    public String performEditSuperhero(Superhero superhero,
            HttpServletRequest request, Model model) {

        boolean isHero = false;
        String superPowerId = request.getParameter("superPowerId");
        String selection = request.getParameter("herovillain");
        if (selection.equals("hero")) {
            isHero = true;
        }
        superhero.setIsHero(isHero);
        superhero.setSuperPower(superPowerDao.getSuperPowerById(Integer.parseInt(superPowerId)));
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(superhero);
        if (!violations.isEmpty()) {
            model.addAttribute("superPowers", superPowerDao.getAllSuperPowers());
            model.addAttribute("superhero", superhero);
            model.addAttribute("errors", violations);
            violations = new HashSet<>();
            return "editSuperhero";
        }

        //Have to look up Superhero by ID because the image URL doesn't come from the page with @Valid
        //superhero = superheroDao.getSuperheroById(superhero.getHeroId());
        //superhero.setImageFile(imageDao.updateImage(file, superhero.getImageFile(), SUPERHERO_UPLOAD_DIR));
        superheroDao.updateSuperhero(superhero);
        return "redirect:/superheroes";
    }
}
