package com.mediLaboSolutions.T2D2Patient.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.ZonedDateTime;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import com.mediLaboSolutions.T2D2Patient.model.key.PersonAddressKey;
import com.mediLaboSolutions.T2D2Patient.model.key.PractitionerPersonKey;
import com.mediLaboSolutions.T2D2Patient.util.ModelInstanceBuilder;

@TestMethodOrder(OrderAnnotation.class)
public class ModelTest {

	private Address address = ModelInstanceBuilder.createAddress(1, "1A", "Street", "Unknown St.", "W1", "Marylebone", "England");
	private Person person = ModelInstanceBuilder.createPerson(1, false, "Byron", "Ada", ZonedDateTime.parse("1815-12-10T00:00:00Z"), "0102030405", "ada.byron@countess.lvl");
	private int practitionerId = 1;
	
	@Test
	@Order(1)
	public void addressToString_isNotBlank() {
		assertThat(address.toString()).isNotBlank();
	}

	@Test
	@Order(2)
	public void personToString_isNotBlank() {
		assertThat(person.toString()).isNotBlank();
	}

	@Test
	@Order(3)
	public void personAddressToString_isNotBlank() {
		PersonAddressKey personAddressKey = ModelInstanceBuilder.createPersonAddressKey(person, address);
		PersonAddress personAddress = ModelInstanceBuilder.createPersonAddress(personAddressKey);
		assertThat(personAddress.toString()).isNotBlank();
	}

	@Test
	@Order(4)
	public void practitionerPersonToString_isNotBlank() {
		PractitionerPersonKey practitionerPersonKey = ModelInstanceBuilder.createPractitionerPersonKey(practitionerId, person);
		PractitionerPerson practitionerPerson = ModelInstanceBuilder.createPractitionerPerson(practitionerPersonKey);
		assertThat(practitionerPerson.toString()).isNotBlank();
	}
}