package com.ibm.domain.person.service;

import java.util.List;

import com.ibm.domain.person.model.entity.Persona;
import com.ibm.domain.person.model.entity.TipoDocumento;
import com.ibm.domain.person.model.request.PersonaRequest;

public interface PersonService {

	/**
	 * Método donde listamos a las personas y realizamos una páginación de la misma.
	 * 
	 * @param pageSize
	 *            Número de paginación.
	 */
	public List<Persona> listAll(Integer pageNo, Integer pageSize);

	/**
	 * Método donde se crea el recurso Persona.
	 * 
	 * @param request
	 *            Objecto persona donde contiene los datos para ser creados.
	 **/
	public Persona create(PersonaRequest request);

	/**
	 * Método donde se actualiza el recurso Persona.
	 * 
	 * @param persona
	 *            Objecto que se utiliza para actualizar a la persona en cuestión.
	 **/
	public Persona update(PersonaRequest request, TipoDocumento tipoDocumento, String documento);

	/**
	 * Método donde se busca por id o por identificador unico a la persona en
	 * cuestión.
	 * 
	 * @param id
	 *            Id o identificador unico de la persona en cuestión.
	 **/
	public Persona findByDocumento(TipoDocumento tipoDocumento, String documento);

	/**
	 * Método donde se elimina el recurso Persona.
	 * 
	 * @param id
	 *            Id o identificador unico del objecto a ser eliminado.
	 **/
	public void delete(TipoDocumento tipoDocumento, String documento);

}
