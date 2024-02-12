package com.mediLaboSolutions.T2D2Diabetes.util;

import com.mediLaboSolutions.T2D2Diabetes.model.TriggerTerm;

public class ModelInstanceBuilder {

	public static TriggerTerm createTrigger(int id, String term) {
		TriggerTerm triggerTerm = new TriggerTerm();
		triggerTerm.setId(id);
		triggerTerm.setTerm(term);

		return triggerTerm;
	}
}