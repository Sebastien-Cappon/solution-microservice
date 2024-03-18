package com.mediLaboSolutions.T2D2Authentication.dto;

/**
 * A model class which creates the DTO (Data Transfer Object)
 * <code>PractitionerLoginDto</code>. It contains getters and setters, as well
 * as an override <code>toSring()</code> method for display in the console.
 * 
 * @frontCall Refers to <code>AuthValue</code> model class.
 * 
 * @singularity <code>PractitionerLoginDto</code> contains the e-mail address
 *              and the non-encrypted password of the <code>User</code> to
 *              connect.
 *
 * @author Sébastien Cappon
 * @version 1.0
 */
public class PractitionerLoginDto {

	private String email;
	private String password;

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
	 * An override method for user-friendly display of
	 * <code>PractitionerLoginDto</code> attributes in the console. Not necessary,
	 * except for test purposes.
	 * 
	 * @return <code>String</code> containing all the attributes of
	 *         <code>PractitionerLoginDto</code>.
	 */
	@Override
	public String toString() {
		return ("[" + email + "]" + "[" + password + "]");
	}
}