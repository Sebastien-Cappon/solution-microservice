package com.mediLaboSolutions.T2D2Patient.dto;

/**
 * A model class which creates the DTO (Data Transfer Object)
 * <code>PersonAddressAddDto</code>. It contains getters and setters, as well as
 * an override <code>toSring()</code> method for display in the console.
 *
 * @author Sébastien Cappon
 * @version 1.0
 */
public class PersonAddressAddDto {

	private int personId;
	private int addressId;

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	/**
	 * An override method for user-friendly display of
	 * <code>PersonAddressAddDto</code> attributes in the console. Not necessary,
	 * except for test purposes.
	 * 
	 * @return <code>String</code> containing all the attributes of
	 *         <code>PersonAddressAddDto</code>.
	 */
	@Override
	public String toString() {
		return ("[" + personId + "]" + "[" + addressId + "]");
	}
}