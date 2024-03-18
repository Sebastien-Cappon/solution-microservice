package com.mediLaboSolutions.T2D2Patient.service.contracts;

import java.util.List;

import com.mediLaboSolutions.T2D2Patient.model.Person;

/**
 * <code>PersonService</code> interface that abstracts it from its
 * implementation in order to achieve better code modularity in compliance with
 * SOLID principles.
 *
 * @author Sébastien Cappon
 * @version 1.0
 */
public interface IPersonService {

	public List<Person> getPersons();
	public Person getPersonById(int personId);
	public Person getPersonByEmail(String personEmail);

	public Person createPerson(Person newPerson) throws Exception;

	public Integer updatePersonById(int personId, Person updatedPerson) throws Exception;

	public void deletePersonById(int personId);
}