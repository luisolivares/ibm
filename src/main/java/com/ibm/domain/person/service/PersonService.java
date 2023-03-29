package com.ibm.domain.person.service;

import java.util.List;

import com.ibm.domain.person.model.entity.Persona;
import com.ibm.domain.person.model.enumerated.TipoDocumento;
import com.ibm.domain.person.model.request.PersonaRequest;

public interface PersonService {

	List<Persona> listAll(Integer pageNo, Integer pageSize);

	Persona create(PersonaRequest request);

	Persona update(PersonaRequest request, TipoDocumento tipoDocumento, String documento);

	Persona findByDocumento(TipoDocumento tipoDocumento, String documento);

	void delete(TipoDocumento tipoDocumento, String documento);

}
