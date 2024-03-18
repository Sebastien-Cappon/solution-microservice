package com.mediLaboSolutions.T2D2Patient.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mediLaboSolutions.T2D2Patient.constant.MySqlQuery;
import com.mediLaboSolutions.T2D2Patient.model.PractitionerPerson;

/**
 * Repository interface which extends the JPA (Jakarta Persistence API)
 * Repository in order to deal with derived queries relative to
 * <code>PractitionerPerson</code> entities.
 * 
 * @author Sébastien Cappon
 * @version 1.0
 */
@Repository
public interface IPractitionerPersonRepository extends JpaRepository<PractitionerPerson, Integer> {

	@Query(value = MySqlQuery.allPersonsByPractitionerId, nativeQuery = true)
	public List<PractitionerPerson> getPersonsByPractitionerId(int practitionerId);
}