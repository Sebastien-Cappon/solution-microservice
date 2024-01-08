package com.mediLaboSolutions.T2D2Patient.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mediLaboSolutions.T2D2Patient.model.Address;
import com.mediLaboSolutions.T2D2Patient.model.Patient;
import com.mediLaboSolutions.T2D2Patient.model.PatientAddress;
import com.mediLaboSolutions.T2D2Patient.repository.IAddressRepository;
import com.mediLaboSolutions.T2D2Patient.repository.IPatientAddressRepository;
import com.mediLaboSolutions.T2D2Patient.repository.IPatientRepository;
import com.mediLaboSolutions.T2D2Patient.service.contracts.IPatientAddressService;
import com.mediLaboSolutions.T2D2Patient.util.PatientAddressBuilder;


@Service
public class PatientAddressService implements IPatientAddressService {
	
	@Autowired
	IPatientAddressRepository iPatientAddressRepository;
	@Autowired
	IPatientRepository iPatientRepository;
	@Autowired
	IAddressRepository iAddressRepository;
	
	@Override
	public List<Address> getPatientAddressesByPatientId(int patientId) {
		List<PatientAddress> patientAddressesByPatientId = iPatientAddressRepository.getPatientAddressesByPatientId(patientId);
		List<Address> patientAddresses = new ArrayList<>();
		
		for (PatientAddress patientAddress : patientAddressesByPatientId) {
			patientAddresses.add(patientAddress.getId().getAddress());
		}
	
		return patientAddresses;
	}
	
	@Override
	public Integer addPatientAddress(int patientId, int addressId) {
		if(iPatientRepository.findById(patientId).isPresent() && iAddressRepository.findById(addressId).isPresent()) {
			Patient patient = iPatientRepository.findById(patientId).get();
			Address address = iAddressRepository.findById(addressId).get();
			
			PatientAddress newPatientAddress = PatientAddressBuilder.createPatientAddress(patient, address);	
			iPatientAddressRepository.save(newPatientAddress);
			
			return 1;
		}
		
		return null;
	}
	
	@Override
	public void deletePatientAddress(int patientId, int addressId) {
		if(iPatientRepository.findById(patientId).isPresent() && iAddressRepository.findById(addressId).isPresent()) {
			Patient patient = iPatientRepository.findById(patientId).get();
			Address address = iAddressRepository.findById(addressId).get();
			
			PatientAddress patientAddressToDelete = PatientAddressBuilder.createPatientAddress(patient, address);	
			iPatientAddressRepository.delete(patientAddressToDelete);
		}	
	}
}