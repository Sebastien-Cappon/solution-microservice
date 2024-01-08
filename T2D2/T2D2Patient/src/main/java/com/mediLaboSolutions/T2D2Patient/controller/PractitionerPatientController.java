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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.mediLaboSolutions.T2D2Patient.model.Patient;
import com.mediLaboSolutions.T2D2Patient.service.contracts.IPractitionerPatientService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PractitionerPatientController {

	@Autowired
	IPractitionerPatientService iPractitionerPatientService;
	
	@GetMapping("/{practitionerId}/patients")
	public ResponseEntity<List<Patient>> getPatientsByPractitionerId(@PathVariable("practitionerId") int practitionerId) {
		List<Patient> patients = iPractitionerPatientService.getPatientsByPractitionerId(practitionerId);
		
		if (patients.isEmpty()) {
			return new ResponseEntity<List<Patient>>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<Patient>>(patients, HttpStatus.OK);
		}
	}
	
	@PostMapping("/{practitionerId}/patients/patient/add")
	public ResponseEntity<Integer> addPatientToPractitioner(@PathVariable("practitionerId") int practitionerId, @RequestParam("id") int patientId ) {
		Integer isAssociated = iPractitionerPatientService.addPatientToPractitioner(practitionerId, patientId);
		
		if (isAssociated == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Integer>(HttpStatus.CREATED);
		}
	}
	
	@DeleteMapping("/{practitionerId}/patients/patient/delete")
	public void deletePatientFromPractitioner(@PathVariable("practitionerId") int practitionerId, @RequestParam("id") int patientId) {
		iPractitionerPatientService.deletePatientFromPractitioner(practitionerId, patientId);
	}
}