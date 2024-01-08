package com.mediLaboSolutions.T2D2Patient.service.contracts;

import java.util.List;

import com.mediLaboSolutions.T2D2Patient.model.Address;

public interface IPatientAddressService {

	public List<Address> getPatientAddressesByPatientId(int patientId);
	
	public Integer addPatientAddress(int patientId, int addressId);
	
	public void deletePatientAddress(int patientId, int addressId);
}