package com.mediLaboSolutions.T2D2Patient.model;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * A model class which creates the POJO (Plain Old Java Object)
 * <code>Address</code>. It contains getters and setters, as well as an override
 * <code>toSring()</code> method for display in the console.
 *
 * @singularity <code>Address</code> is linked to the <code>address</code> table
 *              of the database.
 * 
 * @author Sébastien Cappon
 * @version 1.0
 */
@Entity
@DynamicUpdate
@Table(name = "address")
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "address_id")
	private int id;
	@Column(name = "address_number")
	private String number;
	@Column(name = "address_way_type")
	private String wayType;
	@Column(name = "address_way_name")
	private String wayName;
	@Column(name = "address_postcode")
	private String postcode;
	@Column(name = "address_city")
	private String city;
	@Column(name = "address_country")
	private String country;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getWayType() {
		return wayType;
	}

	public void setWayType(String wayType) {
		this.wayType = wayType;
	}

	public String getWayName() {
		return wayName;
	}

	public void setWayName(String wyaName) {
		this.wayName = wyaName;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * An override method for user-friendly display of <code>Address</code>
	 * attributes in the console. Not necessary, except for test purposes.
	 * 
	 * @return <code>String</code> containing all the attributes of
	 *         <code>Address</code>.
	 */
	@Override
	public String toString() {
		return ("[" + id + "]" + "[" + number + "]" + "[" + wayType + "]" + "[" + wayName + "]" + "[" + postcode + "]"
				+ "[" + city + "]" + "[" + country + "]");
	}
}