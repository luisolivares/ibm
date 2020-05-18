package com.ibm.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ibm.models.entity.Persona;

@Repository
public interface PersonaDAO extends JpaRepository<Persona, Integer>{

}
