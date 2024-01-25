package com.mediLaboSolutions.T2D2Patient.service.contracts;

import java.util.List;

import com.mediLaboSolutions.T2D2Patient.dto.PersonAddressAddDto;
import com.mediLaboSolutions.T2D2Patient.model.Address;

public interface IPersonAddressService {

	public List<Address> getPersonAddressesByPersonId(int personId);
	public List<Address> getPersonAddressesByPersonEmail(String personEmail);

	public Integer addPersonAddress(PersonAddressAddDto personAddressAddDto);

	public void deletePersonAddress(int personId, int addressId);
}