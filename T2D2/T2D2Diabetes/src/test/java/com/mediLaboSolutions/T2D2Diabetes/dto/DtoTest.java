package com.mediLaboSolutions.T2D2Diabetes.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.mediLaboSolutions.T2D2Diabetes.util.DtoInstanceBuilder;

@TestMethodOrder(OrderAnnotation.class)
public class DtoTest {

	private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	@Test
	@Order(1)
	public void riskFactorsDtoToString_isNotBlank() {
		RiskFactorsDto riskFactorsDto = DtoInstanceBuilder.createRiskFactorsDto(false, LocalDate.parse("1989-04-13", dateTimeFormatter), new ArrayList<String>());
		assertThat(riskFactorsDto.toString()).isNotBlank();
	}
}