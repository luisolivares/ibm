package com.ibm.domain.person.model.response;

import com.ibm.domain.person.model.enumerated.Sexo;
import com.ibm.domain.person.model.enumerated.TipoDocumento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonaResponse {

    private String nombres;

    private String apellidos;

    private int edad;

    private Sexo sexo;

    private TipoDocumento tipoDocumento;

    private String documento;
}
