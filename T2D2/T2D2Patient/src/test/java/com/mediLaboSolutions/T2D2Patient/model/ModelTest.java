package com.mediLaboSolutions.T2D2Patient.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import com.mediLaboSolutions.T2D2Patient.model.key.PersonAddressKey;
import com.mediLaboSolutions.T2D2Patient.model.key.PractitionerPersonKey;
import com.mediLaboSolutions.T2D2Patient.util.ModelInstanceBuilder;

public class ModelTest {

	private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	private Address address = ModelInstanceBuilder.createAddress(1, "1A", "Street", "Unknown St.", "W1", "Marylebone", "England");
	private Person person = ModelInstanceBuilder.createPerson(1, false, "Byron", "Ada", LocalDate.parse("1815-12-10", dateTimeFormatter), "0102030405", "ada.byron@countess.lvl");
	private Practitioner practitioner = ModelInstanceBuilder.createPractitioner(1, "Eliot", "Ramesh", "ramesh.eliot@abernathyclinic.com", "UnsecuredPassword");

	@Test
	public void addressToString_isNotBlank() {
		assertThat(address.toString()).isNotBlank();
	}

	@Test
	public void personToString_isNotBlank() {
		assertThat(person.toString()).isNotBlank();
	}

	@Test
	public void personAddressToString_isNotBlank() {
		PersonAddressKey personAddressKey = ModelInstanceBuilder.createPersonAddressKey(person, address);
		PersonAddress personAddress = ModelInstanceBuilder.createPersonAddress(personAddressKey);
		assertThat(personAddress.toString()).isNotBlank();
	}

	@Test
	public void practitionerToString_isNotBlank() {
		assertThat(practitioner.toString()).isNotBlank();
	}

	@Test
	public void practitionerPersonToString_isNotBlank() {
		PractitionerPersonKey practitionerPersonKey = ModelInstanceBuilder.createPractitionerPersonKey(practitioner, person);
		PractitionerPerson practitionerPerson = ModelInstanceBuilder.createPractitionerPerson(practitionerPersonKey);
		assertThat(practitionerPerson.toString()).isNotBlank();
	}
}