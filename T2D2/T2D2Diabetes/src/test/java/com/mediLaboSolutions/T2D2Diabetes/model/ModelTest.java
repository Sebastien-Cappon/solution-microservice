package com.mediLaboSolutions.T2D2Diabetes.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.mediLaboSolutions.T2D2Diabetes.util.ModelInstanceBuilder;

public class ModelTest {

	private TriggerTerm triggerTerm = ModelInstanceBuilder.createTrigger(1, "Plague");

	@Test
	public void triggerTermToString_isNotBlank() {
		assertThat(triggerTerm.toString()).isNotBlank();
	}
}