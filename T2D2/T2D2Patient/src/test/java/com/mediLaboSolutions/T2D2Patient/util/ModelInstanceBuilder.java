package com.mediLaboSolutions.T2D2Patient.util;

import java.time.LocalDate;

import com.mediLaboSolutions.T2D2Patient.model.Address;
import com.mediLaboSolutions.T2D2Patient.model.Patient;
import com.mediLaboSolutions.T2D2Patient.model.PatientAddress;
import com.mediLaboSolutions.T2D2Patient.model.Practitioner;
import com.mediLaboSolutions.T2D2Patient.model.PractitionerPatient;
import com.mediLaboSolutions.T2D2Patient.model.key.PatientAddressKey;
import com.mediLaboSolutions.T2D2Patient.model.key.PractitionerPatientKey;


public class ModelInstanceBuilder {

	public static Address createAddress(int id, String number, String wayType, String wayName, String postcode, String city, String country) {
		Address address = new Address();
		address.setId(id);
		address.setNumber(number);
		address.setWayType(wayType);
		address.setWayName(wayName);
		address.setPostcode(postcode);
		address.setCity(city);
		address.setCountry(country);
		
		return address;
	}

	public static Patient createPatient(int id, Boolean gender, String lastname, String firstname, LocalDate birthdate, String phone, String email) {
		Patient patient = new Patient();
		patient.setId(id);
		patient.setGender(gender);
		patient.setLastname(lastname);
		patient.setFirstname(firstname);
		patient.setBirthdate(birthdate);
		patient.setPhone(phone);
		patient.setEmail(email);
		
		return patient;
	}

	public static Practitioner createPractitioner(int id, String lastname, String firstname, String email, String password) {
		Practitioner practitioner = new Practitioner();
		practitioner.setId(id);
		practitioner.setLastname(lastname);
		practitioner.setFirstname(firstname);
		practitioner.setEmail(email);
		practitioner.setPassword(password);
		
		return practitioner;
	}
	
	public static PatientAddressKey createPatientAddressKey(Patient patient, Address address) {
		PatientAddressKey patientAddressKey = new PatientAddressKey();
		patientAddressKey.setPatient(patient);
		patientAddressKey.setAddress(address);
		
		return patientAddressKey;
	}
	
	public static PatientAddress createPatientAddress(PatientAddressKey patientAddressKey) {
		PatientAddress patientAddress = new PatientAddress();
		patientAddress.setId(patientAddressKey);
		
		return patientAddress;
	}
	
	public static PractitionerPatientKey createPractitionerPatientKey(Practitioner practitioner, Patient patient) {
		PractitionerPatientKey practitionerPatientKey = new PractitionerPatientKey();
		practitionerPatientKey.setPractitioner(practitioner);
		practitionerPatientKey.setPatient(patient);
		
		return practitionerPatientKey;
	}
	
	public static PractitionerPatient createPractitionerPatient(PractitionerPatientKey practitionerPatientKey) {
		PractitionerPatient practitionerPatient = new PractitionerPatient();
		practitionerPatient.setId(practitionerPatientKey);
		
		return practitionerPatient;
	}
}