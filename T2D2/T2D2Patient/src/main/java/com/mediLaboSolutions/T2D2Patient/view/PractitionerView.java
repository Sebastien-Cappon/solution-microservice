package com.mediLaboSolutions.T2D2Patient.view;

public class PractitionerView {

	public interface IdView {}
	public interface LastnameView {}
	public interface FirstnameView {}
	public interface EmailView {}
	public interface PasswordView {}

	public interface LoginView extends IdView, LastnameView, FirstnameView {}
}