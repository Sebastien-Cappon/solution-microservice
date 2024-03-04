package com.mediLaboSolutions.T2D2Diabetes.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.ZonedDateTime;
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
import com.mediLaboSolutions.T2D2Diabetes.model.Risk;
import com.mediLaboSolutions.T2D2Diabetes.service.contracts.IRiskService;
import com.mediLaboSolutions.T2D2Diabetes.util.DtoInstanceBuilder;
import com.mediLaboSolutions.T2D2Diabetes.util.ModelInstanceBuilder;

@WebMvcTest(controllers = RiskController.class)
@TestMethodOrder(OrderAnnotation.class)
public class RiskControllerTest {

	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private IRiskService iRiskService;

	private RiskFactorsDto riskFactorsDtoRequest = DtoInstanceBuilder.createRiskFactorsDto(false, ZonedDateTime.parse("1914-06-28T01:02:03Z"), new ArrayList<String>());
	private Risk riskResponse = ModelInstanceBuilder.createRisk("Miraculous cure", ":D", "#FFD700");

	@Test
	@Order(1)
	public void getRiskScore_shouldReturnOk() throws Exception {
		when(iRiskService.calculateRiskScore(any(RiskFactorsDto.class)))
			.thenReturn(riskResponse);
		
		mockMvc.perform(post("/risk")
				.content(objectMapper.writeValueAsString(riskFactorsDtoRequest))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.level").value("Miraculous cure"))
			.andExpect(jsonPath("$.badgeSymbol").value(":D"))
			.andExpect(jsonPath("$.badgeColor").value("#FFD700"));
	}
}