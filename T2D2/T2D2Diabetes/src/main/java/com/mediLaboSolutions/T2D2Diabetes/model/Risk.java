package com.mediLaboSolutions.T2D2Diabetes.model;

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

	@Override
	public String toString() {
		return ("[" + level + "]" + "[" + badgeSymbol + "]" + "[" + badgeColor + "]");
	}
}