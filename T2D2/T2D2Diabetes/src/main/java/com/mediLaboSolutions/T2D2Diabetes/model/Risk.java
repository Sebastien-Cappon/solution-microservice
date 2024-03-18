package com.mediLaboSolutions.T2D2Diabetes.model;

/**
 * A model class which creates the POJO (Plain Old Java Object)
 * <code>Risk</code>. It contains getters and setters, as well as an
 * override <code>toSring()</code> method for display in the console.
 *
 * @author Sébastien Cappon
 * @version 1.0
 */
public class Risk {

	private String level;
	private String badgeSymbol;
	private String badgeColor;

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getBadgeSymbol() {
		return badgeSymbol;
	}

	public void setBadgeSymbol(String badgeSymbol) {
		this.badgeSymbol = badgeSymbol;
	}

	public String getBadgeColor() {
		return badgeColor;
	}

	public void setBadgeColor(String badgeColor) {
		this.badgeColor = badgeColor;
	}

	/**
	 * An override method for user-friendly display of <code>Risk</code> attributes
	 * in the console. Not necessary, except for test purposes.
	 * 
	 * @return <code>String</code> containing all the attributes of
	 *         <code>Risk</code>.
	 */
	@Override
	public String toString() {
		return ("[" + level + "]" + "[" + badgeSymbol + "]" + "[" + badgeColor + "]");
	}
}