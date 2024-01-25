package com.mediLaboSolutions.T2D2Patient.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mediLaboSolutions.T2D2Patient.model.Practitioner;

public interface IPractitionerRepository extends JpaRepository<Practitioner, Integer> {

	Optional<Practitioner> findByEmail(String email);
	Optional<Practitioner> findById(int practitionerId);

	void deleteById(int practitionerId);
}