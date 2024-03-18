package com.mediLaboSolutions.T2D2Patient.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mediLaboSolutions.T2D2Patient.dto.PersonAddressAddDto;
import com.mediLaboSolutions.T2D2Patient.model.Address;
import com.mediLaboSolutions.T2D2Patient.model.Person;
import com.mediLaboSolutions.T2D2Patient.model.PersonAddress;
import com.mediLaboSolutions.T2D2Patient.repository.IAddressRepository;
import com.mediLaboSolutions.T2D2Patient.repository.IPersonAddressRepository;
import com.mediLaboSolutions.T2D2Patient.repository.IPersonRepository;
import com.mediLaboSolutions.T2D2Patient.service.contracts.IPersonAddressService;
import com.mediLaboSolutions.T2D2Patient.util.PersonAddressBuilder;

/**
 * A service class which performs the business processes relating to the POJO
 * <code>PersonAddress</code> before calling the repository.
 * 
 * @author Sébastien Cappon
 * @version 1.0
 */
@Service
public class PersonAddressService implements IPersonAddressService {

	@Autowired
	IPersonAddressRepository iPersonAddressRepository;
	@Autowired
	IPersonRepository iPersonRepository;
	@Autowired
	IAddressRepository iAddressRepository;

	/**
	 * A <code>GET</code> method that returns a list of all addresses for the person
	 * whose id is passed as parameter.
	 * 
	 * @return An <code>Address</code> list.
	 */
	@Override
	public List<Address> getPersonAddressesByPersonId(int personId) {
		List<PersonAddress> personAddressesByPersonId = iPersonAddressRepository.getPersonAddressesByPersonId(personId);
		List<Address> personAddresses = new ArrayList<>();

		for (PersonAddress personAddress : personAddressesByPersonId) {
			personAddresses.add(personAddress.getId().getAddress());
		}

		return personAddresses;
	}

	/**
	 * A <code>GET</code> method that returns a list of all addresses for the person
	 * whose email is passed as parameter.
	 * 
	 * @return An <code>Address</code> list.
	 */
	@Override
	public List<Address> getPersonAddressesByPersonEmail(String personEmail) {
		List<Address> personAddresses = new ArrayList<>();
		if (iPersonRepository.findByEmail(personEmail).isPresent()) {
			Person person = iPersonRepository.findByEmail(personEmail).get();

			List<PersonAddress> personAddressesByPersonEmail = iPersonAddressRepository.getPersonAddressesByPersonId(person.getId());
			for (PersonAddress personAddress : personAddressesByPersonEmail) {
				personAddresses.add(personAddress.getId().getAddress());
			}
		} else {
			Address emptyPlaceholderAddress = new Address();
			personAddresses.add(emptyPlaceholderAddress);
		}

		return personAddresses;
	}

	/**
	 * A <code>POST</code> method that returns an <code>Integer</code> if the
	 * PersonAddressAddDto passed as parameter is not existing yet in the database,
	 * and calls the <code>JpaRepository</code> <code>save()</code> method.
	 * 
	 * @return An <code>Integer</code> OR <code>null</code> if the junction already
	 *         exists in the database.
	 */
	@Override
	public Integer addPersonAddress(PersonAddressAddDto personAddressAddDto) {
		if (iPersonRepository.findById(personAddressAddDto.getPersonId()).isPresent() && iAddressRepository.findById(personAddressAddDto.getAddressId()).isPresent()) {
			Person person = iPersonRepository.findById(personAddressAddDto.getPersonId()).get();
			Address address = iAddressRepository.findById(personAddressAddDto.getAddressId()).get();

			PersonAddress newPersonAddress = PersonAddressBuilder.createPersonAddress(person, address);
			iPersonAddressRepository.save(newPersonAddress);

			return 1;
		}

		return null;
	}

	/**
	 * A <code>DELETE</code> method that deletes the junction between a person and
	 * an address which ids are passed as parameters. It calls the derived query
	 * <code>deleteById()</code> method.
	 */
	@Override
	public void deletePersonAddress(int personId, int addressId) {
		if (iPersonRepository.findById(personId).isPresent() && iAddressRepository.findById(addressId).isPresent()) {
			Person person = iPersonRepository.findById(personId).get();
			Address address = iAddressRepository.findById(addressId).get();

			PersonAddress personAddressToDelete = PersonAddressBuilder.createPersonAddress(person, address);
			iPersonAddressRepository.delete(personAddressToDelete);
		}
	}
}