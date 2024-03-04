package com.mediLaboSolutions.T2D2Patient.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.ZonedDateTime;
import java.util.List;

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
import com.mediLaboSolutions.T2D2Patient.dto.PractitionerPersonAddDto;
import com.mediLaboSolutions.T2D2Patient.model.Person;
import com.mediLaboSolutions.T2D2Patient.model.Practitioner;
import com.mediLaboSolutions.T2D2Patient.service.contracts.IPersonService;
import com.mediLaboSolutions.T2D2Patient.service.contracts.IPractitionerPersonService;
import com.mediLaboSolutions.T2D2Patient.service.contracts.IPractitionerService;
import com.mediLaboSolutions.T2D2Patient.util.DtoInstanceBuilder;
import com.mediLaboSolutions.T2D2Patient.util.ModelInstanceBuilder;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "file:config/application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class PractitionerPersonServiceIT {

	private static final Logger logger = LoggerFactory.getLogger(PractitionerPersonServiceIT.class);
	
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private IPractitionerService iPractitionerService;
	@Autowired
	private IPersonService iPersonService;
	@Autowired
	private IPractitionerPersonService iPractitionerPersonService;

	private Practitioner firstPractitioner = ModelInstanceBuilder.createPractitioner(1, "LASTNAME1", "Firstname1", "practioner1@mail.com", "Pass1");
	private Practitioner secondPractitioner = ModelInstanceBuilder.createPractitioner(2, "LASTNAME2", "Firstname2", "practioner2@mail.com", "Pass2");
	
	private Person firstPerson = ModelInstanceBuilder.createPerson(1, false, "LASTNAME1", "Firstname1", ZonedDateTime.parse("1980-01-02T01:02:03Z"), "0102030405", "email1@mail.com");
	private Person secondPerson = ModelInstanceBuilder.createPerson(2, true, "LASTNAME2", "Firstname2", ZonedDateTime.parse("1983-04-05T03:04:05Z"), "1020304050", "email2@mail.com");
	
	@BeforeAll
	private void fillH2Database() throws Exception {
		iPractitionerService.createPractitioner(firstPractitioner);
		iPractitionerService.createPractitioner(secondPractitioner);
		logger.info("practioner table in the H2 database filled.");
		
		iPersonService.createPerson(firstPerson);
		iPersonService.createPerson(secondPerson);
		logger.info("person table in the H2 database filled.");
		
		PractitionerPersonAddDto practitionerPersonAddDto = DtoInstanceBuilder.createPractitionerPersonAddDto(firstPractitioner.getId(), firstPerson.getEmail());
		iPractitionerPersonService.addPersonToPractitioner(practitionerPersonAddDto);
		logger.info("practioner_person table in the H2 database filled.");
	}
	
	@AfterAll
	private void tearDown() {
		logger.info("H2 test database cleaned.");
	}
	
	@Test
	@Order(1)
	public void getPersonsByPractitionerId_shouldReturnOk() throws Exception {
		mockMvc.perform(get("/patients/practitioners/{practitionerId}/persons", firstPractitioner.getId())
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.[0].id").value(firstPerson.getId()))
			.andExpect(jsonPath("$.[0].gender").value(firstPerson.getGender()))
			.andExpect(jsonPath("$.[0].lastname").value(firstPerson.getLastname()))
			.andExpect(jsonPath("$.[0].firstname").value(firstPerson.getFirstname()))
			.andExpect(jsonPath("$.[0].birthdate").value(firstPerson.getBirthdate().toString()))
			.andExpect(jsonPath("$.[0].phone").value(firstPerson.getPhone()))
			.andExpect(jsonPath("$.[0].email").value(firstPerson.getEmail()));
	}

	@Test
	@Order(2)
	public void getNotPatientsByPractitionerId_shouldReturnOk() throws Exception {
		mockMvc.perform(get("/patients/practitioners/{practitionerId}/persons/not-patients", firstPractitioner.getId())
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.[0].id").value(secondPerson.getId()))
			.andExpect(jsonPath("$.[0].gender").value(secondPerson.getGender()))
			.andExpect(jsonPath("$.[0].lastname").value(secondPerson.getLastname()))
			.andExpect(jsonPath("$.[0].firstname").value(secondPerson.getFirstname()))
			.andExpect(jsonPath("$.[0].birthdate").value(secondPerson.getBirthdate().toString()))
			.andExpect(jsonPath("$.[0].phone").value(secondPerson.getPhone()))
			.andExpect(jsonPath("$.[0].email").value(secondPerson.getEmail()));
	}

	@Test
	@Order(3)
	public void addPersonToPractitioner_shouldReturnCreated() throws Exception {
		PractitionerPersonAddDto newPractitionerPersonAddDto = DtoInstanceBuilder.createPractitionerPersonAddDto(secondPractitioner.getId(), secondPerson.getEmail());
		
		mockMvc.perform(post("/patient")
				.content(objectMapper.writeValueAsString(newPractitionerPersonAddDto))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());
		
		List<Person> practitionerNewPatients = iPractitionerPersonService.getPersonsByPractitionerId(secondPractitioner.getId());
		assertThat(practitionerNewPatients.get(0).getGender()).isEqualTo(secondPerson.getGender());
		assertThat(practitionerNewPatients.get(0).getLastname()).isEqualTo(secondPerson.getLastname());
		assertThat(practitionerNewPatients.get(0).getFirstname()).isEqualTo(secondPerson.getFirstname());
		assertThat(practitionerNewPatients.get(0).getBirthdate()).isEqualTo(secondPerson.getBirthdate());
		assertThat(practitionerNewPatients.get(0).getPhone()).isEqualTo(secondPerson.getPhone());
		assertThat(practitionerNewPatients.get(0).getEmail()).isEqualTo(secondPerson.getEmail());
	}

	@Test
	@Order(4)
	public void addPersonToPractitioner_shouldReturnBadRequest() throws Exception {
		mockMvc.perform(post("/patient")
				.content(objectMapper.writeValueAsBytes(null))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}

	@Test
	@Order(5)
	public void deletePersonFromPractitioner_shouldReturnOk() throws Exception {
		mockMvc.perform(delete("/patients/practitioners/{practitionerId}/persons/{personId}", secondPractitioner.getId(), secondPerson.getId())
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
}