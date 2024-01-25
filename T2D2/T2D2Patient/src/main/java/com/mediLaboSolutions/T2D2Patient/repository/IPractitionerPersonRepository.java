package com.mediLaboSolutions.T2D2Patient.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mediLaboSolutions.T2D2Patient.constant.MySqlQuery;
import com.mediLaboSolutions.T2D2Patient.model.PractitionerPerson;

public interface IPractitionerPersonRepository extends JpaRepository<PractitionerPerson, Integer> {

	@Query(value = MySqlQuery.allPersonsByPractitionerId, nativeQuery = true)
	public List<PractitionerPerson> getPersonsByPractitionerId(int practitionerId);
}