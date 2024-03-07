package com.mediLaboSolutions.T2D2Patient.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.mediLaboSolutions.T2D2Patient.dto.PersonAddressAddDto;
import com.mediLaboSolutions.T2D2Patient.model.Address;
import com.mediLaboSolutions.T2D2Patient.service.contracts.IPersonAddressService;

@RestController
public class PersonAddressController {

	@Autowired
	private IPersonAddressService iPersonAddressService;

	@GetMapping("/residences/persons/{personId}/addresses")
	public List<Address> getPersonAddressesByPersonId(@PathVariable("personId") int personId) {
		return iPersonAddressService.getPersonAddressesByPersonId(personId);
	}

	@GetMapping("/residences/persons/email/{personEmail}/addresses")
	public List<Address> getPersonAddressesByPersonEmail(@PathVariable("personEmail") String personEmail) {
		return iPersonAddressService.getPersonAddressesByPersonEmail(personEmail);
	}

	@PostMapping("/residence")
	public ResponseEntity<Integer> addPersonAddress(@RequestBody PersonAddressAddDto personAddressAddDto) {
		Integer isAssociated = iPersonAddressService.addPersonAddress(personAddressAddDto);

		if (isAssociated == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Integer>(HttpStatus.CREATED);
		}
	}

	@DeleteMapping("/residences/persons/{personId}/addresses/{addressId}")
	public void deletePersonAddress(@PathVariable("personId") int personId, @PathVariable("addressId") int addressId) {
		iPersonAddressService.deletePersonAddress(personId, addressId);
	}
}