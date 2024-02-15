package com.mediLaboSolutions.T2D2Diabetes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mediLaboSolutions.T2D2Diabetes.dto.RiskFactorsDto;
import com.mediLaboSolutions.T2D2Diabetes.service.contracts.IRiskService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class RiskController {

	@Autowired
	IRiskService iRiskService;

	@GetMapping("/risk")
	public double getRiskScore(@RequestBody RiskFactorsDto riskFactors) {
		return iRiskService.calculateRiskScore(riskFactors);
	}
}