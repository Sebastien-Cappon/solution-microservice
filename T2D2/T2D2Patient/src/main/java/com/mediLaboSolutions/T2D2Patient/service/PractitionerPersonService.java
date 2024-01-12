package com.mediLaboSolutions.T2D2Patient.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mediLaboSolutions.T2D2Patient.model.Person;
import com.mediLaboSolutions.T2D2Patient.model.Practitioner;
import com.mediLaboSolutions.T2D2Patient.model.PractitionerPerson;
import com.mediLaboSolutions.T2D2Patient.repository.IPersonRepository;
import com.mediLaboSolutions.T2D2Patient.repository.IPractitionerPersonRepository;
import com.mediLaboSolutions.T2D2Patient.repository.IPractitionerRepository;
import com.mediLaboSolutions.T2D2Patient.service.contracts.IPractitionerPersonService;
import com.mediLaboSolutions.T2D2Patient.util.PractitionerPersonBuilder;

@Service
public class PractitionerPersonService implements IPractitionerPersonService{

	@Autowired
	IPractitionerPersonRepository iPractitionerPersonRepository;
	@Autowired
	IPractitionerRepository iPractitionerRepository;
	@Autowired
	IPersonRepository iPersonRepository;
	
	@Override
	public List<Person> getPersonsByPractitionerId(int practitionerId) {
		List<PractitionerPerson> personsByPractitionerId = iPractitionerPersonRepository.getPersonsByPractitionerId(practitionerId);
		List<Person> persons = new ArrayList<>();
		
		for (PractitionerPerson practitionerPerson : personsByPractitionerId) {
			persons.add(practitionerPerson.getId().getPerson());
		}
	
		return persons;
	}
	
	@Override
	public Integer addPersonToPractitioner(int practitionerId, int personId) {
		if(iPractitionerRepository.findById(practitionerId).isPresent() && iPersonRepository.findById(personId).isPresent()) {
			Practitioner practitioner = iPractitionerRepository.findById(practitionerId).get();
			Person person = iPersonRepository.findById(personId).get();
			
			PractitionerPerson newPractitionerPerson = PractitionerPersonBuilder.createPractitionerPerson(practitioner, person);
			iPractitionerPersonRepository.save(newPractitionerPerson);
			
			return 1;
		}
		
		return null;
	}
	
	@Override
	public void deletePersonFromPractitioner(int practitionerId, int personId) {
		if(iPractitionerRepository.findById(practitionerId).isPresent() && iPersonRepository.findById(personId).isPresent()) {
			Practitioner practitioner = iPractitionerRepository.findById(practitionerId).get();
			Person person = iPersonRepository.findById(personId).get();
			
			PractitionerPerson practitionerPersonToDelete = PractitionerPersonBuilder.createPractitionerPerson(practitioner, person);
			iPractitionerPersonRepository.delete(practitionerPersonToDelete);
		}	
	}
}