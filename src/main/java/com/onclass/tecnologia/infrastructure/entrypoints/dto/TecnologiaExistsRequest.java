package com.onclass.tecnologia.infrastructure.entrypoints.dto;

import java.util.List;

public record TecnologiaExistsRequest(
        List<Long> ids
) { }
