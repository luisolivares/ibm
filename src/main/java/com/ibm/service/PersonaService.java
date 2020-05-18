package com.ibm.service;

import java.util.List;

import com.ibm.models.entity.Persona;

public interface PersonaService {

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
	 * @param persona
	 *            Objecto persona donde contiene los datos para ser creados.
	 **/
	public Persona create(Persona persona);

	/**
	 * Método donde se actualiza el recurso Persona.
	 * 
	 * @param persona
	 *            Objecto que se utiliza para actualizar a la persona en cuestión.
	 **/
	public Persona update(Persona persona);

	/**
	 * Método donde se busca por id o por identificador unico a la persona en
	 * cuestión.
	 * 
	 * @param id
	 *            Id o identificador unico de la persona en cuestión.
	 **/
	public Persona findById(Integer id);

	/**
	 * Método donde se elimina el recurso Persona.
	 * 
	 * @param id
	 *            Id o identificador unico del objecto a ser eliminado.
	 **/
	public boolean delete(Integer id);

}
