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

import com.mediLaboSolutions.T2D2Patient.model.Person;
import com.mediLaboSolutions.T2D2Patient.service.contracts.IPersonService;

/**
 * A class that receives requests made from some of the usual CRUD endpoints and
 * specific URL for the Person.
 * 
 * @author Sébastien Cappon
 * @version 1.0
 */
@RestController
public class PersonController {

	@Autowired
	private IPersonService iPersonService;

	/**
	 * A <code>GetMapping</code> method on the <code>/persons</code> URI. It calls
	 * the eponymous <code>IPersonService</code> method and returns a list of
	 * <code>Person</code> model entities.
	 * 
	 * @singularity Method created for testing purposes with Postman. Could be use
	 *              for administration.
	 * 
	 * @return A <code>Person</code> list.
	 */
	@GetMapping("/persons")
	public List<Person> getPersons() {
		return iPersonService.getPersons();
	}

	/**
	 * A <code>GetMapping</code> method on the <code>/persons</code> URI with a
	 * person id as <code>PathVariable</code>. It calls the eponymous
	 * <code>IPersonService</code> method and returns the <code>Person</code> model
	 * entity whose id is the one passed in parameter.
	 * 
	 * @frontCall <code>person.service.ts</code> : <code>getPersonById(personId: number)</code>
	 * 
	 * @return A <code>Person</code>.
	 */
	@GetMapping("/persons/{personId}")
	public Person getPersonById(@PathVariable("personId") int personId) {
		return iPersonService.getPersonById(personId);
	}

	/**
	 * A <code>GetMapping</code> method on the <code>/persons/email</code> URI with a
	 * person email as <code>PathVariable</code>. It calls the eponymous
	 * <code>IPersonService</code> method and returns the <code>Person</code> model
	 * entity whose email is the one passed in parameter.
	 * 
	 * @frontCall <code>person.service.ts</code> : <code>getPersonByEmail(personEmail: string)</code>
	 * 
	 * @return A <code>Person</code>.
	 */
	@GetMapping("/persons/email/{personEmail}")
	public Person getPersonByEmail(@PathVariable("personEmail") String personEmail) {
		return iPersonService.getPersonByEmail(personEmail);
	}

	/**
	 * A <code>PostMapping</code> method on the <code>/person</code> URI with an
	 * Person model entity as <code>RequestBody</code>. It calls the eponymous
	 * <code>IPersonService</code> method and returns the <code>Person</code> added
	 * with status code 201. If the result is null, it returns status code 400.
	 * 
	 * @frontCall <code>person.service.ts</code> : <code>createNewPerson(nesPerson: Person)</code>
	 * 
	 * @throws <code>BAD_REQUEST</code> if the person already exist in the person
	 * table.
	 * 
	 * @return A <code>Person</code> and a status code.
	 */
	@PostMapping("/person")
	public ResponseEntity<Person> createPerson(@RequestBody Person newPerson) throws Exception {
		Person createdPerson = iPersonService.createPerson(newPerson);

		if (createdPerson == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Person>(createdPerson, HttpStatus.CREATED);
		}
	}

	/**
	 * A <code>PutMapping</code> method on the <code>/persons</code> URI with an
	 * person id as <code>PathVariables</code> and an <code>Person</code> as
	 * <code>RequestBody</code>. It calls the eponymous <code>IPersonService</code>
	 * method and returns an <code>Integer</code> used by the front end to determine
	 * the success of the request, with status code 200. If the result is null, it
	 * returns status code 400.
	 * 
	 * @frontCall <code>person.service.ts</code> : <code>updatePersonById(personId: number, personUpdate: Person)</code>
	 * 
	 * @throws <code>BAD_REQUEST</code> if the person concerned doesn't exist.
	 * 
	 * @return An <code>Integer</code> and a status code.
	 */
	@PutMapping("/persons/{personId}")
	public ResponseEntity<Integer> updatePersonById(@PathVariable("personId") int personId, @RequestBody Person updatedPerson) throws Exception {
		Integer isUpdated = iPersonService.updatePersonById(personId, updatedPerson);

		if (isUpdated == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Integer>(HttpStatus.OK);
		}
	}

	/**
	 * A <code>DeleteMapping</code> method on the <code>/persons</code> URI with the
	 * a person id as <code>PathVariables</code>. It calls the eponymous
	 * <code>IPersonService</code> method and returns nothing.
	 * 
	 * @singularity Method created for testing purposes with Postman. Could be use
	 *              for administration.
	 */
	@DeleteMapping("/persons/{personId}")
	public void deletePatientById(@PathVariable("personId") int personId) {
		iPersonService.deletePersonById(personId);
	}
}