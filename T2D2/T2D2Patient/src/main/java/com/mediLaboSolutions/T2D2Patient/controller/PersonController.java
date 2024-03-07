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

@RestController
public class PersonController {

	@Autowired
	private IPersonService iPersonService;

	@GetMapping("/persons")
	public List<Person> getPersons() {
		return iPersonService.getPersons();
	}

	@GetMapping("/persons/{personId}")
	public Person getPersonById(@PathVariable("personId") int personId) {
		return iPersonService.getPersonById(personId);
	}

	@GetMapping("/persons/email/{personEmail}")
	public Person getPersonByEmail(@PathVariable("personEmail") String personEmail) {
		return iPersonService.getPersonByEmail(personEmail);
	}

	@PostMapping("/person")
	public ResponseEntity<Person> createPerson(@RequestBody Person newPerson) throws Exception {
		Person createdPerson = iPersonService.createPerson(newPerson);

		if (createdPerson == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Person>(createdPerson, HttpStatus.CREATED);
		}
	}

	@PutMapping("/persons/{personId}")
	public ResponseEntity<Integer> updatePersonById(@PathVariable("personId") int personId, @RequestBody Person updatedPerson) throws Exception {
		Integer isUpdated = iPersonService.updatePersonById(personId, updatedPerson);

		if (isUpdated == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Integer>(HttpStatus.OK);
		}
	}

	@DeleteMapping("/persons/{personId}")
	public void deletePatientById(@PathVariable("personId") int personId) {
		iPersonService.deletePersonById(personId);
	}
}