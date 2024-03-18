package com.mediLaboSolutions.T2D2Patient.model;

import com.mediLaboSolutions.T2D2Patient.model.key.PractitionerPersonKey;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * A model class which creates the POJO (Plain Old Java Object)
 * <code>PractitionerPerson</code>. It contains getter and setter, as well as an
 * override <code>toSring()</code> method for display in the console.
 *
 * @singularity <code>PractitionerPerson</code> is linked to the
 *              <code>practitioner_person</code> join table of the database. Its
 *              only attribute is the composite key
 *              <code>PractitionerPersonKey</code>.
 *
 * @author Sébastien Cappon
 * @version 1.0
 */
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

	/**
	 * An override method for user-friendly display of
	 * <code>PractitionerPerson</code> attributes in the console. Not necessary,
	 * except for test purposes.
	 * 
	 * @return <code>String</code> containing all the attributes of
	 *         <code>PractitionerPerson</code>.
	 */
	@Override
	public String toString() {
		return ("[" + id + "]");
	}
}