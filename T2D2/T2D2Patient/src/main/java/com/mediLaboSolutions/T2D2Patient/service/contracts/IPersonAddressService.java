package com.mediLaboSolutions.T2D2Patient.service.contracts;

import java.util.List;

import com.mediLaboSolutions.T2D2Patient.dto.PersonAddressAddDto;
import com.mediLaboSolutions.T2D2Patient.model.Address;

/**
 * <code>PersonAddressService</code> interface that abstracts it from its
 * implementation in order to achieve better code modularity in compliance with
 * SOLID principles.
 *
 * @author Sébastien Cappon
 * @version 1.0
 */
public interface IPersonAddressService {

	public List<Address> getPersonAddressesByPersonId(int personId);
	public List<Address> getPersonAddressesByPersonEmail(String personEmail);

	public Integer addPersonAddress(PersonAddressAddDto personAddressAddDto);

	public void deletePersonAddress(int personId, int addressId);
}