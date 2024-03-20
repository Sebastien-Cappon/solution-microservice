package com.mediLaboSolutions.T2D2Note.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mediLaboSolutions.T2D2Note.model.Note;
import com.mediLaboSolutions.T2D2Note.repository.INoteRepository;
import com.mediLaboSolutions.T2D2Note.service.contracts.INoteService;

/**
 * A service class which performs the business processes relating to the POJO
 * <code>Note</code> before calling the repository.
 * 
 * @author Sébastien Cappon
 * @version 1.0
 */
@Service
public class NoteService implements INoteService {

	private static Logger logger = LoggerFactory.getLogger(NoteService.class);

	@Autowired
	private INoteRepository iNoteRepository;

	/**
	 * A <code>GET</code> method that returns a list of all notes.
	 * 
	 * @return A <code>Note</code> list.
	 */
	@Override
	public List<Note> getNotes() {
		return iNoteRepository.findAll();
	}

	/**
	 * A <code>GET</code> method that returns <code>Note</code> list attached to the
	 * person whose id is passed as a parameter, after calling the
	 * <code>findAllByPersonId()</code> derived query from repository.
	 * 
	 * @return A <code>Note</code> OR <code>null</code> if it doesn't exist in the
	 *         database.
	 */
	@Override
	public List<Note> getNotesByPersonId(int personId) {
		return iNoteRepository.findAllByPersonId(personId);
	}

	/**
	 * A <code>GET</code> method that returns a <code>Note</code> which id is passed
	 * as a parameter after calling the <code>findById()</code> derived query from
	 * repository.
	 * 
	 * @return A <code>Note</code> OR <code>null</code> if it doesn't exist in the
	 *         database.
	 */
	@Override
	public Note getNoteById(String noteId) {
		if (iNoteRepository.findById(noteId).isPresent()) {
			return iNoteRepository.findById(noteId).get();
		}

		return null;
	}

	/**
	 * A <code>POST</code> method that returns the <code>Note</code> passed as
	 * parameter if it is created, and calls the <code>MongoRepository</code>
	 * <code>save()</code> method.
	 * 
	 * @return A <code>Note</code> OR <code>null</code> if the <code>Note</code> is already in
	 *         the collection.
	 */
	@Override
	public Note createNote(Note newNote) throws Exception {
		for (Note checkNote : iNoteRepository.findByPersonId(newNote.getPersonId())) {
			if (checkNote.getContent().contentEquals(newNote.getContent())) {
				logger.warn("This note has already been written for this person.");
				return null;
			}
		}

		return iNoteRepository.save(newNote);
	}

	/**
	 * An <code>UPDATE</code> method that checks informations passed as
	 * <code>Note</code> parameter and calls then the derived query
	 * <code>save()</code> for the note which id is passed as the first parameter.
	 *
	 * @return An <code>Integer</code> OR <code>null</code> if the <code>Note</code>
	 *         doesn't exists in the collection.
	 */
	@Override
	public Integer updateNoteById(String noteId, Note updatedNote) throws Exception {
		if (iNoteRepository.findById(noteId).isPresent()) {
			Note noteToUpdate = iNoteRepository.findById(noteId).get();

			noteToUpdate.setContent(updatedNote.getContent());

			iNoteRepository.save(noteToUpdate);
			return 1;
		}

		logger.warn("Can't modify : This note doesn't exists in the database.");
		return null;
	}

	/**
	 * A <code>DELETE</code> method that deletes the note which id is passed as
	 * parameter. It calls the derived query <code>deleteById()</code> method.
	 */
	@Override
	public void deleteNoteById(String noteId) {
		iNoteRepository.deleteById(noteId);
	}
}