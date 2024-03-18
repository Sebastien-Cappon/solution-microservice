package com.mediLaboSolutions.T2D2Diabetes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mediLaboSolutions.T2D2Diabetes.dto.RiskFactorsDto;
import com.mediLaboSolutions.T2D2Diabetes.model.Risk;
import com.mediLaboSolutions.T2D2Diabetes.service.contracts.IRiskService;

/**
 * A class which receive the only request relating to the calculation of
 * diabetes risks.
 * 
 * @singularity The only controller class of the entire application which is not
 *              a CRUD.
 * 
 * @author Sébastien Cappon
 * @version 1.0
 */
@RestController
public class RiskController {

	@Autowired
	IRiskService iRiskService;

	/**
	 * A <code>PostMapping</code> method on the <code>/risk</code> URI. It calls the
	 * <code>calculateRiskScore(RiskFactorsDto riskFactors)</code> method from
	 * <code>IRiskService</code> and returns a <code>Risk</code> model entities.
	 * 
	 * @frontCall <code>risk.service.ts</code> : <code>getRiskScore(riskFactors: RiskFactorsValue)</code>
	 * 
	 * @return A <code>Risk</code>.
	 */
	@PostMapping("/risk")
	public Risk getRiskScore(@RequestBody RiskFactorsDto riskFactors) {
		return iRiskService.calculateRiskScore(riskFactors);
	}
}