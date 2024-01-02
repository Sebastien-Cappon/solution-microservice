package com.mediLaboSolutions.T2D2Patient.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mediLaboSolutions.T2D2Patient.model.Address;

public interface IAddressRepository extends JpaRepository<Address, Integer> {
	
	Optional<Address> findById(int addressId);
	
	void deleteById(int address);
}