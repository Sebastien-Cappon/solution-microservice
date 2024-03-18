package com.mediLaboSolutions.T2D2Patient.constant;

/**
 * A class of constants that groups together a list of JPQL <i>(Java Persistence
 * Query Language)</i> queries.
 *
 * @author Sébastien Cappon
 * @version 1.0
 */
public class MySqlQuery {

	public static final String allPersonAddressesByPersonId = "SELECT * FROM person_address WHERE person_address_person_id = ?1";
	public static final String allPersonsByPractitionerId = "SELECT * FROM practitioner_person WHERE practitioner_person_practitioner_id = ?1";
}