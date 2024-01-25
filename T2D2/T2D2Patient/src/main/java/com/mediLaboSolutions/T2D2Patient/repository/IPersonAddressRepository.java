package com.mediLaboSolutions.T2D2Patient.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mediLaboSolutions.T2D2Patient.constant.MySqlQuery;
import com.mediLaboSolutions.T2D2Patient.model.PersonAddress;

public interface IPersonAddressRepository extends JpaRepository<PersonAddress, Integer> {

	@Query(value = MySqlQuery.allPersonAddressesByPersonId, nativeQuery = true)
	public List<PersonAddress> getPersonAddressesByPersonId(int personId);
}