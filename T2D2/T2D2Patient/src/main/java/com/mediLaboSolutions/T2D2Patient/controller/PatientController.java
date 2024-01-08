package com.mediLaboSolutions.T2D2Patient.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.mediLaboSolutions.T2D2Patient.model.Patient;
import com.mediLaboSolutions.T2D2Patient.service.contracts.IPatientService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PatientController {

	@Autowired
	private IPatientService iPatientService;

	@GetMapping("/patients")
	public ResponseEntity<List<Patient>> getPatients() {
		List<Patient> patientList = iPatientService.getPatients();

		if (patientList.isEmpty()) {
			return new ResponseEntity<List<Patient>>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<Patient>>(patientList, HttpStatus.OK);
		}
	}

	@GetMapping("/patients/patient")
	public ResponseEntity<Patient> getPatientById(@RequestParam("id") int patientId) {
		Patient patient = iPatientService.getPatientById(patientId);

		if (patient == null) {
			return new ResponseEntity<Patient>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<Patient>(patient, HttpStatus.OK);
		}
	}

	@PostMapping("/patients/patient/create")
	public ResponseEntity<Patient> createPatient(@RequestBody Patient newPatient) throws Exception {
		Patient createdPatient = iPatientService.createPatient(newPatient);

		if (createdPatient == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Patient>(createdPatient, HttpStatus.CREATED);
		}
	}

	@PutMapping("/patients/patient/update")
	public ResponseEntity<Integer> updatePatient(@RequestParam("id") int patientId, @RequestBody Patient updatedPatient) throws Exception {
		Integer isUpdated = iPatientService.updatePatientById(patientId, updatedPatient);

		if (isUpdated == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Integer>(HttpStatus.OK);
		}
	}

	@DeleteMapping("/patients/patient/delete")
	public void deletePatientById(@RequestParam("id") int patientId) {
		iPatientService.deletePatientById(patientId);
	}
}