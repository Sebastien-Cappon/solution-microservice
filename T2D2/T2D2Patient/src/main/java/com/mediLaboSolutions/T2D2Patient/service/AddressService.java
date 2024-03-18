package com.mediLaboSolutions.T2D2Patient.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mediLaboSolutions.T2D2Patient.model.Address;
import com.mediLaboSolutions.T2D2Patient.repository.IAddressRepository;
import com.mediLaboSolutions.T2D2Patient.service.contracts.IAddressService;

/**
 * A service class which performs the business processes relating to the POJO
 * <code>Address</code> before calling the repository.
 * 
 * @author Sébastien Cappon
 * @version 1.0
 */
@Service
public class AddressService implements IAddressService {

	private static final Logger logger = LoggerFactory.getLogger(AddressService.class);

	@Autowired
	private IAddressRepository iAddressRepository;

	/**
	 * A <code>GET</code> method that returns a list of all addresses.
	 * 
	 * @return An <code>Address</code> list.
	 */
	@Override
	public List<Address> getAddresses() {
		return iAddressRepository.findAll();
	}

	/**
	 * A <code>GET</code> method that returns an <code>Address</code> which id is
	 * passed as a parameter after calling the <code>findById()</code> derived query
	 * from repository.
	 * 
	 * @return An <code>Address</code> OR <code>null</code> if it doesn't exist in
	 *         the database.
	 */
	@Override
	public Address getAddressById(int addressId) {
		if (iAddressRepository.findById(addressId).isPresent()) {
			return iAddressRepository.findById(addressId).get();
		}

		return null;
	}

	/**
	 * A <code>POST</code> method that returns the <code>Address</code> passed as
	 * parameter if it is created, and calls the <code>JpaRepository</code>
	 * <code>save()</code> method.
	 * 
	 * @return An <code>Address</code> OR <code>null</code> if the address is
	 *         already in the database.
	 */
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

	/**
	 * An <code>UPDATE</code> method that checks informations passed as
	 * <code>Address</code> parameter and calls then the derived query
	 * <code>save()</code> for the address which id is passed as the first
	 * parameter.
	 *
	 * @return An <code>Integer</code> OR <code>null</code> if the
	 *         <code>Address</code> doesn't exists in the database.
	 */
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

	/**
	 * A <code>DELETE</code> method that deletes the address which id is passed
	 * as parameter. It calls the derived query <code>deleteById()</code> method.
	 */
	@Override
	public void deleteAddressById(int addressId) {
		iAddressRepository.deleteById(addressId);
	}
}