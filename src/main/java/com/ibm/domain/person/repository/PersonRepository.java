package com.ibm.domain.person.repository;

import com.ibm.domain.person.model.entity.Persona;
import com.ibm.domain.person.model.enumerated.TipoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("personRepository")
public interface PersonRepository extends JpaRepository<Persona, Integer> {

    @Query(value = "SELECT p FROM Persona p WHERE p.tipoDocumento = :tipoDocumento AND p.documento = :documento ")
    Optional<Persona> findByDocumento(@Param("tipoDocumento") TipoDocumento tipoDocumento, @Param("documento") String documento);

    @Modifying
    @Query(value = "DELETE FROM Persona p WHERE p.tipoDocumento = :tipoDocumento AND p.documento = :documento  ")
    void deleteByDocumento(@Param("tipoDocumento") TipoDocumento tipoDocumento, @Param("documento") String documento);

}
