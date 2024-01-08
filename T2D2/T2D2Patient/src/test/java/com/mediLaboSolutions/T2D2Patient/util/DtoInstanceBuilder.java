package com.mediLaboSolutions.T2D2Patient.util;

import com.mediLaboSolutions.T2D2Patient.dto.PractitionerLoginDto;

public class DtoInstanceBuilder {
	
	public static PractitionerLoginDto createPractitionerLoginDto(String email, String password) {
		PractitionerLoginDto practitionerLoginDto = new PractitionerLoginDto();
		practitionerLoginDto.setEmail(email);
		practitionerLoginDto.setPassword(password);
		
		return practitionerLoginDto;
	}
}