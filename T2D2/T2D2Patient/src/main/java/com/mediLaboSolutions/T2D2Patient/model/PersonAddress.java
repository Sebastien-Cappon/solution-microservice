package com.mediLaboSolutions.T2D2Patient.model;

import com.mediLaboSolutions.T2D2Patient.model.key.PersonAddressKey;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "person_address")
public class PersonAddress {

	@EmbeddedId
	private PersonAddressKey id;

	public PersonAddressKey getId() {
		return id;
	}

	public void setId(PersonAddressKey id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return ("[" + id + "]");
	}
}