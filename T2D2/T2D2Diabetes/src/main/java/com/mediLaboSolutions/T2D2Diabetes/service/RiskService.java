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

/**
 * A service class which performs the business processes relating to the POJO
 * <code>Risk</code>.
 * 
 * @singularity The only service class of the all application which doesn't call
 *              a repository.
 * 
 * @author Sébastien Cappon
 * @version 1.0
 */
@Service
public class RiskService implements IRiskService {

	@Autowired
	ITriggerTermService iTriggerTermService;

	/**
	 * A <code>private</code> method that returns a score accordingly to the
	 * patient's gender.
	 * 
	 * @return A <code>int</code> : 1 or 0.
	 */
	private int getGenderScorePoints(boolean riskFactorsDtoGender) {
		if (riskFactorsDtoGender) {
			return GenderScorePoint.MALE_SCORE_POINTS;
		} else {
			return GenderScorePoint.FEMALE_SCORE_POINTS;
		}
	}

	/**
	 * A <code>private</code> method that returns a score accordingly to the
	 * patient's age.
	 * 
	 * @return A <code>int</code> : 2 or 0.
	 */
	private int getAgeScorePoints(ZonedDateTime riskFactorsDtoBirthdate) {
		int personAge = Period.between(riskFactorsDtoBirthdate.toLocalDate(), ZonedDateTime.now().toLocalDate()).getYears();

		if (personAge < 30) {
			return AgeScorePoint.LESS_THAN_30;
		} else {
			return AgeScorePoint.MORE_THAN_OR_EQUAL_30;
		}
	}

	/**
	 * A <code>private</code> method that returns a score accordingly to the number
	 * of trigger terms into the patient's notes.
	 * 
	 * @return A <code>int</code> : 1 point per trigger terms.
	 */
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

	/**
	 * A <code>private</code> method that takes the risk score calculated and
	 * returns a score formatted into an object containing a level of risk, a symbol
	 * and a color, for display purpose.
	 * 
	 * @return A <code>Risk</code>.
	 */
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

	/**
	 * A method that calls the three aboves, calculate a score and returns a
	 * formated Risk.
	 * 
	 * @return A <code>Risk</code> list.
	 */
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