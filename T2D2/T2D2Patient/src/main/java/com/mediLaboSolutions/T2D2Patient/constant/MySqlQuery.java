package com.mediLaboSolutions.T2D2Patient.constant;

/**
 * A class of constants that groups together a list of JPQL <i>(Java Persistence
 * Query Language)</i> queries. Most of these are linked to the application's
 * sorting system.
 *
 * @author Sébastien Cappon
 * @version 1.0
 */
public class MySqlQuery {

	public static final String allPatientAddressesByPatientId = "SELECT * FROM patient_address WHERE patient_address_patient_id = ?1";
	public static final String allPatientsByPractitionerId = "SELECT * FROM practitioner_patient WHERE practitioner_patient_practitioner_id = ?1";
}