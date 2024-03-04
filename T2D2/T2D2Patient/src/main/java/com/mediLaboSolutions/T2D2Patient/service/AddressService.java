package com.mediLaboSolutions.T2D2Patient.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mediLaboSolutions.T2D2Patient.model.Address;
import com.mediLaboSolutions.T2D2Patient.repository.IAddressRepository;
import com.mediLaboSolutions.T2D2Patient.service.contracts.IAddressService;

@Service
public class AddressService implements IAddressService {

	private static final Logger logger = LoggerFactory.getLogger(AddressService.class);

	@Autowired
	private IAddressRepository iAddressRepository;

	@Override
	public List<Address> getAddresses() {
		return iAddressRepository.findAll();
	}

	@Override
	public Address getAddressById(int addressId) {
		if (iAddressRepository.findById(addressId).isPresent()) {
			return iAddressRepository.findById(addressId).get();
		}

		return null;
	}

	@Override
	public Address createAddress(Address newAddress) throws Exception {
		for (Address checkAddress : iAddressRepository.findAll()) {
			if (checkAddress.getNumber().contentEquals(newAddress.getNumber())
					&& checkAddress.getWayType().contentEquals(newAddress.getWayType())
					&& checkAddress.getWayName().contentEquals(newAddress.getWayName())
					&& checkAddress.getPostcode().contentEquals(newAddress.getPostcode())
					&& checkAddress.getCity().contentEquals(newAddress.getCity())
					&& checkAddress.getCountry().contentEquals(newAddress.getCountry())) {
				logger.warn("This address already exists in the database.");
				return null;
			}
		}

		return iAddressRepository.save(newAddress);
	}

	@Override
	public Integer updateAddressById(int addressId, Address updatedAddress) throws Exception {
		if (iAddressRepository.findById(addressId).isPresent()) {
			Address addressToUpdate = iAddressRepository.findById(addressId).get();

			updatedAddress.setId(addressId);
			if (updatedAddress.getNumber() == null || updatedAddress.getNumber().isBlank()) {
				updatedAddress.setNumber(addressToUpdate.getNumber());
			}
			if (updatedAddress.getWayType() == null || updatedAddress.getWayType().isBlank()) {
				updatedAddress.setWayType(addressToUpdate.getWayType());
			}
			if (updatedAddress.getWayName() == null || updatedAddress.getWayName().isBlank()) {
				updatedAddress.setWayName(addressToUpdate.getWayName());
			}
			if (updatedAddress.getPostcode() == null || updatedAddress.getPostcode().isBlank()) {
				updatedAddress.setPostcode(addressToUpdate.getPostcode());
			}
			if (updatedAddress.getCity() == null || updatedAddress.getCity().isBlank()) {
				updatedAddress.setCity(addressToUpdate.getCity());
			}
			if (updatedAddress.getCountry() == null || updatedAddress.getCountry().isBlank()) {
				updatedAddress.setCountry(addressToUpdate.getCountry());
			}

			iAddressRepository.save(updatedAddress);
			return 1;
		}

		logger.warn("Can't modify : This address doesn't exist in the database.");
		return null;
	}

	@Override
	public void deleteAddressById(int addressId) {
		iAddressRepository.deleteById(addressId);
	}
}