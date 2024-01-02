package com.mediLaboSolutions.T2D2Patient.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mediLaboSolutions.T2D2Patient.model.Patient;
import com.mediLaboSolutions.T2D2Patient.repository.IPatientRepository;

@Service
public class PatientService implements IPatientService {
	
	private static final Logger logger = LoggerFactory.getLogger(PatientService.class);
	
	@Autowired
	private IPatientRepository iPatientRepository;
	
	@Override
	public List<Patient> getPatients(int practitionerId) {
		return iPatientRepository.findAll();
	}
	
	@Override
	public Patient getPatientById(int practitionerId, int patientId) {
		if(iPatientRepository.findById(patientId).isPresent()) {
			Patient patientRequested = iPatientRepository.findById(patientId).get(); 
			return patientRequested;
		}
		
		return null;
	}
	
	@Override
	public Patient createPatient(int practitionerId, Patient newPatient) throws Exception {
		for (Patient checkPatient : iPatientRepository.findAll()) {
			if (checkPatient.getGender().equals(newPatient.getGender())
					&& checkPatient.getLastname().contentEquals(newPatient.getLastname())
					&& checkPatient.getFirstname().contentEquals(newPatient.getFirstname())
					&& checkPatient.getBirthdate().equals(newPatient.getBirthdate())
					&& checkPatient.getPhone().contentEquals(newPatient.getPhone())
					&& checkPatient.getEmail().contentEquals(newPatient.getEmail())) {
				logger.warn("This patient already exists in the database.");
				return null;
			}
		}
		
		return iPatientRepository.save(newPatient);
	}
	
	@Override
	public Integer updatePatientById(int practitionerId, int patientId, Patient updatedPatient) throws Exception {
		if(iPatientRepository.findById(patientId).isPresent()) {
			Patient patientToUpdate = iPatientRepository.findById(patientId).get();
			
			updatedPatient.setId(patientToUpdate.getId());
			if(updatedPatient.getGender() == null) {
				updatedPatient.setGender(patientToUpdate.getGender());
			}
			if(updatedPatient.getLastname() == null || updatedPatient.getLastname().isBlank()) {
				updatedPatient.setLastname(patientToUpdate.getLastname());
			}
			if(updatedPatient.getFirstname() == null || updatedPatient.getFirstname().isBlank()) {
				updatedPatient.setFirstname(patientToUpdate.getFirstname());
			}
			if(updatedPatient.getBirthdate() == null) {
				updatedPatient.setBirthdate(patientToUpdate.getBirthdate());
			}
			if(updatedPatient.getPhone() == null || updatedPatient.getPhone().isBlank()) {
				updatedPatient.setPhone(patientToUpdate.getPhone());
			}
			if(updatedPatient.getEmail() == null || updatedPatient.getEmail().isBlank()) {
				updatedPatient.setEmail(patientToUpdate.getEmail());
			}
			
			iPatientRepository.save(updatedPatient);
			return 1;
		}
		
		logger.warn("Can't modify : This patient doesn't exist in the database.");
		return null;
	}
	
	@Override
	public void deletePatientById(int practitionerId, int patientId) {
		iPatientRepository.deleteById(patientId);
	}
}