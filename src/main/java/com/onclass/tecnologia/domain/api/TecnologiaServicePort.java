package com.onclass.tecnologia.domain.api;

import com.onclass.tecnologia.domain.model.Tecnologia;
import com.onclass.tecnologia.infrastructure.entrypoints.dto.TecnologiaResumenDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface TecnologiaServicePort {
    
    Mono<Tecnologia> registrar(Tecnologia tecnologia);
    Mono<Boolean> existenPorIds(List<Long> ids);
    Flux<TecnologiaResumenDTO> obtenerPorIds(List<Long> ids);

    Mono<Void> eliminarPorIds(List<Long> ids);
}
