/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ig.SuperheroSightings.controller;

import com.ig.SuperheroSightings.dao.*;
import com.ig.SuperheroSightings.entity.Address;
import com.ig.SuperheroSightings.entity.Location;
import com.ig.SuperheroSightings.entity.Organization;
import com.ig.SuperheroSightings.entity.Superhero;
import java.util.ArrayList;
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
public class OrganizationController {

    Set<ConstraintViolation<Organization>> violations = new HashSet<>();

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

    @GetMapping("organizations")
    public String displayOrganizations(Model model) {
        List<Organization> organizations = organizationDao.getAllOrganizations();
        List<Address> addresses = addressDao.getAllAddress();
        List<Superhero> superheroes = superheroDao.getAllSuperhero();
        model.addAttribute("organizations", organizations);
        model.addAttribute("addresses", addresses);
        model.addAttribute("superheroes", superheroes);
        model.addAttribute("errors", violations);
        violations = new HashSet<>();
        return "organizations";
    }

    @PostMapping("addOrganization")
    public String addOrganization(Organization organization, HttpServletRequest request) {
        String phone = request.getParameter("phone");
        phone = phone.trim();
        phone = phone.replaceAll("-", "");
        phone = phone.replaceAll("/", "");
        organization.setPhone(phone);
        String addressId = request.getParameter("addressId");
        String[] superheroIds = request.getParameterValues("heroId");

        organization.setAddress(addressDao.getAddressById(Integer.parseInt(addressId)));

        List<Superhero> superheroes = new ArrayList<>();
        if (superheroIds != null) {
            for (String superheroId : superheroIds) {
                superheroes.add(superheroDao.getSuperheroById(Integer.parseInt(superheroId)));
            }
        }
        organization.setSuperheros(superheroes);
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(organization);
        if (violations.isEmpty()) {
            organizationDao.addOrganization(organization);
        }

        return "redirect:/organizations";
    }

    @GetMapping("organizationDetail")
    public String organizationDetail(Integer id, Model model) {
        Organization organization = organizationDao.getOrganizationById(id);
        model.addAttribute("organization", organization);
        return "organizationDetail";
    }

    @GetMapping("deleteOrganization")
    public String deleteOrganization(Integer id) {
        organizationDao.deleteOrganization(id);
        return "redirect:/organizations";
    }

    @GetMapping("editOrganization")
    public String editOrganization(Integer id, Model model) {
        Organization organization = organizationDao.getOrganizationById(id);
        List<Superhero> superheroes = superheroDao.getAllSuperhero();
        List<Address> addresses = addressDao.getAllAddress();
        model.addAttribute("organization", organization);
        model.addAttribute("superheroes", superheroes);
        model.addAttribute("addresses", addresses);
        return "editOrganization";
    }

    @PostMapping("editOrganization")
    public String performEditOrganization(@Valid Organization organization,
            BindingResult result, HttpServletRequest request, Model model) {
        String name = request.getParameter("organizationName");
        String description = request.getParameter("organizationDescription");
        String phone = request.getParameter("phones");
        phone = phone.trim();
        phone = phone.replaceAll("-", "");
        phone = phone.replaceAll("/", "");
        organization.setOrganizationName(name);
        organization.setOrganizationDescription(description);
        organization.setPhone(phone);
        String addressId = request.getParameter("addressId");
        String[] superheroIds = request.getParameterValues("heroId");

        organization.setAddress(addressDao.getAddressById(Integer.parseInt(addressId)));

        List<Superhero> superheroes = new ArrayList<>();
        if (superheroIds != null) {
            for (String superheroId : superheroIds) {
                superheroes.add(superheroDao.getSuperheroById(Integer.parseInt(superheroId)));
            }
        }
        organization.setSuperheros(superheroes);
        if (result.hasErrors()) {
            model.addAttribute("superheroes", superheroDao.getAllSuperhero());
            model.addAttribute("addresses", addressDao.getAllAddress());
            model.addAttribute("organization", organization);
            model.addAttribute("errors", violations);
            violations = new HashSet<>();
            return "editOrganization";
        }
        organizationDao.updateOrganization(organization);

        return "redirect:/organizations";
    }
}
