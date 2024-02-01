package com.mediLaboSolutions.T2D2Practitioner.util;

import java.time.LocalDate;

import com.mediLaboSolutions.T2D2Practitioner.model.Note;

public class ModelInstanceBuilder {

	public static Note createNote(String id, int personId, LocalDate date, String content) {
		Note note = new Note();
		note.setId(id);
		note.setPersonId(personId);
		note.setDate(date);
		note.setContent(content);

		return note;
	}
}