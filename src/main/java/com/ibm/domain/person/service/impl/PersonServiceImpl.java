package com.ibm.domain.person.service.impl;

import com.ibm.domain.person.model.entity.Persona;
import com.ibm.domain.person.model.enumerated.TipoDocumento;
import com.ibm.domain.person.model.request.PersonaRequest;
import com.ibm.domain.person.model.response.ResponseApiDTO;
import com.ibm.domain.person.repository.PersonRepository;
import com.ibm.domain.person.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service("personServiceImpl")
public class PersonServiceImpl implements PersonService {

    @Qualifier("personRepository")
    private final PersonRepository personRepository;

    @Transactional(readOnly = true)
    @Override
    public ResponseApiDTO<List<Persona>> listAll(Integer pageNo, Integer pageSize) {
        String sortBy = "documento";
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Persona> pagedResult = personRepository.findAll(paging);
        if (pagedResult.hasContent()) {
            return new ResponseApiDTO(pagedResult.getContent(), null);
        } else {
            return new ResponseApiDTO(new ArrayList<>(), null);
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    @Override
    public ResponseApiDTO<Persona> create(PersonaRequest request) {
        try {
            Optional<Persona> persona = personRepository.findByDocumento(request.getTipoDocumento(), request.getDocumento());
            if (persona.isEmpty()) {
                Persona person = new Persona();
                person.setNombres(request.getNombres());
                person.setApellidos(request.getApellidos());
                person.setEdad(request.getEdad());
                person.setSexo(request.getSexo());
                person.setTipoDocumento(request.getTipoDocumento());
                person.setDocumento(request.getDocumento());
                return new ResponseApiDTO(personRepository.save(person), null);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Ya existe una persona cuyo documento es %s %s", request.getTipoDocumento(), request.getDocumento()));
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear una persona", e);
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    @Override
    public ResponseApiDTO<Persona> update(PersonaRequest request, TipoDocumento tipoDocumento, String documento) {
        Optional<Persona> persona = personRepository.findByDocumento(tipoDocumento, documento);
        if (persona.isPresent()) {
            persona.get().setNombres(request.getNombres());
            persona.get().setApellidos(request.getApellidos());
            persona.get().setEdad(request.getEdad());
            persona.get().setSexo(request.getSexo());
            persona.get().setTipoDocumento(request.getTipoDocumento());
            persona.get().setDocumento(request.getDocumento());
            return new ResponseApiDTO(personRepository.saveAndFlush(persona.get()), null);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error al modificar la persona");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public ResponseApiDTO<Persona> findByDocumento(TipoDocumento tipoDocumento, String documento) {
        Optional<Persona> persona = personRepository.findByDocumento(tipoDocumento, documento);
        if (persona.isPresent()) {
            return new ResponseApiDTO(persona.get(), null);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error al modificar la persona cuyo documento es " + documento);
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    @Override
    public void delete(TipoDocumento tipoDocumento, String documento) {
        Optional<Persona> persona = personRepository.findByDocumento(tipoDocumento, documento);
        if (persona.isPresent()) {
            personRepository.deleteByDocumento(tipoDocumento, documento);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error al modificar la persona cuyo documento es " + documento);
        }
    }

}