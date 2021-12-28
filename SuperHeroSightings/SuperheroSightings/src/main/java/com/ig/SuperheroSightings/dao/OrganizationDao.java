/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ig.SuperheroSightings.dao;

import com.ig.SuperheroSightings.entity.*;
import java.util.List;

/**
 *
 * @author ebisa
 */
public interface OrganizationDao {

    Organization addOrganization(Organization organization);

    Organization getOrganizationById(int organizationId);

    List<Organization> getAllOrganizations();

    void updateOrganization(Organization organization);

    void deleteOrganization(int organizationId);

    //List<Superhero> getAllSuperherosByOrganizationId(int organizationId);

    List<Organization> getAllOrganizationsByHeroId(int heroId);
}
