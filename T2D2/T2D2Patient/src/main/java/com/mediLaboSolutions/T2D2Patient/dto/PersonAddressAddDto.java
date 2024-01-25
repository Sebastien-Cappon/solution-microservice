package com.mediLaboSolutions.T2D2Patient.dto;

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

	@Override
	public String toString() {
		return ("[" + personId + "]" + "[" + addressId + "]");
	}
}