package com.mediLaboSolutions.T2D2Patient.util;

import com.mediLaboSolutions.T2D2Patient.model.Address;
import com.mediLaboSolutions.T2D2Patient.model.Person;
import com.mediLaboSolutions.T2D2Patient.model.PersonAddress;
import com.mediLaboSolutions.T2D2Patient.model.key.PersonAddressKey;

/**
 * A class that contains two methods for creating a relationship between a
 * person and an address (residence) and the associated composite key.
 *
 * @author Sébastien Cappon
 * @version 1.0
 */
public class PersonAddressBuilder {

	/**
	 * A method that creates the necessary <code>PersonAddressKey</code> for
	 * creating a residence relationship.
	 *
	 * @return A <code>PersonAddressKey</code>.
	 */
	public static PersonAddressKey createPersonAddressKey(Person person, Address address) {
		PersonAddressKey personAddressKey = new PersonAddressKey();
		personAddressKey.setPerson(person);
		personAddressKey.setAddress(address);

		return personAddressKey;
	}

	/**
	 * A method that creates a <code>PersonAddress</code> relationship between a
	 * <code>Person</code> and an <code>Address</code>.
	 *
	 * @return A <code>PractitionerPerson</code>.
	 */
	public static PersonAddress createPersonAddress(Person person, Address address) {
		PersonAddressKey personAddressKey = createPersonAddressKey(person, address);

		PersonAddress personAddress = new PersonAddress();
		personAddress.setId(personAddressKey);

		return personAddress;
	}
}