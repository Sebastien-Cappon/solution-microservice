package com.mediLaboSolutions.T2D2Diabetes.service.contracts;

import com.mediLaboSolutions.T2D2Diabetes.dto.RiskFactorsDto;
import com.mediLaboSolutions.T2D2Diabetes.model.Risk;

public interface IRiskService {

	public Risk calculateRiskScore(RiskFactorsDto riskFactors);
}