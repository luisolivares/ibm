package com.ibm.domain.person.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibm.domain.person.model.enumerated.Sexo;
import com.ibm.domain.person.model.enumerated.TipoDocumento;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonaRequest {

    @NotBlank(message = "El campo nombres es obligatorio")
    @NotNull(message = "El campo nombres es obligatorio")
    private String nombres;

    @NotBlank(message = "El campo apellidos es obligatorio")
    @NotNull(message = "El campo apellidos es obligatorio")
    private String apellidos;

    @NotNull(message = "El campo nombre es obligatorio")
    private int edad;

    @NotNull(message = "El campo sexo es obligatorio")
    @JsonProperty(value = "sexo")
    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    @NotNull(message = "El campo tipo de documento es obligatorio")
    @JsonProperty(value = "tipoDocumento")
    @Enumerated(EnumType.STRING)
    private TipoDocumento tipoDocumento;

    @NotBlank(message = "El campo documento es obligatorio")
    @NotNull(message = "El campo documento es obligatorio")
    private String documento;

}