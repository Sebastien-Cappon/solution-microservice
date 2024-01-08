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
import com.mediLaboSolutions.T2D2Patient.service.contracts.IPatientAddressService;
import com.mediLaboSolutions.T2D2Patient.util.ModelInstanceBuilder;

@WebMvcTest(controllers = PatientAddressController.class)
@TestMethodOrder(OrderAnnotation.class)
public class PatientAddressControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private IPatientAddressService iPatientAddressService;
	
	private Address addressResponse = ModelInstanceBuilder.createAddress(1, "1A", "Street", "Unknown St.", "W1", "Marylebone", "England");
	private List<Address> addressResponseList = new ArrayList<>(Arrays.asList(addressResponse, addressResponse, addressResponse));
	
	@Test
	@Order(1)
	public void getPatientAddressesByPatientId_shouldReturnOk() throws Exception {
		when(iPatientAddressService.getPatientAddressesByPatientId(anyInt()))
			.thenReturn(addressResponseList);
		
		mockMvc.perform(get("/{patientId}/addresses", "1")
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
	public void getPatientAddressesByPatientId_shouldReturnNoContent() throws Exception {
		when(iPatientAddressService.getPatientAddressesByPatientId(anyInt()))
			.thenReturn(new ArrayList<>());
		
		mockMvc.perform(get("/{patientId}/addresses", "0")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isNoContent());
	}
	
	@Test
	@Order(3)
	public void addPatientAddress_shouldReturnCreated() throws Exception {
		when(iPatientAddressService.addPatientAddress(anyInt(), anyInt()))
			.thenReturn(1);
		
		mockMvc.perform(post("/{patientId}/addresses/address/add", "1")
				.param("id", "1")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());
	}
	
	@Test
	@Order(4)
	public void addPatientAddress_shouldReturnBadRequest() throws Exception {
		when(iPatientAddressService.addPatientAddress(anyInt(), anyInt()))
			.thenReturn(null);
	
		mockMvc.perform(post("/{patientId}/addresses/address/add", "1")
				.param("id", "1")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	@Order(5)
	public void deletePatientAddress_shouldReturnOk() throws Exception {
		mockMvc.perform(delete("/{patientId}/addresses/address/delete", "1")
				.param("id", "1")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
}