package com.mediLaboSolutions.T2D2Patient.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mediLaboSolutions.T2D2Patient.model.Person;
import com.mediLaboSolutions.T2D2Patient.repository.IPersonRepository;
import com.mediLaboSolutions.T2D2Patient.service.contracts.IPersonService;

/**
 * A service class which performs the business processes relating to the POJO
 * <code>Address</code> before calling the repository.
 * 
 * @author Sébastien Cappon
 * @version 1.0
 */
@Service
public class PersonService implements IPersonService {

	private static final Logger logger = LoggerFactory.getLogger(PersonService.class);

	@Autowired
	private IPersonRepository iPersonRepository;

	/**
	 * A <code>GET</code> method that returns a list of all persons.
	 * 
	 * @return A <code>Person</code> list.
	 */
	@Override
	public List<Person> getPersons() {
		return iPersonRepository.findAll();
	}

	/**
	 * A <code>GET</code> method that returns an <code>Person</code> whose id is
	 * passed as a parameter after calling the <code>findById()</code> derived query
	 * from repository.
	 * 
	 * @return A <code>Person</code> OR <code>null</code> if she doesn't exist in
	 *         the database.
	 */
	@Override
	public Person getPersonById(int personId) {
		if (iPersonRepository.findById(personId).isPresent()) {
			return iPersonRepository.findById(personId).get();
		}

		return null;
	}

	/**
	 * A <code>GET</code> method that returns an <code>Person</code> whose email is
	 * passed as a parameter after calling the <code>findByEmail()</code> derived
	 * query from repository.
	 * 
	 * @singularity An empty object must be returned if the person doesn't exists,
	 *              in order to deal with observables into HTML file of the
	 *              component, without subscribing it in the TS file component.
	 * 
	 * @return A <code>Person</code>.
	 */
	@Override
	public Person getPersonByEmail(String personEmail) {
		if (iPersonRepository.findByEmail(personEmail).isPresent()) {
			return iPersonRepository.findByEmail(personEmail).get();
		}

		return new Person();
	}

	/**
	 * A <code>POST</code> method that returns the <code>Person</code> passed as
	 * parameter if she is created, and calls the <code>JpaRepository</code>
	 * <code>save()</code> method.
	 * 
	 * @return A <code>User</code> OR <code>null</code> if the person is already in
	 *         the database.
	 */
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

	/**
	 * An <code>UPDATE</code> method that checks informations passed as
	 * <code>Person</code> parameter and calls then the derived query
	 * <code>save()</code> for the person whose id is passed as the first parameter.
	 *
	 * @return An <code>Integer</code> OR <code>null</code> if the
	 *         <code>Person</code> doesn't exists in the database.
	 */
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

	/**
	 * A <code>DELETE</code> method that deletes the person whose id is passed as
	 * parameter. It calls the derived query <code>deleteById()</code> method.
	 */
	@Override
	public void deletePersonById(int personId) {
		iPersonRepository.deleteById(personId);
	}
}