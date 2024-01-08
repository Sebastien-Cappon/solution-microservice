package com.mediLaboSolutions.T2D2Patient.util;

import com.mediLaboSolutions.T2D2Patient.model.Address;
import com.mediLaboSolutions.T2D2Patient.model.Patient;
import com.mediLaboSolutions.T2D2Patient.model.PatientAddress;
import com.mediLaboSolutions.T2D2Patient.model.key.PatientAddressKey;

public class PatientAddressBuilder {

	public static PatientAddressKey createPatientAddressKey(Patient patient, Address address) {
		PatientAddressKey patientAddressKey = new PatientAddressKey();
		patientAddressKey.setPatient(patient);
		patientAddressKey.setAddress(address);
		
		return patientAddressKey;
	}
	
	public static PatientAddress createPatientAddress(Patient patient, Address address) {
		PatientAddressKey patientAddressKey = createPatientAddressKey(patient, address);
		
		PatientAddress patientAddress = new PatientAddress();
		patientAddress.setId(patientAddressKey);
		
		return patientAddress;
	}
}