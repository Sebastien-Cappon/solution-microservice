package com.mediLaboSolutions.T2D2Patient.model.key;

import java.io.Serializable;

import com.mediLaboSolutions.T2D2Patient.model.Address;
import com.mediLaboSolutions.T2D2Patient.model.Person;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * The serializable composite Key <code>PersonAddressKey</code> of
 * <code>PersonAddress</code> class. It contains getters and setters, as well as
 * an override <code>toSring()</code> method for display in the console.
 *
 * @singularity It contains two Many-To-One relationship with the
 *              <code>Person</code> and <code>Address</code> classes to
 *              represent the link between a person and an address.
 *
 *
 * @author Sébastien Cappon
 * @version 1.0
 */
@Embeddable
public class PersonAddressKey implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne()
	@JoinColumn(name = "person_address_person_id")
	private Person person;

	@ManyToOne()
	@JoinColumn(name = "person_address_address_id")
	private Address address;

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}