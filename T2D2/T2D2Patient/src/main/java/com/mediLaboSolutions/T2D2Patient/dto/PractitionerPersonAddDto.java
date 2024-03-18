package com.mediLaboSolutions.T2D2Patient.dto;

/**
 * A model class which creates the DTO (Data Transfer Object)
 * <code>PractitionerPersonAddDto</code>. It contains getters and setters, as
 * well as an override <code>toSring()</code> method for display in the console.
 *
 * @author Sébastien Cappon
 * @version 1.0
 */
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

	/**
	 * An override method for user-friendly display of
	 * <code>PractitionerPersonAddDto</code> attributes in the console. Not
	 * necessary, except for test purposes.
	 * 
	 * @return <code>String</code> containing all the attributes of
	 *         <code>PractitionerPersonAddDto</code>.
	 */
	@Override
	public String toString() {
		return ("[" + practitionerId + "]" + "[" + personEmail + "]");
	}
}