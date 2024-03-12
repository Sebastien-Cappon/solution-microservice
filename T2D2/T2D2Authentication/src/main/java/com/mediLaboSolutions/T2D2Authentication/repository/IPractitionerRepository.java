package com.mediLaboSolutions.T2D2Authentication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mediLaboSolutions.T2D2Authentication.model.Practitioner;

@Repository
public interface IPractitionerRepository extends JpaRepository<Practitioner, Integer> {

	Optional<Practitioner> findByEmail(String email);
}