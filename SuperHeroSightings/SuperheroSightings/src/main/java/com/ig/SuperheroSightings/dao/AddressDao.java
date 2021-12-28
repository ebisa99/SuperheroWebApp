/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ig.SuperheroSightings.dao;

import com.ig.SuperheroSightings.entity.Address;
import java.util.List;

/**
 *
 * @author ebisa
 */
public interface AddressDao {
    Address addAddress(Address address);
    Address getAddressById(int addressId);
    List<Address> getAllAddress();
    void updateAddress(Address address);
    void deleteAddress(int addressId);
}
