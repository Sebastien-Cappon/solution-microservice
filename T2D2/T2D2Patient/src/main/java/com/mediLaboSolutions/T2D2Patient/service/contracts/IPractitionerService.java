package com.mediLaboSolutions.T2D2Patient.service.contracts;

import java.util.List;

import com.mediLaboSolutions.T2D2Patient.dto.PractitionerLoginDto;
import com.mediLaboSolutions.T2D2Patient.model.Practitioner;

public interface IPractitionerService {
	
	public List<Practitioner> getPractitioners();
	public Practitioner getPractitionerById(int practitionerId);
	
	public Practitioner connectPractitionerWithEmailAndPassword(PractitionerLoginDto practitionerLoginDto) throws Exception;
	public Practitioner createPractitioner(Practitioner newPractitioner) throws Exception;
	
	public Integer updatePractitionerById(int practitionerId, Practitioner updatedPractitioner) throws Exception;
	
	public void deletePractitionerById(int practitionerId);
}