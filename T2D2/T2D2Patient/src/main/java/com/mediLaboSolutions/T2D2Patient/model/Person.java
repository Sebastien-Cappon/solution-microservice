package com.mediLaboSolutions.T2D2Patient.model;

import java.time.ZonedDateTime;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@DynamicUpdate
@Table(name = "person")
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "person_id")
	private int id;
	@Column(name = "person_gender")
	private Boolean gender;
	@Column(name = "person_lastname")
	private String lastname;
	@Column(name = "person_firstname")
	private String firstname;
	@Column(name = "person_birthdate")
	private ZonedDateTime birthdate;
	@Column(name = "person_phone")
	private String phone;
	@Column(name = "person_email")
	private String email;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Boolean getGender() {
		return gender;
	}

	public void setGender(Boolean gender) {
		this.gender = gender;
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

	public ZonedDateTime getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(ZonedDateTime birthdate) {
		this.birthdate = birthdate;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return ("[" + id + "]" + "[" + gender + "]" + "[" + lastname + "]" + "[" + firstname + "]" + "[" + birthdate + "]" + "[" + phone + "]" + "[" + email + "]");
	}
}