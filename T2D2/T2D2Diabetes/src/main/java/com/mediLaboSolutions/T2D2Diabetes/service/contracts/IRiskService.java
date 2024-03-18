package com.mediLaboSolutions.T2D2Diabetes.service.contracts;

import com.mediLaboSolutions.T2D2Diabetes.dto.RiskFactorsDto;
import com.mediLaboSolutions.T2D2Diabetes.model.Risk;

/**
 * <code>RiskService</code> interface that abstracts it from its implementation
 * in order to achieve better code modularity in compliance with SOLID
 * principles.
 *
 * @author Sébastien Cappon
 * @version 1.0
 */
public interface IRiskService {

	public Risk calculateRiskScore(RiskFactorsDto riskFactors);
}