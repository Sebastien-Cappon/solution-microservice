package com.mediLaboSolutions.T2D2Patient.util;

import com.mediLaboSolutions.T2D2Patient.model.Address;
import com.mediLaboSolutions.T2D2Patient.model.Person;
import com.mediLaboSolutions.T2D2Patient.model.PersonAddress;
import com.mediLaboSolutions.T2D2Patient.model.key.PersonAddressKey;

public class PersonAddressBuilder {

	public static PersonAddressKey createPersonAddressKey(Person person, Address address) {
		PersonAddressKey personAddressKey = new PersonAddressKey();
		personAddressKey.setPerson(person);
		personAddressKey.setAddress(address);
		
		return personAddressKey;
	}
	
	public static PersonAddress createPersonAddress(Person person, Address address) {
		PersonAddressKey personAddressKey = createPersonAddressKey(person, address);
		
		PersonAddress personAddress = new PersonAddress();
		personAddress.setId(personAddressKey);
		
		return personAddress;
	}
}