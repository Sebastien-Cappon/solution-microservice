package com.mediLaboSolutions.T2D2Diabetes.service.contracts;

import java.util.List;

import com.mediLaboSolutions.T2D2Diabetes.model.TriggerTerm;

public interface ITriggerTermService {

	public List<TriggerTerm> getTriggerTerms();
	public TriggerTerm getTriggerTermById(int triggerTermId);

	public TriggerTerm createTriggerTerm(TriggerTerm newTriggerTerm) throws Exception;

	public Integer updateTriggerTermById(int triggerTermId, TriggerTerm updatedTriggerTerm) throws Exception;

	public void deleteTriggerTermById(int triggerTermId);
}