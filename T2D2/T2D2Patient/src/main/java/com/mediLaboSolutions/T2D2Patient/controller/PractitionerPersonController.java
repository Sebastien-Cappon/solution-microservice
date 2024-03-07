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

import com.mediLaboSolutions.T2D2Patient.dto.PractitionerPersonAddDto;
import com.mediLaboSolutions.T2D2Patient.model.Person;
import com.mediLaboSolutions.T2D2Patient.service.contracts.IPractitionerPersonService;

@RestController
public class PractitionerPersonController {

	@Autowired
	IPractitionerPersonService iPractitionerPersonService;

	@GetMapping("/patients/practitioners/{practitionerId}/persons")
	public List<Person> getPersonsByPractitionerId(@PathVariable("practitionerId") int practitionerId) {
		return iPractitionerPersonService.getPersonsByPractitionerId(practitionerId);
	}

	@GetMapping("/patients/practitioners/{practitionerId}/persons/not-patients")
	public List<Person> getNotPatientsByPractitionerId(@PathVariable("practitionerId") int practitionerId) {
		return iPractitionerPersonService.getNotPatientsByPractitionerId(practitionerId);
	}

	@PostMapping("/patient")
	public ResponseEntity<Integer> addPersonToPractitioner(@RequestBody PractitionerPersonAddDto practitionerPersonAddDto) {
		Integer isAssociated = iPractitionerPersonService.addPersonToPractitioner(practitionerPersonAddDto);

		if (isAssociated == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Integer>(HttpStatus.CREATED);
		}
	}

	@DeleteMapping("/patients/practitioners/{practitionerId}/persons/{personId}")
	public void deletePersonFromPractitioner(@PathVariable("practitionerId") int practitionerId, @PathVariable("personId") int personId) {
		iPractitionerPersonService.deletePersonFromPractitioner(practitionerId, personId);
	}
}