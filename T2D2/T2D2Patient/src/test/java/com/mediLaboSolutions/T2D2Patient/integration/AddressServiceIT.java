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
import com.mediLaboSolutions.T2D2Patient.model.Address;
import com.mediLaboSolutions.T2D2Patient.service.contracts.IAddressService;
import com.mediLaboSolutions.T2D2Patient.util.ModelInstanceBuilder;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "file:config/application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class AddressServiceIT {

	private static final Logger logger = LoggerFactory.getLogger(AddressServiceIT.class);
	
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private IAddressService iAddressService;
	
	private Address firstAddress = ModelInstanceBuilder.createAddress(1, "1A", "St.", "Street Name", "01234A", "CITY", "COUNTRY");
	private Address secondAddress = ModelInstanceBuilder.createAddress(2, "2B", "Rd.", "Road Name", "56789B", "CITY2", "COUNTRY2");

	@BeforeAll
	private void fillH2Database() throws Exception {
		iAddressService.createAddress(firstAddress);
		iAddressService.createAddress(secondAddress);
		logger.info("address table in the H2 database filled.");
	}
	
	@AfterAll
	private void tearDown() {
		logger.info("H2 test database cleaned.");
	}
	
	@Test
	@Order(1)
	public void getAddresses_shouldReturnOk() throws Exception {
		mockMvc.perform(get("/addresses")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.[*].id").isNotEmpty())
			.andExpect(jsonPath("$.[*].number").isNotEmpty())
			.andExpect(jsonPath("$.[*].wayType").isNotEmpty())
			.andExpect(jsonPath("$.[*].wayName").isNotEmpty())
			.andExpect(jsonPath("$.[*].postcode").isNotEmpty())
			.andExpect(jsonPath("$.[*].city").isNotEmpty())
			.andExpect(jsonPath("$.[*].country").isNotEmpty());
	}
	
	@Test
	@Order(2)
	public void getAddressById_shouldReturnOk() throws Exception {
		mockMvc.perform(get("/addresses/{addressId}", firstAddress.getId())
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(firstAddress.getId()))
			.andExpect(jsonPath("$.number").value(firstAddress.getNumber()))
			.andExpect(jsonPath("$.wayType").value(firstAddress.getWayType()))
			.andExpect(jsonPath("$.wayName").value(firstAddress.getWayName()))
			.andExpect(jsonPath("$.postcode").value(firstAddress.getPostcode()))
			.andExpect(jsonPath("$.city").value(firstAddress.getCity()))
			.andExpect(jsonPath("$.country").value(firstAddress.getCountry()));
	}
	
	@Test
	@Order(3)
	public void createAddress_shouldReturnCreated() throws Exception {
		Address newAddress = ModelInstanceBuilder.createAddress(3, "3C", "Ave.", "Avenue Name", "02468C", "CITY3", "COUNTRY3");
		
		mockMvc.perform(post("/address")
				.content(objectMapper.writeValueAsString(newAddress))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").value(newAddress.getId()))
			.andExpect(jsonPath("$.number").value(newAddress.getNumber()))
			.andExpect(jsonPath("$.wayType").value(newAddress.getWayType()))
			.andExpect(jsonPath("$.wayName").value(newAddress.getWayName()))
			.andExpect(jsonPath("$.postcode").value(newAddress.getPostcode()))
			.andExpect(jsonPath("$.city").value(newAddress.getCity()))
			.andExpect(jsonPath("$.country").value(newAddress.getCountry()));
	}

	@Test
	@Order(4)
	public void createAddress_shouldReturnBadRequest() throws Exception {
		mockMvc.perform(post("/address")
				.content(objectMapper.writeValueAsString(firstAddress))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}

	@Test
	@Order(5)
	public void updateAddress_shouldReturnOk() throws Exception {
		Address updatedAddress = ModelInstanceBuilder.createAddress(4, "4D", "", "", "", "", "");
		
		mockMvc.perform(put("/addresses/{addressId}", secondAddress.getId())
				.content(objectMapper.writeValueAsString(updatedAddress))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
		
		Address addressToUpdate = iAddressService.getAddressById(secondAddress.getId());
		assertThat(addressToUpdate.getNumber()).isEqualTo(updatedAddress.getNumber());
	}

	@Test
	@Order(6)
	public void updateAddressById_shouldReturnBadRequest() throws Exception {
		Address updatedAddress = ModelInstanceBuilder.createAddress(5, "5E", "Sqr.", "Square Name", "97531E", "CITY5", "COUNTRY5");
		
		mockMvc.perform(put("/addresses/{addressId}", "42")
				.content(objectMapper.writeValueAsString(updatedAddress))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}

	@Test
	@Order(7)
	void deleteAddressById_shouldReturnOk() throws Exception {
		mockMvc.perform(delete("/addresses/{addressId}", secondAddress.getId())
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
}