package com.mediLaboSolutions.T2D2Diabetes.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediLaboSolutions.T2D2Diabetes.model.TriggerTerm;
import com.mediLaboSolutions.T2D2Diabetes.service.contracts.ITriggerTermService;
import com.mediLaboSolutions.T2D2Diabetes.util.ModelInstanceBuilder;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "file:config/application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class TriggerTermServiceIT {

	private static final Logger logger = LoggerFactory.getLogger(TriggerTermServiceIT.class);
	
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ITriggerTermService iTriggerTermService;
	
	private TriggerTerm firstTriggerTerm = ModelInstanceBuilder.createTrigger(1, "réaction");
	private TriggerTerm secondTriggerTerm = ModelInstanceBuilder.createTrigger(2, "vertiges");

	@BeforeAll
	private void fillH2Database() throws Exception {
		iTriggerTermService.createTriggerTerm(firstTriggerTerm);
		iTriggerTermService.createTriggerTerm(secondTriggerTerm);
		logger.info("triggerterm table in the H2 database filled.");
	}
	
	@AfterAll
	private void tearDown() {
		logger.info("H2 test database cleaned.");
	}
	
	@Test
	@Order(1)
	public void getTriggerTerms_shouldReturnOk() throws Exception {
		mockMvc.perform(get("/triggers")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.[*].id").isNotEmpty())
			.andExpect(jsonPath("$.[*].term").isNotEmpty());
	}

	@Test
	@Order(2)
	public void getTriggerTermById_shouldReturnOk() throws Exception {
		mockMvc.perform(get("/triggers/{triggerTermId}", "1")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(firstTriggerTerm.getId()))
			.andExpect(jsonPath("$.term").value(firstTriggerTerm.getTerm()));
	}

	@Test
	@Order(3)
	public void createTriggerTerm_shouldReturnCreated() throws Exception {
		TriggerTerm newTriggerTerm = ModelInstanceBuilder.createTrigger(3, "rechute");
		
		mockMvc.perform(post("/trigger")
				.content(objectMapper.writeValueAsString(newTriggerTerm))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").value(newTriggerTerm.getId()))
			.andExpect(jsonPath("$.term").value(newTriggerTerm.getTerm()));
	}

	@Test
	@Order(4)
	public void createTriggerTerm_shouldReturnBadRequest() throws Exception {
		mockMvc.perform(post("/trigger")
				.content(objectMapper.writeValueAsString(firstTriggerTerm))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}

	@Test
	@Order(5)
	public void updateTriggerTermById_shouldReturnOk() throws Exception {
		TriggerTerm updatedTriggerTerm = ModelInstanceBuilder.createTrigger(4, "vertige");
		
		mockMvc.perform(put("/triggers/{triggerTermId}", "2")
				.content(objectMapper.writeValueAsString(updatedTriggerTerm))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
		
		TriggerTerm triggerTermToUpdate = iTriggerTermService.getTriggerTermById(2);
		assertThat(triggerTermToUpdate.getId()).isEqualTo(2);
		assertThat(triggerTermToUpdate.getTerm()).isEqualTo(updatedTriggerTerm.getTerm());
	}

	@Test
	@Order(6)
	public void updateTriggerTermById_shouldReturnBadRequest() throws Exception {
		TriggerTerm updatedTriggerTerm = ModelInstanceBuilder.createTrigger(5, "anticorps");
		
		mockMvc.perform(put("/triggers/{triggerTermId}", "42")
				.content(objectMapper.writeValueAsString(updatedTriggerTerm))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}

	@Test
	@Order(7)
	public void deleteTriggerTermById_shouldReturnOk() throws Exception {
		mockMvc.perform(delete("/triggers/{triggerTermId}", "2")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
}