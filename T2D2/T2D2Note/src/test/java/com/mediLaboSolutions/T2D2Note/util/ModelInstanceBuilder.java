package com.mediLaboSolutions.T2D2Note.util;

import java.time.LocalDateTime;

import com.mediLaboSolutions.T2D2Note.model.Note;

public class ModelInstanceBuilder {

	public static Note createNote(String id, int personId, LocalDateTime date, String content) {
		Note note = new Note();
		note.setId(id);
		note.setPersonId(personId);
		note.setDate(date);
		note.setContent(content);

		return note;
	}
}