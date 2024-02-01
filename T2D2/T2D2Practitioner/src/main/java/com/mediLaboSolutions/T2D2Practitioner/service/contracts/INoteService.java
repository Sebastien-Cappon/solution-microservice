package com.mediLaboSolutions.T2D2Practitioner.service.contracts;

import java.util.List;

import com.mediLaboSolutions.T2D2Practitioner.model.Note;

public interface INoteService {

	public List<Note> getNotes();
	public List<Note> getNotesByPersonId(int personId);
	public Note getNoteById(String noteId);

	public Note createNote(Note newNote) throws Exception;

	public Integer updateNoteById(String noteId, String updatedNoteContent) throws Exception;

	public void deleteNoteById(String noteId);
}