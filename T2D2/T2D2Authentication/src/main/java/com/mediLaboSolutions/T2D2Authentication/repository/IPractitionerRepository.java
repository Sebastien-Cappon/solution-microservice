package com.mediLaboSolutions.T2D2Authentication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mediLaboSolutions.T2D2Authentication.model.Practitioner;

/**
 * Repository interface which extends the JPA (Jakarta Persistence API)
 * Repository in order to deal with derived queries relative to
 * <code>Practitioner</code> entities.
 * 
 * @author Sébastien Cappon
 * @version 1.0
 */
@Repository
public interface IPractitionerRepository extends JpaRepository<Practitioner, Integer> {

	Optional<Practitioner> fingById(int ipractitionerId);
	Optional<Practitioner> findByEmail(String practitionerEmail);
	void deleteById(int practitionerId);
}