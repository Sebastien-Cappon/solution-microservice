package com.mediLaboSolutions.T2D2Diabetes.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediLaboSolutions.T2D2Diabetes.dto.RiskFactorsDto;
import com.mediLaboSolutions.T2D2Diabetes.service.contracts.IRiskService;
import com.mediLaboSolutions.T2D2Diabetes.util.DtoInstanceBuilder;

@WebMvcTest(controllers = RiskController.class)
@TestMethodOrder(OrderAnnotation.class)
public class RiskControllerTest {

	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private IRiskService iRiskService;

	private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private RiskFactorsDto riskFactorsDtoResponse = DtoInstanceBuilder.createRiskFactorsDto(false, LocalDate.parse("1989-04-13", dateTimeFormatter), new ArrayList<String>());

	@Test
	@Order(1)
	public void getRiskScore_shouldReturnOk() throws Exception {
		when(iRiskService.calculateRiskScore(any(RiskFactorsDto.class)))
			.thenReturn(11.0);
		
		mockMvc.perform(get("/risk")
				.content(objectMapper.writeValueAsString(riskFactorsDtoResponse))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$").value(11.0));
	}
}