package com.ibm.domain.person.service;

import java.util.List;

import com.ibm.domain.person.model.entity.Persona;
import com.ibm.domain.person.model.enumerated.TipoDocumento;
import com.ibm.domain.person.model.request.PersonaRequest;
import com.ibm.domain.person.model.response.ResponseApiDTO;

public interface PersonService {

	ResponseApiDTO<List<Persona>> listAll(Integer pageNo, Integer pageSize);

	ResponseApiDTO<Persona> create(PersonaRequest request);

	ResponseApiDTO<Persona> update(PersonaRequest request, TipoDocumento tipoDocumento, String documento);

	ResponseApiDTO<Persona> findByDocumento(TipoDocumento tipoDocumento, String documento);

	void delete(TipoDocumento tipoDocumento, String documento);

}
