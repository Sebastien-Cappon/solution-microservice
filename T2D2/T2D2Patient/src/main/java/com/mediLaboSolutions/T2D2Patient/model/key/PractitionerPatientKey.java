package com.mediLaboSolutions.T2D2Patient.model.key;

import java.io.Serializable;

import com.mediLaboSolutions.T2D2Patient.model.Patient;
import com.mediLaboSolutions.T2D2Patient.model.Practitioner;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class PractitionerPatientKey implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne()
	@JoinColumn(name = "practitioner_patient_practitioner_id")
	private Practitioner practitioner;

	@ManyToOne()
	@JoinColumn(name = "practitioner_patient_patient_id")
	private Patient patient;

	public Practitioner getPractitioner() {
		return practitioner;
	}

	public void setPractitioner(Practitioner practitioner) {
		this.practitioner = practitioner;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}
}