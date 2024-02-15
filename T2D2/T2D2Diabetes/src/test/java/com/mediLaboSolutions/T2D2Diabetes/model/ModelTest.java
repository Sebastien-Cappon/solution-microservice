package com.mediLaboSolutions.T2D2Diabetes.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.mediLaboSolutions.T2D2Diabetes.util.ModelInstanceBuilder;

@TestMethodOrder(OrderAnnotation.class)
public class ModelTest {

	private TriggerTerm triggerTerm = ModelInstanceBuilder.createTrigger(1, "Plague");

	@Test
	@Order(1)
	public void triggerTermToString_isNotBlank() {
		assertThat(triggerTerm.toString()).isNotBlank();
	}
}