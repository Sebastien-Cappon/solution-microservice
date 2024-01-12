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

import com.mediLaboSolutions.T2D2Patient.model.Person;
import com.mediLaboSolutions.T2D2Patient.service.contracts.IPractitionerPersonService;
import com.mediLaboSolutions.T2D2Patient.util.ModelInstanceBuilder;

@WebMvcTest(controllers = PractitionerPersonController.class)
@TestMethodOrder(OrderAnnotation.class)
public class PractitionerPersonControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	IPractitionerPersonService iPractitionerPersonService;
	
	private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	private Person personResponse = ModelInstanceBuilder.createPerson(1, false, "Byron", "Ada", LocalDate.parse("1815-12-10", dateTimeFormatter), "0102030405", "ada.byron@countess.lvl");
	private List<Person> personResponseList = new ArrayList<Person>(Arrays.asList(personResponse, personResponse, personResponse));
	
	@Test
	@Order(1)
	public void getPersonsByPractitionerId_shouldReturnOk() throws Exception {
		when(iPractitionerPersonService.getPersonsByPractitionerId(anyInt()))
			.thenReturn(personResponseList);
		
		mockMvc.perform(get("/patient/practitioners/{practitionerId}/persons", "1")
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
	public void getPersonsByPractitionerId_shouldReturnNoContent() throws Exception {
		when(iPractitionerPersonService.getPersonsByPractitionerId(anyInt()))
			.thenReturn(new ArrayList<>());
		
		mockMvc.perform(get("/patient/practitioners/{practitionerId}/persons", "0")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isNoContent());
	}
	
	@Test
	@Order(3)
	public void addPersonToPractitioner_shouldReturnCreated() throws Exception {
		when(iPractitionerPersonService.addPersonToPractitioner(anyInt(), anyInt()))
			.thenReturn(1);
		
		mockMvc.perform(post("/patient-creation/practitioners/{practitionerId}/persons/{personId}", "1", "1")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());
	}
	
	@Test
	@Order(4)
	public void addPersonToPractitioner_shouldReturnBadRequest() throws Exception {
		when(iPractitionerPersonService.addPersonToPractitioner(anyInt(), anyInt()))
			.thenReturn(null);
	
		mockMvc.perform(post("/patient-creation/practitioners/{practitionerId}/persons/{personId}", "0", "0")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	@Order(5)
	public void deletePersonFromPractitioner_shouldReturnOk() throws Exception {
		mockMvc.perform(delete("/patient-deletion/practitioners/{practitionerId}/persons/{personId}", "1", "1")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
}