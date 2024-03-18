package com.mediLaboSolutions.T2D2Diabetes.model;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * A model class which creates the POJO (Plain Old Java Object)
 * <code>TriggerTerm</code>. It contains getters and setters, as well as an
 * override <code>toSring()</code> method for display in the console.
 *
 * @singularity <code>TriggerTerm</code> is linked to the
 *              <code>triggerterm</code> table of the database.
 *
 * @author Sébastien Cappon
 * @version 1.0
 */
@Entity
@DynamicUpdate
@Table(name = "triggerterm")
public class TriggerTerm {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "triggerterm_id")
	private int id;
	@Column(name = "triggerterm_term")
	private String term;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	/**
	 * An override method for user-friendly display of <code>TriggerTerm</code>
	 * attributes in the console. Not necessary, except for test purposes.
	 * 
	 * @return <code>String</code> containing all the attributes of
	 *         <code>TriggerTerm</code>.
	 */
	@Override
	public String toString() {
		return ("[" + id + "]" + "[" + term + "]");
	}
}