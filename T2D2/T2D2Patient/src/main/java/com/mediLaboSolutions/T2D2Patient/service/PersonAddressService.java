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

@Service
public class PersonAddressService implements IPersonAddressService {

	@Autowired
	IPersonAddressRepository iPersonAddressRepository;
	@Autowired
	IPersonRepository iPersonRepository;
	@Autowired
	IAddressRepository iAddressRepository;

	@Override
	public List<Address> getPersonAddressesByPersonId(int personId) {
		List<PersonAddress> personAddressesByPersonId = iPersonAddressRepository.getPersonAddressesByPersonId(personId);
		List<Address> personAddresses = new ArrayList<>();

		for (PersonAddress personAddress : personAddressesByPersonId) {
			personAddresses.add(personAddress.getId().getAddress());
		}

		return personAddresses;
	}

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