package com.mediLaboSolutions.T2D2Diabetes.util;

import java.time.LocalDate;
import java.util.ArrayList;

import com.mediLaboSolutions.T2D2Diabetes.dto.RiskFactorsDto;

public class DtoInstanceBuilder {
	public static RiskFactorsDto createRiskFactorsDto(boolean gender, LocalDate birthdate, ArrayList<String> notes) {
		RiskFactorsDto riskFactorsDto = new RiskFactorsDto();
		riskFactorsDto.setGender(gender);
		riskFactorsDto.setBirthdate(birthdate);
		riskFactorsDto.setNotes(notes);

		return riskFactorsDto;
	}
}