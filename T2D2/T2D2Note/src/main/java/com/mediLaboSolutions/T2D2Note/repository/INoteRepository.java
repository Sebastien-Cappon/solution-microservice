package com.mediLaboSolutions.T2D2Note.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mediLaboSolutions.T2D2Note.model.Note;

/**
 * Repository interface which extends the Mongo Repository in order to deal with
 * derived queries relative to <code>Note</code> entities.
 * 
 * @author Sébastien Cappon
 * @version 1.0
 */
@Repository
public interface INoteRepository extends MongoRepository<Note, String> {

	Optional<Note> findById(String noteId);
	List<Note> findAllByPersonId(int personId);
	List<Note> findByPersonId(int personId);

	void deleteById(String noteId);
}