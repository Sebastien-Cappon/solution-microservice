package com.mediLaboSolutions.T2D2Patient.service.contracts;

import java.util.List;

import com.mediLaboSolutions.T2D2Patient.model.Patient;

public interface IPractitionerPatientService {

	public List<Patient> getPatientsByPractitionerId(int practitionerId);
	
	public Integer addPatientToPractitioner(int practitionerId, int patientId);
	
	public void deletePatientFromPractitioner(int practitionerId, int patientId);
}