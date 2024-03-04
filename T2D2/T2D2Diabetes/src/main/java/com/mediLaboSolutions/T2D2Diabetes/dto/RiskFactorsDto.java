package com.mediLaboSolutions.T2D2Diabetes.dto;

import java.time.ZonedDateTime;
import java.util.List;

public class RiskFactorsDto {

	private Boolean gender;
	private ZonedDateTime birthdate;
	private List<String> notes;

	public Boolean getGender() {
		return gender;
	}

	public void setGender(Boolean gender) {
		this.gender = gender;
	}

	public ZonedDateTime getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(ZonedDateTime birthdate) {
		this.birthdate = birthdate;
	}

	public List<String> getNotes() {
		return notes;
	}

	public void setNotes(List<String> notes) {
		this.notes = notes;
	}

	@Override
	public String toString() {
		return ("[" + gender + "]" + "[" + birthdate + "]" + "[" + notes + "]");
	}
}