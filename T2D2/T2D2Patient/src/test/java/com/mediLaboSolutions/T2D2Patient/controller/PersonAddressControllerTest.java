package com.mediLaboSolutions.T2D2Patient.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import com.mediLaboSolutions.T2D2Patient.model.Address;
import com.mediLaboSolutions.T2D2Patient.service.contracts.IPersonAddressService;
import com.mediLaboSolutions.T2D2Patient.util.ModelInstanceBuilder;

@WebMvcTest(controllers = PersonAddressController.class)
@TestMethodOrder(OrderAnnotation.class)
public class PersonAddressControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private IPersonAddressService iPersonAddressService;
	
	private Address addressResponse = ModelInstanceBuilder.createAddress(1, "1A", "Street", "Unknown St.", "W1", "Marylebone", "England");
	private List<Address> addressResponseList = new ArrayList<>(Arrays.asList(addressResponse, addressResponse, addressResponse));
	
	@Test
	@Order(1)
	public void getPersonAddressesByPersonId_shouldReturnOk() throws Exception {
		when(iPersonAddressService.getPersonAddressesByPersonId(anyInt()))
			.thenReturn(addressResponseList);
		
		mockMvc.perform(get("/residences/persons/{personId}/addresses", "1")
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
	public void getPersonAddressesByPersonId_shouldReturnNoContent() throws Exception {
		when(iPersonAddressService.getPersonAddressesByPersonId(anyInt()))
			.thenReturn(new ArrayList<>());
		
		mockMvc.perform(get("/residences/persons/{personId}/addresses", "0")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isNoContent());
	}
	
	@Test
	@Order(3)
	public void addPersonAddress_shouldReturnCreated() throws Exception {
		when(iPersonAddressService.addPersonAddress(anyInt(), anyInt()))
			.thenReturn(1);
		
		mockMvc.perform(post("/residence-creation/persons/{personId}/addresses/{addressId}", "1", "1")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());
	}
	
	@Test
	@Order(4)
	public void addPersonAddress_shouldReturnBadRequest() throws Exception {
		when(iPersonAddressService.addPersonAddress(anyInt(), anyInt()))
			.thenReturn(null);
	
		mockMvc.perform(post("/residence-creation/persons/{personId}/addresses/{addressId}", "0", "0")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	@Order(5)
	public void deletePersonAddress_shouldReturnOk() throws Exception {
		mockMvc.perform(delete("/residence-deletion/persons/{personId}/addresses/{addressId}", "1", "1")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
}