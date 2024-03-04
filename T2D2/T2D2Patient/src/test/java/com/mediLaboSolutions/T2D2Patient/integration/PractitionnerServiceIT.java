package com.mediLaboSolutions.T2D2Patient.integration;

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
import com.mediLaboSolutions.T2D2Patient.dto.PractitionerLoginDto;
import com.mediLaboSolutions.T2D2Patient.model.Practitioner;
import com.mediLaboSolutions.T2D2Patient.service.contracts.IPractitionerService;
import com.mediLaboSolutions.T2D2Patient.util.DtoInstanceBuilder;
import com.mediLaboSolutions.T2D2Patient.util.ModelInstanceBuilder;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "file:config/application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class PractitionnerServiceIT {

	private static final Logger logger = LoggerFactory.getLogger(PractitionnerServiceIT.class);
	
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private IPractitionerService iPractitionerService;

	private String firstUnhashedPassword = "Pass1"; 
	private Practitioner firstPractitioner = ModelInstanceBuilder.createPractitioner(1, "LASTNAME1", "Firstname1", "practioner1@mail.com", firstUnhashedPassword);
	
	private String secondUnhashedPassword = "Pass2";
	private Practitioner secondPractitioner = ModelInstanceBuilder.createPractitioner(2, "LASTNAME2", "Firstname2", "practioner2@mail.com", secondUnhashedPassword);
	
	@BeforeAll
	private void fillH2Database() throws Exception {
		iPractitionerService.createPractitioner(firstPractitioner);
		iPractitionerService.createPractitioner(secondPractitioner);
		logger.info("practitioner table in the H2 database filled.");
	}
	
	@AfterAll
	private void tearDown() {
		logger.info("H2 test database cleaned.");
	}
	
	@Test
	@Order(1)
	public void getPractitioners_shouldReturnOk() throws Exception {
		mockMvc.perform(get("/practitioners")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.[*].id").isNotEmpty())
			.andExpect(jsonPath("$.[*].lastname").isNotEmpty())
			.andExpect(jsonPath("$.[*].firstname").isNotEmpty())
			.andExpect(jsonPath("$.[*].email").isNotEmpty())
			.andExpect(jsonPath("$.[*].password").isNotEmpty());
	}

	@Test
	@Order(2)
	public void getPractitionerById_shouldReturnOk() throws Exception {
		mockMvc.perform(get("/practitioners/{practitionerId}", firstPractitioner.getId())
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(firstPractitioner.getId()))
			.andExpect(jsonPath("$.lastname").value(firstPractitioner.getLastname()))
			.andExpect(jsonPath("$.firstname").value(firstPractitioner.getFirstname()))
			.andExpect(jsonPath("$.email").value(firstPractitioner.getEmail()))
			.andExpect(jsonPath("$.password").value(firstPractitioner.getPassword()));
	}

	@Test
	@Order(3)
	public void connectPractitionerWithEmailAndPassword_shouldReturnOk() throws Exception {
		PractitionerLoginDto loginRequest = DtoInstanceBuilder.createPractitionerLoginDto(firstPractitioner.getEmail(), firstUnhashedPassword);
		
		mockMvc.perform(post("/login")
				.content(objectMapper.writeValueAsString(loginRequest))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(firstPractitioner.getId()))
			.andExpect(jsonPath("$.lastname").value(firstPractitioner.getLastname()))
			.andExpect(jsonPath("$.firstname").value(firstPractitioner.getFirstname()));
	}

	@Test
	@Order(4)
	public void connectPractitionerWithEmailAndPassword_shouldReturnBadRequest() throws Exception {
		mockMvc.perform(post("/login")
				.content(objectMapper.writeValueAsString(null))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}

	@Test
	@Order(5)
	public void createPractitioner_shouldReturnOk() throws Exception {
		Practitioner newPractitioner = ModelInstanceBuilder.createPractitioner(3, "LASTNAME3", "Firstname3", "practioner3@mail.com", "Pass3");
		
		mockMvc.perform(post("/practitioner")
				.content(objectMapper.writeValueAsString(newPractitioner))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").value(newPractitioner.getId()))
			.andExpect(jsonPath("$.lastname").value(newPractitioner.getLastname()))
			.andExpect(jsonPath("$.firstname").value(newPractitioner.getFirstname()))
			.andExpect(jsonPath("$.email").value(newPractitioner.getEmail()));
	}

	@Test
	@Order(6)
	public void createPractitioner_shouldReturnBadRequest() throws Exception {
		mockMvc.perform(post("/practitioner")
			.content(objectMapper.writeValueAsString(firstPractitioner))
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());
	}

	@Test
	@Order(7)
	public void updatePractitionerById_shouldReturnOk() throws Exception {
		Practitioner updatedPractitioner = ModelInstanceBuilder.createPractitioner(4, "LASTNAME4", "", firstPractitioner.getEmail(), "");
		
		mockMvc.perform(put("/practitioners/{practitionerId}", secondPractitioner.getId())
				.content(objectMapper.writeValueAsString(updatedPractitioner))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
		
		Practitioner practionerToUpdate = iPractitionerService.getPractitionerById(secondPractitioner.getId());
		assertThat(practionerToUpdate.getLastname()).isEqualTo(updatedPractitioner.getLastname());
	}

	@Test
	@Order(8)
	public void updatePractitionerById_shouldReturnBadRequest() throws Exception {
		Practitioner updatedPractitioner = ModelInstanceBuilder.createPractitioner(5, "LASTNAME5", "Firstname5", "practioner5@mail.com", "Pass5");
		
		mockMvc.perform(put("/practitioners/{practitionerId}", "42")
				.content(objectMapper.writeValueAsString(updatedPractitioner))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}

	@Test
	@Order(9)
	public void deletePractitionerById_shouldReturnOk() throws Exception {
		mockMvc.perform(delete("/practitioners/{practitionerId}", secondPractitioner.getId())
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
}