package com.mediLaboSolutions.T2D2Patient.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mediLaboSolutions.T2D2Patient.model.Person;

@Repository
public interface IPersonRepository extends JpaRepository<Person, Integer> {

	Optional<Person> findById(int personId);
	Optional<Person> findByEmail(String personEmail);

	void deleteById(int personId);
}