package com.mediLaboSolutions.T2D2Diabetes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mediLaboSolutions.T2D2Diabetes.dto.RiskFactorsDto;
import com.mediLaboSolutions.T2D2Diabetes.model.Risk;
import com.mediLaboSolutions.T2D2Diabetes.service.contracts.IRiskService;

@RestController
public class RiskController {

	@Autowired
	IRiskService iRiskService;

	@PostMapping("/risk")
	public Risk getRiskScore(@RequestBody RiskFactorsDto riskFactors) {
		return iRiskService.calculateRiskScore(riskFactors);
	}
}