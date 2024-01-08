package com.mediLaboSolutions.T2D2Patient.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.mediLaboSolutions.T2D2Patient.util.DtoInstanceBuilder;

public class DtoTest {
	@Test
	public void practitionerLoginDtoToString_isNotBlank() {
		PractitionerLoginDto practitionerLoginDto = DtoInstanceBuilder.createPractitionerLoginDto("ramesh.eliot@abernathyclinic.com", "UnsecuredPassword");
		assertThat(practitionerLoginDto.toString()).isNotBlank();
	}
}