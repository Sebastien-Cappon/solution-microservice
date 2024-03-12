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
	private Risk risk = ModelInstanceBuilder.createRisk("Dead", "X", "#FFF");

	@Test
	@Order(1)
	public void triggerTermToString_isNotBlank() {
		assertThat(triggerTerm.toString()).isNotBlank();
	}
	
	@Test
	@Order(2)
	public void riskToString_isNotBlank() {
		assertThat(risk.toString()).isNotBlank();
	}
}