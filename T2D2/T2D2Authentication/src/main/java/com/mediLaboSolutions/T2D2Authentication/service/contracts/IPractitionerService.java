package com.mediLaboSolutions.T2D2Authentication.service.contracts;

import java.util.List;

import com.mediLaboSolutions.T2D2Authentication.dto.PractitionerLoginDto;
import com.mediLaboSolutions.T2D2Authentication.model.Practitioner;

/**
 * <code>PractitionerService</code> interface that abstracts it from its
 * implementation in order to achieve better code modularity in compliance with
 * SOLID principles.
 *
 * @author Sébastien Cappon
 * @version 1.0
 */
public interface IPractitionerService {
	
	public List<Practitioner> getPractitioners();
	public Practitioner getPractitionerById(int practitionerId);

	public Practitioner connectPractitionerWithEmailAndPassword(PractitionerLoginDto practitionerLoginDto) throws Exception;
	public Practitioner createPractitioner(Practitioner newPractitioner) throws Exception;
	
	public Integer updatePractitionerById(int practitionerId, Practitioner updatedPractitioner) throws Exception;
	
	public void deletePractitionerById(int practitionerId);
}