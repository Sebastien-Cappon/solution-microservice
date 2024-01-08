package com.mediLaboSolutions.T2D2Patient.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mediLaboSolutions.T2D2Patient.constant.MySqlQuery;
import com.mediLaboSolutions.T2D2Patient.model.PatientAddress;

public interface IPatientAddressRepository extends JpaRepository<PatientAddress, Integer> {
	
	@Query(value = MySqlQuery.allPatientAddressesByPatientId, nativeQuery = true)
	public List<PatientAddress> getPatientAddressesByPatientId(int patientId);
}