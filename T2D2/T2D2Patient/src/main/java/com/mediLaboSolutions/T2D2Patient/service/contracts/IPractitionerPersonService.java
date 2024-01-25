package com.mediLaboSolutions.T2D2Patient.service.contracts;

import java.util.List;

import com.mediLaboSolutions.T2D2Patient.dto.PractitionerPersonAddDto;
import com.mediLaboSolutions.T2D2Patient.model.Person;

public interface IPractitionerPersonService {

	public List<Person> getPersonsByPractitionerId(int practitionerId);
	public List<Person> getNotPatientsByPractitionerId(int practitionerId);

	public Integer addPersonToPractitioner(PractitionerPersonAddDto practitionerPersonAddDto);

	public void deletePersonFromPractitioner(int practitionerId, int personId);
}