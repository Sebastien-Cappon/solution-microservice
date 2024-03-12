package com.mediLaboSolutions.T2D2Authentication.view;

public class PractitionerView {

	public interface IdView {}
	public interface LastnameView {}
	public interface FirstnameView {}
	public interface EmailView {}
	public interface PasswordView {}

	public interface LoginView extends IdView, LastnameView, FirstnameView {}
}