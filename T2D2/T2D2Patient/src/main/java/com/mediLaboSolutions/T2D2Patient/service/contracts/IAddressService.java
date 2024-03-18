package com.mediLaboSolutions.T2D2Patient.service.contracts;

import java.util.List;

import com.mediLaboSolutions.T2D2Patient.model.Address;

/**
 * <code>AddressService</code> interface that abstracts it from its
 * implementation in order to achieve better code modularity in compliance with
 * SOLID principles.
 *
 * @author Sébastien Cappon
 * @version 1.0
 */
public interface IAddressService {

	public List<Address> getAddresses();
	public Address getAddressById(int addressId);

	public Address createAddress(Address newAddress) throws Exception;

	public Integer updateAddressById(int addressId, Address updatedAddress) throws Exception;

	public void deleteAddressById(int addressId);
}