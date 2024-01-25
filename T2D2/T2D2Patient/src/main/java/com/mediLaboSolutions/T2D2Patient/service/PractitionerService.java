package com.mediLaboSolutions.T2D2Patient.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mediLaboSolutions.T2D2Patient.dto.PractitionerLoginDto;
import com.mediLaboSolutions.T2D2Patient.model.Practitioner;
import com.mediLaboSolutions.T2D2Patient.repository.IPractitionerRepository;
import com.mediLaboSolutions.T2D2Patient.service.contracts.IPractitionerService;
import com.mediLaboSolutions.T2D2Patient.util.PasswordManager;

@Service
public class PractitionerService implements IPractitionerService {

	private static final Logger logger = LoggerFactory.getLogger(PractitionerService.class);

	@Autowired
	IPractitionerRepository iPractitionerRepository;

	@Override
	public List<Practitioner> getPractitioners() {
		return iPractitionerRepository.findAll();
	}

	@Override
	public Practitioner getPractitionerById(int practitionerId) {
		if (iPractitionerRepository.findById(practitionerId).isPresent()) {
			return iPractitionerRepository.findById(practitionerId).get();
		}

		return null;
	}

	@Override
	public Practitioner connectPractitionerWithEmailAndPassword(PractitionerLoginDto practitionerLoginDto) throws Exception {
		if (iPractitionerRepository.findByEmail(practitionerLoginDto.getEmail()).isPresent()) {
			Practitioner practitioner = iPractitionerRepository.findByEmail(practitionerLoginDto.getEmail()).get();

			if (PasswordManager.checkPassword(practitionerLoginDto.getPassword(), practitioner.getPassword())) {
				return practitioner;
			}
		}

		return null;
	}

	@Override
	public Practitioner createPractitioner(Practitioner newPractitioner) throws Exception {
		for (Practitioner checkPractitioner : iPractitionerRepository.findAll()) {
			if (checkPractitioner.getEmail().contentEquals(newPractitioner.getEmail())) {
				logger.warn("A practitioner with this email address already exists in the database.");
				return null;
			}
		}

		newPractitioner.setPassword(PasswordManager.hashPassword(newPractitioner.getPassword()));

		return iPractitionerRepository.save(newPractitioner);
	}

	@Override
	public Integer updatePractitionerById(int practitionerId, Practitioner updatedPractitioner) throws Exception {
		if (iPractitionerRepository.findById(practitionerId).isPresent()) {
			Practitioner practitionerToUpdate = iPractitionerRepository.findById(practitionerId).get();

			updatedPractitioner.setId(practitionerToUpdate.getId());
			if (updatedPractitioner.getLastname() == null || updatedPractitioner.getLastname().isBlank()) {
				updatedPractitioner.setLastname(practitionerToUpdate.getLastname());
			}
			if (updatedPractitioner.getFirstname() == null || updatedPractitioner.getFirstname().isBlank()) {
				updatedPractitioner.setFirstname(practitionerToUpdate.getFirstname());
			}
			if (updatedPractitioner.getEmail() == null || updatedPractitioner.getEmail().isBlank()) {
				updatedPractitioner.setEmail(practitionerToUpdate.getEmail());
			} else {
				for (Practitioner practitioner : iPractitionerRepository.findAll()) {
					if (practitioner.getEmail().contentEquals(updatedPractitioner.getEmail())) {
						logger.warn("This email address is already used.");
						updatedPractitioner.setEmail(practitionerToUpdate.getEmail());
						break;
					}
				}
			}
			if (updatedPractitioner.getPassword() == null || updatedPractitioner.getPassword().isBlank()) {
				updatedPractitioner.setPassword(practitionerToUpdate.getPassword());
			} else {
				updatedPractitioner.setPassword(PasswordManager.hashPassword(updatedPractitioner.getPassword()));
			}

			iPractitionerRepository.save(updatedPractitioner);
			return 1;
		}

		logger.warn("Can't modify : This practitioner doesn't exist in the database.");
		return null;
	}

	@Override
	public void deletePractitionerById(int practitionerId) {
		iPractitionerRepository.deleteById(practitionerId);
	}
}