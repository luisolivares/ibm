package com.ibm;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.controller.PersonaController;
import com.ibm.models.entity.Persona;
import com.ibm.service.PersonaService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = PersonaController.class)
public class PersonaControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PersonaService personaService;

	@Test
	public void lisAll() throws Exception {

		Persona persona1 = new Persona(1, "Luis Alberto", "Olivares Peña", 30, "Masculino");
		Persona persona2 = new Persona(2, "Luis Oswaldo", "Olivares Peña", 38, "Masculino");
		Persona persona3 = new Persona(3, "Luis Oswaldo", "Olivares", 72, "Masculino");
		Persona persona4 = new Persona(4, "Ana", "Vazquez Peña", 64, "Masculino");
		Persona persona5 = new Persona(5, "Luisana", "Olivares Peña", 43, "Femenino");

		List<Persona> users = Arrays.asList(persona1, persona2, persona3, persona4, persona5);

		when(personaService.listAll(1, 2)).thenReturn(users);

		mockMvc.perform(get("/personas")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));

		verify(personaService, times(1)).listAll(1, 2);
		verifyNoMoreInteractions(personaService);

	}

	@Test
	public void create() throws Exception {
		Persona persona = new Persona(1, "Luis Alberto", "Olivares Peña", 30, "Masculino");

		when(personaService.create(persona)).thenReturn(persona);

		mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(asJsonString(persona)))
				.andExpect(status().isConflict());
	}

	@Test
	public void update() throws Exception {
		Persona persona = new Persona(1, "Luis Alberto", "Olivares Peña", 29, "Masculino");

		when(personaService.findById(persona.getId())).thenReturn(null);

		mockMvc.perform(
				put("/personas", persona).contentType(MediaType.APPLICATION_JSON).content(asJsonString(persona)))
				.andExpect(status().isNotFound());

		verify(personaService, times(1)).findById(persona.getId());
		verifyNoMoreInteractions(personaService);
	}

	@Test
	public void delete() throws Exception {
		Persona persona = new Persona(5, "Luisana", "Olivares Peña", 43, "Femenino");

		when(personaService.findById(persona.getId())).thenReturn(null);

		mockMvc.perform(MockMvcRequestBuilders.delete("/personas/{id}", persona.getId())).andExpect(status().isOk());

		verify(personaService, times(1)).findById(persona.getId());
		verify(personaService, times(1)).delete(persona.getId());
		verifyNoMoreInteractions(personaService);
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}