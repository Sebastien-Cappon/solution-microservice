package com.mediLaboSolutions.T2D2Patient.model;

import com.mediLaboSolutions.T2D2Patient.model.key.PractitionerPersonKey;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "practitioner_person")
public class PractitionerPerson {

	@EmbeddedId
	private PractitionerPersonKey id;

	public PractitionerPersonKey getId() {
		return id;
	}

	public void setId(PractitionerPersonKey id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return ("[" + id + "]");
	}
}