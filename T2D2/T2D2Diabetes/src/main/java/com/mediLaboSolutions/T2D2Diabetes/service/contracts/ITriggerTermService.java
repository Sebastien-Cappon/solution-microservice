package com.mediLaboSolutions.T2D2Diabetes.service.contracts;

import java.util.List;

import com.mediLaboSolutions.T2D2Diabetes.model.TriggerTerm;

/**
 * <code>TriggerTermService</code> interface that abstracts it from its
 * implementation in order to achieve better code modularity in compliance with
 * SOLID principles.
 *
 * @author Sébastien Cappon
 * @version 1.0
 */
public interface ITriggerTermService {

	public List<TriggerTerm> getTriggerTerms();

	public TriggerTerm createTriggerTerm(TriggerTerm newTriggerTerm) throws Exception;

	public void deleteTriggerTermById(int triggerTermId);
}