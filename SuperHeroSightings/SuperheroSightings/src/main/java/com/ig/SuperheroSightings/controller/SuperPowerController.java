/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ig.SuperheroSightings.controller;

import com.ig.SuperheroSightings.dao.*;
import com.ig.SuperheroSightings.entity.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author ebisa
 */
@Controller
public class SuperPowerController {
    Set<ConstraintViolation<SuperPower>> violations = new HashSet<>();
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
    
    @GetMapping("superPowers")
    public String displaySuperPowers(Model model) {
        List<SuperPower> superPowers = superPowerDao.getAllSuperPowers();
        model.addAttribute("superPowers", superPowers);
        model.addAttribute("errors", violations);
        violations = new HashSet<>();
        return "superPowers";
    }

    @PostMapping("addSuperPower")
    public String addSuperPower(HttpServletRequest request) {
        String superPowerName = request.getParameter("name");
        String superPowerDescription = request.getParameter("description");
        SuperPower superPower = new SuperPower();
        superPower.setSuperPowerName(superPowerName);
        superPower.setSuperPowerDescription(superPowerDescription);
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(superPower);
        if (violations.isEmpty()) {
            superPowerDao.addSuperPower(superPower);
        }
        return "redirect:/superPowers";
    }

    @GetMapping("deleteSuperPower")
    public String deleteSuperPower(Integer id) {
        superPowerDao.deleteSuperPower(id);
        return "redirect:/superPowers";
    }

    @GetMapping("editSuperPower")
    public String editSuperPower(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        SuperPower superPower = superPowerDao.getSuperPowerById(id);

        model.addAttribute("superPower", superPower);
        model.addAttribute("errors", violations);
        return "editSuperPower";
    }

    @PostMapping("editSuperPower")
    public String performEditSuperPower(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        SuperPower superPower = superPowerDao.getSuperPowerById(id);

        superPower.setSuperPowerName(request.getParameter("superPowerName"));
        superPower.setSuperPowerDescription(request.getParameter("superPowerDescription"));
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(superPower);
        model.addAttribute("superPower", superPower);
        model.addAttribute("errors", violations);
        if (violations.isEmpty()) {
            superPowerDao.updateSuperPower(superPower);
            return "redirect:/superPowers";
        }
        violations = new HashSet<>();
        return "editSuperPower";
    }
}
