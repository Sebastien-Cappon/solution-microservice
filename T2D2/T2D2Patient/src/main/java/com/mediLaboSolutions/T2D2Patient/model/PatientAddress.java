package com.mediLaboSolutions.T2D2Patient.model;

import com.mediLaboSolutions.T2D2Patient.model.key.PatientAddressKey;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "patient_address")
public class PatientAddress {

	@EmbeddedId
	private PatientAddressKey id;

	public PatientAddressKey getId() {
		return id;
	}

	public void setId(PatientAddressKey id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return ("[" + id + "]");
	}
}