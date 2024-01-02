package com.mediLaboSolutions.T2D2Patient.model;

import com.mediLaboSolutions.T2D2Patient.model.key.PractitionerPatientKey;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "practitioner_patient")
public class PractitionerPatient {

	@EmbeddedId
	private PractitionerPatientKey id;

	public PractitionerPatientKey getId() {
		return id;
	}

	public void setId(PractitionerPatientKey id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return ("[" + id + "]");
	}
}