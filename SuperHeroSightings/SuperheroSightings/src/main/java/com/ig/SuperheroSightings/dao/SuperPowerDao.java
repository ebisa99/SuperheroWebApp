/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ig.SuperheroSightings.dao;

import com.ig.SuperheroSightings.entity.SuperPower;
import java.util.List;

/**
 *
 * @author ebisa
 */
public interface SuperPowerDao {
    SuperPower addSuperPower(SuperPower superPower);
    SuperPower getSuperPowerById(int superPowerId);
    List<SuperPower> getAllSuperPowers();
    void updateSuperPower(SuperPower superPower);
    void deleteSuperPower(int superPowerId);
}
