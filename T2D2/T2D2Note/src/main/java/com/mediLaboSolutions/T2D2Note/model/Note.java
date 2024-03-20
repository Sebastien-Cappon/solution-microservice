package com.mediLaboSolutions.T2D2Note.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A model class which creates the POJO (Plain Old Java Object)
 * <code>Note</code>. It contains getters and setters, as well as an override
 * <code>toSring()</code> method for display in the console.
 *
 * @singularity <code>Note</code> is linked to the <code>note</code> collection
 *              of the database.
 * 
 * @author Sébastien Cappon
 * @version 1.0
 */
@Document(collection = "note")
public class Note {

	@Id
	private String id;
	@Field("note_person_id")
	private int personId;
	@Field("note_date")
	private LocalDateTime date;
	@Field("note_content")
	private String content;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * An override method for user-friendly display of <code>Note</code> attributes
	 * in the console. Not necessary, except for test purposes.
	 * 
	 * @return <code>String</code> containing all the attributes of
	 *         <code>Note</code>.
	 */
	@Override
	public String toString() {
		return ("[" + id + "]" + "[" + personId + "]" + "[" + date + "]" + "[" + content + "]");
	}
}