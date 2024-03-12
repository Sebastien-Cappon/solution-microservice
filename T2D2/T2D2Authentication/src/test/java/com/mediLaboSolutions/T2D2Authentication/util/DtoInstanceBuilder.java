package com.mediLaboSolutions.T2D2Authentication.util;

import com.mediLaboSolutions.T2D2Authentication.dto.PractitionerLoginDto;

public class DtoInstanceBuilder {

	public static PractitionerLoginDto createPractitionerLoginDto(String email, String password) {
		PractitionerLoginDto practitionerLoginDto = new PractitionerLoginDto();
		practitionerLoginDto.setEmail(email);
		practitionerLoginDto.setPassword(password);

		return practitionerLoginDto;
	}
}