package com.mediLaboSolutions.T2D2Authentication.controller;

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

import com.fasterxml.jackson.annotation.JsonView;
import com.mediLaboSolutions.T2D2Authentication.dto.PractitionerLoginDto;
import com.mediLaboSolutions.T2D2Authentication.model.Practitioner;
import com.mediLaboSolutions.T2D2Authentication.service.contracts.IPractitionerService;
import com.mediLaboSolutions.T2D2Authentication.view.PractitionerView;

/**
 * A class that receives requests made from some of the usual CRUD endpoints and
 * specific URL for the Practitioner.
 * 
 * @singularity Some responses are filtered using JsonView to match the response
 *              pattern desired by the front-end.
 * 
 * @author Sébastien Cappon
 * @version 1.0
 */
@RestController
public class PractitionerController {

	@Autowired
	private IPractitionerService iPractitionerService;

	/**
	 * A <code>GetMapping</code> method on the <code>/practitioners</code> URI. It
	 * calls the eponymous <code>IPractitionerService</code> method and returns a
	 * list of <code>Practitioner</code> model entities.
	 * 
	 * @singularity Method created for testing purposes with Postman. Could be use
	 *              for administration.
	 * 
	 * @return A <code>Practitioner</code> list.
	 */
	@GetMapping("/practitioners")
	public List<Practitioner> getPractitioners() {
		return iPractitionerService.getPractitioners();
	}

	/**
	 * A <code>GetMapping</code> method on the <code>/practitioners</code> URI with
	 * a practitioner id as <code>PathVariable</code>. It calls the eponymous
	 * <code>IPractitionerService</code> method and returns the
	 * <code>Practitioner</code> model entity whose id is the one passed in
	 * parameter.
	 * 
	 * @frontCall <code>auth.service.ts</code> : <code>getPractitionerById(practitionerId: number)</code>
	 * 
	 * @return A <code>Practitioner</code>.
	 */
	@GetMapping("/practitioners/{practitionerId}")
	public Practitioner getPractitionerById(@PathVariable("practitionerId") int practitionerId) {
		return iPractitionerService.getPractitionerById(practitionerId);
	}

	/**
	 * A <code>PostMapping</code> method on the <code>/login</code> URI with a DTO,
	 * consisting of the active user email and non-hashed password, as
	 * <code>RequestBody</code>. It calls the eponymous
	 * <code>IPractitionerService</code> method and returns the <code>Practitioner</code> 
	 * added with status code 200. If the result is null, it returns status code 400.
	 * 
	 * @frontCall <code>auth.service.ts</code> : <code>login(authValue: AuthValue)</code>
	 * 
	 * @view Display id, lastname and firstname for the involved
	 *       <code>Practitioner</code> model entities.
	 * 
	 * @throws <code>BAD_REQUEST</code> if the user concerned doesn't exist or the
	 * password doesn't fit to its hashed version.
	 * 
	 * @return A <code>Practitioner</code> and a status code.
	 */
	@JsonView(PractitionerView.LoginView.class)
	@PostMapping("/login")
	public ResponseEntity<Practitioner> connectPractitionerWithEmailAndPassword(@RequestBody PractitionerLoginDto practitionerLoginDto) throws Exception {
		Practitioner loggedPractitioner = iPractitionerService.connectPractitionerWithEmailAndPassword(practitionerLoginDto);

		if (loggedPractitioner == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Practitioner>(loggedPractitioner, HttpStatus.OK);
		}
	}

	/**
	 * A <code>PostMapping</code> method on the <code>/practitioner</code> URI with
	 * a Practitioner model entity as <code>RequestBody</code>. It calls the
	 * eponymous <code>IPractitionerService</code> method and returns the
	 * <code>Practitioner</code> added with status code 201. If the result is null,
	 * it returns status code 400.
	 * 
	 * @frontCall <code>new-account.service.ts</code> : <code>createNewAccount(newPractitionerValue: NewPractitionerValue)</code>
	 * 
	 * @throws <code>BAD_REQUEST</code> if the user concerned email already exist in
	 * the practitioner table.
	 * 
	 * @return A <code>Practitioner</code> and a status code.
	 */
	@PostMapping("/practitioner")
	public ResponseEntity<Practitioner> createPractitioner(@RequestBody Practitioner newPractitioner) throws Exception {
		Practitioner practitioner = iPractitionerService.createPractitioner(newPractitioner);

		if (practitioner == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Practitioner>(practitioner, HttpStatus.CREATED);
		}
	}

	/**
	 * A <code>PutMapping</code> method on the <code>/practitioners</code> URI with
	 * a practitioner id as <code>PathVariables</code> and a
	 * <code>Practitioner</code> as <code>RequestBody</code>. It calls the eponymous
	 * <code>IPractitionerService</code> method and returns an <code>Integer</code>
	 * used by the front end to determine the success of the request, with status
	 * code 200. If the result is null, it returns status code 400.
	 * 
	 * @singularity Method created for testing purposes with Postman. Could be use
	 *              for administration.
	 * 
	 * @throws <code>BAD_REQUEST</code> if the practitoner concerned doesn't exist.
	 * 
	 * @return An <code>Integer</code> and a status code.
	 */
	@PutMapping("/practitioners/{practitionerId}")
	public ResponseEntity<Integer> updatePractitionerById(@PathVariable("practitionerId") int practitionerId, @RequestBody Practitioner updatedPractitioner) throws Exception {
		Integer isUpdated = iPractitionerService.updatePractitionerById(practitionerId, updatedPractitioner);

		if (isUpdated == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Integer>(HttpStatus.OK);
		}
	}

	/**
	 * A <code>DeleteMapping</code> method on the <code>/practitioners</code> URI
	 * with the a practitioner id as <code>PathVariables</code>. It calls the
	 * eponymous <code>IPractitionerService</code> method and returns nothing.
	 * 
	 * @singularity Method created for testing purposes with Postman. Could be use
	 *              for administration.
	 */
	@DeleteMapping("/practitioners/{practitionerId}")
	public void deletePractitionerById(@PathVariable("practitionerId") int practitionerId) {
		iPractitionerService.deletePractitionerById(practitionerId);
	}
}