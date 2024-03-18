package com.mediLaboSolutions.T2D2Diabetes.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mediLaboSolutions.T2D2Diabetes.model.TriggerTerm;
import com.mediLaboSolutions.T2D2Diabetes.repository.ITriggerTermRepository;
import com.mediLaboSolutions.T2D2Diabetes.service.contracts.ITriggerTermService;

@Service
public class TriggerTermService implements ITriggerTermService {

	private Logger logger = LoggerFactory.getLogger(TriggerTermService.class);

	@Autowired
	private ITriggerTermRepository iTriggerTermRepository;

	/**
	 * A <code>GET</code> method that returns a list of all trigger terms.
	 * 
	 * @return A <code>TriggerTerm</code> list.
	 */
	@Override
	public List<TriggerTerm> getTriggerTerms() {
		return iTriggerTermRepository.findByOrderByTermAsc();
	}

	/**
	 * A <code>POST</code> method that returns the <code>TriggerTerm</code> passed
	 * as parameter and calls the <code>JpaRepository</code> <code>save()</code>
	 * method.
	 * 
	 * @return A <code>TriggerTerm</code> OR <code>null</code> if it is already in
	 *         the database.
	 */
	@Override
	public TriggerTerm createTriggerTerm(TriggerTerm newTriggerTerm) {
		for (TriggerTerm checkTriggerTerm : iTriggerTermRepository.findAll()) {
			if (newTriggerTerm.getTerm().contentEquals(checkTriggerTerm.getTerm())) {
				logger.warn("This trigger term has already been registered.");
				return null;
			}
		}

		return iTriggerTermRepository.save(newTriggerTerm);
	}

	/**
	 * A <code>DELETE</code> method that deletes the trigger term whose id is passed
	 * as parameter. It calls the derived query <code>deleteById()</code> method.
	 */
	@Override
	public void deleteTriggerTermById(int triggerTermId) {
		iTriggerTermRepository.deleteById(triggerTermId);
	}
}