package com.mediLaboSolutions.T2D2Diabetes.constant;

/**
 * A class of constants that contains scores according to age. Early detection
 * occurs under the age of 30. So the score is higher (2). Beyond that, we're
 * within the norm. The score is 0.
 * 
 * @author Sébastien Cappon
 * @version 1.0
 */
public class AgeScorePoint {

	public static final int LESS_THAN_30 = 2;
	public static final int MORE_THAN_OR_EQUAL_30 = 0;
}