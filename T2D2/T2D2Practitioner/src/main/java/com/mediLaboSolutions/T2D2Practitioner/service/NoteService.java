package com.mediLaboSolutions.T2D2Practitioner.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mediLaboSolutions.T2D2Practitioner.model.Note;
import com.mediLaboSolutions.T2D2Practitioner.repository.INoteRepository;
import com.mediLaboSolutions.T2D2Practitioner.service.contracts.INoteService;

@Service
public class NoteService implements INoteService {

	private static Logger logger = LoggerFactory.getLogger(NoteService.class);

	@Autowired
	private INoteRepository iNoteRepository;

	@Override
	public List<Note> getNotes() {
		return iNoteRepository.findAll();
	}

	@Override
	public List<Note> getNotesByPersonId(int personId) {
		return iNoteRepository.findAllByPersonId(personId);
	}

	@Override
	public Note getNoteById(String noteId) {
		return iNoteRepository.findById(noteId).get();
	}

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

	@Override
	public void deleteNoteById(String noteId) {
		iNoteRepository.deleteById(noteId);
	}
}