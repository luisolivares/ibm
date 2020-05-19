package com.ibm.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.ibm.models.entity.Persona;
import com.ibm.service.PersonaService;

@RestController
public class PersonaController {

	private static final Logger logger = LoggerFactory.getLogger(PersonaController.class);

	@Autowired
	private PersonaService personaService;

	private HttpHeaders headers = new HttpHeaders();

	@GetMapping(value = "/personas/{pageNo}/{pageSize}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<Persona>> listAllPaginate(@PathVariable("pageNo") Integer pageNo,
			@PathVariable("pageSize") Integer pageSize) {
		List<Persona> listAll = new ArrayList<Persona>();

		headers.add("Content-Type", "application/json");
		headers.add("Responded", "PersonaController");

		try {
			listAll = personaService.listAll(pageNo, pageSize);

			if (listAll.size() > 0) {
				return ResponseEntity.ok().headers(headers).body(listAll);

			} else {
				return ResponseEntity.notFound().headers(headers).build();
			}

		} catch (HttpClientErrorException e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			logger.error(e.getResponseBodyAsString(), e);
			return ResponseEntity.badRequest().headers(headers).build();
		}

	}

	@PostMapping(value = "/personas", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Persona> create(@RequestBody Persona persona) {

		headers.add("Content-Type", "application/json");
		headers.add("Responded", "PersonaController");

		try {
			Persona person = new Persona();
			person.setNombres(persona.getNombres());
			person.setApellidos(persona.getApellidos());
			person.setEdad(persona.getEdad());
			person.setSexo(persona.getSexo());
			person = personaService.create(persona);
			return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(person);
		} catch (HttpClientErrorException e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			logger.error(e.getResponseBodyAsString(), e);
			return ResponseEntity.badRequest().headers(headers).build();
		}

	}

	
	@PutMapping(value = "/personas", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Persona> update(@RequestBody Persona persona) {

		headers.add("Content-Type", "application/json");
		headers.add("Responded", "PersonaController");

		Persona person = new Persona();

		try {
			person = personaService.update(persona);
			return ResponseEntity.ok().headers(headers).body(person);
		} catch (HttpClientErrorException e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			logger.error(e.getResponseBodyAsString(), e);
			return ResponseEntity.badRequest().headers(headers).build();
		}

	}

	@DeleteMapping(value = "/personas/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id) {

		headers.add("Content-Type", "application/json");
		headers.add("Responded", "PersonaController");

		Boolean isDelete = false;

		try {
			isDelete = personaService.delete(id);
			return ResponseEntity.ok().headers(headers).body(isDelete);
		} catch (HttpClientErrorException e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			logger.error(e.getResponseBodyAsString(), e);
			return ResponseEntity.badRequest().headers(headers).build();
		}

	}


}
