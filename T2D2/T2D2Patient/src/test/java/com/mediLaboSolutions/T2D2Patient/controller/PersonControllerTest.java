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
import com.mediLaboSolutions.T2D2Patient.model.Person;
import com.mediLaboSolutions.T2D2Patient.service.contracts.IPersonService;
import com.mediLaboSolutions.T2D2Patient.util.ModelInstanceBuilder;

@WebMvcTest(controllers = PersonController.class)
@TestMethodOrder(OrderAnnotation.class)
public class PersonControllerTest {

	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private IPersonService iPersonService;

	private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	private Person personResponse = ModelInstanceBuilder.createPerson(1, false, "Byron", "Ada", LocalDate.parse("1815-12-10", dateTimeFormatter), "0102030405", "ada.byron@countess.lvl");
	private List<Person> personResponseList = new ArrayList<Person>(Arrays.asList(personResponse, personResponse, personResponse));

	@Test
	@Order(1)
	public void getPersons_shouldReturnOk() throws Exception {
		when(iPersonService.getPersons())
			.thenReturn(personResponseList);
		
		mockMvc.perform(get("/persons")
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
	public void getPersonById_shouldReturnOk() throws Exception {
		when(iPersonService.getPersonById(anyInt()))
			.thenReturn(personResponse);
		
		mockMvc.perform(get("/persons/{personId}", "1")
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
	@Order(3)
	public void getPersonByEmail_shouldReturnOk() throws Exception {
		when(iPersonService.getPersonByEmail(any(String.class)))
			.thenReturn(personResponse);
		
		mockMvc.perform(get("/persons/email/{personEmail}", "ada.byron@countess.lvl")
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
	public void createPerson_shouldReturnCreated() throws Exception {
		when(iPersonService.createPerson(any(Person.class)))
			.thenReturn(personResponse);
		
		mockMvc.perform(post("/person")
				.content(objectMapper.writeValueAsString(personResponse))
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
	@Order(5)
	public void createPerson_shouldReturnBadRequest() throws Exception {
		when(iPersonService.createPerson(any(Person.class)))
			.thenReturn(null);
		
		mockMvc.perform(post("/person")
				.content(objectMapper.writeValueAsString(personResponse))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}

	@Test
	@Order(6)
	public void updatePersonById_shouldReturnOk() throws Exception {
		when(iPersonService.updatePersonById(anyInt(), any(Person.class)))
			.thenReturn(1);
		
		mockMvc.perform(put("/person/{personId}", "1")
				.content(objectMapper.writeValueAsString(personResponse))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}

	@Test
	@Order(7)
	public void updatePersonById_shouldReturnBadRequest() throws Exception {
		when(iPersonService.updatePersonById(anyInt(), any(Person.class)))
			.thenReturn(null);
		
		mockMvc.perform(put("/person/{personId}", "0")
				.content(objectMapper.writeValueAsString(personResponse))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}

	@Test
	@Order(8)
	public void deletePersonById_shouldReturnOk() throws Exception {
		mockMvc.perform(delete("/person/{personId}", "1")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
}