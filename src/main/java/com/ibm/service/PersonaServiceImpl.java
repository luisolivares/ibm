package com.ibm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ibm.models.dao.PersonaDAO;
import com.ibm.models.entity.Persona;

@Service
public class PersonaServiceImpl implements PersonaService {

	private static final Logger logger = LoggerFactory.getLogger(PersonaServiceImpl.class);

	@Autowired
	private PersonaDAO personaDAO;

	@Override
	public List<Persona> listAll(Integer pageNo, Integer pageSize) {
		// TODO Auto-generated method stub

		String sortBy = "id";

		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

		Page<Persona> pagedResult = personaDAO.findAll(paging);

		if (pagedResult.hasContent()) {
			return pagedResult.getContent();
		} else {
			return new ArrayList<Persona>();
		}
	}

	@Override
	public Persona create(Persona persona) {
		// TODO Auto-generated method stub

		try {
			persona = personaDAO.save(persona);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("ERROR " + e.getMessage(), e);
			persona = null;
		}

		return persona;
	}

	@Override
	public Persona update(Persona persona) {
		// TODO Auto-generated method stub

		try {
			Optional<Persona> person = personaDAO.findById(persona.getId());

			if (person.isPresent()) {
				persona.setId(persona.getId());
				persona.setNombres(persona.getNombres());
				persona.setApellidos(persona.getApellidos());
				persona.setEdad(persona.getEdad());
				persona.setSexo(persona.getSexo());
				persona = personaDAO.save(persona);
			} else {
				persona = null;
			}

		} catch (Exception e) {
			// TODO: handle exception
			persona = null;
			logger.error(e.getMessage(), e);
		}

		return persona;
	}

	@Override
	public boolean delete(Integer id) {
		// TODO Auto-generated method stub
		boolean isDelete;

		try {
			personaDAO.deleteById(id);
			isDelete = true;
		} catch (Exception e) {
			// TODO: handle exception
			isDelete = false;
			logger.error("ERROR " + e.getMessage(), e);
		}

		return isDelete;
	}

	@Override
	public Persona findById(Integer id) {
		// TODO Auto-generated method stub
		
		Persona persona = new Persona();
		
		try {
			Optional<Persona> person = personaDAO.findById(id);

			if (person.isPresent()) {

				persona = person.get();
			} else {
				persona = null;
			}

		} catch (Exception e) {
			// TODO: handle exception

			logger.error(e.getMessage(), e);
		}

		return persona;
	}

}
