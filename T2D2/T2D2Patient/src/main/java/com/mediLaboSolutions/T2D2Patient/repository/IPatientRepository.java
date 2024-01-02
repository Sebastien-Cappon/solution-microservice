package com.mediLaboSolutions.T2D2Patient.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mediLaboSolutions.T2D2Patient.model.Patient;

public interface IPatientRepository extends JpaRepository<Patient, Integer>{

	Optional<Patient> findById(int patientId);
	
	void deleteById(int patient);
}