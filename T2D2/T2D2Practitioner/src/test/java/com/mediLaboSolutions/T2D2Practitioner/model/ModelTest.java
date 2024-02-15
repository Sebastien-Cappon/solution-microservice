package com.mediLaboSolutions.T2D2Practitioner.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.mediLaboSolutions.T2D2Practitioner.util.ModelInstanceBuilder;

@TestMethodOrder(OrderAnnotation.class)
public class ModelTest {

	private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	private Note note = ModelInstanceBuilder.createNote("a1", 1, LocalDate.parse("1970-01-01", dateTimeFormatter), "The patient will probably die within 4 days. Sadly, our profit are going to suffer.");

	@Test
	@Order(1)
	public void noteToString_isNotBlank() {
		assertThat(note.toString()).isNotBlank();
	}
}