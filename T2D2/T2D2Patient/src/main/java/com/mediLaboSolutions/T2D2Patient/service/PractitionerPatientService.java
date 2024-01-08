package com.mediLaboSolutions.T2D2Patient.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mediLaboSolutions.T2D2Patient.model.Patient;
import com.mediLaboSolutions.T2D2Patient.model.Practitioner;
import com.mediLaboSolutions.T2D2Patient.model.PractitionerPatient;
import com.mediLaboSolutions.T2D2Patient.repository.IPatientRepository;
import com.mediLaboSolutions.T2D2Patient.repository.IPractitionerPatientRepository;
import com.mediLaboSolutions.T2D2Patient.repository.IPractitionerRepository;
import com.mediLaboSolutions.T2D2Patient.service.contracts.IPractitionerPatientService;
import com.mediLaboSolutions.T2D2Patient.util.PractitionerPatientBuilder;

@Service
public class PractitionerPatientService implements IPractitionerPatientService{

	@Autowired
	IPractitionerPatientRepository iPractitionerPatientRepository;
	@Autowired
	IPractitionerRepository iPractitionerRepository;
	@Autowired
	IPatientRepository iPatientRepository;
	
	@Override
	public List<Patient> getPatientsByPractitionerId(int practitionerId) {
		List<PractitionerPatient> patientsByPractitionerId = iPractitionerPatientRepository.getPatientsByPractitionerId(practitionerId);
		List<Patient> patients = new ArrayList<>();
		
		for (PractitionerPatient practitionerPatient : patientsByPractitionerId) {
			patients.add(practitionerPatient.getId().getPatient());
		}
	
		return patients;
	}
	
	@Override
	public Integer addPatientToPractitioner(int practitionerId, int patientId) {
		if(iPractitionerRepository.findById(practitionerId).isPresent() && iPatientRepository.findById(patientId).isPresent()) {
			Practitioner practitioner = iPractitionerRepository.findById(practitionerId).get();
			Patient patient = iPatientRepository.findById(patientId).get();
			
			PractitionerPatient newPractitionerPatient = PractitionerPatientBuilder.createPractitionerPatient(practitioner, patient);
			iPractitionerPatientRepository.save(newPractitionerPatient);
			
			return 1;
		}
		
		return null;
	}
	
	@Override
	public void deletePatientFromPractitioner(int practitionerId, int patientId) {
		if(iPractitionerRepository.findById(practitionerId).isPresent() && iPatientRepository.findById(patientId).isPresent()) {
			Practitioner practitioner = iPractitionerRepository.findById(practitionerId).get();
			Patient patient = iPatientRepository.findById(patientId).get();
			
			PractitionerPatient practitionerPatientToDelete = PractitionerPatientBuilder.createPractitionerPatient(practitioner, patient);
			iPractitionerPatientRepository.delete(practitionerPatientToDelete);
		}	
	}
}