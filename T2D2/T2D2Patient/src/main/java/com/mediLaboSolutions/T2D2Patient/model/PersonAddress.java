package com.mediLaboSolutions.T2D2Patient.model;

import com.mediLaboSolutions.T2D2Patient.model.key.PersonAddressKey;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * A model class which creates the POJO (Plain Old Java Object) <code>PersonAddress</code>.
 * It contains getter and setter, as well as an override
 * <code>toSring()</code> method for display in the console.
 *
 * @singularity <code>PersonAddress</code> is linked to the <code>practitioner_person</code> join table of
 *              the database. Its only attribute is the composite key <code>PErsonAddressKey</code>.
 *
 * @author Sébastien Cappon
 * @version 1.0
 */
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

	/**
	 * An override method for user-friendly display of <code>PersonAddress</code>
	 * attributes in the console. Not necessary, except for test purposes.
	 * 
	 * @return <code>String</code> containing all the attributes of
	 *         <code>PersonAddress</code>.
	 */
	@Override
	public String toString() {
		return ("[" + id + "]");
	}
}