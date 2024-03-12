package com.mediLaboSolutions.T2D2Practitioner.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediLaboSolutions.T2D2Practitioner.model.Note;
import com.mediLaboSolutions.T2D2Practitioner.service.contracts.INoteService;
import com.mediLaboSolutions.T2D2Practitioner.util.ModelInstanceBuilder;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "file:config/application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class NoteServiceIT {
	
	private MongoClient testMongoClient = MongoClients.create("mongodb://localhost:27017/");
	private MongoDatabase testMongoDatabase = testMongoClient.getDatabase("mls_t2d2_practitioner_test");
	
	private static final Logger logger = LoggerFactory.getLogger(NoteServiceIT.class);
	
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private INoteService iNoteService;
	
	private Note firstNote = ModelInstanceBuilder.createNote("1A", 1, LocalDateTime.parse("2001-02-03T01:02:03"), "This is the first note.");
	private Note secondNote = ModelInstanceBuilder.createNote("2B", 2, LocalDateTime.parse("2004-05-06T04:05:06"), "This is the second note.");
	
	@BeforeAll
	private void fillH2Database() throws Exception {
		iNoteService.createNote(firstNote);
		iNoteService.createNote(secondNote);		
		logger.info("note collection in the embedded Mongo database filled.");
	}
	
	@AfterAll
	private void tearDown() {
		testMongoDatabase.drop();
		logger.info("Embedded Mongo database cleaned.");
	}
	
	@Test
	@Order(1)
	public void getNotes_shouldReturnOk() throws Exception {
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
		mockMvc.perform(get("/notes/persons/{personId}", firstNote.getPersonId())
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.[0].id").value(firstNote.getId()))
			.andExpect(jsonPath("$.[0].personId").value(firstNote.getPersonId()))
			.andExpect(jsonPath("$.[0].date").value(firstNote.getDate().toString()))
			.andExpect(jsonPath("$.[0].content").value(firstNote.getContent()));
	}

	@Test
	@Order(3)
	public void getNoteById_shouldReturnOk() throws Exception {
		mockMvc.perform(get("/notes/{noteId}", firstNote.getId())
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(firstNote.getId()))
			.andExpect(jsonPath("$.personId").value(firstNote.getPersonId()))
			.andExpect(jsonPath("$.date").value(firstNote.getDate().toString()))
			.andExpect(jsonPath("$.content").value(firstNote.getContent()));
	}

	@Test
	@Order(4)
	public void createNote_shouldReturnCreated() throws Exception {
		Note newNote = ModelInstanceBuilder.createNote("3C", 3, LocalDateTime.parse("2007-08-09T07:08:09"), "This is the third note.");
		
		mockMvc.perform(post("/note")
				.content(objectMapper.writeValueAsString(newNote))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").value(newNote.getId()))
			.andExpect(jsonPath("$.personId").value(newNote.getPersonId()))
			.andExpect(jsonPath("$.date").value(newNote.getDate().toString()))
			.andExpect(jsonPath("$.content").value(newNote.getContent()));
	}

	@Test
	@Order(5)
	public void createNote_shouldReturnBadRequest() throws Exception {
		mockMvc.perform(post("/note")
				.content(objectMapper.writeValueAsString(firstNote))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}

	@Test
	@Order(6)
	public void updateNoteById_shouldReturnOk() throws Exception {
		Note updatedNote = ModelInstanceBuilder.createNote("4D", 4, LocalDateTime.parse("2010-11-12T10:11:12"), "This is the fourth note.");
		
		mockMvc.perform(put("/notes/{noteId}", secondNote.getId())
				.content(objectMapper.writeValueAsString(updatedNote))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
		
		Note noteToUpdate = iNoteService.getNoteById(secondNote.getId());
		assertThat(noteToUpdate.getPersonId()).isEqualTo(secondNote.getPersonId());
		assertThat(noteToUpdate.getDate()).isEqualTo(secondNote.getDate());
		assertThat(noteToUpdate.getContent()).isEqualTo(updatedNote.getContent());
	}

	@Test
	@Order(7)
	public void updateNoteById_shouldReturnBadRequest() throws Exception {
		Note updatedNote = ModelInstanceBuilder.createNote("5E", 5, LocalDateTime.parse("2013-12-11T13:12:11"), "This is the fifth note.");
		
		mockMvc.perform(put("/notes/{noteId}", "26Z")
				.content(objectMapper.writeValueAsString(updatedNote))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}

	@Test
	@Order(8)
	public void deleteNoteById_shouldReturnOk() throws Exception {
		mockMvc.perform(delete("/notes/{noteId}", secondNote.getId())
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
}