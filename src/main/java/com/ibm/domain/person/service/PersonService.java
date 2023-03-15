package com.ibm.domain.person.service;

import java.util.List;

import com.ibm.domain.person.model.entity.Persona;
import com.ibm.domain.person.model.enumerated.TipoDocumento;
import com.ibm.domain.person.model.request.PersonaRequest;

public interface PersonService {

	public List<Persona> listAll(Integer pageNo, Integer pageSize);

	public Persona create(PersonaRequest request);

	public Persona update(PersonaRequest request, TipoDocumento tipoDocumento, String documento);

	public Persona findByDocumento(TipoDocumento tipoDocumento, String documento);

	public void delete(TipoDocumento tipoDocumento, String documento);

}
