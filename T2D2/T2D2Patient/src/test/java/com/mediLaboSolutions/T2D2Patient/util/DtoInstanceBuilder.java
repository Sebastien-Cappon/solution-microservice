package com.mediLaboSolutions.T2D2Patient.util;

import com.mediLaboSolutions.T2D2Patient.dto.PersonAddressAddDto;
import com.mediLaboSolutions.T2D2Patient.dto.PractitionerPersonAddDto;

public class DtoInstanceBuilder {

	public static PersonAddressAddDto createPersonAddressAddDto(int personId, int addressId) {
		PersonAddressAddDto personAddressAddDto = new PersonAddressAddDto();
		personAddressAddDto.setPersonId(personId);
		personAddressAddDto.setAddressId(addressId);

		return personAddressAddDto;
	}

	public static PractitionerPersonAddDto createPractitionerPersonAddDto(int practitionerId, String personEmail) {
		PractitionerPersonAddDto practitionerPersonAddDto = new PractitionerPersonAddDto();
		practitionerPersonAddDto.setPractitionerId(practitionerId);
		practitionerPersonAddDto.setPersonEmail(personEmail);

		return practitionerPersonAddDto;
	}
}