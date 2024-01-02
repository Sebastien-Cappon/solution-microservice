package com.mediLaboSolutions.T2D2Patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mediLaboSolutions.T2D2Patient.model.PatientAddress;

public interface IPatientAddressRepository extends JpaRepository<PatientAddress, Integer> {

}