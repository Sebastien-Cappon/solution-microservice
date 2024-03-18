package com.mediLaboSolutions.T2D2Patient.util;

import com.mediLaboSolutions.T2D2Patient.model.Person;
import com.mediLaboSolutions.T2D2Patient.model.PractitionerPerson;
import com.mediLaboSolutions.T2D2Patient.model.key.PractitionerPersonKey;

/**
 * A class that contains two methods for creating a relationship between a
 * practitioner and a person (patient) and the associated composite key.
 *
 * @author Sébastien Cappon
 * @version 1.0
 */
public class PractitionerPersonBuilder {

	/**
	 * A method that creates the necessary <code>PractitionerPersonKey</code> for
	 * creating a patient relationship.
	 *
	 * @return A <code>PractitionerPersonKey</code>.
	 */
	public static PractitionerPersonKey createPractitionerPersonKey(int practitionerId, Person person) {
		PractitionerPersonKey practitionerPersonKey = new PractitionerPersonKey();
		practitionerPersonKey.setPractitioner(practitionerId);
		practitionerPersonKey.setPerson(person);

		return practitionerPersonKey;
	}

	/**
	 * A method that creates a <code>PractitionerPerson</code> relationship between
	 * a <code>Practitioner</code> and a <code>Person</code>.
	 *
	 * @return A <code>PractitionerPerson</code>.
	 */
	public static PractitionerPerson createPractitionerPerson(int practitionerId, Person person) {
		PractitionerPersonKey practitionerPersonKey = createPractitionerPersonKey(practitionerId, person);

		PractitionerPerson practitionerPerson = new PractitionerPerson();
		practitionerPerson.setId(practitionerPersonKey);

		return practitionerPerson;
	}
}