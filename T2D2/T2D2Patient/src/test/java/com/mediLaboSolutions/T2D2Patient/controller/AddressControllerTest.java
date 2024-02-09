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
import com.mediLaboSolutions.T2D2Patient.model.Address;
import com.mediLaboSolutions.T2D2Patient.service.contracts.IAddressService;
import com.mediLaboSolutions.T2D2Patient.util.ModelInstanceBuilder;

@WebMvcTest(controllers = AddressController.class)
@TestMethodOrder(OrderAnnotation.class)
public class AddressControllerTest {

	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private IAddressService iAddressService;

	private Address addressResponse = ModelInstanceBuilder.createAddress(1, "1A", "Street", "Unknown St.", "W1", "Marylebone", "England");
	private List<Address> addressResponseList = new ArrayList<>(Arrays.asList(addressResponse, addressResponse, addressResponse));

	@Test
	@Order(1)
	public void getAddresses_shouldReturnOk() throws Exception {
		when(iAddressService.getAddresses())
			.thenReturn(addressResponseList);
		
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
		when(iAddressService.getAddressById(anyInt()))
			.thenReturn(addressResponse);
		
		mockMvc.perform(get("/addresses/{addressId}", "1")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(1))
			.andExpect(jsonPath("$.number").value("1A"))
			.andExpect(jsonPath("$.wayType").value("Street"))
			.andExpect(jsonPath("$.wayName").value("Unknown St."))
			.andExpect(jsonPath("$.postcode").value("W1"))
			.andExpect(jsonPath("$.city").value("Marylebone"))
			.andExpect(jsonPath("$.country").value("England"));
	}

	@Test
	@Order(3)
	public void createAddress_shouldReturnCreated() throws Exception {
		when(iAddressService.createAddress(any(Address.class)))
			.thenReturn(addressResponse);
		
		mockMvc.perform(post("/address")
				.content(objectMapper.writeValueAsString(addressResponse))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").value(1))
			.andExpect(jsonPath("$.number").value("1A"))
			.andExpect(jsonPath("$.wayType").value("Street"))
			.andExpect(jsonPath("$.wayName").value("Unknown St."))
			.andExpect(jsonPath("$.postcode").value("W1"))
			.andExpect(jsonPath("$.city").value("Marylebone"))
			.andExpect(jsonPath("$.country").value("England"));
	}

	@Test
	@Order(4)
	public void createAddress_shouldReturnBadRequest() throws Exception {
		when(iAddressService.createAddress(any(Address.class)))
			.thenReturn(null);
		
		mockMvc.perform(post("/address")
				.content(objectMapper.writeValueAsString(addressResponse))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}

	@Test
	@Order(5)
	public void updateAddress_shouldReturnOk() throws Exception {
		when(iAddressService.updateAddressById(anyInt(), any(Address.class)))
			.thenReturn(1);
		
		mockMvc.perform(put("/addresses/{addressId}", "1")
				.content(objectMapper.writeValueAsString(addressResponse))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}

	@Test
	@Order(6)
	public void updateAddressById_shouldReturnBadRequest() throws Exception {
		when(iAddressService.updateAddressById(anyInt(), any(Address.class)))
			.thenReturn(null);
		
		mockMvc.perform(put("/addresses/{addressId}", "0")
				.content(objectMapper.writeValueAsString(addressResponse))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}

	@Test
	@Order(7)
	void deleteAddressById_shouldReturnOk() throws Exception {
		mockMvc.perform(delete("/addresses/{addressId}", "1")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
}