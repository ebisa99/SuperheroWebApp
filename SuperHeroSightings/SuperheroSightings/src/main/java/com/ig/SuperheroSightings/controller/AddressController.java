/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ig.SuperheroSightings.controller;

import com.ig.SuperheroSightings.dao.*;
import com.ig.SuperheroSightings.entity.Address;
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
public class AddressController {

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

    Set<ConstraintViolation<Address>> violations = new HashSet<>();

    @GetMapping("addresses")
    public String displayAddresses(Model model) {
        List<Address> addresses = addressDao.getAllAddress();
        model.addAttribute("addresses", addresses);
        model.addAttribute("errors", violations);
        violations = new HashSet<>();
        return "addresses";
    }

    @PostMapping("addAddress")
    public String addAddress(Address address, HttpServletRequest request) {
        String street = request.getParameter("street");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String zipCode = request.getParameter("zipCode");
        String country = request.getParameter("country");
        address.setStreet(street);
        address.setCity(city);
        address.setState(state);
        address.setZipcode(zipCode);
        address.setCountry(country);
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(address);

        if (violations.isEmpty()) {
            addressDao.addAddress(address);
        }
        return "redirect:/addresses";
    }

    @GetMapping("deleteAddress")
    public String deleteAddress(Integer id) {
        addressDao.deleteAddress(id);
        return "redirect:/addresses";
    }

    @GetMapping("editAddress")
    public String editAddress(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Address address = addressDao.getAddressById(id);

        model.addAttribute("address", address);
        model.addAttribute("errors", violations);
        return "editAddress";
    }

    @PostMapping("editAddress")
    public String performEditAddress(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Address address = addressDao.getAddressById(id);

        address.setStreet(request.getParameter("street"));
        address.setCity(request.getParameter("city"));
        address.setState(request.getParameter("state"));
        address.setZipcode(request.getParameter("zipcode"));
        address.setCountry(request.getParameter("country"));
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(address);
        model.addAttribute("address", address);
        if (!violations.isEmpty()) {
            model.addAttribute("errors", violations);
            violations = new HashSet<>();
            return "editAddress";
        }
         addressDao.updateAddress(address);
        return "redirect:/addresses";
    }
}
