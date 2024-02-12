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

	@Override
	public List<TriggerTerm> getTriggerTerms() {
		return iTriggerTermRepository.findAll();
	}

	@Override
	public TriggerTerm getTriggerTermById(int triggerTermId) {
		if (iTriggerTermRepository.findById(triggerTermId).isPresent()) {
			return iTriggerTermRepository.findById(triggerTermId).get();
		}

		return null;
	}

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

	@Override
	public Integer updateTriggerTermById(int triggerTermId, TriggerTerm updatedTriggerTerm) {
		if (iTriggerTermRepository.findById(triggerTermId).isPresent()) {
			TriggerTerm triggerTermToUpdate = iTriggerTermRepository.findById(triggerTermId).get();

			triggerTermToUpdate.setTerm(updatedTriggerTerm.getTerm());

			iTriggerTermRepository.save(triggerTermToUpdate);
			return 1;
		}

		logger.warn("Can't modify : This trigger term doesn't exists in the database.");
		return null;
	}

	@Override
	public void deleteTriggerTermById(int triggerTermId) {
		iTriggerTermRepository.deleteById(triggerTermId);
	}
}