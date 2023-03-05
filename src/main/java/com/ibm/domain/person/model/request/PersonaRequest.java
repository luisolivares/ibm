package com.ibm.domain.person.model.request;

import com.ibm.domain.person.model.entity.Sexo;
import com.ibm.domain.person.model.entity.TipoDocumento;
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

    @NotBlank(message = "El campo nombre es obligatorio")
    @NotNull(message = "El campo nombre es obligatorio")
    private int edad;

    @NotBlank(message = "El campo sexo es obligatorio")
    @NotNull(message = "El campo sexo es obligatorio")
    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    @NotBlank(message = "El campo tipo de documento es obligatorio")
    @NotNull(message = "El campo tipo de documento es obligatorio")
    @Enumerated(EnumType.STRING)
    private TipoDocumento tipoDocumento;

    @NotBlank(message = "El campo documento es obligatorio")
    @NotNull(message = "El campo documento es obligatorio")
    private String documento;


}