package com.mediLaboSolutions.T2D2Note.service.contracts;

import java.util.List;

import com.mediLaboSolutions.T2D2Note.model.Note;

/**
 * <code>NoteService</code> interface that abstracts it from its implementation
 * in order to achieve better code modularity in compliance with SOLID
 * principles.
 *
 * @author Sébastien Cappon
 * @version 1.0
 */
public interface INoteService {

	public List<Note> getNotes();
	public List<Note> getNotesByPersonId(int personId);
	public Note getNoteById(String noteId);

	public Note createNote(Note newNote) throws Exception;

	public Integer updateNoteById(String noteId, Note updatedNote) throws Exception;

	public void deleteNoteById(String noteId);
}