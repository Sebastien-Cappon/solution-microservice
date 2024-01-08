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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import com.mediLaboSolutions.T2D2Patient.model.Patient;
import com.mediLaboSolutions.T2D2Patient.service.contracts.IPatientService;
import com.mediLaboSolutions.T2D2Patient.util.ModelInstanceBuilder;

@WebMvcTest(controllers = PatientController.class)
@TestMethodOrder(OrderAnnotation.class)
public class PatientControllerTest {

	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private IPatientService iPatientService;

	private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	private Patient patientResponse = ModelInstanceBuilder.createPatient(1, false, "Byron", "Ada", LocalDate.parse("1815-12-10", dateTimeFormatter), "0102030405", "ada.byron@countess.lvl");
	private List<Patient> patientResponseList = new ArrayList<Patient>(Arrays.asList(patientResponse, patientResponse, patientResponse));

	@Test
	@Order(1)
	public void getPatients_shouldReturnOk() throws Exception {
		when(iPatientService.getPatients())
			.thenReturn(patientResponseList);
		
		mockMvc.perform(get("/patients", "1")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.[*].id").isNotEmpty())
			.andExpect(jsonPath("$.[*].gender").isNotEmpty())
			.andExpect(jsonPath("$.[*].lastname").isNotEmpty())
			.andExpect(jsonPath("$.[*].firstname").isNotEmpty())
			.andExpect(jsonPath("$.[*].birthdate").isNotEmpty())
			.andExpect(jsonPath("$.[*].phone").isNotEmpty())
			.andExpect(jsonPath("$.[*].email").isNotEmpty());
	}
	
	@Test
	@Order(2)
	public void getPatients_shouldReturnNoContent() throws Exception {
		when(iPatientService.getPatients())
			.thenReturn(new ArrayList<>());
		
		mockMvc.perform(get("/patients", "1")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isNoContent());
	}

	@Test
	@Order(3)
	public void getPatientById_shouldReturnOk() throws Exception {
		when(iPatientService.getPatientById(anyInt()))
			.thenReturn(patientResponse);
		
		mockMvc.perform(get("/patients/patient", "1")
				.param("id", "1")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value("1"))
			.andExpect(jsonPath("$.gender").value(false))
			.andExpect(jsonPath("$.lastname").value("Byron"))
			.andExpect(jsonPath("$.firstname").value("Ada"))
			.andExpect(jsonPath("$.birthdate").value("1815-12-10"))
			.andExpect(jsonPath("$.phone").value("0102030405"))
			.andExpect(jsonPath("$.email").value("ada.byron@countess.lvl"));
	}
	
	@Test
	@Order(4)
	public void getPatientById_shouldReturnNoContent() throws Exception {
		when(iPatientService.getPatientById(anyInt()))
			.thenReturn(null);
		
		mockMvc.perform(get("/patients/patient", "1")
				.param("id", "1")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isNoContent());
	}

	@Test
	@Order(5)
	public void createPatient_shouldReturnCreated() throws Exception {
		when(iPatientService.createPatient(any(Patient.class)))
			.thenReturn(patientResponse);
		
		mockMvc.perform(post("/patients/patient/create", "1")
				.content(objectMapper.writeValueAsString(patientResponse))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").value("1"))
			.andExpect(jsonPath("$.gender").value(false))
			.andExpect(jsonPath("$.lastname").value("Byron"))
			.andExpect(jsonPath("$.firstname").value("Ada"))
			.andExpect(jsonPath("$.birthdate").value("1815-12-10"))
			.andExpect(jsonPath("$.phone").value("0102030405"))
			.andExpect(jsonPath("$.email").value("ada.byron@countess.lvl"));
	}

	@Test
	@Order(6)
	public void createPatient_shouldReturnBadRequest() throws Exception {
		when(iPatientService.createPatient(any(Patient.class)))
			.thenReturn(null);
		
		mockMvc.perform(post("/patients/patient/create", "1")
				.content(objectMapper.writeValueAsString(patientResponse))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}

	@Test
	@Order(7)
	public void updatePatientById_shouldReturnOk() throws Exception {
		when(iPatientService.updatePatientById(anyInt(), any(Patient.class)))
			.thenReturn(1);
		
		mockMvc.perform(put("/patients/patient/update", "1")
				.param("id", "1")
				.content(objectMapper.writeValueAsString(patientResponse))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}

	@Test
	@Order(8)
	public void updatePatientById_shouldReturnBadRequest() throws Exception {
		when(iPatientService.updatePatientById(anyInt(), any(Patient.class)))
			.thenReturn(null);
		
		mockMvc.perform(put("/patients/patient/update", "1")
				.param("id", "0")
				.content(objectMapper.writeValueAsString(patientResponse))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}

	@Test
	@Order(9)
	public void deletePatientById_shouldReturnOk() throws Exception {
		mockMvc.perform(delete("/patients/patient/delete", "1")
				.param("id", "1")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
}