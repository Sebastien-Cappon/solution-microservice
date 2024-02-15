package com.mediLaboSolutions.T2D2Diabetes.service.contracts;

import com.mediLaboSolutions.T2D2Diabetes.dto.RiskFactorsDto;

public interface IRiskService {

	public double calculateRiskScore(RiskFactorsDto riskFactors);
}