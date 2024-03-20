package com.mediLaboSolutions.T2D2Note.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.mediLaboSolutions.T2D2Note.model.Note;
import com.mediLaboSolutions.T2D2Note.service.contracts.INoteService;

/**
 * A class that receives requests made from some of the usual CRUD endpoints and
 * specific URL for the Note.
 * 
 * @author Sébastien Cappon
 * @version 1.0
 */
@RestController
public class NoteController {

	@Autowired
	private INoteService iNoteService;

	/**
	 * A <code>GetMapping</code> method on the <code>/notes</code> URI. It calls
	 * the eponymous <code>INoteService</code> method and returns a list of
	 * <code>Note</code> model entities.
	 * 
	 * @singularity Method created for testing purposes with Postman. Could be use
	 *              for administration.
	 * 
	 * @return A <code>Note</code> list.
	 */
	@GetMapping("/notes")
	public List<Note> getNotes() {
		return iNoteService.getNotes();
	}

	/**
	 * A <code>GetMapping</code> method on the <code>/notes/persons</code> URI with
	 * a person id as <code>PathVariable</code>. It calls the eponymous
	 * <code>INoteService</code> method and returns a list of <code>Note</code>
	 * model entities related to the person whose id is the one passed in parameter.
	 * 
	 * @frontCall <code>note.service.ts</code> : <code>getNotes(personId: number)</code>
	 * 
	 * @return A <code>Note</code> list.
	 */
	@GetMapping("/notes/persons/{personId}")
	public List<Note> getNotesByPersonId(@PathVariable("personId") int personId) {
		return iNoteService.getNotesByPersonId(personId);
	}

	/**
	 * A <code>GetMapping</code> method on the <code>/notes</code> URI with an
	 * note id as <code>PathVariable</code>. It calls the eponymous
	 * <code>INoteService</code> method and returns the <code>Note</code>
	 * model entity whose id is the one passed in parameter.
	 * 
	 * @frontCall <code>note.service.ts</code> : <code>getNoteById(noteId: string)</code>
	 * 
	 * @return A <code>Note</code>.
	 */
	@GetMapping("/notes/{noteId}")
	public Note getNoteById(@PathVariable("noteId") String noteId) {
		return iNoteService.getNoteById(noteId);
	}

	/**
	 * A <code>PostMapping</code> method on the <code>/notes</code> URI with a Note
	 * model entity as <code>RequestBody</code>. It calls the eponymous
	 * <code>INoteService</code> method and returns the <code>Note</code> added with
	 * status code 201. If the result is null, it returns status code 400.
	 * 
	 * @frontCall <code>note.service.ts</code> : <code>createNewNote(newNote: Note)</code>
	 * 
	 * @throws <code>BAD_REQUEST</code> if the note already exist in the note
	 * collection.
	 * 
	 * @return A <code>Note</code> and a status code.
	 */
	@PostMapping("/note")
	public ResponseEntity<Note> createNote(@RequestBody Note newNote) throws Exception {
		Note createdNote = iNoteService.createNote(newNote);

		if (createdNote == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Note>(createdNote, HttpStatus.CREATED);
		}
	}

	/**
	 * A <code>PutMapping</code> method on the <code>/notes</code> URI with an note
	 * id as <code>PathVariables</code> and a <code>Note</code> as
	 * <code>RequestBody</code>. It calls the eponymous <code>INoteService</code>
	 * method and returns an <code>Integer</code> used by the front end to determine
	 * the success of the request, with status code 200. If the result is null, it
	 * returns status code 400.
	 * 
	 * @frontCall <code>note.service.ts</code> : <code>updateNoteById(noteId: string, noteUpdate: Note)</code>
	 * 
	 * @throws <code>BAD_REQUEST</code> if the note concerned doesn't exist.
	 * 
	 * @return An <code>Integer</code> and a status code.
	 */
	@PutMapping("/notes/{noteId}")
	public ResponseEntity<Integer> updateNote(@PathVariable("noteId") String noteId, @RequestBody Note updatedNote) throws Exception {
		Integer isUpdated = iNoteService.updateNoteById(noteId, updatedNote);

		if (isUpdated == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Integer>(HttpStatus.OK);
		}
	}

	/**
	 * A <code>DeleteMapping</code> method on the <code>/notes</code> URI with the a
	 * note id as <code>PathVariables</code>. It calls the eponymous
	 * <code>INoteService</code> method and returns nothing.
	 * 
	 * @frontCall <code>note.service.ts</code> : <code>deleteNoteById(noteId: string)</code>
	 */
	@DeleteMapping("/notes/{noteId}")
	public void deleteNoteById(@PathVariable("noteId") String noteId) {
		iNoteService.deleteNoteById(noteId);
	}
}