package com.mediLaboSolutions.T2D2Diabetes.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import com.mediLaboSolutions.T2D2Diabetes.model.TriggerTerm;
import com.mediLaboSolutions.T2D2Diabetes.service.contracts.ITriggerTermService;
import com.mediLaboSolutions.T2D2Diabetes.util.ModelInstanceBuilder;

@WebMvcTest(controllers = TriggerTermController.class)
@TestMethodOrder(OrderAnnotation.class)
public class TriggerTermControllerTest {

	private TriggerTerm triggerTermResponse = ModelInstanceBuilder.createTrigger(1, "Plague");
	private List<TriggerTerm> triggerTermResponseList = new ArrayList<TriggerTerm>(Arrays.asList(triggerTermResponse, triggerTermResponse, triggerTermResponse));

	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private ITriggerTermService iTriggerTermService;

	@Test
	@Order(1)
	public void getTriggerTerms_shouldReturnOk() throws Exception {
		when(iTriggerTermService.getTriggerTerms())
			.thenReturn(triggerTermResponseList);
		
		mockMvc.perform(get("/triggers")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.[*].id").isNotEmpty())
			.andExpect(jsonPath("$.[*].term").isNotEmpty());
	}

	@Test
	@Order(2)
	public void getTriggerTermById_shouldReturnOk() throws Exception {
		when(iTriggerTermService.getTriggerTermById(anyInt()))
			.thenReturn(triggerTermResponse);
		
		mockMvc.perform(get("/triggers/{triggerTermId}", "1")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value("1"))
			.andExpect(jsonPath("$.term").value("Plague"));
	}

	@Test
	@Order(3)
	public void createTriggerTerm_shouldReturnCreated() throws Exception {
		when(iTriggerTermService.createTriggerTerm(any(TriggerTerm.class)))
			.thenReturn(triggerTermResponse);
		
		mockMvc.perform(post("/trigger")
				.content(objectMapper.writeValueAsString(triggerTermResponse))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").value("1"))
			.andExpect(jsonPath("$.term").value("Plague"));
	}

	@Test
	@Order(4)
	public void createTriggerTerm_shouldReturnBadRequest() throws Exception {
		when(iTriggerTermService.createTriggerTerm(any(TriggerTerm.class)))
			.thenReturn(null);
		
		mockMvc.perform(post("/trigger")
				.content(objectMapper.writeValueAsString(triggerTermResponse))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}

	@Test
	@Order(5)
	public void updateTriggerTermById_shouldReturnOk() throws Exception {
		when(iTriggerTermService.updateTriggerTermById(anyInt(), any(TriggerTerm.class)))
			.thenReturn(1);
		
		mockMvc.perform(put("/triggers/{triggerTermId}", "1")
				.content(objectMapper.writeValueAsString(triggerTermResponse))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}

	@Test
	@Order(6)
	public void updateTriggerTermById_shouldReturnBadRequest() throws Exception {
		when(iTriggerTermService.updateTriggerTermById(anyInt(), any(TriggerTerm.class)))
			.thenReturn(null);
		
		mockMvc.perform(put("/triggers/{triggerTermId}", "1")
				.content(objectMapper.writeValueAsString(triggerTermResponse))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}

	@Test
	@Order(7)
	public void deleteTriggerTermById_shouldReturnOk() throws Exception {
		mockMvc.perform(delete("/triggers/{triggerTermId}", 1)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
}