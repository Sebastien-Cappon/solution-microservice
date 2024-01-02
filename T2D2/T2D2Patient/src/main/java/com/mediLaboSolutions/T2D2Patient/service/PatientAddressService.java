package com.mediLaboSolutions.T2D2Patient.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mediLaboSolutions.T2D2Patient.repository.IPatientAddressRepository;

@Service
public class PatientAddressService implements IPatientAddressService {

	private static final Logger logger = LoggerFactory.getLogger(PatientAddressService.class);
	
	@Autowired
	IPatientAddressRepository iPatientAddressRepository;
	
	
}