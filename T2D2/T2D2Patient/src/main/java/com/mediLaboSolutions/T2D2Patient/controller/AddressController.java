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
	public ResponseEntity<List<Address>> getAddresses() {
		List<Address> addressList = iAddressService.getAddresses();

		if (addressList.isEmpty()) {
			return new ResponseEntity<List<Address>>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<Address>>(addressList, HttpStatus.OK);
		}
	}

	@GetMapping("/addresses/{addressId}")
	public ResponseEntity<Address> getAddressById(@PathVariable("addressId") int addressId) {
		Address address = iAddressService.getAddressById(addressId);

		if (address == null) {
			return new ResponseEntity<Address>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<Address>(address, HttpStatus.OK);
		}
	}

	@PostMapping("/address-creation")
	public ResponseEntity<Address> createAddress(@RequestBody Address newAddress) throws Exception {
		Address createdAddress = iAddressService.createAddress(newAddress);

		if (createdAddress == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Address>(createdAddress, HttpStatus.CREATED);
		}
	}

	@PutMapping("/address-edition/{addressId}")
	public ResponseEntity<Integer> updateAddress(@PathVariable("addressId") int addressId, @RequestBody Address updatedAddress) throws Exception {
		Integer isUpdated = iAddressService.updateAddressById(addressId, updatedAddress);

		if (isUpdated == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Integer>(HttpStatus.OK);
		}
	}

	@DeleteMapping("/address-deletion/{addressId}")
	public void deleteAddress(@PathVariable("addressId") int addressId) {
		iAddressService.deleteAddressById(addressId);
	}
}