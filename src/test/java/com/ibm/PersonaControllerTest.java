package com.ibm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.domain.person.model.entity.Persona;
import com.ibm.domain.person.model.enumerated.Sexo;
import com.ibm.domain.person.model.enumerated.TipoDocumento;
import com.ibm.domain.person.model.request.PersonaRequest;
import com.ibm.domain.person.model.response.ResponseApiDTO;
import com.ibm.domain.person.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonaControllerTest {

	private final MockMvc mockMvc;

	private final ObjectMapper objectMapper;

	@MockBean
	private PersonService personaService;

	@Autowired
	public PersonaControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {
		this.mockMvc = mockMvc;
		this.objectMapper = objectMapper;
	}

	@Test
	@DisplayName("Listado de personas con paginación.")
	@Order(2)
	void lisAll() throws Exception {

		Persona persona1 = new Persona(1, "Luis Alberto", "Olivares Peña", 30, Sexo.MASCULINO, TipoDocumento.CEDULA, "20390708");
		Persona persona2 = new Persona(2, "Ana", "Vazquez Peña", 64, Sexo.FEMENINO, TipoDocumento.CEDULA, "42142152");
        Persona persona3 = new Persona(3, "Luis Oswaldo", "Olivares", 72, Sexo.MASCULINO, TipoDocumento.CEDULA, "20390708");
		Persona persona4 = new Persona(5, "Luisana", "Olivares Peña", 43, Sexo.FEMENINO, TipoDocumento.CEDULA, "20390708");

		List<Persona> users = Arrays.asList(persona1, persona2, persona3, persona4);

		when(personaService.listAll(1, 2)).thenReturn(new ResponseApiDTO(users, null));

		mockMvc.perform(get("/api/v1/personas/{pageNo}/{pageSize}", 0, 2))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));

		verify(personaService, times(1)).listAll(0, 2);
		verifyNoMoreInteractions(personaService);

	}

	@Test
	@DisplayName("Creación de un registro de persona.")
	@Order(1)
	void create() throws Exception {
        Persona personaRes = new Persona(1, "Luis Alberto", "Olivares Peña", 30, Sexo.MASCULINO, TipoDocumento.CEDULA, "20390708");

        PersonaRequest request = new PersonaRequest();
        request.setNombres("Luis Alberto");
        request.setApellidos("Olivares Peña");
        request.setSexo(Sexo.MASCULINO);
        request.setTipoDocumento(TipoDocumento.CEDULA);
        request.setDocumento("20390708");
        request.setEdad(30);

		when(personaService.create(any(PersonaRequest.class))).thenReturn(new ResponseApiDTO(personaRes, null));

		mockMvc.perform(post("/api/v1/personas/")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.documento").value("20390708"));
	}


	@Test
	@DisplayName("Modificación de un registro de persona.")
	@Order(3)
	void update() throws Exception {

		Persona personaRes = new Persona(1, "Luis Alberto", "Olivares Peña", 30, Sexo.MASCULINO, TipoDocumento.CEDULA, "20390708");

		PersonaRequest request = new PersonaRequest();
		request.setNombres("Luis Oswaldo");
		request.setApellidos("Olivares Peña");
		request.setSexo(Sexo.MASCULINO);
		request.setTipoDocumento(TipoDocumento.CEDULA);
		request.setDocumento("20390708");
		request.setEdad(30);

		mockMvc.perform(
				put("/api/v1/personas/{tipoDocumento}/{documento}", TipoDocumento.CEDULA, "18390608")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("Eliminación de un registro de persona.")
	@Order(4)
	void delete() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/personas/{tipoDocumento}/{documento}", TipoDocumento.CEDULA, "18390608" ))
				.andExpect(status().isNoContent());

	}

}