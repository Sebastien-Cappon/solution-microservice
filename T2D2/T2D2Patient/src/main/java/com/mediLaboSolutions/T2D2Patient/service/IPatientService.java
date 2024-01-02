package com.mediLaboSolutions.T2D2Patient.service;

import java.util.List;

import com.mediLaboSolutions.T2D2Patient.model.Patient;

public interface IPatientService {
	
	public List<Patient> getPatients(int practitionerId);
	public Patient getPatientById(int practitionerId, int patientId);
	
	public Patient createPatient(int practitionerId, Patient newPatient) throws Exception;
	
	public Integer updatePatientById(int practitionerId, int patientId, Patient updatedPatient) throws Exception;
	
	public void deletePatientById(int practitionerId, int patientId);
}