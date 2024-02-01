package com.mediLaboSolutions.T2D2Practitioner.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.mediLaboSolutions.T2D2Practitioner.model.Note;
import com.mediLaboSolutions.T2D2Practitioner.service.contracts.INoteService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class NoteController {

	@Autowired
	private INoteService iNoteService;

	@GetMapping("/notes")
	public List<Note> getNotes() {
		return iNoteService.getNotes();
	}

	@GetMapping("/notes/persons/{personId}")
	public List<Note> getNotesByPersonId(@PathVariable("personId") int personId) {
		return iNoteService.getNotesByPersonId(personId);
	}

	@GetMapping("/notes/{noteId}")
	public Note getNoteById(@PathVariable("noteId") String noteId) {
		return iNoteService.getNoteById(noteId);
	}

	@PostMapping("/note")
	public ResponseEntity<Note> createNote(@RequestBody Note newNote) throws Exception {
		Note createdNote = iNoteService.createNote(newNote);

		if (createdNote == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Note>(createdNote, HttpStatus.CREATED);
		}
	}

	@PutMapping("/notes/{noteId}")
	public ResponseEntity<Integer> updateNote(@PathVariable("noteId") String noteId, @RequestBody String updatedNoteContent) throws Exception {
		Integer isUpdated = iNoteService.updateNoteById(noteId, updatedNoteContent);

		if (isUpdated == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Integer>(HttpStatus.OK);
		}
	}

	@DeleteMapping("/notes/{noteId}")
	public void deleteNoteById(@PathVariable("noteId") String noteId) {
		iNoteService.deleteNoteById(noteId);
	}
}