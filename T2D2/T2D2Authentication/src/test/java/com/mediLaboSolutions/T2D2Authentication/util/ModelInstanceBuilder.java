package com.mediLaboSolutions.T2D2Authentication.util;

import com.mediLaboSolutions.T2D2Authentication.model.Practitioner;

public class ModelInstanceBuilder {

	public static Practitioner createPractitioner(int id, String lastname, String firstname, String email, String password) {
		Practitioner practitioner = new Practitioner();
		practitioner.setId(id);
		practitioner.setLastname(lastname);
		practitioner.setFirstname(firstname);
		practitioner.setEmail(email);
		practitioner.setPassword(password);

		return practitioner;
	}
}