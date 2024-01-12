package com.mediLaboSolutions.T2D2Patient.service.contracts;

import java.util.List;

import com.mediLaboSolutions.T2D2Patient.model.Person;

public interface IPractitionerPersonService {

	public List<Person> getPersonsByPractitionerId(int practitionerId);
	
	public Integer addPersonToPractitioner(int practitionerId, int personId);
	
	public void deletePersonFromPractitioner(int practitionerId, int personId);
}