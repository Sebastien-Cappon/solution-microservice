package com.mediLaboSolutions.T2D2Diabetes.util;

import com.mediLaboSolutions.T2D2Diabetes.model.Risk;
import com.mediLaboSolutions.T2D2Diabetes.model.TriggerTerm;

public class ModelInstanceBuilder {

	public static TriggerTerm createTrigger(int id, String term) {
		TriggerTerm triggerTerm = new TriggerTerm();
		triggerTerm.setId(id);
		triggerTerm.setTerm(term);

		return triggerTerm;
	}

	public static Risk createRisk(String level, String badgeSymbol, String badgeColor) {
		Risk risk = new Risk();
		risk.setLevel(level);
		risk.setBadgeSymbol(badgeSymbol);
		risk.setBadgeColor(badgeColor);

		return risk;
	}
}