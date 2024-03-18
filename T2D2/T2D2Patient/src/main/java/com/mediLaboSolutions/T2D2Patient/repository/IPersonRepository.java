package com.mediLaboSolutions.T2D2Patient.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mediLaboSolutions.T2D2Patient.model.Person;

/**
 * Repository interface which extends the JPA (Jakarta Persistence API)
 * Repository in order to deal with derived queries relative to
 * <code>Person</code> entities.
 * 
 * @author Sébastien Cappon
 * @version 1.0
 */
@Repository
public interface IPersonRepository extends JpaRepository<Person, Integer> {

	Optional<Person> findById(int personId);
	Optional<Person> findByEmail(String personEmail);

	void deleteById(int personId);
}