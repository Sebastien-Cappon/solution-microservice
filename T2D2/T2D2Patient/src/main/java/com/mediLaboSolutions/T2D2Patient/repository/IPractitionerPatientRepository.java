package com.mediLaboSolutions.T2D2Patient.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mediLaboSolutions.T2D2Patient.constant.MySqlQuery;
import com.mediLaboSolutions.T2D2Patient.model.PractitionerPatient;

public interface IPractitionerPatientRepository extends JpaRepository<PractitionerPatient, Integer>{

	@Query(value = MySqlQuery.allPatientsByPractitionerId, nativeQuery = true)
	public List<PractitionerPatient> getPatientsByPractitionerId(int practitionerId);
}