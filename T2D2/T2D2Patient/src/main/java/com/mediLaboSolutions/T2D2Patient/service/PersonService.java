package com.mediLaboSolutions.T2D2Patient.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mediLaboSolutions.T2D2Patient.model.Person;
import com.mediLaboSolutions.T2D2Patient.repository.IPersonRepository;
import com.mediLaboSolutions.T2D2Patient.service.contracts.IPersonService;

@Service
public class PersonService implements IPersonService {

	private static final Logger logger = LoggerFactory.getLogger(PersonService.class);

	@Autowired
	private IPersonRepository iPersonRepository;

	@Override
	public List<Person> getPersons() {
		return iPersonRepository.findAll();
	}

	@Override
	public Person getPersonById(int personId) {
		if (iPersonRepository.findById(personId).isPresent()) {
			return iPersonRepository.findById(personId).get();
		}

		return null;
	}

	@Override
	public Person getPersonByEmail(String personEmail) {
		if (iPersonRepository.findByEmail(personEmail).isPresent()) {
			return iPersonRepository.findByEmail(personEmail).get();
		}

		// FRONT END MATTER : NEED EMPTY OBJECT IF PERSON DOESN'T EXIST, IN ORDER TO
		// DEAL WITH OBSERVEABLES INTO HTML FILE OF THE COMPONENT, WITHOUT SUBSCRIBING
		// IT IN THE TS FILE OF THE COMPONENT.
		return new Person();
	}

	@Override
	public Person createPerson(Person newPerson) throws Exception {
		for (Person checkPerson : iPersonRepository.findAll()) {
			if (checkPerson.getGender().equals(newPerson.getGender())
					&& checkPerson.getLastname().contentEquals(newPerson.getLastname())
					&& checkPerson.getFirstname().contentEquals(newPerson.getFirstname())
					&& checkPerson.getBirthdate().equals(newPerson.getBirthdate())
					&& checkPerson.getPhone().contentEquals(newPerson.getPhone())
					&& checkPerson.getEmail().contentEquals(newPerson.getEmail())) {
				logger.warn("This person already exists in the database.");
				return null;
			}
		}

		return iPersonRepository.save(newPerson);
	}

	@Override
	public Integer updatePersonById(int personId, Person updatedPerson) throws Exception {
		if (iPersonRepository.findById(personId).isPresent()) {
			Person personToUpdate = iPersonRepository.findById(personId).get();

			updatedPerson.setId(personToUpdate.getId());
			if (updatedPerson.getGender() == null) {
				updatedPerson.setGender(personToUpdate.getGender());
			}
			if (updatedPerson.getLastname() == null || updatedPerson.getLastname().isBlank()) {
				updatedPerson.setLastname(personToUpdate.getLastname());
			}
			if (updatedPerson.getFirstname() == null || updatedPerson.getFirstname().isBlank()) {
				updatedPerson.setFirstname(personToUpdate.getFirstname());
			}
			if (updatedPerson.getBirthdate() == null) {
				updatedPerson.setBirthdate(personToUpdate.getBirthdate());
			}
			if (updatedPerson.getPhone() == null || updatedPerson.getPhone().isBlank()) {
				updatedPerson.setPhone(personToUpdate.getPhone());
			}
			if (updatedPerson.getEmail() == null || updatedPerson.getEmail().isBlank()) {
				updatedPerson.setEmail(personToUpdate.getEmail());
			}

			iPersonRepository.save(updatedPerson);
			return 1;
		}

		logger.warn("Can't modify : This person doesn't exist in the database.");
		return null;
	}

	@Override
	public void deletePersonById(int personId) {
		iPersonRepository.deleteById(personId);
	}
}