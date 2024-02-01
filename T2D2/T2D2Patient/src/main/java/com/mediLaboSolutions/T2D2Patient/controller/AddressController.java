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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.mediLaboSolutions.T2D2Patient.model.Address;
import com.mediLaboSolutions.T2D2Patient.service.contracts.IAddressService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AddressController {

	@Autowired
	private IAddressService iAddressService;

	@GetMapping("/addresses")
	public List<Address> getAddresses() {
		return iAddressService.getAddresses();
	}

	@GetMapping("/addresses/{addressId}")
	public Address getAddressById(@PathVariable("addressId") int addressId) {
		return iAddressService.getAddressById(addressId);
	}

	@PostMapping("/address")
	public ResponseEntity<Address> createAddress(@RequestBody Address newAddress) throws Exception {
		Address createdAddress = iAddressService.createAddress(newAddress);

		if (createdAddress == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Address>(createdAddress, HttpStatus.CREATED);
		}
	}

	@PutMapping("/addresses/{addressId}")
	public ResponseEntity<Integer> updateAddress(@PathVariable("addressId") int addressId, @RequestBody Address updatedAddress) throws Exception {
		Integer isUpdated = iAddressService.updateAddressById(addressId, updatedAddress);

		if (isUpdated == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Integer>(HttpStatus.OK);
		}
	}

	@DeleteMapping("/addresses/{addressId}")
	public void deleteAddress(@PathVariable("addressId") int addressId) {
		iAddressService.deleteAddressById(addressId);
	}
}