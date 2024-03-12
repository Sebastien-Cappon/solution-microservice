package com.mediLaboSolutions.T2D2Patient.model.key;

import java.io.Serializable;

import com.mediLaboSolutions.T2D2Patient.model.Person;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class PractitionerPersonKey implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "practitioner_person_practitioner_id")
	private int practitionerId;

	@ManyToOne()
	@JoinColumn(name = "practitioner_person_person_id")
	private Person person;

	public int getPractitioner() {
		return practitionerId;
	}

	public void setPractitioner(int practitionerId) {
		this.practitionerId = practitionerId;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
}