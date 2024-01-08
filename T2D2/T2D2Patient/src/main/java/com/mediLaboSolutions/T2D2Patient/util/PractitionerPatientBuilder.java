package com.mediLaboSolutions.T2D2Patient.util;

import com.mediLaboSolutions.T2D2Patient.model.Patient;
import com.mediLaboSolutions.T2D2Patient.model.Practitioner;
import com.mediLaboSolutions.T2D2Patient.model.PractitionerPatient;
import com.mediLaboSolutions.T2D2Patient.model.key.PractitionerPatientKey;

public class PractitionerPatientBuilder {

	public static PractitionerPatientKey createPractitionerPatientKey(Practitioner practitioner, Patient patient) {
		PractitionerPatientKey practitionerPatientKey = new PractitionerPatientKey();
		practitionerPatientKey.setPractitioner(practitioner);
		practitionerPatientKey.setPatient(patient);
		
		return practitionerPatientKey;
	}
	
	public static PractitionerPatient createPractitionerPatient(Practitioner practitioner, Patient patient) {
		PractitionerPatientKey practitionerPatientKey = createPractitionerPatientKey(practitioner, patient);
		
		PractitionerPatient practitionerPatient = new PractitionerPatient();
		practitionerPatient.setId(practitionerPatientKey);
		
		return practitionerPatient;
	}
}