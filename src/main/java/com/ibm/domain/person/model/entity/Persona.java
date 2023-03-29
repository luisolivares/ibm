package com.ibm.domain.person.model.entity;

import com.ibm.domain.person.model.enumerated.Sexo;
import com.ibm.domain.person.model.enumerated.TipoDocumento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Persona")
@Table(name = "persona")
public class Persona implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Integer id;

	@Column(name = "nombres")
	private String nombres;

	@Column(name = "apellidos")
	private String apellidos;

	@Column(name = "edad")
	private Integer edad;

	@Column(name = "sexo")
	@Enumerated(EnumType.STRING)
	private Sexo sexo;

	@Column(name = "tipo_documento")
	@Enumerated(EnumType.STRING)
	private TipoDocumento tipoDocumento;

	@Column(name = "documento", unique = true)
	private String documento;
}