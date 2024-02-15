package com.mediLaboSolutions.T2D2Diabetes.dto;

import java.time.LocalDate;
import java.util.List;

public class RiskFactorsDto {

	private Boolean gender;
	private LocalDate birthdate;
	private List<String> notes;

	public Boolean getGender() {
		return gender;
	}

	public void setGender(Boolean gender) {
		this.gender = gender;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
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