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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.mediLaboSolutions.T2D2Patient.model.Address;
import com.mediLaboSolutions.T2D2Patient.service.contracts.IPersonAddressService;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
public class PersonAddressController {

	@Autowired
	private IPersonAddressService iPersonAddressService;
	
	@GetMapping("/residences/persons/{personId}/addresses")
	public ResponseEntity<List<Address>> getPersonAddressesByPersonId(@PathVariable("personId") int personId) {
		List<Address> personAddresses = iPersonAddressService.getPersonAddressesByPersonId(personId);
		
		if (personAddresses.isEmpty()) {
			return new ResponseEntity<List<Address>>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<Address>>(personAddresses, HttpStatus.OK);
		}
	}
	
	@PostMapping("/residence-creation/persons/{personId}/addresses/{addressId}")
	public ResponseEntity<Integer> addPersonAddress(@PathVariable("personId") int personId, @PathVariable("addressId") int addressId ) {
		Integer isAssociated = iPersonAddressService.addPersonAddress(personId, addressId);
		
		if (isAssociated == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Integer>(HttpStatus.CREATED);
		}
	}
	
	@DeleteMapping("/residence-deletion/persons/{personId}/addresses/{addressId}")
	public void deletePersonAddress(@PathVariable("personId") int personId, @PathVariable("addressId") int addressId) {
		iPersonAddressService.deletePersonAddress(personId, addressId);
	}
}