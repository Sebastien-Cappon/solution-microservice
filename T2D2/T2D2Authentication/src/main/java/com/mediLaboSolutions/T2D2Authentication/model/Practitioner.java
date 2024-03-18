package com.mediLaboSolutions.T2D2Authentication.model;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonView;
import com.mediLaboSolutions.T2D2Authentication.view.PractitionerView;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * A model class which creates the POJO (Plain Old Java Object)
 * <code>Practitioner</code>. It contains getters and setters, as well as an
 * override <code>toSring()</code> method for display in the console.
 *
 * @singularity <code>Practitioner</code> is linked to the
 *              <code>practitioner</code> table of the database. For security
 *              purpose, the password attribute is not attached to a view
 *              interface.
 *
 * @author Sébastien Cappon
 * @version 1.0
 */
@Entity
@DynamicUpdate
@Table(name = "practitioner")
public class Practitioner {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(PractitionerView.IdView.class)
	@Column(name = "practitioner_id")
	private int id;
	@JsonView(PractitionerView.LastnameView.class)
	@Column(name = "practitioner_lastname")
	private String lastname;
	@JsonView(PractitionerView.FirstnameView.class)
	@Column(name = "practitioner_firstname")
	private String firstname;
	@JsonView(PractitionerView.EmailView.class)
	@Column(name = "practitioner_email")
	private String email;
	@Column(name = "practitioner_password")
	private String password;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * An override method for user-friendly display of <code>Practitioner</code>
	 * attributes in the console. Not necessary, except for test purposes.
	 * 
	 * @return <code>String</code> containing all the attributes of
	 *         <code>Practitioner</code>.
	 */
	@Override
	public String toString() {
		return ("[" + id + "]" + "[" + lastname + "]" + "[" + firstname + "]" + "[" + email + "]" + "[" + password + "]");
	}
}