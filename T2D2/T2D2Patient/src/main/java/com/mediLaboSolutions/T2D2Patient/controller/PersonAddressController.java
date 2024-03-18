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

/**
 * A class that receives requests made from some of the usual CRUD endpoints and
 * specific URL for the persons residences. Residences are the relationship
 * between persons and addresses. In the database, they are represented by a
 * join table : person_address
 * 
 * @author Sébastien Cappon
 * @version 1.0
 */
@RestController
public class PersonAddressController {

	@Autowired
	private IPersonAddressService iPersonAddressService;

	/**
	 * A <code>GetMapping</code> method on the
	 * <code>/residences/persons/{personId}/addresses</code> URI with a person id as
	 * <code>PathVariable</code>. It calls the eponymous
	 * <code>IPersonAddressService</code> method and returns a list of
	 * <code>Address</code> model entities whose id is attached, in the database, to
	 * the one passed in parameter.
	 * 
	 * @frontCall <code>residence.service.ts</code> : <code>getResidencesByPersonId(personId: number)</code>
	 * 
	 * @return A <code>Address</code> list.
	 */
	@GetMapping("/residences/persons/{personId}/addresses")
	public List<Address> getPersonAddressesByPersonId(@PathVariable("personId") int personId) {
		return iPersonAddressService.getPersonAddressesByPersonId(personId);
	}

	/**
	 * A <code>GetMapping</code> method on the
	 * <code>/residences/persons/email/{personEmail}/addresses</code> URI with a
	 * person email as <code>PathVariable</code>. It calls the eponymous
	 * <code>IPersonAddressService</code> method and returns a list of
	 * <code>Address</code> model entities whose id is attached, in the database, to
	 * the id of the person is passed as parameter.
	 * 
	 * @frontCall <code>residence.service.ts</code> : <code>getResidencesByPersonEmail(personEmail: string)</code>
	 * 
	 * @return A <code>Address</code> list.
	 */
	@GetMapping("/residences/persons/email/{personEmail}/addresses")
	public List<Address> getPersonAddressesByPersonEmail(@PathVariable("personEmail") String personEmail) {
		return iPersonAddressService.getPersonAddressesByPersonEmail(personEmail);
	}

	/**
	 * A <code>PostMapping</code> method on the <code>/residence</code> URI with an
	 * PersonAddressAddDto DTO as <code>RequestBody</code>. It calls the eponymous
	 * <code>IPersonAddressService</code> method and returns an <code>Integer</code>
	 * used by the front end to determine the success of the request with status
	 * code 201. If the result is null, it returns status code 400.
	 * 
	 * @frontCall <code>residence.service.ts</code> : <code>addResidence(residenceValue: ResidenceValue)</code>
	 * 
	 * @throws <code>BAD_REQUEST</code> if the residence already exist in the
	 * person_address table.
	 * 
	 * @return An <code>Integer</code> and a status code.
	 */
	@PostMapping("/residence")
	public ResponseEntity<Integer> addPersonAddress(@RequestBody PersonAddressAddDto personAddressAddDto) {
		Integer isAssociated = iPersonAddressService.addPersonAddress(personAddressAddDto);

		if (isAssociated == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Integer>(HttpStatus.CREATED);
		}
	}

	/**
	 * A <code>DeleteMapping</code> method on the
	 * <code>/residences/persons/{personId}/addresses/</code> URI with the a person
	 * id and an address id as <code>PathVariables</code>. It calls the eponymous
	 * <code>IPersonAddressService</code> method and returns nothing.
	 * 
	 * @singularity Method created for testing purposes with Postman. Could be use
	 *              for administration.
	 */
	@DeleteMapping("/residences/persons/{personId}/addresses/{addressId}")
	public void deletePersonAddress(@PathVariable("personId") int personId, @PathVariable("addressId") int addressId) {
		iPersonAddressService.deletePersonAddress(personId, addressId);
	}
}