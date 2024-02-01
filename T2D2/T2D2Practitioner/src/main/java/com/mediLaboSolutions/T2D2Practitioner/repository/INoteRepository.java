package com.mediLaboSolutions.T2D2Practitioner.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mediLaboSolutions.T2D2Practitioner.model.Note;

@Repository
public interface INoteRepository extends MongoRepository<Note, String> {

	Optional<Note> findById(String noteId);
	List<Note> findAllByPersonId(int personId);
	List<Note> findByPersonId(int personId);

	void deleteById(String noteId);
}