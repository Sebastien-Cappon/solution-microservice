package com.mediLaboSolutions.T2D2patient.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import com.mediLaboSolutions.T2D2Patient.model.Address;
import com.mediLaboSolutions.T2D2Patient.model.Patient;
import com.mediLaboSolutions.T2D2Patient.model.PatientAddress;
import com.mediLaboSolutions.T2D2Patient.model.Practitioner;
import com.mediLaboSolutions.T2D2Patient.model.PractitionerPatient;
import com.mediLaboSolutions.T2D2Patient.model.key.PatientAddressKey;
import com.mediLaboSolutions.T2D2Patient.model.key.PractitionerPatientKey;
import com.mediLaboSolutions.T2D2patient.util.InstanceBuilder;

public class ModelTest {

	private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	private Address address = InstanceBuilder.createAddress(1, "1A", "Street", "Unknown St.", "W1", "Marylebone", "England");
	private Patient patient = InstanceBuilder.createPatient(1, false, "Byron", "Ada", LocalDate.parse("1815-12-10", dateTimeFormatter), "0102030405", "ada.byron@countess.lvl");
	private Practitioner practitioner = InstanceBuilder.createPractitioner(1, "Eliot", "Ramesh", "ramesh.eliot@abernathyclinic.com", "UnsecuredPassword");
	
	@Test
	public void addressToString_isNotBlank() {
		assertThat(address.toString()).isNotBlank();
	}
	
	@Test
	public void patientToString_isNotBlank() {
		assertThat(patient.toString()).isNotBlank();
	}

	@Test
	public void patientAddressToString_isNotBlank() {
		PatientAddressKey patientAddressKey = InstanceBuilder.createPatientAddressKey(patient, address);
		PatientAddress patientAddress = InstanceBuilder.createPatientAddress(patientAddressKey);
		assertThat(patientAddress.toString()).isNotBlank();
	}

	@Test
	public void practitionerToString_isNotBlank() {
		assertThat(practitioner.toString()).isNotBlank();
	}

	@Test
	public void practitionerPatientToString_isNotBlank() {
		PractitionerPatientKey practitionerPatientKey = InstanceBuilder.createPractitionerPatientKey(practitioner, patient);
		PractitionerPatient practitionerPatient = InstanceBuilder.createPractitionerPatient(practitionerPatientKey);
		assertThat(practitionerPatient.toString()).isNotBlank();
	}
}