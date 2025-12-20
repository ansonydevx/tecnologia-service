package com.onclass.tecnologia.infrastructure.adapters.persistence;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("tecnologias")
@Getter
@Setter
public class TecnologiaEntity {

    @Id
    private Long id;
    private String nombre;
    private String descripcion;
}
