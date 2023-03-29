package com.ibm.domain.person.controller;

import java.util.List;

import com.ibm.domain.person.model.enumerated.TipoDocumento;
import com.ibm.domain.person.model.request.PersonaRequest;
import com.ibm.domain.person.service.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ibm.domain.person.model.entity.Persona;

@Slf4j
@RestController()
@RequestMapping(value = "/api/v1/personas")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PersonController {

	private final PersonService personaService;

	@GetMapping(value = "/{pageNo}/{pageSize}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Persona>> listAllPaginate(@PathVariable(name = "pageNo", required = true) Integer pageNo,
			@PathVariable(name="pageSize", required = true) Integer pageSize) {
		return new ResponseEntity<>(this.personaService.listAll(pageNo, pageSize), HttpStatus.OK);
	}

	@PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Persona> create(@Valid @RequestBody PersonaRequest persona) {
		return ResponseEntity.status(HttpStatus.CREATED).body(personaService.create(persona));
	}

	@PutMapping(value = "/{tipoDocumento}/{documento}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Persona> update(@Valid @RequestBody PersonaRequest request, @PathVariable("tipoDocumento") TipoDocumento tipoDocumento, @PathVariable("documento") String documento) {
		return ResponseEntity.status(HttpStatus.OK).body(personaService.update(request, tipoDocumento, documento));
	}

	@DeleteMapping(value = "/{tipoDocumento}/{documento}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> delete(@PathVariable("tipoDocumento") TipoDocumento tipoDocumento, @PathVariable("documento") String documento) {
		personaService.delete(tipoDocumento, documento);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
