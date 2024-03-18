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

/**
 * A class that receives requests made from some of the usual CRUD endpoints and
 * specific URL for the practitioners patients. Patients are the relationship
 * between practitioners and persons. In the database, they are represented by a
 * join table : practitioner_person
 * 
 * @author Sébastien Cappon
 * @version 1.0
 */
@RestController
public class PractitionerPersonController {

	@Autowired
	IPractitionerPersonService iPractitionerPersonService;

	/**
	 * A <code>GetMapping</code> method on the
	 * <code>/patients/practitioners/{practitionerId}/persons</code> URI with a
	 * practitioner id as <code>PathVariable</code>. It calls the eponymous
	 * <code>IPractitionerPersonService</code> method and returns a list of
	 * <code>Person</code> model entities whose id is attached, in the database, to
	 * the one passed in parameter.
	 * 
	 * @frontCall <code>patient.service.ts</code> : <code>getPatientsByPractitionerId(practitionerId: number)</code>
	 * 
	 * @return A <code>Person</code> list.
	 */
	@GetMapping("/patients/practitioners/{practitionerId}/persons")
	public List<Person> getPersonsByPractitionerId(@PathVariable("practitionerId") int practitionerId) {
		return iPractitionerPersonService.getPersonsByPractitionerId(practitionerId);
	}

	/**
	 * A <code>GetMapping</code> method on the
	 * <code>/patients/practitioners/{practitionerId}/persons/not-patients</code>
	 * URI with a practitioner id as <code>PathVariable</code>. It calls the
	 * eponymous <code>IPractitionerPersonService</code> method and returns a list
	 * of <code>Person</code> model entities whose id is NOT attached, in the
	 * database, to the one passed in parameter.
	 * 
	 * @frontCall <code>patient.service.ts</code> : <code>getNotPatientsByPractitionerId(practitionerId: number)</code>
	 * 
	 * @return A <code>Person</code> list.
	 */
	@GetMapping("/patients/practitioners/{practitionerId}/persons/not-patients")
	public List<Person> getNotPatientsByPractitionerId(@PathVariable("practitionerId") int practitionerId) {
		return iPractitionerPersonService.getNotPatientsByPractitionerId(practitionerId);
	}

	/**
	 * A <code>PostMapping</code> method on the <code>/patient</code> URI with an
	 * PractitionerPersonAddDto DTO as <code>RequestBody</code>. It calls the
	 * eponymous <code>IPractitionerPersonService</code> method and returns an
	 * <code>Integer</code> used by the front end to determine the success of the
	 * request with status code 201. If the result is null, it returns status code
	 * 400.
	 * 
	 * @frontCall <code>patient.service.ts</code> : <code>addPatient(patientValue: PatientValue)</code>
	 * 
	 * @throws <code>BAD_REQUEST</code> if the patient already exist in the
	 * practitioner_person table.
	 * 
	 * @return An <code>Integer</code> and a status code.
	 */
	@PostMapping("/patient")
	public ResponseEntity<Integer> addPersonToPractitioner(@RequestBody PractitionerPersonAddDto practitionerPersonAddDto) {
		Integer isAssociated = iPractitionerPersonService.addPersonToPractitioner(practitionerPersonAddDto);

		if (isAssociated == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Integer>(HttpStatus.CREATED);
		}
	}

	/**
	 * A <code>DeleteMapping</code> method on the
	 * <code>/patients/practitioners/{practitionerId}/persons/</code> URI with the a
	 * practitioner id and a person id as <code>PathVariables</code>. It calls the
	 * eponymous <code>IPractitionerPersonService</code> method and returns nothing.
	 * 
	 * @singularity Method created for testing purposes with Postman. Could be use
	 *              for administration.
	 */
	@DeleteMapping("/patients/practitioners/{practitionerId}/persons/{personId}")
	public void deletePersonFromPractitioner(@PathVariable("practitionerId") int practitionerId, @PathVariable("personId") int personId) {
		iPractitionerPersonService.deletePersonFromPractitioner(practitionerId, personId);
	}
}