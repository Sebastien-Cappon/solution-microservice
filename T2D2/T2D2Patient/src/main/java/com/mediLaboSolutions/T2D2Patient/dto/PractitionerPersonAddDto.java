package com.mediLaboSolutions.T2D2Patient.dto;

public class PractitionerPersonAddDto {

	private int practitionerId;
	private String personEmail;

	public int getPractitionerId() {
		return practitionerId;
	}

	public void setPractitionerId(int practitionerId) {
		this.practitionerId = practitionerId;
	}

	public String getPersonEmail() {
		return personEmail;
	}

	public void setPersonEmail(String personEmail) {
		this.personEmail = personEmail;
	}

	@Override
	public String toString() {
		return ("[" + practitionerId + "]" + "[" + personEmail + "]");
	}
}