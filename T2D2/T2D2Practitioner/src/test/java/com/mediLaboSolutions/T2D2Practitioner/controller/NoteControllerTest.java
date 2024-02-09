package com.mediLaboSolutions.T2D2Practitioner.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
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
import com.mediLaboSolutions.T2D2Practitioner.model.Note;
import com.mediLaboSolutions.T2D2Practitioner.service.contracts.INoteService;
import com.mediLaboSolutions.T2D2Practitioner.util.ModelInstanceBuilder;

@WebMvcTest(controllers = NoteController.class)
@TestMethodOrder(OrderAnnotation.class)
public class NoteControllerTest {

	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private INoteService iNoteService;

	private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	private Note noteResponse = ModelInstanceBuilder.createNote("1a", 1, LocalDate.parse("1602-06-12", dateTimeFormatter), "Wonderfull day in the New World !");
	private List<Note> noteResponseList = new ArrayList<Note>(Arrays.asList(noteResponse, noteResponse, noteResponse));

	@Test
	@Order(1)
	public void getNotes_shouldReturnOk() throws Exception {
		when(iNoteService.getNotes())
			.thenReturn(noteResponseList);
		
		mockMvc.perform(get("/notes")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.[*].id").isNotEmpty())
			.andExpect(jsonPath("$.[*].personId").isNotEmpty())
			.andExpect(jsonPath("$.[*].date").isNotEmpty())
			.andExpect(jsonPath("$.[*].content").isNotEmpty());
	}

	@Test
	@Order(2)
	public void getNotesByPersonId_shouldReturnOk() throws Exception {
		when(iNoteService.getNotesByPersonId(anyInt()))
			.thenReturn(noteResponseList);
		
		mockMvc.perform(get("/notes/persons/{personId}", "1")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.[*].id").isNotEmpty())
			.andExpect(jsonPath("$.[*].personId").isNotEmpty())
			.andExpect(jsonPath("$.[*].date").isNotEmpty())
			.andExpect(jsonPath("$.[*].content").isNotEmpty());
	}

	@Test
	@Order(3)
	public void getNoteById_shouldReturnOk() throws Exception {
		when(iNoteService.getNoteById(anyString()))
			.thenReturn(noteResponse);
		
		mockMvc.perform(get("/notes/{noteId}", "1a")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value("1a"))
			.andExpect(jsonPath("$.personId").value(1))
			.andExpect(jsonPath("$.date").value("1602-06-12"))
			.andExpect(jsonPath("$.content").value("Wonderfull day in the New World !"));
	}

	@Test
	@Order(4)
	public void createNote_shouldReturnCreated() throws Exception {
		when(iNoteService.createNote(any(Note.class)))
			.thenReturn(noteResponse);
		
		mockMvc.perform(post("/note")
				.content(objectMapper.writeValueAsString(noteResponse))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").value("1a"))
			.andExpect(jsonPath("$.personId").value(1))
			.andExpect(jsonPath("$.date").value("1602-06-12"))
			.andExpect(jsonPath("$.content").value("Wonderfull day in the New World !"));
	}

	@Test
	@Order(5)
	public void createNote_shouldReturnBadRequest() throws Exception {
		when(iNoteService.createNote(any(Note.class)))
			.thenReturn(null);
		
		mockMvc.perform(post("/note")
				.content(objectMapper.writeValueAsString(noteResponse))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}

	@Test
	@Order(6)
	public void updateNoteById_shouldReturnOk() throws Exception {
		when(iNoteService.updateNoteById(anyString(), any(Note.class)))
			.thenReturn(1);
		
		mockMvc.perform(put("/notes/{noteId}", "1a")
				.content(objectMapper.writeValueAsString(noteResponse))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}

	@Test
	@Order(7)
	public void updateNoteById_shouldReturnBadRequest() throws Exception {
		when(iNoteService.updateNoteById(anyString(), any(Note.class)))
			.thenReturn(null);
		
		mockMvc.perform(put("/notes/{noteId}", "0")
				.content(objectMapper.writeValueAsString(noteResponse))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}

	@Test
	@Order(8)
	public void deleteNoteById_shouldReturnOk() throws Exception {
		mockMvc.perform(delete("/notes/{noteId}", "1a")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
}