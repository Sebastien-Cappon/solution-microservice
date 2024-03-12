package com.mediLaboSolutions.T2D2Practitioner.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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

	@Override
	public String toString() {
		return ("[" + id + "]" + "[" + personId + "]" + "[" + date + "]" + "[" + content + "]");
	}
}