package com.mediLaboSolutions.T2D2Patient.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

/**
 * A class that receives requests made from some of the usual CRUD endpoints and
 * specific URL for the Address.
 * 
 * @author Sébastien Cappon
 * @version 1.0
 */
@RestController
public class AddressController {

	@Autowired
	private IAddressService iAddressService;

	/**
	 * A <code>GetMapping</code> method on the <code>/addresses</code> URI. It calls
	 * the eponymous <code>IAddressService</code> method and returns a list of
	 * <code>Address</code> model entities.
	 * 
	 * @singularity Method created for testing purposes with Postman. Could be use
	 *              for administration.
	 * 
	 * @return A <code>Address</code> list.
	 */
	@GetMapping("/addresses")
	public List<Address> getAddresses() {
		return iAddressService.getAddresses();
	}

	/**
	 * A <code>GetMapping</code> method on the <code>/addresses</code> URI with an
	 * address id as <code>PathVariable</code>. It calls the eponymous
	 * <code>IAddressService</code> method and returns the <code>Address</code>
	 * model entity whose id is the one passed in parameter.
	 * 
	 * @singularity Method created for testing purposes with Postman. Could be use
	 *              for administration.
	 * 
	 * @return An <code>Address</code>.
	 */
	@GetMapping("/addresses/{addressId}")
	public Address getAddressById(@PathVariable("addressId") int addressId) {
		return iAddressService.getAddressById(addressId);
	}

	/**
	 * A <code>PostMapping</code> method on the <code>/address</code> URI with an
	 * Address model entity as <code>RequestBody</code>. It calls the eponymous
	 * <code>IAddressService</code> method and returns the <code>Address</code>
	 * added with status code 201. If the result is null, it returns status code
	 * 400.
	 * 
	 * @frontCall <code>address.service.ts</code> : <code>createNewAddress(newAddress: Address)</code>
	 * 
	 * @throws <code>BAD_REQUEST</code> if the address already exist in the address
	 * table.
	 * 
	 * @return A <code>Address</code> and a status code.
	 */
	@PostMapping("/address")
	public ResponseEntity<Address> createAddress(@RequestBody Address newAddress) throws Exception {
		Address createdAddress = iAddressService.createAddress(newAddress);

		if (createdAddress == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Address>(createdAddress, HttpStatus.CREATED);
		}
	}

	/**
	 * A <code>PutMapping</code> method on the <code>/addresses</code> URI with an
	 * address id as <code>PathVariables</code> and an <code>Address</code> as
	 * <code>RequestBody</code>. It calls the eponymous <code>IAddressService</code>
	 * method and returns an <code>Integer</code> used by the front end to determine
	 * the success of the request, with status code 200. If the result is null, it
	 * returns status code 400.
	 * 
	 * @frontCall <code>address.service.ts</code> : <code>updateAddressById(addressId: number, addressesUpdate: Address)</code>
	 * 
	 * @throws <code>BAD_REQUEST</code> if the address concerned doesn't exist.
	 * 
	 * @return An <code>Integer</code> and a status code.
	 */
	@PutMapping("/addresses/{addressId}")
	public ResponseEntity<Integer> updateAddress(@PathVariable("addressId") int addressId, @RequestBody Address updatedAddress) throws Exception {
		Integer isUpdated = iAddressService.updateAddressById(addressId, updatedAddress);

		if (isUpdated == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Integer>(HttpStatus.OK);
		}
	}

	/**
	 * A <code>DeleteMapping</code> method on the <code>/addresses</code> URI
	 * with the an address id as <code>PathVariables</code>. It calls the
	 * eponymous <code>IAddressService</code> method and returns nothing.
	 * 
	 * @singularity Method created for testing purposes with Postman. Could be use
	 *              for administration.
	 */
	@DeleteMapping("/addresses/{addressId}")
	public void deleteAddress(@PathVariable("addressId") int addressId) {
		iAddressService.deleteAddressById(addressId);
	}
}