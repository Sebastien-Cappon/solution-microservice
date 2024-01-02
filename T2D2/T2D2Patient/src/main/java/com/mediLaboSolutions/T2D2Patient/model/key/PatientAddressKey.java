package com.mediLaboSolutions.T2D2Patient.model.key;

import java.io.Serializable;

import com.mediLaboSolutions.T2D2Patient.model.Address;
import com.mediLaboSolutions.T2D2Patient.model.Patient;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class PatientAddressKey implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne()
	@JoinColumn(name = "patient_address_patient_id")
	private Patient patient;

	@ManyToOne()
	@JoinColumn(name = "patient_address_address_id")
	private Address address;

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}