package com.mediLaboSolutions.T2D2Patient.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.mediLaboSolutions.T2D2Patient.util.DtoInstanceBuilder;

@TestMethodOrder(OrderAnnotation.class)
public class DtoTest {

	@Test
	@Order(1)
	public void personAddressAddDtoToString_isNotBlank() {
		PersonAddressAddDto personAddressAddDto = DtoInstanceBuilder.createPersonAddressAddDto(1, 1);
		assertThat(personAddressAddDto.toString()).isNotBlank();
	}

	@Test
	@Order(2)
	public void practitionerLoginDtoToString_isNotBlank() {
		PractitionerLoginDto practitionerLoginDto = DtoInstanceBuilder.createPractitionerLoginDto("ramesh.eliot@abernathyclinic.com", "UnsecuredPassword");
		assertThat(practitionerLoginDto.toString()).isNotBlank();
	}

	@Test
	@Order(3)
	public void practitionerPersonAddDtoToString_isNotBlank() {
		PractitionerPersonAddDto practitionerPersonAddDto = DtoInstanceBuilder.createPractitionerPersonAddDto(1, "clientTest@abernathyclinic.com");
		assertThat(practitionerPersonAddDto.toString()).isNotBlank();
	}
}