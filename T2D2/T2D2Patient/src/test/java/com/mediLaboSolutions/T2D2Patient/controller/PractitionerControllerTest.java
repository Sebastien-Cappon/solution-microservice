package com.mediLaboSolutions.T2D2Patient.controller;

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
import com.mediLaboSolutions.T2D2Patient.dto.PractitionerLoginDto;
import com.mediLaboSolutions.T2D2Patient.model.Practitioner;
import com.mediLaboSolutions.T2D2Patient.service.contracts.IPractitionerService;
import com.mediLaboSolutions.T2D2Patient.util.DtoInstanceBuilder;
import com.mediLaboSolutions.T2D2Patient.util.ModelInstanceBuilder;

@WebMvcTest(controllers = PractitionerController.class)
@TestMethodOrder(OrderAnnotation.class)
public class PractitionerControllerTest {

	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private IPractitionerService iPractitionerService;
	
	private Practitioner practitionerResponse = ModelInstanceBuilder.createPractitioner(1, "Eliot", "Ramesh", "ramesh.eliot@abernathyclinic.com", "UnsecuredPassword");
	private List<Practitioner> practitionerResponseList = new ArrayList<>(Arrays.asList(practitionerResponse, practitionerResponse, practitionerResponse));
	
	@Test
	@Order(1)
	public void getPractitioners_shouldReturnOk() throws Exception {
		when(iPractitionerService.getPractitioners())
			.thenReturn(practitionerResponseList);
		
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
	public void getPractitioners_shouldReturnNoContent() throws Exception {
		when(iPractitionerService.getPractitioners())
			.thenReturn(new ArrayList<>());
		
		mockMvc.perform(get("/practitioners")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isNoContent());
	}

	@Test
	@Order(3)
	public void getPractitionerById_shouldReturnOk() throws Exception {
		when(iPractitionerService.getPractitionerById(anyInt()))
			.thenReturn(practitionerResponse);
			
		mockMvc.perform(get("/practitioners/practitioner")
				.param("id", "1")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(1))
			.andExpect(jsonPath("$.lastname").value("Eliot"))
			.andExpect(jsonPath("$.firstname").value("Ramesh"))
			.andExpect(jsonPath("$.email").value("ramesh.eliot@abernathyclinic.com"))
			.andExpect(jsonPath("$.password").value("UnsecuredPassword"));
	}

	@Test
	@Order(4)
	public void getPractitionerById_shouldReturnNoContent() throws Exception {
		when(iPractitionerService.getPractitionerById(anyInt()))
			.thenReturn(null);
		
		mockMvc.perform(get("/practitioners/practitioner")
				.param("id", "0")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isNoContent());
	}

	@Test
	@Order(5)
	public void connectPractitionerWithEmailAndPassword_shouldReturnOk() throws Exception {
		PractitionerLoginDto loginRequest = DtoInstanceBuilder.createPractitionerLoginDto(practitionerResponse.getEmail(), practitionerResponse.getPassword());
		
		when(iPractitionerService.connectPractitionerWithEmailAndPassword(any(PractitionerLoginDto.class)))
			.thenReturn(practitionerResponse);
		
		mockMvc.perform(post("/login")
				.content(objectMapper.writeValueAsString(loginRequest))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(1))
			.andExpect(jsonPath("$.lastname").value("Eliot"))
			.andExpect(jsonPath("$.firstname").value("Ramesh"))
			.andExpect(jsonPath("$.email").value("ramesh.eliot@abernathyclinic.com"))
			.andExpect(jsonPath("$.password").value("UnsecuredPassword"));
	}

	@Test
	@Order(6)
	public void connectPractitionerWithEmailAndPassword_shouldReturnBadRequest() throws Exception {
		when(iPractitionerService.connectPractitionerWithEmailAndPassword(any(PractitionerLoginDto.class)))
			.thenReturn(null);
		
		mockMvc.perform(post("/login")
				.content(objectMapper.writeValueAsString(practitionerResponse))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	@Order(7)
	public void createPractitioner_shouldReturnOk() throws Exception {
		when(iPractitionerService.createPractitioner(any(Practitioner.class)))
			.thenReturn(practitionerResponse);
		
		mockMvc.perform(post("/practitioners/practitioner/create")
				.content(objectMapper.writeValueAsString(practitionerResponse))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").value(1))
			.andExpect(jsonPath("$.lastname").value("Eliot"))
			.andExpect(jsonPath("$.firstname").value("Ramesh"))
			.andExpect(jsonPath("$.email").value("ramesh.eliot@abernathyclinic.com"))
			.andExpect(jsonPath("$.password").value("UnsecuredPassword"));
	}

	@Test
	@Order(8)
	public void createPractitioner_shouldReturnBadRequest() throws Exception {
		when(iPractitionerService.createPractitioner(any(Practitioner.class)))
			.thenReturn(null);
	
		mockMvc.perform(post("/practitioners/practitioner/create")
			.content(objectMapper.writeValueAsString(practitionerResponse))
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	@Order(9)
	public void updatePractitionerById_shouldReturnOk() throws Exception {
		when(iPractitionerService.updatePractitionerById(anyInt(), any(Practitioner.class)))
			.thenReturn(1);
		
		mockMvc.perform(put("/practitioners/practitioner/update")
				.param("id", "1")
				.content(objectMapper.writeValueAsString(practitionerResponse))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
	
	@Test
	@Order(10)
	public void updatePractitionerById_shouldReturnBadRequest() throws Exception {
		when(iPractitionerService.updatePractitionerById(anyInt(), any(Practitioner.class)))
			.thenReturn(null);
	
		mockMvc.perform(put("/practitioners/practitioner/update")
				.param("id", "0")
				.content(objectMapper.writeValueAsString(practitionerResponse))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	@Order(11)
	public void deletePractitionerById_shouldReturnOk() throws Exception {
		mockMvc.perform(delete("/practitioners/practitioner/delete")
				.param("id", "1")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
}