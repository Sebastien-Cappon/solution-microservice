package com.mediLaboSolutions.T2D2Diabetes.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediLaboSolutions.T2D2Diabetes.dto.RiskFactorsDto;
import com.mediLaboSolutions.T2D2Diabetes.model.TriggerTerm;
import com.mediLaboSolutions.T2D2Diabetes.service.contracts.ITriggerTermService;
import com.mediLaboSolutions.T2D2Diabetes.util.DtoInstanceBuilder;
import com.mediLaboSolutions.T2D2Diabetes.util.ModelInstanceBuilder;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "file:config/application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class RiskServiceIT {

	private static final Logger logger = LoggerFactory.getLogger(TriggerTermServiceIT.class);
	
	private TriggerTerm firstTriggerTerm = ModelInstanceBuilder.createTrigger(1, "réaction");
	private TriggerTerm secondTriggerTerm = ModelInstanceBuilder.createTrigger(2, "vertiges");
	private TriggerTerm thirdTriggerTerm = ModelInstanceBuilder.createTrigger(3, "fumeur");
	private TriggerTerm fourthTriggerTerm = ModelInstanceBuilder.createTrigger(4, "anormal");
	private TriggerTerm fifthTriggerTerm = ModelInstanceBuilder.createTrigger(5, "rechute");

	@BeforeAll
	private void fillH2Database() throws Exception {
		iTriggerTermService.createTriggerTerm(firstTriggerTerm);
		iTriggerTermService.createTriggerTerm(secondTriggerTerm);
		iTriggerTermService.createTriggerTerm(thirdTriggerTerm);
		iTriggerTermService.createTriggerTerm(fourthTriggerTerm);
		iTriggerTermService.createTriggerTerm(fifthTriggerTerm);
		logger.info("triggerterm table in the H2 database filled.");
	}
	
	@AfterAll
	private void tearDown() {
		logger.info("H2 test database cleaned.");
	}
	
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ITriggerTermService iTriggerTermService;
	
	@Test
	@Order(1)
	public void getRiskScore_shouldReturnOk_shouldResultNone() throws Exception {
		RiskFactorsDto noneRiskFactorsDtoRequest = DtoInstanceBuilder.createRiskFactorsDto(true, ZonedDateTime.parse("1914-06-28T01:02:03Z"), new ArrayList<String>());
		
		mockMvc.perform(post("/risk")
				.content(objectMapper.writeValueAsString(noneRiskFactorsDtoRequest))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.level").value("None"))
			.andExpect(jsonPath("$.badgeSymbol").value("-"))
			.andExpect(jsonPath("$.badgeColor").value("#26A69A"));
	}
	
	@Test
	@Order(2)
	public void getRiskScore_shouldReturnOk_shouldResultBorderline() throws Exception {
		RiskFactorsDto borderlineRiskFactorsDtoRequest = DtoInstanceBuilder.createRiskFactorsDto(true, ZonedDateTime.parse("1914-06-28T01:02:03Z"), new ArrayList<String>(List.of("réaction")));
		
		mockMvc.perform(post("/risk")
				.content(objectMapper.writeValueAsString(borderlineRiskFactorsDtoRequest))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.level").value("Borderline"))
			.andExpect(jsonPath("$.badgeSymbol").value("?"))
			.andExpect(jsonPath("$.badgeColor").value("#FF9800"));
	}
	
	@Test
	@Order(3)
	public void getRiskScore_shouldReturnOk_shouldResultDanger() throws Exception {
		RiskFactorsDto dangerRiskFactorsDtoRequest = DtoInstanceBuilder.createRiskFactorsDto(false, ZonedDateTime.parse("2001-01-01T01:02:03Z"), new ArrayList<String>(List.of("réaction", "vertiges", "fumeur", "anormal")));
		
		mockMvc.perform(post("/risk")
				.content(objectMapper.writeValueAsString(dangerRiskFactorsDtoRequest))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.level").value("Danger"))
			.andExpect(jsonPath("$.badgeSymbol").value("!"))
			.andExpect(jsonPath("$.badgeColor").value("#D32F2F"));
	}
	
	@Test
	@Order(4)
	public void getRiskScore_shouldReturnOk_shouldResultEarlyOnset() throws Exception {
		RiskFactorsDto earlyOnsetRiskFactorsDtoRequest = DtoInstanceBuilder.createRiskFactorsDto(true, ZonedDateTime.parse("2001-01-01T01:02:03Z"), new ArrayList<String>(List.of("réaction", "vertiges", "fumeur", "anormal", "rechute")));

		mockMvc.perform(post("/risk")
				.content(objectMapper.writeValueAsString(earlyOnsetRiskFactorsDtoRequest))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.level").value("Early onset"))
			.andExpect(jsonPath("$.badgeSymbol").value("!!"))
			.andExpect(jsonPath("$.badgeColor").value("#000000"));
	}
}