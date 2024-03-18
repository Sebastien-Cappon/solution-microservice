package com.mediLaboSolutions.T2D2Patient.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mediLaboSolutions.T2D2Patient.constant.MySqlQuery;
import com.mediLaboSolutions.T2D2Patient.model.PersonAddress;

/**
 * Repository interface which extends the JPA (Jakarta Persistence API)
 * Repository in order to deal with derived queries relative to
 * <code>PersonAddress</code> entities.
 * 
 * @author Sébastien Cappon
 * @version 1.0
 */
@Repository
public interface IPersonAddressRepository extends JpaRepository<PersonAddress, Integer> {

	@Query(value = MySqlQuery.allPersonAddressesByPersonId, nativeQuery = true)
	public List<PersonAddress> getPersonAddressesByPersonId(int personId);
}