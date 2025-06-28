package org.fatec.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Consulta {
    private String id;

    @DBRef
    private Animal pet;

    @DBRef
    private Veterinario veterinario;
    private String data;
    private String diagnostico;
    private String medicamento;
}
