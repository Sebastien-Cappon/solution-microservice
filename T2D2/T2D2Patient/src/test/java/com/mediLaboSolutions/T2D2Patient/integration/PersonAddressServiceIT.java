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
import com.mediLaboSolutions.T2D2Patient.dto.PersonAddressAddDto;
import com.mediLaboSolutions.T2D2Patient.model.Address;
import com.mediLaboSolutions.T2D2Patient.model.Person;
import com.mediLaboSolutions.T2D2Patient.service.contracts.IAddressService;
import com.mediLaboSolutions.T2D2Patient.service.contracts.IPersonAddressService;
import com.mediLaboSolutions.T2D2Patient.service.contracts.IPersonService;
import com.mediLaboSolutions.T2D2Patient.util.DtoInstanceBuilder;
import com.mediLaboSolutions.T2D2Patient.util.ModelInstanceBuilder;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "file:config/application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class PersonAddressServiceIT {
	
	private static final Logger logger = LoggerFactory.getLogger(PersonAddressServiceIT.class);
	
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private IAddressService iAddressService;
	@Autowired
	private IPersonService iPersonService;
	@Autowired
	private IPersonAddressService iPersonAddressService;

	private Person firstPerson = ModelInstanceBuilder.createPerson(1, false, "LASTNAME1", "Firstname1", ZonedDateTime.parse("1980-01-02T01:02:03Z"), "0102030405", "email1@mail.com");
	private Person secondPerson = ModelInstanceBuilder.createPerson(2, true, "LASTNAME2", "Firstname2", ZonedDateTime.parse("1983-04-05T03:04:05Z"), "1020304050", "email2@mail.com");
	
	private Address firstAddress = ModelInstanceBuilder.createAddress(1, "1A", "St.", "Street Name", "01234A", "CITY", "COUNTRY");
	private Address secondAddress = ModelInstanceBuilder.createAddress(2, "2B", "Rd.", "Road Name", "56789B", "CITY2", "COUNTRY2");
	
	@BeforeAll
	private void fillH2Database() throws Exception {
		iAddressService.createAddress(firstAddress);
		iAddressService.createAddress(secondAddress);
		logger.info("Address table in the H2 database filled.");
		
		iPersonService.createPerson(firstPerson);
		iPersonService.createPerson(secondPerson);
		logger.info("person table in the H2 database filled.");
		
		PersonAddressAddDto firstPersonAddressAddDto = DtoInstanceBuilder.createPersonAddressAddDto(firstPerson.getId(), firstAddress.getId());
		iPersonAddressService.addPersonAddress(firstPersonAddressAddDto);
		logger.info("person_address table in the H2 database filled.");
	}
	
	@AfterAll
	private void tearDown() {
		logger.info("H2 test database cleaned.");
	}
	
	@Test
	@Order(1)
	public void getPersonAddressesByPersonId_shouldReturnOk() throws Exception {
		mockMvc.perform(get("/residences/persons/{personId}/addresses", firstPerson.getId())
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.[0].id").value(firstAddress.getId()))
			.andExpect(jsonPath("$.[0].number").value(firstAddress.getNumber()))
			.andExpect(jsonPath("$.[0].wayType").value(firstAddress.getWayType()))
			.andExpect(jsonPath("$.[0].wayName").value(firstAddress.getWayName()))
			.andExpect(jsonPath("$.[0].postcode").value(firstAddress.getPostcode()))
			.andExpect(jsonPath("$.[0].city").value(firstAddress.getCity()))
			.andExpect(jsonPath("$.[0].country").value(firstAddress.getCountry()));
	}

	@Test
	@Order(2)
	public void getPersonAddressesByPersonEmail_shouldReturnOk() throws Exception {
		mockMvc.perform(get("/residences/persons/email/{personEmail}/addresses", firstPerson.getEmail())
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.[0].id").value(firstAddress.getId()))
			.andExpect(jsonPath("$.[0].number").value(firstAddress.getNumber()))
			.andExpect(jsonPath("$.[0].wayType").value(firstAddress.getWayType()))
			.andExpect(jsonPath("$.[0].wayName").value(firstAddress.getWayName()))
			.andExpect(jsonPath("$.[0].postcode").value(firstAddress.getPostcode()))
			.andExpect(jsonPath("$.[0].city").value(firstAddress.getCity()))
			.andExpect(jsonPath("$.[0].country").value(firstAddress.getCountry()));
	}

	@Test
	@Order(3)
	public void addPersonAddress_shouldReturnCreated() throws Exception {
		PersonAddressAddDto newPersonAddressAddDto = DtoInstanceBuilder.createPersonAddressAddDto(secondPerson.getId(), secondAddress.getId());
		
		mockMvc.perform(post("/residence")
				.content(objectMapper.writeValueAsString(newPersonAddressAddDto))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());
		
		List<Address> personNewAddresses = iPersonAddressService.getPersonAddressesByPersonId(secondPerson.getId());
		assertThat(personNewAddresses.get(0).getId()).isEqualTo(secondAddress.getId());
		assertThat(personNewAddresses.get(0).getNumber()).isEqualTo(secondAddress.getNumber());
		assertThat(personNewAddresses.get(0).getWayType()).isEqualTo(secondAddress.getWayType());
		assertThat(personNewAddresses.get(0).getWayName()).isEqualTo(secondAddress.getWayName());
		assertThat(personNewAddresses.get(0).getPostcode()).isEqualTo(secondAddress.getPostcode());
		assertThat(personNewAddresses.get(0).getCity()).isEqualTo(secondAddress.getCity());
		assertThat(personNewAddresses.get(0).getCountry()).isEqualTo(secondAddress.getCountry());
	}

	@Test
	@Order(4)
	public void addPersonAddress_shouldReturnBadRequest() throws Exception {
		mockMvc.perform(post("/residence")
				.content(objectMapper.writeValueAsString(null))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}

	@Test
	@Order(5)
	public void deletePersonAddress_shouldReturnOk() throws Exception {
		mockMvc.perform(delete("/residences/persons/{personId}/addresses/{addressId}", secondPerson.getId(), secondAddress.getId())
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
}