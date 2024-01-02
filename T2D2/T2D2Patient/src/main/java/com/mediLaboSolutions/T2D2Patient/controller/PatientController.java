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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.mediLaboSolutions.T2D2Patient.model.Patient;
import com.mediLaboSolutions.T2D2Patient.service.IPatientService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PatientController {

	@Autowired
	IPatientService iPatientService;

	@GetMapping("/{practitionerId}/patients")
	public ResponseEntity<List<Patient>> getPatients(@PathVariable("practitionerId") int practitionerId) {
		List<Patient> patientList = iPatientService.getPatients(practitionerId);

		if (patientList.isEmpty()) {
			return new ResponseEntity<List<Patient>>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<Patient>>(patientList, HttpStatus.OK);
		}
	}

	@GetMapping("/{practitionerId}/patients/patient")
	public ResponseEntity<Patient> getPatientById(@PathVariable("practitionerId") int practitionerId, @RequestParam("id") int patientId) {
		Patient patient = iPatientService.getPatientById(practitionerId, patientId);

		if (patient == null) {
			return new ResponseEntity<Patient>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<Patient>(patient, HttpStatus.OK);
		}
	}

	@PostMapping("/{practitionerId}/patients/patient/create")
	public ResponseEntity<Patient> createPatient(@PathVariable("practitionerId") int practitionerId, @RequestBody Patient newPatient) throws Exception {
		Patient createdPatient = iPatientService.createPatient(practitionerId, newPatient);

		if (createdPatient == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Patient>(createdPatient, HttpStatus.CREATED);
		}
	}

	@PutMapping("/{practitionerId}/patients/patient/update")
	public ResponseEntity<Integer> updatePatient(@PathVariable("practitionerId") int practitionerId, @RequestParam("id") int patientId, @RequestBody Patient updatedPatient) throws Exception {
		Integer isUpdated = iPatientService.updatePatientById(practitionerId, patientId, updatedPatient);

		if (isUpdated == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Integer>(HttpStatus.OK);
		}
	}

	@DeleteMapping("/{practitionerId}/patients/patient/delete")
	public void deletePatientById(@PathVariable("practitionerId") int practitionerId, @RequestParam("id") int patientId) {
		iPatientService.deletePatientById(practitionerId, patientId);
	}
}