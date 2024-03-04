package com.mediLaboSolutions.T2D2Patient.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.ZonedDateTime;

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
import com.mediLaboSolutions.T2D2Patient.model.Person;
import com.mediLaboSolutions.T2D2Patient.service.contracts.IPersonService;
import com.mediLaboSolutions.T2D2Patient.util.ModelInstanceBuilder;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "file:config/application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class PersonServiceIT {

	private static final Logger logger = LoggerFactory.getLogger(PersonServiceIT.class);
	
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private IPersonService iPersonService;

	private Person firstPerson = ModelInstanceBuilder.createPerson(1, false, "LASTNAME1", "Firstname1", ZonedDateTime.parse("1980-01-02T01:02:03Z"), "0102030405", "email1@mail.com");
	private Person secondPerson = ModelInstanceBuilder.createPerson(2, true, "LASTNAME2", "Firstname2", ZonedDateTime.parse("1983-04-05T03:04:05Z"), "1020304050", "email2@mail.com");
	
	@BeforeAll
	private void fillH2Database() throws Exception {
		iPersonService.createPerson(firstPerson);
		iPersonService.createPerson(secondPerson);
		logger.info("person table in the H2 database filled.");
	}
	
	@AfterAll
	private void tearDown() {
		logger.info("H2 test database cleaned.");
	}
	
	@Test
	@Order(1)
	public void getPersons_shouldReturnOk() throws Exception {
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
		mockMvc.perform(get("/persons/{personId}", firstPerson.getId())
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(firstPerson.getId()))
			.andExpect(jsonPath("$.gender").value(firstPerson.getGender()))
			.andExpect(jsonPath("$.lastname").value(firstPerson.getLastname()))
			.andExpect(jsonPath("$.firstname").value(firstPerson.getFirstname()))
			.andExpect(jsonPath("$.birthdate").value(firstPerson.getBirthdate().toString()))
			.andExpect(jsonPath("$.phone").value(firstPerson.getPhone()))
			.andExpect(jsonPath("$.email").value(firstPerson.getEmail()));
	}

	@Test
	@Order(3)
	public void getPersonByEmail_shouldReturnOk() throws Exception {
		mockMvc.perform(get("/persons/email/{personEmail}", firstPerson.getEmail())
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(firstPerson.getId()))
			.andExpect(jsonPath("$.gender").value(firstPerson.getGender()))
			.andExpect(jsonPath("$.lastname").value(firstPerson.getLastname()))
			.andExpect(jsonPath("$.firstname").value(firstPerson.getFirstname()))
			.andExpect(jsonPath("$.birthdate").value(firstPerson.getBirthdate().toString()))
			.andExpect(jsonPath("$.phone").value(firstPerson.getPhone()))
			.andExpect(jsonPath("$.email").value(firstPerson.getEmail()));
	}

	@Test
	@Order(4)
	public void createPerson_shouldReturnCreated() throws Exception {
		Person newPerson = ModelInstanceBuilder.createPerson(3, false, "LASTNAME3", "Firstname3", ZonedDateTime.parse("1986-07-08T06:07:08Z"), "1122334455", "email3@mail.com");
				
		mockMvc.perform(post("/person")
				.content(objectMapper.writeValueAsString(newPerson))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").value(newPerson.getId()))
			.andExpect(jsonPath("$.gender").value(newPerson.getGender()))
			.andExpect(jsonPath("$.lastname").value(newPerson.getLastname()))
			.andExpect(jsonPath("$.firstname").value(newPerson.getFirstname()))
			.andExpect(jsonPath("$.birthdate").value(newPerson.getBirthdate().toString()))
			.andExpect(jsonPath("$.phone").value(newPerson.getPhone()))
			.andExpect(jsonPath("$.email").value(newPerson.getEmail()));
	}

	@Test
	@Order(5)
	public void createPerson_shouldReturnBadRequest() throws Exception {
		mockMvc.perform(post("/person")
				.content(objectMapper.writeValueAsString(firstPerson))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}

	@Test
	@Order(6)
	public void updatePersonById_shouldReturnOk() throws Exception {
		Person updatedPerson = ModelInstanceBuilder.createPerson(4, false, "", "", null, "", "");
		
		mockMvc.perform(put("/persons/{personId}", secondPerson.getId())
				.content(objectMapper.writeValueAsString(updatedPerson))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
		
		Person personToUpdate = iPersonService.getPersonById(secondPerson.getId());
		assertThat(personToUpdate.getGender()).isEqualTo(updatedPerson.getGender());
	}

	@Test
	@Order(7)
	public void updatePersonById_shouldReturnBadRequest() throws Exception {
		Person updatedPerson = ModelInstanceBuilder.createPerson(5, false, "LASTNAME5", "Firstname5", ZonedDateTime.parse("1989-10-11T09:10:11Z"), "5040302010", "email5@mail.com");
		
		mockMvc.perform(put("/persons/{personId}", "42")
				.content(objectMapper.writeValueAsString(updatedPerson))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}

	@Test
	@Order(8)
	public void deletePersonById_shouldReturnOk() throws Exception {
		mockMvc.perform(delete("/persons/{personId}", secondPerson.getId())
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
}