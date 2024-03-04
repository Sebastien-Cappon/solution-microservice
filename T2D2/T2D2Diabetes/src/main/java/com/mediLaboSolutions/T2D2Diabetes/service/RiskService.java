package com.mediLaboSolutions.T2D2Diabetes.service;

import java.time.Period;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mediLaboSolutions.T2D2Diabetes.constant.AgeScorePoint;
import com.mediLaboSolutions.T2D2Diabetes.constant.GenderScorePoint;
import com.mediLaboSolutions.T2D2Diabetes.dto.RiskFactorsDto;
import com.mediLaboSolutions.T2D2Diabetes.model.Risk;
import com.mediLaboSolutions.T2D2Diabetes.model.TriggerTerm;
import com.mediLaboSolutions.T2D2Diabetes.service.contracts.IRiskService;
import com.mediLaboSolutions.T2D2Diabetes.service.contracts.ITriggerTermService;

@Service
public class RiskService implements IRiskService {

	@Autowired
	ITriggerTermService iTriggerTermService;

	private int getGenderScorePoints(boolean riskFactorsDtoGender) {
		if (riskFactorsDtoGender) {
			return GenderScorePoint.MALE_SCORE_POINTS;
		} else {
			return GenderScorePoint.FEMALE_SCORE_POINTS;
		}
	}

	private int getAgeScorePoints(ZonedDateTime riskFactorsDtoBirthdate) {
		int personAge = Period.between(riskFactorsDtoBirthdate.toLocalDate(), ZonedDateTime.now().toLocalDate()).getYears();

		if (personAge < 30) {
			return AgeScorePoint.LESS_THAN_30;
		} else {
			return AgeScorePoint.MORE_THAN_OR_EQUAL_30;
		}
	}

	private int getTriggerTermScorePoints(List<String> riskFactorsDtoNotes) {
		int triggerTermScorePoints = 0;

		// OH NO ! A REGEX !
		// NEED SOME EXPLANATION ? OK, LET'S GO _o/
		// .* : means anything (nothing included) until the beginning ... to the end
		// (?i) : means case insensitive
		// \b : means word boundary. Boundaries are : whitespace, simplequote, hypen.
		for (String note : riskFactorsDtoNotes) {
			for (TriggerTerm triggerTerm : iTriggerTermService.getTriggerTerms()) {
				if (Pattern.matches(".*(?i)\\b" + triggerTerm.getTerm() + "\\b.*", note)) {
					triggerTermScorePoints++;
				}
			}
		}

		return triggerTermScorePoints;
	}

	private Risk formatRisk(double riskScore) {
		Risk risk = new Risk();

		if (riskScore < 20) {
			risk.setLevel("None");
			risk.setBadgeSymbol("-");
			risk.setBadgeColor("#26A69A");
		} else if (riskScore >= 20 && riskScore < 60) {
			risk.setLevel("Borderline");
			risk.setBadgeSymbol("?");
			risk.setBadgeColor("#FF9800");
		} else if (riskScore >= 60 && riskScore < 80) {
			risk.setLevel("Danger");
			risk.setBadgeSymbol("!");
			risk.setBadgeColor("#D32F2F");
		} else {
			risk.setLevel("Early onset");
			risk.setBadgeSymbol("!!");
			risk.setBadgeColor("#000000");
		}

		return risk;
	}

	@Override
	public Risk calculateRiskScore(RiskFactorsDto riskFactors) {
		double riskScore;

		int triggerTermScorePoints = getTriggerTermScorePoints(riskFactors.getNotes());

		if (triggerTermScorePoints == 0) {
			riskScore = 0;
		} else {
			int genderScorePoints = getGenderScorePoints(riskFactors.getGender());
			int ageScorePoints = getAgeScorePoints(riskFactors.getBirthdate());

			int riskPoint = genderScorePoints + ageScorePoints + triggerTermScorePoints;
			riskScore = riskPoint * 100d / 9d;
		}

		return formatRisk(riskScore);
	}
}