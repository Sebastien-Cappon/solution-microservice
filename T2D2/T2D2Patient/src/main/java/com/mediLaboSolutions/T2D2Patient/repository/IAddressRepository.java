package com.mediLaboSolutions.T2D2Patient.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mediLaboSolutions.T2D2Patient.model.Address;

/**
 * Repository interface which extends the JPA (Jakarta Persistence API)
 * Repository in order to deal with derived queries relative to
 * <code>Address</code> entities.
 * 
 * @author Sébastien Cappon
 * @version 1.0
 */
@Repository
public interface IAddressRepository extends JpaRepository<Address, Integer> {

	Optional<Address> findById(int addressId);

	void deleteById(int addressId);
}