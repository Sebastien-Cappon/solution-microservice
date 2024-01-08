package com.mediLaboSolutions.T2D2Patient.service.contracts;

import java.util.List;

import com.mediLaboSolutions.T2D2Patient.model.Patient;

public interface IPatientService {
	
	public List<Patient> getPatients();
	public Patient getPatientById(int patientId);
	
	public Patient createPatient(Patient newPatient) throws Exception;
	
	public Integer updatePatientById(int patientId, Patient updatedPatient) throws Exception;
	
	public void deletePatientById(int patientId);
}