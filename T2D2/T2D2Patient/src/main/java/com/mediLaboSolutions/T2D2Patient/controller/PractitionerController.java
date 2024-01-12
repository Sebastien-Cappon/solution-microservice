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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.JsonView;
import com.mediLaboSolutions.T2D2Patient.dto.PractitionerLoginDto;
import com.mediLaboSolutions.T2D2Patient.model.Practitioner;
import com.mediLaboSolutions.T2D2Patient.service.contracts.IPractitionerService;
import com.mediLaboSolutions.T2D2Patient.view.PractitionerView;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PractitionerController {

	@Autowired
	private IPractitionerService iPractitionerService;
	
	@GetMapping("/practitioners")
	public ResponseEntity<List<Practitioner>> getPractitioners() {
		List<Practitioner> practitionerList = iPractitionerService.getPractitioners();
		
		if(practitionerList.isEmpty()) {
			return new ResponseEntity<List<Practitioner>>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<Practitioner>>(practitionerList, HttpStatus.OK);
		}
	}
	
	@GetMapping("/practitioners/{practitionerId}")
	public ResponseEntity<Practitioner> getPractitionerById(@PathVariable("practitionerId") int practitionerId) {
		Practitioner practitioner = iPractitionerService.getPractitionerById(practitionerId);
		
		if (practitioner == null) {
			return new ResponseEntity<Practitioner>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<Practitioner>(practitioner, HttpStatus.OK);
		}
	}
	
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
	
	@PostMapping("/practitioner-creation")
	public ResponseEntity<Practitioner> createPractitioner(@RequestBody Practitioner newPractitioner) throws Exception {
		Practitioner practitioner = iPractitionerService.createPractitioner(newPractitioner);
		
		if (practitioner == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Practitioner>(practitioner, HttpStatus.CREATED);
		}
	}
	
	@PutMapping("/practitioner-edition/{practitionerId}")
	public ResponseEntity<Integer> updatePractitionerById(@PathVariable("practitionerId") int practitionerId, @RequestBody Practitioner updatedPractitioner) throws Exception {
		Integer isUpdated = iPractitionerService.updatePractitionerById(practitionerId, updatedPractitioner);
		
		if(isUpdated == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Integer>(HttpStatus.OK);
		}	
	}
	
	@DeleteMapping("/practitioner-deletion/{practitionerId}")
	public void deletePractitionerById(@PathVariable("practitionerId") int practitionerId) {
		iPractitionerService.deletePractitionerById(practitionerId);
	}
}