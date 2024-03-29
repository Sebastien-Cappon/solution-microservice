package com.mediLaboSolutions.T2D2patient.controller;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediLaboSolutions.T2D2Patient.controller.AddressController;
import com.mediLaboSolutions.T2D2Patient.model.Address;
import com.mediLaboSolutions.T2D2Patient.service.IAddressService;
import com.mediLaboSolutions.T2D2patient.util.InstanceBuilder;

@WebMvcTest(controllers = AddressController.class)
@TestMethodOrder(OrderAnnotation.class)
public class AddressControllerTest {

	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private IAddressService iAddressService;

	private Address addressResponse = InstanceBuilder.createAddress(1, "1A", "Street", "Unknown St.", "W1", "Marylebone", "England");
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
	public void getAddresses_shouldReturnNoContent() throws Exception {
		when(iAddressService.getAddresses())
			.thenReturn(new ArrayList<>());
		
		mockMvc.perform(get("/addresses")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isNoContent());
	}

	@Test
	@Order(3)
	public void getAddressById_shouldReturnOk() throws Exception {
		when(iAddressService.getAddressById(anyInt()))
			.thenReturn(addressResponse);
		
		mockMvc.perform(get("/addresses/address")
				.param("id", "1")
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
	@Order(4)
	public void getAddressById_shouldReturnNoContent() throws Exception {
		when(iAddressService.getAddressById(anyInt()))
			.thenReturn(null);
		
		mockMvc.perform(get("/addresses/address")
				.param("id", "1")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isNoContent());
	}

	@Test
	@Order(5)
	public void createAddress_shouldReturnCreated() throws Exception {
		when(iAddressService.createAddress(any(Address.class)))
			.thenReturn(addressResponse);
		
		mockMvc.perform(post("/addresses/address/create")
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
	@Order(6)
	public void createAddress_shouldReturnBadRequest() throws Exception {
		when(iAddressService.createAddress(any(Address.class)))
			.thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));
		
		mockMvc.perform(post("/addresses/address/create")
				.content(objectMapper.writeValueAsString(null))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}

	@Test
	@Order(7)
	public void updateAddress_shouldReturnOk() throws Exception {
		when(iAddressService.updateAddressById(anyInt(), any(Address.class)))
			.thenReturn(1);
		
		mockMvc.perform(put("/addresses/address/update")
				.param("id", "1")
				.content(objectMapper.writeValueAsString(addressResponse))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}

	@Test
	@Order(8)
	public void updateAddressById_shouldReturnBadRequest() throws Exception {
		when(iAddressService.updateAddressById(anyInt(), any(Address.class)))
			.thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));
		
		mockMvc.perform(put("/addresses/address/update")
				.param("id", "0")
				.content(objectMapper.writeValueAsString(null))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}

	@Test
	@Order(9)
	void deleteAddressById_shouldReturnOk() throws Exception {
		mockMvc.perform(delete("/addresses/address/delete")
				.param("id", "1")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
}