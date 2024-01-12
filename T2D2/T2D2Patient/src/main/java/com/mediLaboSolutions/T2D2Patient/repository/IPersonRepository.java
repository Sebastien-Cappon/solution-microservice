package com.mediLaboSolutions.T2D2Patient.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mediLaboSolutions.T2D2Patient.model.Person;

public interface IPersonRepository extends JpaRepository<Person, Integer>{

	Optional<Person> findById(int personId);
	
	void deleteById(int personId);
}