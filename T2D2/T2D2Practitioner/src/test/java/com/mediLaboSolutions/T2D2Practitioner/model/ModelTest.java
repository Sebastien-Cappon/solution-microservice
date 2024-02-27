package com.mediLaboSolutions.T2D2Practitioner.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.mediLaboSolutions.T2D2Practitioner.util.ModelInstanceBuilder;

@TestMethodOrder(OrderAnnotation.class)
public class ModelTest {

	private Note note = ModelInstanceBuilder.createNote("a1", 1, LocalDateTime.parse("1970-01-01T00:00:00"), "The patient will probably die within 4 days. Sadly, our profit are going to suffer.");

	@Test
	@Order(1)
	public void noteToString_isNotBlank() {
		assertThat(note.toString()).isNotBlank();
	}
}