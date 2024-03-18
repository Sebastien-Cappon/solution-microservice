package com.mediLaboSolutions.T2D2Authentication.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mediLaboSolutions.T2D2Authentication.dto.PractitionerLoginDto;
import com.mediLaboSolutions.T2D2Authentication.model.Practitioner;
import com.mediLaboSolutions.T2D2Authentication.repository.IPractitionerRepository;
import com.mediLaboSolutions.T2D2Authentication.service.contracts.IPractitionerService;
import com.mediLaboSolutions.T2D2Authentication.util.PasswordManager;

/**
 * A service class which performs the business processes relating to the POJO
 * <code>Practitioner</code> before calling the repository.
 * 
 * @author Sébastien Cappon
 * @version 1.0
 */
@Service
public class PractitionerService implements IPractitionerService {

	private static final Logger logger = LoggerFactory.getLogger(PractitionerService.class);
	
	@Autowired
	IPractitionerRepository iPractitionerRepository;
	
	/**
	 * A <code>GET</code> method that returns a list of all practitioners.
	 * 
	 * @return A <code>Practitioner</code> list.
	 */
	@Override
	public List<Practitioner> getPractitioners() {
		return iPractitionerRepository.findAll();
	}

	/**
	 * A <code>GET</code> method that returns a <code>Practitioner</code>s whose id
	 * is passed as a parameter after calling the <code>findById()</code> derived
	 * query from repository.
	 * 
	 * @return A <code>Practitioner</code> OR <code>null</code> if he doesn't exist
	 *         in the database.
	 */
	@Override
	public Practitioner getPractitionerById(int practitionerId) {
		if (iPractitionerRepository.findById(practitionerId).isPresent()) {
			return iPractitionerRepository.findById(practitionerId).get();
		}

		return null;
	}
	
	/**
	 * A <code>POST</code> method that returns a <code>Practitioner</code> whose email and
	 * non-encrypted password are attributes of the <code>PractitionerLoginDto</code> passed
	 * as parameter. It calls the <code>findByEmail()</code> derived query from
	 * repository.
	 * 
	 * @return A <code>Practitioner</code> OR <code>null</code> if he doesn't exist in the
	 *         database or if the password sent is wrong.
	 */
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
	
	/**
	 * A <code>POST</code> method that returns the <code>Practitioner</code> passed
	 * as parameter if his account is created, and calls the
	 * <code>JpaRepository</code> <code>save()</code> method.
	 * 
	 * @return A <code>User</code> OR <code>null</code> if his email is already in
	 *         the database.
	 */
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

	/**
	 * An <code>UPDATE</code> method that checks informations passed as
	 * <code>Practitioner</code> parameter and calls then the derived query
	 * <code>save()</code> for the practitioner whose id is passed as the first
	 * parameter.
	 *
	 * @return An <code>Integer</code> OR <code>null</code> if the
	 *         <code>Practitioner</code> doesn't exists in the database.
	 */
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
	
	/**
	 * A <code>DELETE</code> method that deletes the practitioner whose id is passed
	 * as parameter. It calls the derived query <code>deleteById()</code> method.
	 */
	@Override
	public void deletePractitionerById(int practitionerId) {
		iPractitionerRepository.deleteById(practitionerId);
	}
}