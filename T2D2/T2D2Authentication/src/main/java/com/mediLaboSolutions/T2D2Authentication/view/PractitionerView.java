package com.mediLaboSolutions.T2D2Authentication.view;

/**
 * A class that contains some interfaces that inherit from each other.These are
 * used to filter the attributes to be returned in response to requests sent to
 * the API.
 * 
 * @author Sébastien Cappon
 * @version 1.0
 */
public class PractitionerView {

	public interface IdView {}
	public interface LastnameView {}
	public interface FirstnameView {}
	public interface EmailView {}

	public interface LoginView extends IdView, LastnameView, FirstnameView {}
}