package com.mediLaboSolutions.T2D2Patient.util;

import com.mediLaboSolutions.T2D2Patient.model.Person;
import com.mediLaboSolutions.T2D2Patient.model.Practitioner;
import com.mediLaboSolutions.T2D2Patient.model.PractitionerPerson;
import com.mediLaboSolutions.T2D2Patient.model.key.PractitionerPersonKey;

public class PractitionerPersonBuilder {

	public static PractitionerPersonKey createPractitionerPersonKey(Practitioner practitioner, Person person) {
		PractitionerPersonKey practitionerPersonKey = new PractitionerPersonKey();
		practitionerPersonKey.setPractitioner(practitioner);
		practitionerPersonKey.setPerson(person);

		return practitionerPersonKey;
	}

	public static PractitionerPerson createPractitionerPerson(Practitioner practitioner, Person person) {
		PractitionerPersonKey practitionerPersonKey = createPractitionerPersonKey(practitioner, person);

		PractitionerPerson practitionerPerson = new PractitionerPerson();
		practitionerPerson.setId(practitionerPersonKey);

		return practitionerPerson;
	}
}