package com.mediLaboSolutions.T2D2Patient.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mediLaboSolutions.T2D2Patient.dto.PractitionerPersonAddDto;
import com.mediLaboSolutions.T2D2Patient.model.Person;
import com.mediLaboSolutions.T2D2Patient.model.PractitionerPerson;
import com.mediLaboSolutions.T2D2Patient.repository.IPersonRepository;
import com.mediLaboSolutions.T2D2Patient.repository.IPractitionerPersonRepository;
import com.mediLaboSolutions.T2D2Patient.service.contracts.IPractitionerPersonService;
import com.mediLaboSolutions.T2D2Patient.util.PractitionerPersonBuilder;

@Service
public class PractitionerPersonService implements IPractitionerPersonService{

	@Autowired
	IPractitionerPersonRepository iPractitionerPersonRepository;
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
	public List<Person> getNotPatientsByPractitionerId(int practitionerId) {
		List<PractitionerPerson> personsByPractitionerId = iPractitionerPersonRepository.getPersonsByPractitionerId(practitionerId);
		List<Person> patients = new ArrayList<>();
		List<Person> persons = iPersonRepository.findAll();
		List<Person> notPatients = new ArrayList<>();
		
		for (PractitionerPerson practitionerPerson : personsByPractitionerId) {
			patients.add(practitionerPerson.getId().getPerson());
		}
		
		for (Person person : persons) {
			if (!patients.contains(person)) {
				notPatients.add(person);
			}
		}
		
		return notPatients;
	}
	
	@Override
	public Integer addPersonToPractitioner(PractitionerPersonAddDto practitionerPersonAddDto) {
		if(iPersonRepository.findByEmail(practitionerPersonAddDto.getPersonEmail()).isPresent()) {
			Person person = iPersonRepository.findByEmail(practitionerPersonAddDto.getPersonEmail()).get();
			
			PractitionerPerson newPractitionerPerson = PractitionerPersonBuilder.createPractitionerPerson(practitionerPersonAddDto.getPractitionerId(), person);
			iPractitionerPersonRepository.save(newPractitionerPerson);
			
			return 1;
		}
		
		return null;
	}
	
	@Override
	public void deletePersonFromPractitioner(int practitionerId, int personId) {
		if(iPersonRepository.findById(personId).isPresent()) {
			Person person = iPersonRepository.findById(personId).get();
			
			PractitionerPerson practitionerPersonToDelete = PractitionerPersonBuilder.createPractitionerPerson(practitionerId, person);
			iPractitionerPersonRepository.delete(practitionerPersonToDelete);
		}	
	}
}