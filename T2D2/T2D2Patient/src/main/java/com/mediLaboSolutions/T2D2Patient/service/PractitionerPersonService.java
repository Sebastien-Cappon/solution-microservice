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

/**
 * A service class which performs the business processes relating to the POJO
 * <code>PractitionerPerson</code> before calling the repository.
 * 
 * @author Sébastien Cappon
 * @version 1.0
 */
@Service
public class PractitionerPersonService implements IPractitionerPersonService{

	@Autowired
	IPractitionerPersonRepository iPractitionerPersonRepository;
	@Autowired
	IPersonRepository iPersonRepository;
	
	/**
	 * A <code>GET</code> method that returns a list of all persons who are patients
	 * of the practitioner whose id is passed as parameter.
	 * 
	 * @return An <code>Person</code> list.
	 */
	@Override
	public List<Person> getPersonsByPractitionerId(int practitionerId) {
		List<PractitionerPerson> personsByPractitionerId = iPractitionerPersonRepository.getPersonsByPractitionerId(practitionerId);
		List<Person> persons = new ArrayList<>();
		
		for (PractitionerPerson practitionerPerson : personsByPractitionerId) {
			persons.add(practitionerPerson.getId().getPerson());
		}
	
		return persons;
	}
	
	/**
	 * A <code>GET</code> method that returns a list of all persons who are NOT
	 * patients of the practitioner whose id is passed as parameter.
	 * 
	 * @return An <code>Person</code> list.
	 */
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
	
	/**
	 * A <code>POST</code> method that returns an <code>Integer</code> if the
	 * PractitionerPersonAddDto passed as parameter is not existing yet in the
	 * database, and calls the <code>JpaRepository</code> <code>save()</code>
	 * method.
	 * 
	 * @return An <code>Integer</code> OR <code>null</code> if the junction already
	 *         exists in the database.
	 */
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
	
	/**
	 * A <code>DELETE</code> method that deletes the junction between a practitioner
	 * and a person which ids are passed as parameters. It calls the derived query
	 * <code>deleteById()</code> method.
	 */
	@Override
	public void deletePersonFromPractitioner(int practitionerId, int personId) {
		if(iPersonRepository.findById(personId).isPresent()) {
			Person person = iPersonRepository.findById(personId).get();
			
			PractitionerPerson practitionerPersonToDelete = PractitionerPersonBuilder.createPractitionerPerson(practitionerId, person);
			iPractitionerPersonRepository.delete(practitionerPersonToDelete);
		}	
	}
}