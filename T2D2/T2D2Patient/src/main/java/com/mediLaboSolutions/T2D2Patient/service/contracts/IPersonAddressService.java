package com.mediLaboSolutions.T2D2Patient.service.contracts;

import java.util.List;

import com.mediLaboSolutions.T2D2Patient.model.Address;

public interface IPersonAddressService {

	public List<Address> getPersonAddressesByPersonId(int personId);
	
	public Integer addPersonAddress(int personId, int addressId);
	
	public void deletePersonAddress(int personId, int addressId);
}