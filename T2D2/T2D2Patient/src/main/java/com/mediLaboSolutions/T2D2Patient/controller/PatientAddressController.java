package com.mediLaboSolutions.T2D2Patient.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.mediLaboSolutions.T2D2Patient.model.Address;
import com.mediLaboSolutions.T2D2Patient.service.contracts.IPatientAddressService;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
public class PatientAddressController {

	@Autowired
	private IPatientAddressService iPatientAddressService;
	
	@GetMapping("/{patientId}/addresses")
	public ResponseEntity<List<Address>> getPatientAddressesByPatientId(@PathVariable("patientId") int patientId) {
		List<Address> patientAddresses = iPatientAddressService.getPatientAddressesByPatientId(patientId);
		
		if (patientAddresses.isEmpty()) {
			return new ResponseEntity<List<Address>>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<Address>>(patientAddresses, HttpStatus.OK);
		}
	}
	
	@PostMapping("/{patientId}/addresses/address/add")
	public ResponseEntity<Integer> addPatientAddress(@PathVariable("patientId") int patientId, @RequestParam("id") int addressId ) {
		Integer isAssociated = iPatientAddressService.addPatientAddress(patientId, addressId);
		
		if (isAssociated == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Integer>(HttpStatus.CREATED);
		}
	}
	
	@DeleteMapping("/{patientId}/addresses/address/delete")
	public void deletePatientAddress(@PathVariable("patientId") int patientId, @RequestParam("id") int addressId) {
		iPatientAddressService.deletePatientAddress(patientId, addressId);
	}
}