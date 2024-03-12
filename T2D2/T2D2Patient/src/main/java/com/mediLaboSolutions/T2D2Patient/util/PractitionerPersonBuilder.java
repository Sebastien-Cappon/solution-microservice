package com.mediLaboSolutions.T2D2Patient.util;

import com.mediLaboSolutions.T2D2Patient.model.Person;
import com.mediLaboSolutions.T2D2Patient.model.PractitionerPerson;
import com.mediLaboSolutions.T2D2Patient.model.key.PractitionerPersonKey;

public class PractitionerPersonBuilder {

	public static PractitionerPersonKey createPractitionerPersonKey(int practitionerId, Person person) {
		PractitionerPersonKey practitionerPersonKey = new PractitionerPersonKey();
		practitionerPersonKey.setPractitioner(practitionerId);
		practitionerPersonKey.setPerson(person);

		return practitionerPersonKey;
	}

	public static PractitionerPerson createPractitionerPerson(int practitionerId, Person person) {
		PractitionerPersonKey practitionerPersonKey = createPractitionerPersonKey(practitionerId, person);

		PractitionerPerson practitionerPerson = new PractitionerPerson();
		practitionerPerson.setId(practitionerPersonKey);

		return practitionerPerson;
	}
}