package com.mediLaboSolutions.T2D2Authentication.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.mediLaboSolutions.T2D2Authentication.util.ModelInstanceBuilder;

public class ModelTest {

	private Practitioner practitioner = ModelInstanceBuilder.createPractitioner(1, "Eliot", "Ramesh", "ramesh.eliot@abernathyclinic.com", "UnsecuredPassword");
	
	@Test
	public void practitionerToString_isNotBlank() {
		assertThat(practitioner.toString()).isNotBlank();
	}
}