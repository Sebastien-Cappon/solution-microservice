package com.mediLaboSolutions.T2D2Patient.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import com.mediLaboSolutions.T2D2Patient.model.Patient;
import com.mediLaboSolutions.T2D2Patient.service.contracts.IPractitionerPatientService;
import com.mediLaboSolutions.T2D2Patient.util.ModelInstanceBuilder;

@WebMvcTest(controllers = PractitionerPatientController.class)
@TestMethodOrder(OrderAnnotation.class)
public class PractitionerPatientControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	IPractitionerPatientService iPractitionerPatientService;
	
	private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	private Patient patientResponse = ModelInstanceBuilder.createPatient(1, false, "Byron", "Ada", LocalDate.parse("1815-12-10", dateTimeFormatter), "0102030405", "ada.byron@countess.lvl");
	private List<Patient> patientResponseList = new ArrayList<Patient>(Arrays.asList(patientResponse, patientResponse, patientResponse));
	
	@Test
	@Order(1)
	public void getPatientsByPractitionerId_shouldReturnOk() throws Exception {
		when(iPractitionerPatientService.getPatientsByPractitionerId(anyInt()))
			.thenReturn(patientResponseList);
		
		mockMvc.perform(get("/{practitionerId}/patients", "1")
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
	public void getPatientsByPractitionerId_shouldReturnNoContent() throws Exception {
		when(iPractitionerPatientService.getPatientsByPractitionerId(anyInt()))
			.thenReturn(new ArrayList<>());
		
		mockMvc.perform(get("/{practitionerId}/patients", "0")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isNoContent());
	}
	
	@Test
	@Order(3)
	public void addPatientToPractitioner_shouldReturnCreated() throws Exception {
		when(iPractitionerPatientService.addPatientToPractitioner(anyInt(), anyInt()))
			.thenReturn(1);
		
		mockMvc.perform(post("/{practitionerId}/patients/patient/add", "1")
				.param("id", "1")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());
	}
	
	@Test
	@Order(4)
	public void addPatientToPractitioner_shouldReturnBadRequest() throws Exception {
		when(iPractitionerPatientService.addPatientToPractitioner(anyInt(), anyInt()))
			.thenReturn(null);
	
		mockMvc.perform(post("/{practitionerId}/patients/patient/add", "1")
				.param("id", "1")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	@Order(5)
	public void deletePatientFromPractitioner_shouldReturnOk() throws Exception {
		mockMvc.perform(delete("/{practitionerId}/patients/patient/delete", "1")
				.param("id", "1")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
}