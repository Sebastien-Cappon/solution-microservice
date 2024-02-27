package com.mediLaboSolutions.T2D2Patient.util;

import java.time.ZonedDateTime;

import com.mediLaboSolutions.T2D2Patient.model.Address;
import com.mediLaboSolutions.T2D2Patient.model.Person;
import com.mediLaboSolutions.T2D2Patient.model.PersonAddress;
import com.mediLaboSolutions.T2D2Patient.model.Practitioner;
import com.mediLaboSolutions.T2D2Patient.model.PractitionerPerson;
import com.mediLaboSolutions.T2D2Patient.model.key.PersonAddressKey;
import com.mediLaboSolutions.T2D2Patient.model.key.PractitionerPersonKey;

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

	public static Person createPerson(int id, Boolean gender, String lastname, String firstname, ZonedDateTime birthdate, String phone, String email) {
		Person person = new Person();
		person.setId(id);
		person.setGender(gender);
		person.setLastname(lastname);
		person.setFirstname(firstname);
		person.setBirthdate(birthdate);
		person.setPhone(phone);
		person.setEmail(email);

		return person;
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

	public static PersonAddressKey createPersonAddressKey(Person person, Address address) {
		PersonAddressKey personAddressKey = new PersonAddressKey();
		personAddressKey.setPerson(person);
		personAddressKey.setAddress(address);

		return personAddressKey;
	}

	public static PersonAddress createPersonAddress(PersonAddressKey personAddressKey) {
		PersonAddress personAddress = new PersonAddress();
		personAddress.setId(personAddressKey);

		return personAddress;
	}

	public static PractitionerPersonKey createPractitionerPersonKey(Practitioner practitioner, Person person) {
		PractitionerPersonKey practitionerPersonKey = new PractitionerPersonKey();
		practitionerPersonKey.setPractitioner(practitioner);
		practitionerPersonKey.setPerson(person);

		return practitionerPersonKey;
	}

	public static PractitionerPerson createPractitionerPerson(PractitionerPersonKey practitionerPersonKey) {
		PractitionerPerson practitionerPerson = new PractitionerPerson();
		practitionerPerson.setId(practitionerPersonKey);

		return practitionerPerson;
	}
}