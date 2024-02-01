package com.mediLaboSolutions.T2D2Patient.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mediLaboSolutions.T2D2Patient.model.Practitioner;

@Repository
public interface IPractitionerRepository extends JpaRepository<Practitioner, Integer> {

	Optional<Practitioner> findByEmail(String email);
	Optional<Practitioner> findById(int practitionerId);

	void deleteById(int practitionerId);
}