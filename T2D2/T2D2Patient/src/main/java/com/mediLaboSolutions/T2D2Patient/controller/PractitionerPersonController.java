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

import com.mediLaboSolutions.T2D2Patient.model.Person;
import com.mediLaboSolutions.T2D2Patient.service.contracts.IPractitionerPersonService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PractitionerPersonController {

	@Autowired
	IPractitionerPersonService iPractitionerPersonService;
	
	@GetMapping("/patient/practitioners/{practitionerId}/persons")
	public ResponseEntity<List<Person>> getPersonsByPractitionerId(@PathVariable("practitionerId") int practitionerId) {
		List<Person> persons = iPractitionerPersonService.getPersonsByPractitionerId(practitionerId);
		
		if (persons.isEmpty()) {
			return new ResponseEntity<List<Person>>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<Person>>(persons, HttpStatus.OK);
		}
	}
	
	@PostMapping("/patient-creation/practitioners/{practitionerId}/persons/{personId}")
	public ResponseEntity<Integer> addPersonToPractitioner(@PathVariable("practitionerId") int practitionerId, @PathVariable("personId") int personId ) {
		Integer isAssociated = iPractitionerPersonService.addPersonToPractitioner(practitionerId, personId);
		
		if (isAssociated == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Integer>(HttpStatus.CREATED);
		}
	}
	
	@DeleteMapping("/patient-deletion/practitioners/{practitionerId}/persons/{personId}")
	public void deletePersonFromPractitioner(@PathVariable("practitionerId") int practitionerId, @PathVariable("personId") int personId) {
		iPractitionerPersonService.deletePersonFromPractitioner(practitionerId, personId);
	}
}