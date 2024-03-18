package com.mediLaboSolutions.T2D2Diabetes.dto;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * A model class which creates the DTO (Data Transfer Object)
 * <code>RiskFactorsDto</code>. It contains getters and setters, as well as an
 * override <code>toSring()</code> method for display in the console.
 * 
 * @frontCall Refers to <code>RiskFactorsValue</code> model class.
 *
 * @author Sébastien Cappon
 * @version 1.0
 */
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

	/**
	 * An override method for user-friendly display of <code>RiskFactorsDto</code>
	 * attributes in the console. Not necessary, except for test purposes.
	 * 
	 * @return <code>String</code> containing all the attributes of
	 *         <code>RiskFactorsDto</code>.
	 */
	@Override
	public String toString() {
		return ("[" + gender + "]" + "[" + birthdate + "]" + "[" + notes + "]");
	}
}