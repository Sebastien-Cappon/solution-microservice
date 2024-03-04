package com.mediLaboSolutions.T2D2Diabetes.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.ZonedDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.mediLaboSolutions.T2D2Diabetes.util.DtoInstanceBuilder;

@TestMethodOrder(OrderAnnotation.class)
public class DtoTest {

	@Test
	@Order(1)
	public void riskFactorsDtoToString_isNotBlank() {
		RiskFactorsDto riskFactorsDto = DtoInstanceBuilder.createRiskFactorsDto(false, ZonedDateTime.parse("1914-06-28T01:02:03Z"), new ArrayList<String>());
		assertThat(riskFactorsDto.toString()).isNotBlank();
	}
}