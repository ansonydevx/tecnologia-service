package com.onclass.tecnologia.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TechnicalMessage {

    TECNOLOGIA_CREADA("201", "Tecnología registrada correctamente", ""),
    TECNOLOGIA_DUPLICADA("400", "La tecnología ya existe", "nombre"),
    NOMBRE_INVALIDO("400", "Nombre inválido", "nombre"),
    DESCRIPCION_INVALIDA("400", "Descripción inválida", "descripcion"),
    INTERNAL_ERROR("500", "Error interno", "");

    private final String code;
    private final String message;
    private final String param;
}
